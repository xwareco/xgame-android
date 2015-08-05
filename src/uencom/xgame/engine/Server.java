package uencom.xgame.engine;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.Gson;
import uencom.xgame.web.GameCategory;
import uencom.xgame.web.xGameAPI;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressWarnings("serial")
public class Server extends AsyncTask<String, Void, Void> implements
		Serializable {

	ArrayList<GameCategory> categories;
	xGameAPI api;
	Context ctx;
	TextView label;
	ImageView logo , error , refresh;
	ProgressBar bar;

	public Server(Context C, TextView tv, ImageView imgv, ImageView imgv2 , ImageView imgv3 , ProgressBar b) {
		ctx = C;
		api = new xGameAPI(ctx);
		label = tv;
		logo = imgv;
		error = imgv2;
		refresh = imgv3;
		bar = b;
	}

	@Override
	protected Void doInBackground(String... arg0) {
		categories = api.getCategoriesAndGames();
		// api.loadGameScoreBoard("1");
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		Gson g = new Gson();
		String catStr = g.toJson(categories);
		MediaPlayer mp;
		//System.out.println(catStr);
		if (!catStr.equals("[]")) {
			Intent I = new Intent(ctx, MainView.class);
			I.putExtra("catJSON", catStr);
			I.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mp = MediaPlayer.create(ctx,uencom.xgame.xgame.R.raw.done);
			mp.start();
			ctx.startActivity(I);
		}
		else
		{
			mp = MediaPlayer.create(ctx,uencom.xgame.xgame.R.raw.failed);
			mp.start();
			bar.setVisibility(View.GONE);
			logo.setVisibility(View.GONE);
			error.setVisibility(View.VISIBLE);
			refresh.setVisibility(View.VISIBLE);
			label.setGravity(Gravity.CENTER_HORIZONTAL);
			label.setText("Error conecting to xGame.");
		}
		super.onPostExecute(result);
	}

}
