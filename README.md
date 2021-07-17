## This Project Architecture
This project is to make it easier for people to read SonarQube analysis results in GitLab, so the design is as follows.  
  
![SystemArchitecture](https://i.imgur.com/RSXtCdm.jpg)

## Spring Cloud Function

### Why use Spring Cloud Function
Why choose Spring cloud function to develop serverless services?  

Spring framework still provides abstraction capabilities to decouple our serverless services from the platform. If the developed services need to be moved to different cloud providers, you almost only need to choose a different adapter without changing the writing and settings. The biggest benefits of decoupling from the different cloud platforms.  

Currently Spring cloud function provides AWS Adapter, Azure Adapter and GCP Adapter.

### Start create project
Download the project template from [https://start.spring.io/](https://start.spring.io/#!type=maven-project&language=java&platformVersion=2.5.2.RELEASE&packaging=jar&jvmVersion=11&groupId=com.github.cloud-technology&artifactId=slack-off-sonar&name=slack-off-sonar&description=Easily%20view%20sonar%20report%20on%20gitlab&packageName=com.github.ct&dependencies=lombok,cloud-feign)

### Update dependency
1. Add [Spring Cloud Function Adapter GCP](https://search.maven.org/artifact/org.springframework.cloud/spring-cloud-function-adapter-gcp)
2. Add [Spring Web](https://search.maven.org/artifact/org.springframework/spring-web)

### Update test dependency
1. Add [GCF Java Invoker](https://search.maven.org/artifact/com.google.cloud.functions.invoker/java-function-invoker)

### Update build plugin
1. Update spring-boot-maven-plugin configuration add outputDirectory=target/deploy
2. Update spring-boot-maven-plugin add dependency [Spring Cloud Function Adapter GCP](https://search.maven.org/artifact/org.springframework.cloud/spring-cloud-function-adapter-gcp)
3. Add plugin [Functions Framework Plugin](https://search.maven.org/artifact/com.google.cloud.functions/function-maven-plugin)

### Create MANIFEST.MF
``` bash
mkdir -p src/main/resources/META-INF
```

Main-Class is your springboot main program
``` bash
cat << 'EOF' > src/main/resources/META-INF/MANIFEST.MF
Main-Class: com.github.ct.SlackOffSonarApplication
EOF
```

### Register Function Bean
``` java
@Bean
public Function<String, String> uppercase() {
	return value -> value.toUpperCase();
}
```

### Test in your local environments
``` bash
./mvnw function:run
```

``` bash
curl -H "Content-Type: text/plain" localhost:8080/uppercase -d Hello
```
(Example output)
```
HELLO
```

### Package to jar
``` bash
./mvnw clean package
```

Get the Jar file to be deployed in target/deploy/slack-off-sonar-0.0.1.jar 

### Customized JsonMessageConverter
Because the default is to use Gson and it is not very convenient, we can customize it or change it to our customary JackSon.  
  
[Note on JSON options](https://docs.spring.io/spring-cloud-function/docs/3.1.3/reference/html/spring-cloud-function.html#_note_on_json_options)  
In this page that you can use spring.cloud.function.preferred-json-mapper=jackson to adjust, but I tested it and it didn't change, so I tried to use Java Bean to replicate the configuration.
``` java
/**
 * reference
 * https://github.com/spring-cloud/spring-cloud-function/blob/main/spring-cloud-function-context/src/main/java/org/springframework/cloud/function/context/config/ContextFunctionCatalogAutoConfiguration.java
 * https://github.com/spring-cloud/spring-cloud-function/blob/main/spring-cloud-function-context/src/main/java/org/springframework/cloud/function/context/config/JsonMessageConverter.java
 */
@Slf4j
public class MyJsonMessageConverter extends AbstractMessageConverter {

    private final JsonMapper jsonMapper;

    public MyJsonMessageConverter(JsonMapper jsonMapper) {
        this(jsonMapper, new MimeType("application", "json"),
                new MimeType(CloudEventMessageUtils.APPLICATION_CLOUDEVENTS.getType(),
                        CloudEventMessageUtils.APPLICATION_CLOUDEVENTS.getSubtype() + "+json"));
    }
    public MyJsonMessageConverter(JsonMapper jsonMapper, MimeType... supportedMimeTypes) {
		super(supportedMimeTypes);
		this.jsonMapper = jsonMapper;
	}

    @Override
	protected boolean supports(Class<?> clazz) {
		// should not be called, since we override canConvertFrom/canConvertTo instead
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean canConvertTo(Object payload, @Nullable MessageHeaders headers) {
		if (!supportsMimeType(headers)) {
			return false;
		}
		return true;
	}

    @Override
	protected boolean canConvertFrom(Message<?> message, @Nullable Class<?> targetClass) {
		if (targetClass == null || !supportsMimeType(message.getHeaders())) {
			return false;
		}
		return true;
	}

    @Override
	protected Object convertFromInternal(Message<?> message, Class<?> targetClass, @Nullable Object conversionHint) {
		log.info("message={}, targetClass={}, conversionHint={}", message, targetClass, conversionHint);
        if (targetClass.isInstance(message.getPayload()) && !(message.getPayload() instanceof Collection<?>)) {
			return message.getPayload();
		}
		Type convertToType = conversionHint == null ? targetClass : (Type) conversionHint;
		if (targetClass == byte[].class && message.getPayload() instanceof String) {
			return ((String) message.getPayload()).getBytes(StandardCharsets.UTF_8);
		}
		else {
			try {
				return this.jsonMapper.fromJson(message.getPayload(), convertToType);
			}
			catch (Exception e) {
				if (message.getPayload() instanceof byte[] && targetClass.isAssignableFrom(String.class)) {
					return new String((byte[]) message.getPayload(), StandardCharsets.UTF_8);
				}
			}
		}

		return null;
	}

	@Override
	protected Object convertToInternal(Object payload, @Nullable MessageHeaders headers,
			@Nullable Object conversionHint) {
		return jsonMapper.toJson(payload);
	}
}
```
Than Register MyJsonMessageConverter Bean
``` java
@Bean
public MyJsonMessageConverter customMessageConverter() {
	return new MyJsonMessageConverter(new JacksonMapper(new ObjectMapper()));
}
```

## Deploy to Google Cloud Function

### In cloud shell
Enable
``` bash
gcloud services list --available

gcloud services enable cloudfunctions.googleapis.com
gcloud services enable cloudbuild.googleapis.com
gcloud services enable cloudresourcemanager.googleapis.com
```
(Example output)
``` bash
Operation "operations/acf.p2-288759886290-b5aed878-b12d-4de3-a0f1-351771b95812" finished successfully.
```
  
[Create Service Account](https://cloud.google.com/sdk/gcloud/reference/iam/service-accounts/create)
SYNOPSIS
``` bash
gcloud iam service-accounts create SERVICE_ACCOUNT_ID \
    --description="DESCRIPTION" \
    --display-name="DISPLAY_NAME"
```
EXAMPLES
``` bash
export SERVICE_ACCOUNT_ID=sam-cloud-deploy

gcloud iam service-accounts create ${SERVICE_ACCOUNT_ID} \
    --description="customize-cloud-function-deploy-sa on github" \
    --display-name="customize-cloud-function-deploy-sa"
```
(Example output)
```
Created service account [sam-cloud-deploy].
```
  
[Add policy binding to service account](https://cloud.google.com/sdk/gcloud/reference/projects/add-iam-policy-binding)
SYNOPSIS
``` bash
gcloud projects add-iam-policy-binding PROJECT_ID \
    --member="serviceAccount:SERVICE_ACCOUNT_ID@PROJECT_ID.iam.gserviceaccount.com" \
    --role="ROLE_NAME"
```
EXAMPLES
``` bash
export PROJECT_ID=cloudfunction-305305
export SERVICE_ACCOUNT_ID=sam-cloud-deploy

gcloud projects add-iam-policy-binding ${PROJECT_ID} \
    --member="serviceAccount:${SERVICE_ACCOUNT_ID}@${PROJECT_ID}.iam.gserviceaccount.com" \
    --role="roles/cloudfunctions.admin"

gcloud projects add-iam-policy-binding ${PROJECT_ID} \
    --member="serviceAccount:${SERVICE_ACCOUNT_ID}@${PROJECT_ID}.iam.gserviceaccount.com" \
    --role="roles/iam.serviceAccountUser"
```
(Example output)
``` bash
Updated IAM policy for project [cloudfunction-305305].
bindings:
- members:
  - serviceAccount:288759886290@cloudbuild.gserviceaccount.com
  role: roles/cloudbuild.builds.builder
.
.
.
etag: BwXHGHgC9CI=
version: 1
```

[Creating service account keys](https://cloud.google.com/iam/docs/creating-managing-service-account-keys)  
SYNOPSIS
``` bash
gcloud iam service-accounts keys create key-file \
    --iam-account=sa-name@project-id.iam.gserviceaccount.com
```
EXAMPLES
``` bash
export PROJECT_ID=cloudfunction-305305
export SERVICE_ACCOUNT_ID=sam-cloud-deploy
export KEY_FILE=./deploy.json

gcloud iam service-accounts keys create ${KEY_FILE} \
    --iam-account=${SERVICE_ACCOUNT_ID}@${PROJECT_ID}.iam.gserviceaccount.com
```
(Example output)
``` bash
created key [3f5ba69de4e7ed604d3453e12bebf3d1d7787df1] of type [json] as [./deploy.json] for [sam-cloud-deploy@cloudfunction-305305.iam.gserviceaccount.com]
```
Then you can download deploy.json to use on your local machine.

### In local environments
Update and clear auth
``` bash
gcloud components update
gcloud auth revoke --all
```

Service account authorization
``` bash
export PROJECT_ID=cloudfunction-305305
export SERVICE_ACCOUNT_ID=sam-cloud-deploy
export KEY_FILE=./deploy.json

gcloud auth activate-service-account ${SERVICE_ACCOUNT_ID}@${PROJECT_ID}.iam.gserviceaccount.com --key-file=${KEY_FILE} --project=${PROJECT_ID}
```
(Example output)
```
Activated service account credentials for: [sam-cloud-deploy@cloudfunction-305305.iam.gserviceaccount.com]
```

Cloud function deploy
``` bash
export PROJECT_ID=cloudfunction-305305
export SERVICE_ACCOUNT_ID=sam-cloud-deploy
export REGION=asia-east1
export FUNCTION_NAME=function-sample-gcp-http
export GITLAB_TOKEN=12345678
export SONAR_TOKEN=12345678974651468487To=
export SONAR_URL=https://sonarqube.com.tw/api

gcloud functions deploy ${FUNCTION_NAME} \
--region=${REGION} \
--service-account=${SERVICE_ACCOUNT_ID}@${PROJECT_ID}.iam.gserviceaccount.com \
--entry-point org.springframework.cloud.function.adapter.gcp.GcfJarLauncher \
--allow-unauthenticated \
--runtime java11 \
--trigger-http \
--source target/deploy \
--memory 512MB \
--set-env-vars gitlabToken=${GITLAB_TOKEN},sonarToken=${SONAR_TOKEN},sonarUrl=${SONAR_URL}
```

### Custom roles
If you want to reduce the permissions, you can refer to [Creating and managing custom roles](https://cloud.google.com/iam/docs/creating-custom-roles) and [gcp/DeployRole.yml](https://github.com/cloud-technology/slack-off-sonar/blob/main/gcp/DeployRole.yml)  

SYNOPSIS
``` bash
gcloud iam roles create role-id --project=project-id \
  --file=yaml-file-path
```

EXAMPLES
``` bash
export PROJECT_ID=cloudfunction-305305
export YAML_FILE_PATH=gcp/DeployRole.yml
export ROLE_ID=SamCloudfunctionTest

gcloud iam roles create ${ROLE_ID} --project=${PROJECT_ID} \
  --file=${YAML_FILE_PATH}
```

(Example output)
``` bash
Created role [SamCloudfunctionTest].
description: custom role to deploy cloud function
etag: BwXHNs_mK1Q=
includedPermissions:
- cloudfunctions.functions.create
- cloudfunctions.functions.get
.
.
.
stage: GA
title: Deploy cloud function role
```

if you need update role
``` bash
gcloud iam roles update ${ROLE_ID} --project=${PROJECT_ID} \
  --file=${YAML_FILE_PATH}
```

remove roles/cloudfunctions.admin and add custom role
``` bash
export PROJECT_ID=cloudfunction-305305
export SERVICE_ACCOUNT_ID=sam-cloud-deploy

gcloud projects remove-iam-policy-binding ${PROJECT_ID} \
    --member="serviceAccount:${SERVICE_ACCOUNT_ID}@${PROJECT_ID}.iam.gserviceaccount.com" \
    --role="roles/cloudfunctions.admin"

gcloud projects add-iam-policy-binding ${PROJECT_ID} \
    --member="serviceAccount:${SERVICE_ACCOUNT_ID}@${PROJECT_ID}.iam.gserviceaccount.com" \
    --role="projects/${PROJECT_ID}/roles/${ROLE_ID}"
```

[Cloud Functions IAM Permissions](https://cloud.google.com/functions/docs/reference/iam/permissions)

## SonarQube
Setup webhook to Google Cloud Function
![Setup webhook](https://i.imgur.com/LdJLR38.png)

## GitLab CI
.gitlab-ci.yml
``` yml
stages:
  - analysis

sonar:
  extends: .job_sonarqube_template
  variables:
    SONAR_HOST_URL: "${SONAR_HOST_URL}"
    SONAR_TOKEN: "${SONAR_TOKEN}"

.job_sonarqube_template:
  stage: analysis
  image: gradle:7.1.1-jdk11-hotspot
  variables:
    SONAR_HOST_URL: ""
    SONAR_TOKEN: ""
  script:
    - |
      gradle sonarqube -Dsonar.qualitygate.wait=true \
      -Dsonar.analysis.gitPlatform="GitLab" \
      -Dsonar.analysis.projectID="${CI_PROJECT_ID}" \
      -Dsonar.analysis.commitTitle="${CI_COMMIT_TITLE}" \
      -Dsonar.analysis.commitSha="${CI_COMMIT_SHORT_SHA}" \
      -Dsonar.analysis.commitBranch="${CI_COMMIT_BRANCH}" \
      -Dsonar.analysis.mergeRequestIID="${CI_MERGE_REQUEST_IID}" \
      -Dsonar.analysis.commitTAG="${CI_COMMIT_TAG}"
```
In the analysis phase, the parameters at the beginning of sonar.analysis are added during the sonarqube analysis, and SonarQube will transmit these parameters to the Cloud Function during the webhook.

## Comment to commit on GitLab
![Comment to commit](https://i.imgur.com/R4yZZsh.png)

## Reference
[Spring Cloud Function Reference Documentation](https://docs.spring.io/spring-cloud-function/docs/3.1.3/reference/html/)  
[Event-Driven with Spring Cloud Function and Spring Cloud Stream](https://youtu.be/61aOCovpz5Y)  
[GoogleCloudPlatform/functions-framework-java](https://github.com/GoogleCloudPlatform/functions-framework-java)  
[GCP - 使用 Github Actions 部署 React 到 Cloud Run](https://dotblogs.com.tw/explooosion/2020/10/09/143330)  
[google-github-actions/setup-gcloud](https://github.com/google-github-actions/setup-gcloud)  
