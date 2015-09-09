package uencom.xgame.engine;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.Gson;

import uencom.xgame.engine.web.Game;
import uencom.xgame.engine.web.GameCategory;
import uencom.xgame.engine.web.xGameAPI;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressWarnings("serial")
public class Server extends AsyncTask<String, String, String> implements
		Serializable {

	ArrayList<GameCategory> categories;
	ArrayList<Game> games;
	SharedPreferences appSharedPrefs;
	xGameAPI api;
	Context ctx;
	TextView label;
	ImageView logo, error, refresh;
	ProgressBar bar;

	public Server(Context C, TextView tv, ImageView imgv, ImageView imgv2,
			ImageView imgv3, ProgressBar b) {
		ctx = C;
		api = new xGameAPI(ctx);
		label = tv;
		logo = imgv;
		error = imgv2;
		refresh = imgv3;
		bar = b;
	}

	@Override
	protected String doInBackground(String... arg0) {
		String res = "cat";
		if (arg0[0].equalsIgnoreCase("cat"))
			categories = api.getCategories();
		else if (arg0[0].equalsIgnoreCase("game")) {
			games = api.getGames(arg0[1],"10",arg0[2]);
			Gson g = new Gson();
			String gamesJSON = g.toJson(games);
			System.out.println("Iam In!!!");
			appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(ctx);
			Editor prefEditor2 = appSharedPrefs.edit();
			prefEditor2.putString("games", gamesJSON);
			prefEditor2.commit();
			res = "games";
		}
		return res;
	}

	@Override
	protected void onPostExecute(String result) {
		System.out.println(result);
		if (result.equalsIgnoreCase("cat")) {
			Gson g = new Gson();
			String catStr = g.toJson(categories);
			MediaPlayer mp;
			// System.out.println(catStr);
			if (!catStr.equals("[]")) {
				Intent I = new Intent(ctx, MainView.class);
				I.putExtra("catJSON", catStr);
				I.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mp = MediaPlayer.create(ctx, uencom.xgame.xgame.R.raw.done);
				mp.start();
				ctx.startActivity(I);
			} else {
				mp = MediaPlayer.create(ctx, uencom.xgame.xgame.R.raw.failed);
				mp.start();
				bar.setVisibility(View.GONE);
				logo.setVisibility(View.GONE);
				error.setVisibility(View.VISIBLE);
				refresh.setVisibility(View.VISIBLE);
				label.setGravity(Gravity.CENTER_HORIZONTAL);
				label.setText("Error conecting to xGame.");
			}
		} else {
			
		}
		super.onPostExecute(result);
	}

}
