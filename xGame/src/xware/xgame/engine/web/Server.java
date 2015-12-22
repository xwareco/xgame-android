package xware.xgame.engine.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import xware.xgame.engine.offlinexGameList;
import xware.xgame.engine.onDeviceGameChecker;
import xware.xgame.engine.xGameList;
import xware.xgame.engine.xGameParser;
import xware.xgame.engine.views.GameView;
import xware.xgame.engine.views.MainView;
import xware.xgame.xgame.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class Server extends AsyncTask<String, String, String> implements
		Serializable {

	ArrayList<GameCategory> categories;
	ArrayList<Game> games;
	SharedPreferences appSharedPrefs;
	xGameAPI api;
	Context ctx;
	ImageView refresh, offline;
	TextView loading, connError;
	ProgressBar bar;
	LinearLayout trans;
	ListView gamesView;
	String testFolder;
	onDeviceGameChecker checkInstallations;

	public Server(Context C, ImageView imgv, ImageView imgv2, TextView tv1,
			TextView tv2, ProgressBar b, LinearLayout lay, ListView lv) {
		ctx = C;
		api = new xGameAPI(ctx);
		refresh = imgv;
		offline = imgv2;
		loading = tv1;
		connError = tv2;
		bar = b;
		trans = lay;
		gamesView = lv;
		checkInstallations = new onDeviceGameChecker(ctx);
		testFolder = Environment.getExternalStorageDirectory().toString()
				+ "/xGame/Games/Car Racer";
	}

	@Override
	protected String doInBackground(String... arg0) {
		String res = "off";
		if (checkInstallations.isOnline() == true) {
			res = "cat";
			if (arg0[0].equalsIgnoreCase("cat")) {
				categories = api.getCategories();
				Collections.sort(categories, new Comparator<GameCategory>() {

					@Override
					public int compare(GameCategory lhs, GameCategory rhs) {
						return lhs.getName().compareTo(rhs.getName());
					}

				});
			} else if (arg0[0].equalsIgnoreCase("game")) {
				games = api.getGames(arg0[1], "10", arg0[2]);
				Collections.sort(games, new Comparator<Game>() {

					@Override
					public int compare(Game lhs, Game rhs) {
						return lhs.getName().compareTo(rhs.getName());
					}

				});
				System.out.println("DATA2:" + arg0[1] + " " + arg0[2]);
				Gson g = new Gson();
				String gamesJSON = g.toJson(games);
				System.out.println("Iam In!!!");
				appSharedPrefs = PreferenceManager
						.getDefaultSharedPreferences(ctx);
				Editor prefEditor2 = appSharedPrefs.edit();
				prefEditor2.putString("games", gamesJSON);
				prefEditor2.commit();
				res = "games";
			} else if (arg0[0].equalsIgnoreCase("test"))
				res = "test";
		}
		return res;
	}

	@Override
	protected void onPostExecute(String result) {
		System.out.println(result);
		MediaPlayer mp;
		if (result.equalsIgnoreCase("cat")) {
			Gson g = new Gson();
			String catStr = g.toJson(categories);
			// System.out.println(catStr);
			if (!catStr.equals("[]")) {
				Intent I = new Intent(ctx, MainView.class);
				I.putExtra("catJSON", catStr);
				I.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mp = MediaPlayer.create(ctx, R.raw.done);
				mp.start();
				ctx.startActivity(I);
				Activity act = (Activity) ctx;
				act.overridePendingTransition(R.anim.transition5,
						R.anim.transition4);
			} else {
				mp = MediaPlayer.create(ctx, R.raw.failed);
				mp.start();
				offlinexGameList xgameAdapter = checkInstallations
						.isOfflineGameExists("any");
				if (xgameAdapter != null) {
					offline.setVisibility(View.VISIBLE);
					gamesView.setAdapter(xgameAdapter);
				}
				bar.setVisibility(View.GONE);
				loading.setVisibility(View.GONE);
				loading.setText("Loading ..");
				connError.setVisibility(View.VISIBLE);
				refresh.setVisibility(View.VISIBLE);
			}
		} else if (result.equalsIgnoreCase("off")) {
			mp = MediaPlayer.create(ctx, R.raw.failed);
			mp.start();
			final offlinexGameList xgameAdapter = checkInstallations
					.isOfflineGameExists("any");
			if (xgameAdapter != null) {
				offline.setVisibility(View.VISIBLE);
				gamesView.setAdapter(xgameAdapter);
				gamesView
						.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								String gameName = xgameAdapter.getItem(arg2);
								String offlinePath = Environment
										.getExternalStorageDirectory()
										.toString()
										+ "/xGame/Games/" + gameName;
								Intent I = new Intent(ctx, xGameParser.class);
								I.putExtra("Folder", offlinePath);
								I.putExtra("gamename", gameName);
								ctx.startActivity(I);
								Activity act = (Activity) ctx;
								act.overridePendingTransition(
										R.anim.transition5, R.anim.transition4);

							}
						});
			}
			bar.setVisibility(View.GONE);
			loading.setVisibility(View.GONE);
			loading.setText("Loading ..");
			connError.setVisibility(View.VISIBLE);
			refresh.setVisibility(View.VISIBLE);
		} else if (result.equalsIgnoreCase("test")) {
			Intent I = new Intent(ctx, GameView.class);
			I.putExtra("Folder", testFolder);
			I.putExtra("Name", "Goal");
			I.putExtra("gamename", "Car Racer");
			ctx.startActivity(I);
			Activity act = (Activity) ctx;
			act.overridePendingTransition(R.anim.transition5,
					R.anim.transition4);
		} else {

			if (bar != null && loading != null && trans != null
					&& gamesView != null) {
				System.out.println("Iam here here!!");
				bar.setVisibility(View.GONE);
				loading.setVisibility(View.GONE);
				loading.setText("Loading..");
				trans.setVisibility(View.GONE);
				if (games != null) {

					Activity act = (Activity) ctx;
					xGameList xgameAdapter = new xGameList(act, games);
					gamesView.setAdapter(xgameAdapter);
					gamesView.setVisibility(View.VISIBLE);
					mp = MediaPlayer.create(ctx, R.raw.done);
					mp.start();
				}
			}
		}
		super.onPostExecute(result);
	}

}
