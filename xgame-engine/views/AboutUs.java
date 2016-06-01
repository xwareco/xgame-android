package xware.xgame.engine.views;

import xware.xgame.xgame.R;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class AboutUs extends SherlockActivity {
	ImageView logo;
	LinearLayout lay;
	TextView name , version, xware, mail;
	Typeface english;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.aboutus);
		english = Typeface.createFromAsset(getAssets(),
				"fonts/klavika-regular-opentype.otf");
		lay = (LinearLayout)findViewById(R.id.lay);
		logo = (ImageView)findViewById(R.id.imageView1);
		name = (TextView)findViewById(R.id.textView1);
		version = (TextView)findViewById(R.id.textView2);
		xware = (TextView)findViewById(R.id.textView3);
		mail = (TextView)findViewById(R.id.textView4);
		name.setTypeface(english);
		version.setTypeface(english);
		xware.setTypeface(english);
		mail.setTypeface(english);
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

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

}
