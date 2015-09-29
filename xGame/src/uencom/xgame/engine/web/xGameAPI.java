package uencom.xgame.engine.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.json.JSONObject;

import uencom.xgame.jsonconverters.BoardJsonParameterConverter;
import uencom.xgame.jsonconverters.GamesJsonParameterConverter;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

public class xGameAPI {

	private ArrayList<GameCategory> categories;
	private static Context ctx;
	private static Gson inputJSONConverter;
	private String urlPrefix;
	private static String authUrlPrefix;

	public xGameAPI(Context c) {
		categories = new ArrayList<GameCategory>();
		ctx = c;
		urlPrefix = "http://xgameapp.com/api/v2/";
		authUrlPrefix = "http://xgameapp.com/oauth/";
		inputJSONConverter = new Gson();

	}

	public ArrayList<GameCategory> getCategories() {
		// Data definition
		String url = urlPrefix + "getCategories";
		String result = makeApiCall(url, "cat", "");

		// Parsing Json result
		if (result != "NULL") {
			try {
				JSONArray jsonArr = new JSONArray(result);
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject cat = jsonArr.getJSONObject(i);
					GameCategory category = new GameCategory();
					category.setId(cat.getString("id"));
					category.setName(cat.getString("category_name"));
					category.setImgPath(cat.getString("category_image"));
					categories.add(category);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return categories;
	}

	// req_data={"category_id":"1","limit":"5","my_last_game":"0"}
	// http://xgameapp.com/api/v2/getCategoryGames?req_data={"category_id":"1","limit":"10","my_last_game":"0"}
	public ArrayList<Game> getGames(String catId, String limit, String last_id) {
		// Data definition
		GamesJsonParameterConverter JPC = new GamesJsonParameterConverter(
				catId, limit, last_id);
		String paramsJSON = inputJSONConverter.toJson(JPC);
		String urlEncodedParams = null;
		try {
			urlEncodedParams = URLEncoder.encode(paramsJSON, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url = urlPrefix + "getCategoryGames?req_data="
				+ urlEncodedParams;
		String result = makeApiCall(url, "game", "");
		ArrayList<Game> games = new ArrayList<Game>();
		if (result != "NULL") {
			try {
				JSONArray gamesJSON = new JSONArray(result);
				for (int j = 0; j < gamesJSON.length(); j++) {
					Game g = new Game();
					JSONObject obj = gamesJSON.getJSONObject(j);
					g.setId(obj.getString("id"));
					g.setName(obj.getString("title"));
					g.setFileName(obj.getString("file"));
					g.setImgPath(obj.getString("logo"));
					g.setCategory_id(obj.getString("category_id"));
					games.add(g);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return games;
	}

	public static String makeApiCall(String url, String TAG, String gameName) {

		// Data definition
		HttpClient httpclient = new DefaultHttpClient();
		ResponseHandler<String> handler = new BasicResponseHandler();
		String result = "NULL";
		HttpGet request = null;
		SharedPreferences appSharedPrefs = null;
		// Request&Response
		if (TAG.equalsIgnoreCase("Cat")) {
			try {
				request = new HttpGet(new URI(url));
				// Authentication Layer
				appSharedPrefs = PreferenceManager
						.getDefaultSharedPreferences(ctx);
				Editor prefsEditor = appSharedPrefs.edit();
				String Auth = appSharedPrefs.getString("access_token", "");
				System.out.println("OLD TOK: " + Auth);
				prefsEditor.commit();
				request.addHeader("Authorization", Auth);
				request.addHeader("Content-Type", "application/json");
				request.addHeader("Accept", "application/json");
				result = httpclient.execute(request, handler);
			} catch (Exception e) {

				if (e.getMessage().contains("Unauthorized")
						|| e.getMessage().contains("Bad Request")) {
					e.printStackTrace();
					String newToken = getNewToken();
					System.out.println("New Token: " + newToken);
					request.setHeader("Authorization", newToken);
					Editor prefEditor2 = appSharedPrefs.edit();
					prefEditor2.putString("access_token", newToken);
					prefEditor2.commit();
					try {
						result = httpclient.execute(request, handler);
					} catch (ClientProtocolException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else
					e.printStackTrace();
			}
		}

		else if (TAG.equalsIgnoreCase("game")) {
			try {
				request = new HttpGet(new URI(url));
				// Authentication Layer
				appSharedPrefs = PreferenceManager
						.getDefaultSharedPreferences(ctx);
				Editor prefsEditor = appSharedPrefs.edit();
				String Auth = appSharedPrefs.getString("access_token", "");
				// System.out.println(Auth);
				prefsEditor.commit();
				request.addHeader("Authorization", Auth);
				request.addHeader("Content-Type", "application/json");
				request.addHeader("Accept", "application/json");
				// List<NameValuePair> nameValuePairs = new
				// ArrayList<NameValuePair>();
				// nameValuePairs.add(new BasicNameValuePair("game_id",
				// gameName));
				result = httpclient.execute(request, handler);
			} catch (Exception e) {

				if (e.getMessage().contains("Unauthorized")
						|| e.getMessage().contains("Bad Request")) {
					e.printStackTrace();
					String newToken = getNewToken();
					System.out.println("New Token: " + newToken);
					request.setHeader("Authorization", newToken);
					Editor prefEditor2 = appSharedPrefs.edit();
					prefEditor2.putString("access_token", newToken);
					prefEditor2.commit();
				} else
					e.printStackTrace();
			}
		}

		else if (TAG.equalsIgnoreCase("board")) {
			try {
				request = new HttpGet(new URI(url));
				// Authentication Layer
				appSharedPrefs = PreferenceManager
						.getDefaultSharedPreferences(ctx);
				Editor prefsEditor = appSharedPrefs.edit();
				String Auth = appSharedPrefs.getString("access_token", "");
				prefsEditor.commit();
				request.addHeader("Authorization", Auth);
				request.addHeader("Content-Type", "application/json");
				request.addHeader("Accept", "application/json");
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("game_id", gameName));
				// request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				result = httpclient.execute(request, handler);
			} catch (Exception e) {

				String newToken = getNewToken();
				// System.out.println(newToken);
				request.setHeader("Authorization", newToken);
				Editor prefEditor2 = appSharedPrefs.edit();
				prefEditor2.putString("access_token", newToken);
				prefEditor2.commit();
				e.printStackTrace();
			}
		}

		// Close connection
		httpclient.getConnectionManager().shutdown();

		return result;
	}

	public static String getNewToken() {
		// Data definition
				String AuthLayerUrl = authUrlPrefix + "access_token";
				final TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
				HttpClient httpclient = new DefaultHttpClient();
				ResponseHandler<String> handler = new BasicResponseHandler();
				String result = "NULL";
				String Final = "";
				try {
					// API method parameters
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
					nameValuePairs.add(new BasicNameValuePair("username", "b@c.com"));
					nameValuePairs.add(new BasicNameValuePair("password", "asdasd"));
					nameValuePairs.add(new BasicNameValuePair("grant_type", "client_credentials"));
					nameValuePairs.add(new BasicNameValuePair("client_id", "!@#"));
					nameValuePairs.add(new BasicNameValuePair("client_secret", "asdasd"));
					nameValuePairs.add(new BasicNameValuePair("device_id", tm.getDeviceId()));
					HttpPost request = new HttpPost(new URI(AuthLayerUrl));
					request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					request.addHeader("Content-Type","application/x-www-form-urlencoded");
					request.addHeader("Accept", "application/json");
					result = httpclient.execute(request, handler);
					JSONObject obj = new JSONObject(result);
					Final = obj.getString("access_token");
		            //System.out.println(Final);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
		return Final;

	}

	public HashMap<String, String> loadGameScoreBoard(String gameID) {
		// Data definition
		BoardJsonParameterConverter BJC = new BoardJsonParameterConverter(
				gameID);
		String gameIdJSON = inputJSONConverter.toJson(BJC);
		String urlEncodedParam = null;
		try {
			urlEncodedParam = URLEncoder.encode(gameIdJSON, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String url = urlPrefix + "loadGameScoreBoard?req_data="
				+ urlEncodedParam;
		String result = makeApiCall(url, "board", gameIdJSON);
		HashMap<String, String> scoreBoard = new HashMap<String, String>();
		try {
			JSONArray arr = new JSONArray(result);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				String key = obj.getString("user_id");
				String value = obj.getString("score");
				scoreBoard.put(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scoreBoard;
	}

}
