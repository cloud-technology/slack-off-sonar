package com.github.ct.component;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.lang.reflect.Type;
import org.springframework.cloud.function.cloudevent.CloudEventMessageUtils;
import org.springframework.cloud.function.json.JsonMapper;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

import lombok.extern.slf4j.Slf4j;

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
