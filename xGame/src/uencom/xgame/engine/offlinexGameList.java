package uencom.xgame.engine;

import java.util.ArrayList;
import uencom.xgame.xgame.R;
import uencom.xgame.xgame.R.drawable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;

public class offlinexGameList extends ArrayAdapter<String> {

	private final Activity context;
	ArrayList<String> games;
	TableRow item;
	Typeface font;
	public offlinexGameList(Activity context, ArrayList<String> games) {
		super(context, R.layout.xgame_list_item, games);
		this.games = games;
		this.context = context;
		font = Typeface.createFromAsset(context.getAssets(), "fonts/DJB Stinky Marker.ttf");
		item = (TableRow)context.findViewById(R.id.TR);
	}
	@SuppressLint({ "ViewHolder", "InflateParams" }) @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.xgame_list_item, null, true);
		if(position%2 == 0)rowView.setBackgroundResource(drawable.even);
		else rowView.setBackgroundResource(drawable.odd);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.text);
		txtTitle.setTypeface(font);
		txtTitle.setText(games.get(position));
		
		return rowView;
	}

}
