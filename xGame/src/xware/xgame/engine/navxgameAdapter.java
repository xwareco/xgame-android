package xware.xgame.engine;

import java.util.ArrayList;
import java.util.Locale;

import uencom.xgame.xgame.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class navxgameAdapter extends BaseAdapter {

	Context context;
	ArrayList<navxgameList> mNavItems;
	Typeface arabic, english;
	Locale current;

	public navxgameAdapter(Context context, ArrayList<navxgameList> navItems) {
		this.context = context;
		mNavItems = navItems;
		arabic = Typeface.createFromAsset(context.getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(context.getAssets(),
				"fonts/DJB Stinky Marker.ttf");
		current = context.getResources().getConfiguration().locale;
	}

	@Override
	public int getCount() {
		return mNavItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mNavItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.drawer_item, null);
		} else {
			view = convertView;
		}

		TextView titleView = (TextView) view.findViewById(R.id.title);
		ImageView iconView = (ImageView) view.findViewById(R.id.icon);
		ImageView editIconView = (ImageView) view.findViewById(R.id.editicon);
		if (current.getDisplayLanguage().equals("Arabic")) {
			titleView.setTypeface(arabic);
		} else if (current.getDisplayLanguage().equals("English")) {
			titleView.setTypeface(english);
		}
		titleView.setText(mNavItems.get(position).title);
		iconView.setImageResource(mNavItems.get(position).icon);
		
		if(position == 1 && !mNavItems.get(1).title.equalsIgnoreCase("register"))
		{
			System.out.println(position + " " + mNavItems.get(1).title);
			editIconView.setVisibility(View.VISIBLE);
		}
		if(position == 0)
			editIconView.setVisibility(View.GONE);
		return view;
	}
}
