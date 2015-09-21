package uencom.xgame.engine;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Environment;
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
	ImageView refresh, offline;
	TextView loading, connError;
	ProgressBar bar;
	LinearLayout trans;
	ListView gamesView;

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
	}

	@Override
	protected String doInBackground(String... arg0) {
		String res = "off";
		if (isOnline() == true) {
			res = "cat";
			if (arg0[0].equalsIgnoreCase("cat"))
				categories = api.getCategories();
			else if (arg0[0].equalsIgnoreCase("game")) {
				games = api.getGames(arg0[1], "10", arg0[2]);
				System.out.println(games.get(0).getName());
				Gson g = new Gson();
				String gamesJSON = g.toJson(games);
				System.out.println("Iam In!!!");
				appSharedPrefs = PreferenceManager
						.getDefaultSharedPreferences(ctx);
				Editor prefEditor2 = appSharedPrefs.edit();
				prefEditor2.putString("games", gamesJSON);
				prefEditor2.commit();
				res = "games";
			}
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
				mp = MediaPlayer.create(ctx, uencom.xgame.xgame.R.raw.done);
				mp.start();
				ctx.startActivity(I);
			} else {
				mp = MediaPlayer.create(ctx, uencom.xgame.xgame.R.raw.failed);
				mp.start();
				offlinexGameList xgameAdapter = isOfflineGameExists();
				if (xgameAdapter != null) {
					offline.setVisibility(View.VISIBLE);
					gamesView.setAdapter(xgameAdapter);
				}
				bar.setVisibility(View.GONE);
				loading.setVisibility(View.GONE);
				connError.setVisibility(View.VISIBLE);
				refresh.setVisibility(View.VISIBLE);
			}
		} else if (result.equalsIgnoreCase("off")) {
			mp = MediaPlayer.create(ctx, uencom.xgame.xgame.R.raw.failed);
			mp.start();
			offlinexGameList xgameAdapter = isOfflineGameExists();
			if (xgameAdapter != null) {
				offline.setVisibility(View.VISIBLE);
				gamesView.setAdapter(xgameAdapter);
			}
			bar.setVisibility(View.GONE);
			loading.setVisibility(View.GONE);
			connError.setVisibility(View.VISIBLE);
			refresh.setVisibility(View.VISIBLE);
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

	private offlinexGameList isOfflineGameExists() {
		offlinexGameList res = null;
		ArrayList<String> offGames = new ArrayList<String>();
		String searchLocation = Environment.getExternalStorageDirectory()
				+ "/xGame/games/";
		File gamesFolder = new File(searchLocation);
		ArrayList<File> folderContents = new ArrayList<File>(
				Arrays.asList(gamesFolder.listFiles()));

		for (int i = 0; i < folderContents.size(); i++) {
			File mainGameFolder = folderContents.get(i);
			ArrayList<File> game = new ArrayList<File>(
					Arrays.asList(mainGameFolder.listFiles()));
			boolean xmlFileExists = false, imagesFoldeNotEmpty = false, soundFoldeNotEmpty = false, sourceFoldeNotEmpty = false;
			String xmlFileName = "App.xml";
			for (int j = 0; j < game.size(); j++) {
				if (game.get(j).getName().equals(xmlFileName))
					xmlFileExists = true;
				else if (game.get(j).getName().equals("Images")
						&& game.get(j).isDirectory()
						&& game.get(j).list().length > 0)
					imagesFoldeNotEmpty = true;
				else if (game.get(j).getName().equals("Sound")
						&& game.get(j).isDirectory()
						&& game.get(j).list().length > 0)
					soundFoldeNotEmpty = true;
				else if (game.get(j).getName().equals("Source")
						&& game.get(j).isDirectory()
						&& game.get(j).list().length > 0)
					sourceFoldeNotEmpty = true;
			}

			if (xmlFileExists && imagesFoldeNotEmpty && soundFoldeNotEmpty
					&& sourceFoldeNotEmpty) {
				offGames.add(mainGameFolder.getName());
			}

			else {
				mainGameFolder.delete();
			}

			if (i == folderContents.size() - 1 && offGames.size() > 0) {
				res = new offlinexGameList((Activity) ctx, offGames);

			}
		}

		return res;
	}

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}
}
