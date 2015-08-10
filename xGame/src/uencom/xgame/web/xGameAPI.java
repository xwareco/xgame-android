package uencom.xgame.web;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

public class xGameAPI {

	private ArrayList<GameCategory> categories;
	private static Context ctx;
	private String urlPrefix;
	private static String authUrlPrefix;

	public xGameAPI(Context c) {
		categories = new ArrayList<GameCategory>();
		ctx = c;
		urlPrefix = "http://xgameapp.com/api/v1/";
		authUrlPrefix = "http://xgameapp.com/oauth/";
		
	}

	public ArrayList<GameCategory> getCategoriesAndGames() {

		// http://192.168.1.102/xgame-app/public/api/v1/getCategoriesAndGames
		// http://xgameapp.com/api/v1/getCategoriesAndGames
		// OS0hDcGVwyCKcnVaoP9rXAWq7Cpy5H0MAQjGISOW

		// Data definition
		String url = urlPrefix + "getCategoriesAndGames";
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

					JSONArray games = cat.getJSONArray("games");
					ArrayList<Game> gamesList = new ArrayList<Game>();
					for (int j = 0; j < games.length(); j++) {
						Game g = new Game();
						JSONObject obj = games.getJSONObject(j);
						g.setId(obj.getString("id"));
						g.setName(obj.getString("title"));
						g.setImgPath(obj.getString("logo"));
						g.setUrl(obj.getString("apk"));
						gamesList.add(g);
					}
					category.setGames(gamesList);
					categories.add(category);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return categories;
	}

	public static String makeApiCall(String url, String TAG, String gameName) {

		// Data definition
		HttpClient httpclient = new DefaultHttpClient();
		ResponseHandler<String> handler = new BasicResponseHandler();
		String result = "NULL";
		HttpPost request = null;
		SharedPreferences appSharedPrefs = null;
		// Request&Response
		if (TAG.equalsIgnoreCase("Cat")) {
			try {
				request = new HttpPost(new URI(url));
				// Authentication Layer
				appSharedPrefs = PreferenceManager
						.getDefaultSharedPreferences(ctx);
				Editor prefsEditor = appSharedPrefs.edit();
				String Auth = appSharedPrefs.getString("access_token", "");
				//System.out.println(Auth);
				prefsEditor.commit();
				request.addHeader("Authorization", Auth);
				request.addHeader("Content-Type",
						"application/x-www-form-urlencoded");
				request.addHeader("Accept", "application/json");
				result = httpclient.execute(request, handler);
			} catch (Exception e) {
				
				if(e.getMessage().contains("Unauthorized") || e.getMessage().contains("Bad Request"))
				{
				String newToken = getNewToken();
				System.out.println("New Token: " + newToken);
				request.setHeader("Authorization", newToken);
				Editor prefEditor2 = appSharedPrefs.edit();
				prefEditor2.putString("access_token", newToken);
				prefEditor2.commit();
				}
				else
				e.printStackTrace();
			}
		}

		else if (TAG.equalsIgnoreCase("board")) {
			try {
				request = new HttpPost(new URI(url));
				// Authentication Layer
				appSharedPrefs = PreferenceManager
						.getDefaultSharedPreferences(ctx);
				Editor prefsEditor = appSharedPrefs.edit();
				String Auth = appSharedPrefs.getString("access_token", "");
				prefsEditor.commit();
				request.addHeader("Authorization", Auth);
				request.addHeader("Content-Type",
						"application/x-www-form-urlencoded");
				request.addHeader("Accept", "application/json");
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("game_id", gameName));
				request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				result = httpclient.execute(request, handler);
			} catch (Exception e) {

				String newToken = getNewToken();
				//System.out.println(newToken);
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

	public HashMap<String, String> loadGameScoreBoard(String gameName) {
		// Data definition
		String url = urlPrefix + "loadGameScoreBoard";
		String result = makeApiCall(url, "board", gameName);
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
