package fr.apa.fieldcommander.webservice;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import fr.apa.fieldcommander.model.Team;
import fr.apa.fieldcommander.webservice.WebServiceDescriptor.WebServiceDescriptorKey;

public class WebService {

	private static final String WS_URL = "http://arnaudportfolio.free.fr/fieldcommander/";
	private static Map<WebServiceDescriptorKey, WebServiceDescriptor> WS_DESCRIPTORS;

	static {
		WS_DESCRIPTORS = new HashMap<WebServiceDescriptor.WebServiceDescriptorKey, WebServiceDescriptor>();

		final WebServiceDescriptorKey retrieveTeamKey = new WebServiceDescriptorKey(
				RequestType.RETRIEVE, String.class);
		final WebServiceDescriptor retrieveTeam = new WebServiceDescriptor(
				retrieveTeamKey, "wsTeam.php?Action=Get", null);

		final WebServiceDescriptorKey addTeamKey = new WebServiceDescriptorKey(
				RequestType.CREATE, Team.class);
		final WebServiceDescriptor addTeam = new WebServiceDescriptor(
				retrieveTeamKey, "wsTeam.php", null);

		WS_DESCRIPTORS.put(addTeamKey, addTeam);
		WS_DESCRIPTORS.put(retrieveTeamKey, retrieveTeam);
	}

	public static <T> WebServiceAsyncTask request(RequestType requestType,
			T parameter, JSONCallBack<String> callback)
			throws IllegalArgumentException, ClientProtocolException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, IOException, JSONException {
		final String url = resolveUrl(requestType, parameter);

		WebServiceAsyncTask task = new WebServiceAsyncTask(callback);
		task.execute(url);

		return task;
	}

	private static <T> String resolveUrl(RequestType requestType, T bean)
			throws NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			ClientProtocolException, IOException {
		WebServiceDescriptor wsDesc = WS_DESCRIPTORS
				.get(new WebServiceDescriptorKey(requestType, bean.getClass()));

		NameValuePair pairs[];
		int i = 0;
		pairs = new NameValuePair[wsDesc.getProperties().size()];
		for (String key : wsDesc.getProperties()) {
			final Method readMethod = bean.getClass().getDeclaredMethod(
					"get" + key, null);

			pairs[i++] = new BasicNameValuePair(key, readMethod.invoke(bean,
					null).toString());
		}

		return buildUrl(pairs, wsDesc.getUrl());
	}

	private static String buildUrl(NameValuePair pairs[], String specificUrl) {
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

	public static class WebServiceResponse<T> {
		private boolean success;
		private String error;
		private T bean;

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public T getBean() {
			return bean;
		}

		public void setBean(T bean) {
			this.bean = bean;
		}
	}
}
