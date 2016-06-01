package xware.xgame.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.json.JSONObject;

import xware.xgame.engine.web.Game;
import xware.xgame.engine.web.Server;
import xware.xgame.xgame.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
//import android.widget.ImageView;

public class xGameList extends ArrayAdapter<Game> {

	private final Activity context;
	ArrayList<Game> games;
	TableRow item;
	Typeface font;
	private static final String IMAGE_PREFIX = "http://xgameapp.com/games/";

	public xGameList(Activity context, ArrayList<Game> games) {
		super(context, R.layout.xgame_list_item, games);
		this.context = context;
		this.games = games;
		font = Typeface.createFromAsset(context.getAssets(),
				"fonts/klavika-regular-opentype.otf");
		item = (TableRow) context.findViewById(R.id.TR);
		// UNIVERSAL IMAGE LOADER SETUP
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisk(true).cacheInMemory(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new WeakMemoryCache())
				.diskCacheSize(100 * 1024 * 1024).build();

		ImageLoader.getInstance().init(config);
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.xgame_list_item, null, true);
		if (position % 2 == 0)
			rowView.setBackgroundResource(R.drawable.even);
		else
			rowView.setBackgroundResource(R.drawable.odd);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.text);
		txtTitle.setTypeface(font);
		String path = Environment.getExternalStorageDirectory()
				+ "/xGame/Games/" + games.get(position).getName();
		File file = new File(path);
		final ImageView gameIcon = (ImageView) rowView.findViewById(R.id.img);
		String offlineVersion = readFromFile(position);
		ImageView statusIcon = (ImageView) rowView.findViewById(R.id.img2);
		onDeviceGameChecker installations = new onDeviceGameChecker(context);

		if (installations.isOfflineGameExists(games.get(position).getName()) != null
				&& !games.get(position).getVersion().equals(offlineVersion)) {
			rowView.setClickable(false);
			statusIcon.setImageResource(R.drawable.update);
			statusIcon
					.setContentDescription("This game needs to be updated, click to download the latest version");
		} else if (installations.isOfflineGameExists(games.get(position)
				.getName()) != null) {
			rowView.setClickable(false);
			statusIcon.setImageResource(R.drawable.start);
			statusIcon
					.setContentDescription("This game is already installed, click to start playing, or long click to delete");
		}

		else if (installations.isOfflineGameExists(games.get(position)
				.getName()) == null && file.exists()) {
			rowView.setClickable(true);
			rowView.setBackgroundColor(Color.LTGRAY);
			txtTitle.setText(txtTitle.getText().toString()
					+ "(is installing, please wait!)");
			statusIcon.setImageResource(R.drawable.installing);
			statusIcon
					.setContentDescription("This game is being installed, please wait");
		}

		else {
			rowView.setClickable(false);
			statusIcon.setImageResource(R.drawable.download);
			statusIcon
					.setContentDescription("This game is not yet installed, click to install");
		}

		if (position != games.size()) {
			txtTitle.setText(games.get(position).getName());
			ImageLoader imageLoader = ImageLoader.getInstance();
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.cacheInMemory(true).cacheOnDisk(true)
					.resetViewBeforeLoading(true).build();
			final String url = IMAGE_PREFIX
					+ games.get(position)
							.getFileName()
							.substring(
									0,
									games.get(position).getFileName()
											.lastIndexOf('.')) + "/"
					+ games.get(position).getImgPath();
			imageLoader.displayImage(url, gameIcon, options);
		} else {
			new Server(context, null, null, null, null, null, null, null)
					.execute("game", games.get(0).getCategory_id(),
							games.get(games.size() - 1).getId());
			SharedPreferences appSharedPrefs = PreferenceManager
					.getDefaultSharedPreferences(context);
			Editor prefsEditor = appSharedPrefs.edit();
			String gamesJSON = appSharedPrefs.getString("games", "");
			prefsEditor.commit();
			Gson g = new Gson();
			java.lang.reflect.Type type = new TypeToken<ArrayList<Game>>() {
			}.getType();
			ArrayList<Game> catGames = g.fromJson(gamesJSON,
					(java.lang.reflect.Type) type);
			games.addAll(catGames);
		}
		return rowView;
	}

	private String readFromFile(int position) {
		File userDataFile = new File(Environment.getExternalStorageDirectory()
				+ "/xGame/Games/" + games.get(position).getName()
				+ "/manifest.json");
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
				JSONObject jO = new JSONObject(ret);
				ret = jO.getString("version");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ret;
	}
}
