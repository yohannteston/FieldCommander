package fr.apa.fieldcommander.webservice;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import fr.apa.fieldcommander.model.Team;
import fr.apa.fieldcommander.utils.AccountIDHolder;

public class WebServiceRequest<Parameter, Result> {

	// private static final String WS_URL =
	// "http://arnaudportfolio.free.fr/fieldcommander/";
	private static final String WS_URL = "http://127.0.0.1/fieldcommander/";
	private static Map<WebServiceId, WebServiceDescriptor<?, ?>> WS_DESCRIPTORS;

	static {
		WS_DESCRIPTORS = new HashMap<WebServiceId, WebServiceDescriptor<?, ?>>();

		final WebServiceDescriptor<String, Team> retrieveTeam = new WebServiceDescriptor<String, Team>(
				WebServiceId.RETRIEVE_TEAM, "wsTeam.php?Action=get",
				String.class, buildMap("TeamID", null), Team.class);

		;
		WS_DESCRIPTORS.put(WebServiceId.RETRIEVE_TEAM, retrieveTeam);
	}

	private static Map<String, String> buildMap(String... data) {

		final Map<String, String> result = new HashMap<String, String>();

		for (int i = 0; i < data.length; i++) {
			result.put(data[i++], data[i]);
		}
		return result;
	}

	public WebServiceAsyncTask<Result> perform(WebServiceId wsId,
			Parameter parameter, WebServiceCallBack<Result> callback)
			throws IllegalArgumentException, ClientProtocolException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, IOException, JSONException {

		final WebServiceDescriptor<Parameter, Result> descriptor = (WebServiceDescriptor<Parameter, Result>) WS_DESCRIPTORS
				.get(wsId);
		final String url = resolveUrl(descriptor, parameter);

		WebServiceAsyncTask<Result> task = new WebServiceAsyncTask<Result>(
				callback, descriptor.getResult());
		task.execute(url);

		return task;
	}

	private String resolveUrl(WebServiceDescriptor<Parameter, Result> wsDesc,
			Parameter bean) throws NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, ClientProtocolException, IOException {

		NameValuePair pairs[];
		int i = 0;
		pairs = new NameValuePair[wsDesc.getProperties().values().size() + 1];
		for (Entry<String, String> parameter : wsDesc.getProperties()
				.entrySet()) {

			String wsParameter = parameter.getKey();
			String propertyName = parameter.getValue();

			String parameterValue;

			if (propertyName != null) {
				final Method readMethod = bean.getClass().getDeclaredMethod(
						"get" + propertyName, null);
				parameterValue = readMethod.invoke(bean, null).toString();
			} else {
				parameterValue = bean.toString();
			}
			pairs[i++] = new BasicNameValuePair(wsParameter, parameterValue);
		}
		pairs[i] = new BasicNameValuePair("AccountID",
				String.valueOf(AccountIDHolder.getAccountID()));

		return buildUrl(pairs, wsDesc.getUrl());
	}

	private String buildUrl(NameValuePair pairs[], String specificUrl) {
		if (!specificUrl.contains("?")) {
			specificUrl += "?";
		} else {
			specificUrl += "&";
		}

		String paramString = URLEncodedUtils.format(Arrays.asList(pairs),
				"utf-8");

		specificUrl = WS_URL + specificUrl + paramString;
		System.out.println(specificUrl);
		return specificUrl;
	}
}
