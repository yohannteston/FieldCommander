package fr.apa.fieldcommander.view;

import java.io.IOException;
import java.util.Arrays;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import fr.apa.fieldcommander.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText login;
	private EditText pwd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		login = (EditText) findViewById(R.id.loginField);
		pwd = (EditText) findViewById(R.id.passwordField);
		new LolClass().execute("lol");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	private String buildThisFuckingGetUrl(NameValuePair pairs[], String url) {
		if (!url.endsWith("?"))
			url += "?";

		String paramString = URLEncodedUtils.format(Arrays.asList(pairs),
				"utf-8");

		url += paramString;
		System.out.println(url);
		return url;
	}

	public void loginClicked(View v) {
		Toast.makeText(getApplicationContext(),
				"Button clicked: " + login.getText() + " " + pwd.getText(),
				Toast.LENGTH_SHORT).show();

		final Intent intent = new Intent(this, ListGamesActivity.class);
		startActivity(intent);
	}

	private void doFetchWS() {
		try {
			// http://androidarabia.net/quran4android/phpserver/connecttoserver.php

			// Log.i(getClass().getSimpleName(), "send  task - start");
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 1000);
			HttpConnectionParams.setSoTimeout(httpParams, 1000);
			//
			HttpParams p = new BasicHttpParams();

			// Instantiate an HttpClient
			HttpClient httpclient = new DefaultHttpClient(p);
			String url = "http://arnaudportfolio.free.fr/fieldcommander/wsGame.php";
			// HttpGet get = new HttpGet(buildThisFuckingGetUrl(
			// new BasicNameValuePair[] {
			// new BasicNameValuePair("AccountID", "1"),
			// new BasicNameValuePair("Action", "GetAll"),
			// new BasicNameValuePair("GameID", "1"),
			// new BasicNameValuePair("Name",
			// "Android was there !") }, url));

			HttpGet get = new HttpGet(buildThisFuckingGetUrl(
					new BasicNameValuePair[] {
							new BasicNameValuePair("AccountID", "1"),
							new BasicNameValuePair("Action", "GetAll") }, url));
			// Instantiate a GET HTTP method
			try {
				Log.i(getClass().getSimpleName(), "send  task - start");
				//
				// HttpParams pp = new BasicHttpParams();
				// httppost.setParams(pp);
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				String responseBody = httpclient.execute(get, responseHandler);
				System.out.println(responseBody);
				// Parse
				JSONObject json = new JSONObject(responseBody);
				System.out.println("-------------->>>>");
				System.out.println(json.toString());
				// JSONArray jArray = json.getJSONArray("posts");
				// ArrayList<HashMap<String, String>> mylist = new
				// ArrayList<HashMap<String, String>>();
				//
				// for (int i = 0; i < jArray.length(); i++) {
				// HashMap<String, String> map = new HashMap<String,
				// String>();
				// JSONObject e = jArray.getJSONObject(i);
				// String s = e.getString("post");
				// JSONObject jObject = new JSONObject(s);
				//
				// map.put("idusers", jObject.getString("idusers"));
				// map.put("UserName", jObject.getString("UserName"));
				// map.put("FullName", jObject.getString("FullName"));
				//
				// mylist.add(map);
				// }
				// Toast.makeText(this, responseBody, Toast.LENGTH_LONG).show();

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Log.i(getClass().getSimpleName(), "send  task - end");

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private class LolClass extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			doFetchWS();
			return "done like dinner";
		}

		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(), "Post exec " + result,
					Toast.LENGTH_SHORT).show();
		}
	}
}
