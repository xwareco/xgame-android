package uencom.xgame.engine;

import java.util.ArrayList;

import uencom.xgame.web.Game;
import uencom.xgame.xgame.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
//import android.widget.ImageView;
import android.widget.TextView;

public class xGameList extends ArrayAdapter<Game> {

	private final Activity context;
	ArrayList<Game>games;

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
		//ImageView gameIcon = (ImageView) rowView.findViewById(R.id.img);
		txtTitle.setText(games.get(position).getName());
		//gameIcon.c(games.get(position).getImgPath());
		return rowView;
	}

}
