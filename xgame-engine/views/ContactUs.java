package xware.xgame.engine.views;

import java.util.Locale;

import xware.xgame.xgame.R;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ContactUs extends SherlockActivity {
	TextView title;
	ImageView voice, text;
	Typeface arabic, english;
	Animation a;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.contactus);
		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(getAssets(),
				"fonts/klavika-regular-opentype.otf");
		Locale current = getResources().getConfiguration().locale;
		a = AnimationUtils.loadAnimation(this, R.anim.fadein);
		title = (TextView) findViewById(R.id.textView3);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		voice = (ImageView) findViewById(R.id.imageView2);
		text = (ImageView) findViewById(R.id.imageView1);
		text.startAnimation(a);
		voice.startAnimation(a);
		if (current.getDisplayLanguage().equals("Arabic")) {
			title.setTypeface(arabic);
		} else if (current.getDisplayLanguage().equals("English")) {
			title.setTypeface(english);
			
		}
		voice.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent I = new Intent(getApplicationContext(),
						AudioRecorder.class);
				startActivity(I);

			}
		});
		text.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent I = new Intent(getApplicationContext(),
						TextMessageSender.class);
				startActivity(I);

			}
		});
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {

		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.actionbar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		 * if (item.getItemId() == android.R.id.home) {
		 * 
		 * if (mDrawerLayout.isDrawerOpen(rellay)) {
		 * mDrawerLayout.closeDrawer(rellay); } else {
		 * mDrawerLayout.openDrawer(rellay); } }
		 */

		if (item.getItemId() == R.id.action_testhead) {
			Intent I = new Intent(getApplicationContext(),
					HeadphoneTester.class);
			startActivity(I);
		}
		else if (item.getItemId() == android.R.id.home) {
			finish();
			overridePendingTransition(R.anim.transition8, R.anim.transition7);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.transition8, R.anim.transition7);
		super.onBackPressed();
	}

}
