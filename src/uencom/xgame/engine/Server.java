package uencom.xgame.engine;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.Gson;

import uencom.xgame.web.GameCategory;
import uencom.xgame.web.xGameAPI;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

@SuppressWarnings("serial")
public class Server extends AsyncTask<String, Void, Void> implements Serializable{

	ArrayList<GameCategory> categories;
	xGameAPI api;
	Context ctx;
	
	public Server(Context C)
	{
		ctx = C;
		api = new xGameAPI(ctx);
	}
	@Override
	protected Void doInBackground(String... arg0) {
		categories = api.getCategoriesAndGames();
		//api.loadGameScoreBoard("1");
		return null;
	}
	@Override
	protected void onPostExecute(Void result) {
	    Gson g = new Gson();
	    String catStr = g.toJson(categories);
	    Intent I = new Intent(ctx , MainView.class);
	    I.putExtra("catJSON", catStr);
	    ctx.startActivity(I);
		super.onPostExecute(result);
	}
	

}
