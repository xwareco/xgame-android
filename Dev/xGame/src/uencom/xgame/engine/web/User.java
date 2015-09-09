package uencom.xgame.engine.web;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import uencom.xgame.jsonconverters.LoadUserMessagesJsonParameter;
import uencom.xgame.jsonconverters.RegisterJsonParameterConverter;
import uencom.xgame.jsonconverters.ScoreJsonParameterConverter;
import uencom.xgame.jsonconverters.SendUserJsonParameterConverter;
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
	HttpClient httpclient = new DefaultHttpClient();
	HttpGet request = null;
	String res = "NULL";
	@SuppressWarnings("unused")
	private int id;
	private Context ctx;

	public User(Context c, String mail, String pass) {
		ctx = c;
		this.email = mail;
		this.inputJSONConverter = new Gson();
		this.password = pass;
		urlPrefix = "http://xgameapp.com/api/v2/";
	}

	public String getName() {
		return email;
	}

	public boolean register() {
		final TelephonyManager tm = (TelephonyManager) ctx
				.getSystemService(Context.TELEPHONY_SERVICE);
		RegisterJsonParameterConverter RJC = new RegisterJsonParameterConverter(
				"password", email, password, tm.getDeviceId());
		String JsonParams = inputJSONConverter.toJson(RJC);
		String urlEncodedParams = null;
		try {
			urlEncodedParams = URLEncoder.encode(JsonParams, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url = urlPrefix + "register?req_data=" + urlEncodedParams;

		SharedPreferences appSharedPrefs = null;
		ResponseHandler<String> handler = new BasicResponseHandler();
		String result = "NULL";

		try {
			request = new HttpGet(new URI(url));
			// Authentication Layer
			appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
			Editor prefsEditor = appSharedPrefs.edit();
			String Auth = appSharedPrefs.getString("access_token", "");
			prefsEditor.commit();
			request.addHeader("Authorization", Auth);
			request.addHeader("Content-Type", "application/json");
			request.addHeader("Accept", "application/json");
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
		SendUserJsonParameterConverter SUJC = new SendUserJsonParameterConverter(
				msg);
		String JsonParams = inputJSONConverter.toJson(SUJC);
		String urlEncodedParams = null;
		try {
			urlEncodedParams = URLEncoder.encode(JsonParams, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url = urlPrefix + "sendUserMessage?req_data=" + urlEncodedParams;
		SharedPreferences appSharedPrefs = null;
		ResponseHandler<String> handler = new BasicResponseHandler();
		String result = "NULL";
		try {
			request = new HttpGet(new URI(url));
			// Authentication Layer
			appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
			Editor prefsEditor = appSharedPrefs.edit();
			String Auth = appSharedPrefs.getString("access_token", "");
			prefsEditor.commit();
			request.addHeader("Authorization", Auth);
			request.addHeader("Content-Type", "application/json");
			request.addHeader("Accept", "application/json");
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
		LoadUserMessagesJsonParameter LUMJC = new LoadUserMessagesJsonParameter(
				userId);
		String JsonParams = inputJSONConverter.toJson(LUMJC);
		String urlEncodedParams = null;
		try {
			urlEncodedParams = URLEncoder.encode(JsonParams, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url = urlPrefix + "loadUserMessages?req_data="
				+ urlEncodedParams;
		SharedPreferences appSharedPrefs = null;
		ArrayList<Message> userMessages;
		ResponseHandler<String> handler = new BasicResponseHandler();
		String result = "NULL";
		try {
			request = new HttpGet(new URI(url));
			// Authentication Layer
			appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
			Editor prefsEditor = appSharedPrefs.edit();
			String Auth = appSharedPrefs.getString("access_token", "");
			prefsEditor.commit();
			request.addHeader("Authorization", Auth);
			request.addHeader("Content-Type", "application/json");
			request.addHeader("Accept", "application/json");
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

	public void addUserScore(String userId, String gameID, String score) {
		ScoreJsonParameterConverter SJC = new ScoreJsonParameterConverter(
				userId, gameID, score);
		String JsonParams = inputJSONConverter.toJson(SJC);
		String urlEncodedParams = null;
		try {
			urlEncodedParams = URLEncoder.encode(JsonParams, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url = urlPrefix + "addUserScore?req_data=" + urlEncodedParams;
		SharedPreferences appSharedPrefs = null;
		ResponseHandler<String> handler = new BasicResponseHandler();
		@SuppressWarnings("unused")
		String result = "NULL";
		try {
			request = new HttpGet(new URI(url));
			// Authentication Layer
			appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
			Editor prefsEditor = appSharedPrefs.edit();
			String Auth = appSharedPrefs.getString("access_token", "");
			prefsEditor.commit();
			request.addHeader("Authorization", Auth);
			request.addHeader("Content-Type", "application/json");
			request.addHeader("Accept", "application/json");
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

		} else if (params[0] == "msg") {

		} else if (params[0] == "load") {

		} else if (params[0] == "register") {

		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		System.out.println(res);
		super.onPostExecute(result);
	}

}
