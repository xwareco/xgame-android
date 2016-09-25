package xware.xgame.engine.views;

import java.util.Locale;

import xware.xgame.xgame.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {
	ImageView logo;
	LinearLayout lay;
	TextView name , version, xware, mail;
	Typeface english, arabic;
	Toolbar actionBar;
	SharedPreferences appSharedPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.aboutus);
		actionBar = (Toolbar) findViewById(R.id.my_toolbar);
		setSupportActionBar(actionBar);
		appSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(AboutUs.this);
		english = Typeface.createFromAsset(getAssets(),
				"fonts/klavika-regular-opentype.otf");
		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		lay = (LinearLayout)findViewById(R.id.lay);
		logo = (ImageView)findViewById(R.id.imageView1);
		name = (TextView)findViewById(R.id.textView1);
		version = (TextView)findViewById(R.id.textView2);
		xware = (TextView)findViewById(R.id.textView3);
		mail = (TextView)findViewById(R.id.textView4);
		String lang = appSharedPrefs.getString("Lang","EN");
		if (lang.equals("AR")) {
			name.setTypeface(arabic);
			version.setTypeface(arabic);
			xware.setTypeface(arabic);
			mail.setTypeface(english);
		}
		else if (lang.equals("EN")) {
			name.setTypeface(english);
			version.setTypeface(english);
			xware.setTypeface(english);
			mail.setTypeface(english);
		}
		
		Animation down = AnimationUtils.loadAnimation(this, R.anim.full_left);
		final Animation fadeIn2 = AnimationUtils.loadAnimation(this, R.anim.fadein);
		down.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				version.setVisibility(View.VISIBLE);
				xware.setVisibility(View.VISIBLE);
				mail.setVisibility(View.VISIBLE);
				version.startAnimation(fadeIn2);
				xware.startAnimation(fadeIn2);
				mail.startAnimation(fadeIn2);
				
			}
		});
		
		lay.startAnimation(down);
		/*ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(true);*/
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onPause() {
		finish();
		overridePendingTransition(R.anim.transition8, R.anim.transition7);
		super.onPause();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		

		if (item.getItemId() == R.id.action_testhead) {
			Intent I = new Intent(getApplicationContext(),
					HeadphoneTester.class);
			startActivity(I);
		}

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

}
