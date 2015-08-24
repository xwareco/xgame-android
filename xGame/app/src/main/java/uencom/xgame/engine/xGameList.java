package uencom.xgame.engine;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import uencom.xgame.web.Game;
import uencom.xgame.xgame.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
//import android.widget.ImageView;
import android.widget.TextView;

public class xGameList extends ArrayAdapter<Game> {

	private final Activity context;
	ArrayList<Game>games;
	private static final String IMAGE_PREFIX = "http://xgameapp.com/games/";

	public xGameList(Activity context, ArrayList<Game>games) {
		super(context, R.layout.xgame_list_item, games);
		this.context = context;
		this.games = games;
	}

	@SuppressLint({ "ViewHolder", "InflateParams" })
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.xgame_list_item, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.text);
		final ImageView gameIcon = (ImageView) rowView.findViewById(R.id.img);
		txtTitle.setText(games.get(position).getName());
		final String url = IMAGE_PREFIX + games.get(position).getFileName().substring(0 , games.get(position).getFileName().lastIndexOf('.')) + "/" + games.get(position).getImgPath();
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					InputStream is;
					try {
						is = new URL(url).openStream();
						final Drawable logo = Drawable.createFromStream(is, "src");
						context.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								gameIcon.setImageDrawable(logo);
								
							}
						});
						
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});t.start();
			
		
		return rowView;
	}

}
