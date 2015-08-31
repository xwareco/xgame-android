package uencom.xgame.engine.web;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

public class User extends AsyncTask<String, Void, Void> {

	private String email;
	private String password;
	private String urlPrefix;
	private Gson inputJSONConverter;
	String res = "NULL";
	@SuppressWarnings("unused")
	private int id;
	private Context ctx;

	public User(Context c, String mail, String pass) {
		ctx = c;
		this.email = mail;
		this.inputJSONConverter = new Gson();
		this.password = pass;
		urlPrefix = "http://xgameapp.com/api/v1/";
	}

	public String getName() {
		return email;
	}

	public boolean register() {
		String url = urlPrefix + "register";

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost request = null;
		SharedPreferences appSharedPrefs = null;
		ResponseHandler<String> handler = new BasicResponseHandler();
		String result = "NULL";

		try {
			request = new HttpPost(new URI(url));
			// Authentication Layer
			appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
			Editor prefsEditor = appSharedPrefs.edit();
			String Auth = appSharedPrefs.getString("access_token", "");
			prefsEditor.commit();
			request.addHeader("Authorization", Auth);
			request.addHeader("Content-Type", "application/json");
			request.addHeader("Accept", "application/json");

			// API method parameters
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			final TelephonyManager tm = (TelephonyManager) ctx
					.getSystemService(Context.TELEPHONY_SERVICE);
			nameValuePairs.add(new BasicNameValuePair("grant_type",
					inputJSONConverter.toJson("password")));
			nameValuePairs.add(new BasicNameValuePair("email",
					inputJSONConverter.toJson(email)));
			nameValuePairs.add(new BasicNameValuePair("password",
					inputJSONConverter.toJson(password)));
			nameValuePairs.add(new BasicNameValuePair("device_id",
					inputJSONConverter.toJson(tm.getDeviceId())));
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			result = httpclient.execute(request, handler);
			res = result;
			System.out.println("Iam in!!");
			JSONObject obj = new JSONObject(result);
			String state = obj.getString("status");
			if (state == "true")
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			String newToken = xGameAPI.getNewToken();
			request.setHeader("Authorization", newToken);
			Editor prefEditor2 = appSharedPrefs.edit();
			prefEditor2.putString("access_token", newToken);
			prefEditor2.commit();
		}
		return false;
	}

	public boolean sendUserMessage(String msg) {
		String url = urlPrefix + "sendUserMessage";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost request = null;
		SharedPreferences appSharedPrefs = null;
		ResponseHandler<String> handler = new BasicResponseHandler();
		String result = "NULL";
		try {
			request = new HttpPost(new URI(url));
			// Authentication Layer
			appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
			Editor prefsEditor = appSharedPrefs.edit();
			String Auth = appSharedPrefs.getString("access_token", "");
			prefsEditor.commit();
			request.addHeader("Authorization", Auth);
			request.addHeader("Content-Type", "application/json");
			request.addHeader("Accept", "application/json");
			// API method parameters
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("user_id",
					inputJSONConverter.toJson("2")));
			nameValuePairs.add(new BasicNameValuePair("body",
					inputJSONConverter.toJson(msg)));
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			result = httpclient.execute(request, handler);
			JSONObject obj = new JSONObject(result);
			String state = obj.getString("status");
			if (state == "true")
				return true;
		} catch (Exception e) {
			String newToken = xGameAPI.getNewToken();
			request.setHeader("Authorization", newToken);
			Editor prefEditor2 = appSharedPrefs.edit();
			prefEditor2.putString("access_token", newToken);
			prefEditor2.commit();
		}
		return false;
	}

	public void loadUserMessages(String userId) {
		String url = urlPrefix + "loadUserMessages";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost request = null;
		SharedPreferences appSharedPrefs = null;
		ArrayList<Message> userMessages;
		ResponseHandler<String> handler = new BasicResponseHandler();
		String result = "NULL";
		try {
			request = new HttpPost(new URI(url));
			// Authentication Layer
			appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
			Editor prefsEditor = appSharedPrefs.edit();
			String Auth = appSharedPrefs.getString("access_token", "");
			prefsEditor.commit();
			request.addHeader("Authorization", Auth);
			request.addHeader("Content-Type", "application/json");
			request.addHeader("Accept", "application/json");
			// API method parameters
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("user_id",
					inputJSONConverter.toJson("2")));
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			result = httpclient.execute(request, handler);
		} catch (Exception e) {
			String newToken = xGameAPI.getNewToken();
			request.setHeader("Authorization", newToken);
			Editor prefEditor2 = appSharedPrefs.edit();
			prefEditor2.putString("access_token", newToken);
			prefEditor2.commit();
		}

		// Parsing resulting JSON
		JSONArray msgs;
		try {
			msgs = new JSONArray(result);
			userMessages = new ArrayList<Message>();
			for (int i = 0; i < msgs.length(); i++) {
				Message singleMessage = new Message();
				JSONObject jsonMsg = msgs.getJSONObject(i);
				singleMessage.setBody(jsonMsg.getString("body"));
				singleMessage.setId(jsonMsg.getString("id"));
				singleMessage.setUser_id(jsonMsg.getString("user_id"));
				userMessages.add(singleMessage);
			}
			Gson g = new Gson();
			String msgsJson = g.toJson(userMessages);
			appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
			Editor prefsEditor = appSharedPrefs.edit();
			prefsEditor.putString("msgs", msgsJson);
			prefsEditor.commit();
		} catch (Exception e) {
			e.printStackTrace();

		}

		return;
	}

	public void addUserScore(String gameName, String score) {
		String url = urlPrefix + "addUserScore";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost request = null;
		SharedPreferences appSharedPrefs = null;
		ResponseHandler<String> handler = new BasicResponseHandler();
		@SuppressWarnings("unused")
		String result = "NULL";
		try {
			request = new HttpPost(new URI(url));
			// Authentication Layer
			appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
			Editor prefsEditor = appSharedPrefs.edit();
			String Auth = appSharedPrefs.getString("access_token", "");
			prefsEditor.commit();
			request.addHeader("Authorization", Auth);
			request.addHeader("Content-Type", "application/json");
			request.addHeader("Accept", "application/json");
			// API method parameters
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("user_id",
					inputJSONConverter.toJson("2")));
			nameValuePairs.add(new BasicNameValuePair("game_id",
					inputJSONConverter.toJson(gameName)));
			nameValuePairs.add(new BasicNameValuePair("score",
					inputJSONConverter.toJson(score)));
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			result = httpclient.execute(request, handler);
		} catch (Exception e) {
			String newToken = xGameAPI.getNewToken();
			request.setHeader("Authorization", newToken);
			Editor prefEditor2 = appSharedPrefs.edit();
			prefEditor2.putString("access_token", newToken);
			prefEditor2.commit();

		}
	}

	@Override
	protected Void doInBackground(String... params) {
		if (params[0] == "add") {
			addUserScore("1", "300");
		} else if (params[0] == "msg") {
			sendUserMessage("This is the first user message!");
		} else if (params[0] == "load") {
			loadUserMessages("2");
		} else if (params[0] == "register") {
			boolean x = register();
			if (x == true)
				System.out.println("Registered!");
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		System.out.println(res);
		super.onPostExecute(result);
	}

}
