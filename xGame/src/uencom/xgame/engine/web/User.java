package uencom.xgame.engine.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class User extends AsyncTask<String, Void, Void> {

	private String email;
	private String password;
	private String urlPrefix;
	private Gson inputJSONConverter;
	HttpClient httpclient = new DefaultHttpClient();
	HttpGet request = null;
	HttpPost postRequest = null;
	String res = "NULL";
	Handler updateHandler;
	@SuppressWarnings("unused")
	private int id;
	private static Context ctx;

	public User(Context c, String mail, String pass) {
		ctx = c;
		this.email = mail;
		this.inputJSONConverter = new Gson();
		this.password = pass;
		updateHandler = new Handler();
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
		System.out.println(url);
		SharedPreferences appSharedPrefs = null;
		ResponseHandler<String> handler = new BasicResponseHandler();
		String result = "NULL";

		try {
			postRequest = new HttpPost(new URI(url));
			// Authentication Layer
			appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
			Editor prefsEditor = appSharedPrefs.edit();
			String Auth = appSharedPrefs.getString("access_token", "");
			prefsEditor.commit();
			postRequest.addHeader("Authorization", Auth);
			postRequest.addHeader("Content-Type", "application/json");
			postRequest.addHeader("Accept", "application/json");
			result = httpclient.execute(postRequest, handler);
			res = result;
			System.out.println("Iam in!!");
			JSONObject obj = new JSONObject(result);
			String state = obj.getString("status");
			final String msg = obj.getString("message");
			String uID = obj.getString("user_id");
			if (state == "true") {
				appSharedPrefs = PreferenceManager
						.getDefaultSharedPreferences(ctx);
				String fileWriteString = uID + "," + email + "," + password;
				writeToFile(fileWriteString);
				prefsEditor = appSharedPrefs.edit();
				prefsEditor.putString("uID", uID);
				prefsEditor.putString("uName", email);
				prefsEditor.putString("uPass", password);
				prefsEditor.commit();
				updateHandler.post(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(ctx, "Successfullly Registered",
								Toast.LENGTH_LONG).show();

					}
				});
				return true;
			} else {
				updateHandler.post(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(ctx, "Failed to register," + msg,
								Toast.LENGTH_LONG).show();

					}
				});

			}
		} catch (Exception e) {
			e.printStackTrace();
			String newToken = xGameAPI.getNewToken();
			postRequest.setHeader("Authorization", newToken);
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
			register();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		System.out.println(res);
		super.onPostExecute(result);
	}

	private void writeToFile(String data) {
		File xGameFolder = new File(Environment.getExternalStorageDirectory()
				+ "/xGame/Data/");
		xGameFolder.mkdirs();
		File DataFile = new File(xGameFolder , "uData.txt");
		BufferedWriter writer;
		try {
			DataFile.createNewFile();
			writer = new BufferedWriter(new FileWriter(DataFile));
			writer.write(data);
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String readFromFile() {

		File userDataFile = new File(Environment.getExternalStorageDirectory()
				+ "/xGame/Data/uData.txt");
		String ret = "";
		if (userDataFile.exists()) {

			BufferedReader bufferedReader;
			try {
				bufferedReader = new BufferedReader(
						new FileReader(userDataFile));
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ((receiveString = bufferedReader.readLine()) != null) {
					stringBuilder.append(receiveString);
				}
				bufferedReader.close();
				ret = stringBuilder.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}
}
