package org.baoting.te.utils;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
 * 
 * @ClassName: ConfigException.java
 * @Description:
 * @author YinChang-bao
 * @date Nov 16, 2015
 *
 */
public class JsonHelper {

	private static final Log logger = LogFactory.getLog(JsonHelper.class);

	public static String bean2Json(Object bean) {
		return bean2Json(bean, true);
	}

	public static String bean2Json(Object bean, boolean displayEmpty) {
		try {
			ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);

			if (displayEmpty)
				mapper.setSerializationInclusion(Inclusion.NON_EMPTY);
			mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			StringWriter sw = new StringWriter();
			JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
			mapper.writeValue(gen, bean);
			gen.close();
			return sw.toString();
		} catch (IOException e) {
			logger.error("bean2Json error" + e.getMessage(), e);
		}
		return null;

	}

	public static <T> T json2Bean(String jsonStr, Class<T> target, boolean parseEmpty) {

		try {
			ObjectMapper mapper = new ObjectMapper().setVisibility(JsonMethod.FIELD, Visibility.ANY);

			if (parseEmpty)
				mapper.setSerializationInclusion(Inclusion.NON_EMPTY);
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);

			T value = mapper.readValue(jsonStr, target);
			return value;
		} catch (Exception e) {
			logger.error("json2Bean error" + e.getMessage(), e);
		}
		return null;

	}

	public static <T> T json2Bean(String jsonStr, Class<T> target) {

		return json2Bean(jsonStr, target, true);

	}
}
