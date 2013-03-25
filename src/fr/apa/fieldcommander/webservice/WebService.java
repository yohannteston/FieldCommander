package fr.apa.fieldcommander.webservice;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import fr.apa.fieldcommander.model.Team;
import fr.apa.fieldcommander.webservice.WebServiceDescriptor.WebServiceDescriptorKey;

public class WebService {

	private static final String WS_URL = "http://arnaudportfolio.free.fr/fieldcommander/";
	private static Map<WebServiceDescriptorKey, WebServiceDescriptor> WS_DESCRIPTORS;

	static {
		WS_DESCRIPTORS = new HashMap<WebServiceDescriptor.WebServiceDescriptorKey, WebServiceDescriptor>();

		final WebServiceDescriptorKey retrieveTeamKey = new WebServiceDescriptorKey(
				RequestType.RETRIEVE, Team.class);
		final WebServiceDescriptor retrieveTeam = new WebServiceDescriptor(
				retrieveTeamKey, "wsTeam.php?Action=Get", null);

		final WebServiceDescriptorKey addTeamKey = new WebServiceDescriptorKey(
				RequestType.CREATE, Team.class);
		final WebServiceDescriptor addTeam = new WebServiceDescriptor(
				retrieveTeamKey, "wsTeam.php", null);

		WS_DESCRIPTORS.put(addTeamKey, addTeam);
		WS_DESCRIPTORS.put(retrieveTeamKey, retrieveTeam);
	}

	public static <T> WebServiceResponse<T> execute(RequestType requestType,
			T parameter) throws IllegalArgumentException,
			ClientProtocolException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException, IOException,
			JSONException {
		final String wsResponse = resolveWebservice(requestType, parameter);

		new JSON2JavaBeanWrapper<T>().updateBean(new JSONObject(wsResponse),
				parameter);

		return new WebServiceResponse<T>();
	}

	private static <T> String resolveWebservice(RequestType requestType, T bean)
			throws NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			ClientProtocolException, IOException {
		WebServiceDescriptor wsDesc = WS_DESCRIPTORS
				.get(new WebServiceDescriptorKey(requestType, bean.getClass()));

		NameValuePair pairs[] = new NameValuePair[wsDesc.getProperties().size()];
		int i = 0;
		for (String key : wsDesc.getProperties()) {
			final Method readMethod = bean.getClass().getDeclaredMethod(
					"get" + key, null);

			pairs[i++] = new BasicNameValuePair(key, readMethod.invoke(bean,
					null).toString());
		}
		final HttpClient client = new DefaultHttpClient();
		final HttpGet request = new HttpGet(buildUrl(pairs, wsDesc.getUrl()));
		return client.execute(request, new BasicResponseHandler());
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
