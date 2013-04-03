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

		final WebServiceResponse<T> response = wrapResponse(json);

		final Iterator<String> hg = json.keys();
		while (hg.hasNext()) {

			String key = hg.next();
			System.out.println(key + " " + json.get(key) + " "
					+ json.get(key).getClass());

		}

		if (response.isSuccess()) {

			final T bean = clazz.newInstance();

			write(json, bean);
			response.setBean(bean);
		}

		return response;
	}

	private WebServiceResponse<T> wrapResponse(JSONObject json) {
		final Integer result = (Integer) json.remove("result");
		final String response = (String) json.remove("response");

		final WebServiceResponse<T> wsResponse = new WebServiceResponse<T>();
		wsResponse.setError(response);
		wsResponse.setSuccess(result == 1);

		return wsResponse;
	}

	public void write(JSONObject json, Object parent) throws JSONException,
			NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException {

		final Iterator<String> keysIt = json.keys();
		while (keysIt.hasNext()) {
			String key = keysIt.next();

			key = toCamelCase(key);

			Object v = json.get(key);

			final String readProperty = "get" + key;
			final String writeProperty = "set" + key;

			final Method readMethod = parent.getClass().getDeclaredMethod(
					readProperty, (Class<?>) null);
			final Class<?> type = readMethod.getReturnType();

			final Method writeMethod = parent.getClass().getDeclaredMethod(
					writeProperty, v.getClass());

			Object child = null;
			if (v instanceof JSONObject) {
				child = type.newInstance();
				write((JSONObject) v, child);
				writeMethod.invoke(parent, child);
			} else {
				writeMethod.invoke(parent, v);
			}
		}
	}

	private String toCamelCase(String key) {
		final StringBuilder camelCased = new StringBuilder();

		boolean upperCase = true;
		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);

			if (c == '_') {
				upperCase = true;
				continue;
			}

			if (upperCase) {
				c = Character.toUpperCase(c);
				upperCase = false;
			}

			camelCased.append(c);
		}
		return camelCased.toString();
	}
}
