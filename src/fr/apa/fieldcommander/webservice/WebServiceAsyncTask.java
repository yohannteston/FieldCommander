package fr.apa.fieldcommander.webservice;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;

public class WebServiceAsyncTask<Result> extends
		AsyncTask<String, Void, WebServiceResponse<Result>> {

	private final WebServiceCallBack<Result> callback;
	private final Class<Result> resultClass;

	public WebServiceAsyncTask(final WebServiceCallBack<Result> callback,
			final Class<Result> resultClass) {
		this.callback = callback;
		this.resultClass = resultClass;
	}

	@Override
	protected WebServiceResponse<Result> doInBackground(String... arg0) {

		final String url = arg0[0];

		final HttpClient client = new DefaultHttpClient();
		final HttpGet request = new HttpGet(url);
		try {
			return new JSON2JavaBeanWrapper<Result>().wrapResponse(
					new JSONObject(client.execute(request,
							new BasicResponseHandler())), resultClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(WebServiceResponse<Result> result) {
		super.onPostExecute(result);

		callback.execute(result);
	}
}
