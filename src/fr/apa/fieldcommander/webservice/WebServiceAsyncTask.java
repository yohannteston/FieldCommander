package fr.apa.fieldcommander.webservice;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class WebServiceAsyncTask extends AsyncTask<String, Void, String> {

	private final JSONCallBack<String> callback;

	public WebServiceAsyncTask(final JSONCallBack<String> callback) {
		this.callback = callback;
	}

	@Override
	protected String doInBackground(String... arg0) {

		final String url = arg0[0];

		final HttpClient client = new DefaultHttpClient();
		final HttpGet request = new HttpGet(url);
		try {
			return client.execute(request, new BasicResponseHandler());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		callback.execute(result);
	}
}
