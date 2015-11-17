package uencom.xgame.engine.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uencom.xgame.engine.Scorer;
import uencom.xgame.engine.views.ContactUs;
import uencom.xgame.engine.views.GameOver;
import uencom.xgame.jsonconverters.ChangeEmailJsonConverter;
import uencom.xgame.jsonconverters.LoadUserMessagesJsonParameter;
import uencom.xgame.jsonconverters.RegisterJsonParameterConverter;
import uencom.xgame.jsonconverters.ScoreJsonParameterConverter;
import uencom.xgame.xgame.R;
import com.google.gson.Gson;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class User extends AsyncTask<String, Void, Void> {

	private String email;
	private String password;
	private String urlPrefix;
	private String TAG;
	private String gameID, gameScore, folder, gameName;
	private Gson inputJSONConverter;
	private FileInputStream fileUpload;
	private TextView newEmail;
	NotificationManager mNotifyManager;
	NotificationCompat.Builder mBuilder;
	HttpClient httpclient = new DefaultHttpClient();
	HttpGet request = null;
	HttpPost postRequest = null;
	String res = "NULL";
	Handler updateHandler;
	private String id;
	private static Context ctx;

	public User(Context c, String mail, String pass, String id, String f,
			String n, FileInputStream fileInputStream, TextView tv) {
		ctx = c;
		this.email = mail;
		this.inputJSONConverter = new Gson();
		this.password = pass;
		updateHandler = new Handler();
		this.id = id;
		this.newEmail = tv;
		this.gameID = mail;
		this.gameScore = pass;
		this.folder = f;
		this.gameName = n;
		fileUpload = fileInputStream;
		mNotifyManager = (NotificationManager) ctx
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(ctx);
		mBuilder.setContentTitle("Sending Message")
				.setContentText("Your Audio feedback is being sent!")
				.setSmallIcon(R.drawable.iconnot);
		urlPrefix = "http://xgameapp.com/api/v2/";
		TAG = id;
	}

	public String getName() {
		return email;
	}

	public void passwordReminder() {
		PasswordReminderJsonConverter PRJC = new PasswordReminderJsonConverter(
				id, password);
		String JsonParams = inputJSONConverter.toJson(PRJC);
		String urlEncodedParams = null;
		try {
			urlEncodedParams = URLEncoder.encode(JsonParams, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url = urlPrefix + "passwordReminder?req_data="
				+ urlEncodedParams;
		System.out.println(url);
		SharedPreferences appSharedPrefs = null;
		ResponseHandler<String> handler = new BasicResponseHandler();
		String result = "NULL";
		Activity act = (Activity) ctx;

		try {
			postRequest = new HttpPost(new URI(url));
			// Authentication Layer
			appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
			Editor prefsEditor = appSharedPrefs.edit();
			String Auth = appSharedPrefs.getString("access_token", "");
			System.out.println("TOK: " + Auth);
			prefsEditor.commit();
			postRequest.addHeader("Authorization", Auth);
			postRequest.addHeader("Content-Type", "application/json");
			postRequest.addHeader("Accept", "application/json");
			result = httpclient.execute(postRequest, handler);
			res = result;
			System.out.println(result);
			final JSONObject obj = new JSONObject(result);
			String state = obj.getString("status");

			if (state == "true") {

				act.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(
								ctx,
								"Your password has been successfully sent to your E-mail",
								Toast.LENGTH_LONG).show();

					}
				});

			} else {
				act.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						try {
							Toast.makeText(
									ctx,
									"Error sending your password, "
											+ obj.getString("message"),
									Toast.LENGTH_LONG).show();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

			}
		} catch (Exception e) {
			if (e.getMessage().contains("Unauthorized")
					|| e.getMessage().contains("Bad Request")
					|| e.getMessage().contains("challenges")) {
				String newToken = xGameAPI.getNewToken("user");
				postRequest.setHeader("Authorization", newToken);
				System.out.println(newToken);
				Editor prefEditor2 = appSharedPrefs.edit();
				prefEditor2.putString("access_token", newToken);
				prefEditor2.commit();

				try {
					result = httpclient.execute(postRequest, handler);
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			/*
			 * else if (e.getMessage().contains("Internal Server Error")) {
			 * act.runOnUiThread(new Runnable() {
			 * 
			 * @Override public void run() { Toast.makeText( ctx,
			 * "Failed to update, user already exists with the same data",
			 * Toast.LENGTH_LONG).show();
			 * 
			 * } });
			 * 
			 * }
			 */

			else {
				e.printStackTrace();
			}
		}
	}

	public void changeEmail() {
		ChangeEmailJsonConverter CEJC = new ChangeEmailJsonConverter(id, email);
		String JsonParams = inputJSONConverter.toJson(CEJC);
		String urlEncodedParams = null;
		try {
			urlEncodedParams = URLEncoder.encode(JsonParams, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url = urlPrefix + "changeEmail?req_data=" + urlEncodedParams;
		System.out.println(url);
		SharedPreferences appSharedPrefs = null;
		ResponseHandler<String> handler = new BasicResponseHandler();
		String result = "NULL";
		Activity act = (Activity) ctx;

		try {
			postRequest = new HttpPost(new URI(url));
			// Authentication Layer
			appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
			Editor prefsEditor = appSharedPrefs.edit();
			String Auth = appSharedPrefs.getString("access_token", "");
			System.out.println("TOK: " + Auth);
			prefsEditor.commit();
			postRequest.addHeader("Authorization", Auth);
			postRequest.addHeader("Content-Type", "application/json");
			postRequest.addHeader("Accept", "application/json");
			result = httpclient.execute(postRequest, handler);
			res = result;
			System.out.println(result);
			final JSONObject obj = new JSONObject(result);
			String state = obj.getString("status");

			if (state == "true") {

				act.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						newEmail.setText(email);

					}
				});
				prefsEditor.putString("uID", id);
				prefsEditor.putString("uName", email);
				prefsEditor.putString("uPass", password);
				prefsEditor.commit();
				String fileWriteString = id + "," + email + "," + password;
				writeToFile(fileWriteString);
				act.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(ctx,
								"Your Email has been successfully changed",
								Toast.LENGTH_LONG).show();

					}
				});

			} else {
				act.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						try {
							Toast.makeText(
									ctx,
									"Error updating your Email, "
											+ obj.getString("message"),
									Toast.LENGTH_LONG).show();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

			}
		} catch (Exception e) {
			if (e.getMessage().contains("Unauthorized")
					|| e.getMessage().contains("Bad Request")
					|| e.getMessage().contains("challenges")) {
				String newToken = xGameAPI.getNewToken("user");
				postRequest.setHeader("Authorization", newToken);
				Editor prefEditor2 = appSharedPrefs.edit();
				prefEditor2.putString("access_token", newToken);
				prefEditor2.commit();

			}

			else if (e.getMessage().contains("Internal Server Error")) {
				act.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(
								ctx,
								"Failed to update, user already exists with the same data",
								Toast.LENGTH_LONG).show();

					}
				});

			}

			else {
				e.printStackTrace();
			}
		}
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
				Intent I = null;

				if (TAG.equalsIgnoreCase("main2")) {
					I = new Intent(ctx, ContactUs.class);
					ctx.startActivity(I);

				} else if (TAG.equalsIgnoreCase("main")) {
					Activity act = (Activity) ctx;
					act.finish();

				}
				updateHandler.post(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(ctx, "Successfullly Registered",
								Toast.LENGTH_LONG).show();

					}
				});
				return true;
			} else {
				System.out.println("Error!!!");
				updateHandler.post(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(ctx, "Failed to register," + msg,
								Toast.LENGTH_LONG).show();

					}
				});

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if (e.getMessage().contains("Internal Server Error")) {
				updateHandler.post(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(
								ctx,
								"Failed to register, user already exists with the same data",
								Toast.LENGTH_LONG).show();

					}
				});
			} else {
				String newToken = xGameAPI.getNewToken("guest");
				postRequest.setHeader("Authorization", newToken);
				Editor prefEditor2 = appSharedPrefs.edit();
				prefEditor2.putString("access_token", newToken);
				prefEditor2.commit();
			}
		}
		return false;
	}

	public void sendAudioFeedback() {
		String URL = urlPrefix + "contact";
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		String Tag = "fSnd";
		mBuilder.setProgress(0, 0, true);
		// Displays the progress bar for the first time.
		mNotifyManager.notify(1, mBuilder.build());
		HttpURLConnection conn = null;
		// Authentication Layer
		SharedPreferences appSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(ctx);
		Editor prefsEditor = appSharedPrefs.edit();
		String Auth = appSharedPrefs.getString("access_token", "");
		prefsEditor.commit();
		URL connectURL = null;
		try {

			connectURL = new URL(URL);
			conn = (HttpURLConnection) connectURL.openConnection();
			// Allow Inputs
			conn.setDoInput(true);
			// Allow Outputs
			conn.setDoOutput(true);
			// Don't use a cached copy.
			conn.setUseCaches(false);
			// Use a post method.
			conn.setRequestMethod("POST");
			System.out.println("ACC: " + Auth);
			conn.setRequestProperty("Authorization", Auth);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"user_id\""
					+ lineEnd);
			dos.writeBytes(lineEnd);
			dos.writeBytes(id);
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + lineEnd);

			dos.writeBytes("Content-Disposition: form-data; name=\"audio_file\";filename=\""
					+ "Audio_file" + "\"" + lineEnd);
			dos.writeBytes(lineEnd);

			// create a buffer of maximum size
			int bytesAvailable = fileUpload.available();

			int maxBufferSize = 1024;
			int bufferSize = Math.min(bytesAvailable, maxBufferSize);
			byte[] buffer = new byte[bufferSize];

			// read file and write it into form...
			int bytesRead = fileUpload.read(buffer, 0, bufferSize);

			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileUpload.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileUpload.read(buffer, 0, bufferSize);
			}
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
			// close streams
			fileUpload.close();
			dos.flush();
			Log.e(Tag,
					"File Sent, Response: "
							+ String.valueOf(conn.getResponseCode()));

			InputStream is = conn.getInputStream();

			// retrieve the response from server
			int ch;

			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			String s = b.toString();
			Log.i("Response", s);
			dos.close();
			mBuilder.setContentText("Message Sent")
			// Removes the progress bar
					.setProgress(0, 0, false);
			mNotifyManager.notify(1, mBuilder.build());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			String newToken = xGameAPI.getNewToken("user");
			System.out.println("NEW: " + newToken);
			Editor prefEditor2 = appSharedPrefs.edit();
			prefEditor2.putString("access_token", newToken);
			prefEditor2.commit();
		}

	}

	public boolean sendUserMessage() {

		String url = urlPrefix + "contact";
		SharedPreferences appSharedPrefs = null;
		ResponseHandler<String> handler = new BasicResponseHandler();
		String result = "NULL";
		try {
			postRequest = new HttpPost(new URI(url));
			// Authentication Layer
			System.out.println("EXCEPTION!!!!");
			appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
			Editor prefsEditor = appSharedPrefs.edit();
			String Auth = appSharedPrefs.getString("access_token", "");
			prefsEditor.commit();
			postRequest.addHeader("Authorization", Auth);
			postRequest.addHeader("Content-Type",
					"application/x-www-form-urlencoded");
			postRequest.addHeader("Accept", "application/json");
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("user_id", id));
			nameValuePairs.add(new BasicNameValuePair("title", email));
			nameValuePairs.add(new BasicNameValuePair("body", password));
			postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			result = httpclient.execute(postRequest, handler);
			System.out.println(result);
			JSONObject obj = new JSONObject(result);
			String state = obj.getString("status");
			if (state == "true")
				return true;
		} catch (Exception e) {
			e.printStackTrace();
			String newToken = xGameAPI.getNewToken("user");
			postRequest.setHeader("Authorization", newToken);
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
			String newToken = xGameAPI.getNewToken("user");
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
			System.out.println(result);
			boolean cheer = false;
			JSONObject obj = new JSONObject(result);
			String state = obj.getString("status");
			String msg = obj.getString("message");

			if (state == "true") {
				String rank = obj.getString("rank");
				System.out.println(msg + " " + Integer.parseInt(rank));
				if (msg.equalsIgnoreCase("Score added successfully")
						&& Integer.parseInt(rank) >= 1
						&& Integer.parseInt(rank) < 4) {
					cheer = true;
				}
				ArrayList<Scorer> topScorers = new ArrayList<Scorer>();
				JSONArray top = obj.getJSONArray("top_scorers");
				for (int i = 0; i < top.length(); i++) {
					JSONObject scoreJSON = top.getJSONObject(i);
					JSONObject mailJSON = scoreJSON.getJSONObject("user");
					String scorerScore = scoreJSON.getString("score");
					String scorerEmail = mailJSON.getString("email");

					Scorer scorer = new Scorer();
					scorer.setRank(Integer.toString(i + 1));
					scorer.setScore(scorerScore);
					scorer.setUserMail(scorerEmail);
					topScorers.add(scorer);
				}

				String scorersJSON = inputJSONConverter.toJson(topScorers);
				Editor ed = appSharedPrefs.edit();
				ed.putString("scorers", scorersJSON);
				ed.commit();

				Intent gameOver = new Intent(ctx, GameOver.class);
				gameOver.putExtra("Score", Integer.parseInt(gameScore));
				gameOver.putExtra("Folder", folder);
				gameOver.putExtra("gamename", gameName);
				gameOver.putExtra("gameid", gameID);
				gameOver.putExtra("rank", rank);
				gameOver.putExtra("cheer", cheer);
				ctx.startActivity(gameOver);
				Activity act = (Activity) ctx;
				act.finish();
				act.overridePendingTransition(R.anim.transition10,
						R.anim.transition9);
			}
		} catch (Exception e) {
			String newToken = xGameAPI.getNewToken("user");
			postRequest.setHeader("Authorization", newToken);
			Editor prefEditor2 = appSharedPrefs.edit();
			prefEditor2.putString("access_token", newToken);
			prefEditor2.commit();

		}
	}

	@Override
	protected Void doInBackground(String... params) {
		if (params[0] == "add") {

		} else if (params[0].equals("msg")) {
			sendUserMessage();

		} else if (params[0].equals("load")) {

		} else if (params[0].equals("register")) {
			register();
		} else if (params[0].equals("contact")) {
			sendAudioFeedback();
		} else if (params[0].equals("change")) {
			changeEmail();
		} else if (params[0].equals("passRem")) {
			passwordReminder();
		} else if (params[0].equals("score")) {
			addUserScore(id, gameID, gameScore);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (fileUpload != null) {
			File f = new File(Environment.getExternalStorageDirectory()
					.toString() + "/xGame/feedback/Audio_File.aac");
			if (f.exists()) {
				f.delete();
			}
		}

		super.onPostExecute(result);
	}

	private void writeToFile(String data) {
		File xGameFolder = new File(Environment.getExternalStorageDirectory()
				+ "/xGame/Data/");
		xGameFolder.mkdirs();
		File DataFile = new File(xGameFolder, "uData.txt");
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
