package uencom.xgame.engine.views;

import java.util.Locale;

import uencom.xgame.xgame.R;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class GameOver extends SherlockActivity {
	
	ImageView gamescore, tryAgain;
	TextView gameName;
	Typeface english;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.game_over_view);
		english = Typeface.createFromAsset(getAssets(),
				"fonts/DJB Stinky Marker.ttf");
		Locale current = getResources().getConfiguration().locale;
		gameName = (TextView) findViewById(R.id.textView1);
		if (current.getDisplayLanguage().equals("English")) {
			gameName.setTypeface(english);
		}
		gamescore = (ImageView) findViewById(R.id.imageView3);
		tryAgain = (ImageView) findViewById(R.id.imageView2);
		Animation a = AnimationUtils.loadAnimation(this, R.anim.transition4);
		//int Score = getIntent().getIntExtra("Score", 0);
		gamescore.startAnimation(a);
		gameName.setText(getIntent().getStringExtra("gamename"));
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		super.onCreate(savedInstanceState);
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
	protected void onPause() {
		finish();
		super.onPause();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
}
