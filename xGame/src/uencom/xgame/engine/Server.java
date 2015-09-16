package uencom.xgame.engine;

import java.io.Serializable;
import java.util.ArrayList;
import com.google.gson.Gson;
import uencom.xgame.engine.web.Game;
import uencom.xgame.engine.web.GameCategory;
import uencom.xgame.engine.web.xGameAPI;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
	ImageView refresh;
	TextView loading, connError;
	ProgressBar bar;
	LinearLayout trans;
	ListView gamesView;

	public Server(Context C, ImageView imgv, TextView tv1, TextView tv2,
			ProgressBar b, LinearLayout lay, ListView lv) {
		ctx = C;
		api = new xGameAPI(ctx);
		refresh = imgv;
		loading = tv1;
		connError = tv2;
		bar = b;
		trans = lay;
		gamesView = lv;
	}

	@Override
	protected String doInBackground(String... arg0) {
		String res = "cat";
		if (arg0[0].equalsIgnoreCase("cat"))
			categories = api.getCategories();
		else if (arg0[0].equalsIgnoreCase("game")) {
			games = api.getGames(arg0[1], "10", arg0[2]);
			System.out.println(games.get(0).getName());
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
				loading.setVisibility(View.GONE);
				connError.setVisibility(View.VISIBLE);
				refresh.setVisibility(View.VISIBLE);
			}
		} else {
			System.out.println("Iam here!!");
			if (bar != null && loading != null && trans != null
					&& gamesView != null) {

				bar.setVisibility(View.GONE);
				loading.setVisibility(View.GONE);
				trans.setVisibility(View.GONE);
				if (games != null) {

					Activity act = (Activity) ctx;
					xGameList xgameAdapter = new xGameList(act, games);
					gamesView.setAdapter(xgameAdapter);
					gamesView.setVisibility(View.VISIBLE);
				}
			}
		}
		super.onPostExecute(result);
	}

}
