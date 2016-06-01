package xware.xgame.engine.views;

import java.util.Locale;

import xware.xgame.engine.xGameParser;
import xware.xgame.gestures.HandGestures;
import xware.xgame.xgame.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class GameView extends SherlockActivity {

	HandGestures HG;
	ImageView gameImg, play, home;
	TextView gameName;
	AccessibilityManager manager;
	LinearLayout main;
	Typeface english;
	String name, gameID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.game_view);
		name = getIntent().getStringExtra("Name");
		gameID = getIntent().getStringExtra("gameid");
		SharedPreferences appSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		Editor ed = appSharedPrefs.edit();
		ed.putString("game", name);
		ed.commit();
		System.out.println("Game_View: " + name);
		english = Typeface.createFromAsset(getAssets(),
				"fonts/klavika-regular-opentype.otf");
		Locale current = getResources().getConfiguration().locale;
		play = (ImageView) findViewById(R.id.img);
		home = (ImageView) findViewById(R.id.img2);

		home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.transition8,
						R.anim.transition7);
			}
		});

		gameImg = (ImageView) findViewById(R.id.imageView1);

		gameName = (TextView) findViewById(R.id.textView1);
		if (current.getDisplayLanguage().equals("English")) {
			gameName.setTypeface(english);
		}
		gameName.setText(name);

		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent I = new Intent(getApplicationContext(),
						xGameParser.class);
				I.putExtra("Folder", getIntent().getStringExtra("Folder"));
				I.putExtra("gamename", name);
				I.putExtra("gameid", gameID);
				startActivity(I);
				finish();
				overridePendingTransition(R.anim.transition10,
						R.anim.transition9);

			}
		});
		gameImg.setImageURI(Uri.parse(Environment.getExternalStorageDirectory()
				.toString()
				+ "/xGame/Games/"
				+ getIntent().getStringExtra("Name") + "/Images/logo.png"));
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		main = (LinearLayout) findViewById(R.id.gameLay);
		HG = new HandGestures(getApplicationContext()) {
			@Override
			public void onSwipeRight() {
				Toast.makeText(getApplicationContext(), "Right Swipe!",
						Toast.LENGTH_LONG).show();
				super.onSwipeRight();
			}
		};
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}
	
	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.transition8, R.anim.transition7);
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {

		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.actionbar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent I = new Intent(this, MainView.class);
			startActivity(I);
			break;
		default:

			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		HG.OnTouchEvent(event);
		// Be sure to call the superclass implementation
		return main.onTouchEvent(event);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
}
