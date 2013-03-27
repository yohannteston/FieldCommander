package fr.apa.fieldcommander.webservice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;


public class JSON2JavaBeanWrapper<T> {

	public WebServiceResponse<T> wrapResponse(JSONObject json, Class<T> clazz)
			throws NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, JSONException,
			InstantiationException {

		final T bean = clazz.newInstance();

		final WebServiceResponse<T> response = new WebServiceResponse<T>();

		boolean result = (Boolean) json.get("result");
		response.setSuccess(result);

		if (result) {
			final Iterator<String> keysIt = json.keys();
			while (keysIt.hasNext()) {
				String key = keysIt.next();

				if ("result".equals(key) || "error".equals(key))
					continue;

				final String writeProperty = "set" + key;

				final Method writeMethod = bean.getClass().getDeclaredMethod(
						writeProperty, String.class);
				writeMethod.invoke(bean, json.get(key));
			}
			response.setBean(bean);
		} else {
			response.setError((String) json.get("error"));
		}

		return response;
	}
}
