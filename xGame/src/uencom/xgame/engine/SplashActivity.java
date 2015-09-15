package uencom.xgame.engine;

import java.util.Locale;

import uencom.xgame.xgame.R;
import uencom.xgame.xgame.R.drawable;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {

	LinearLayout trans, bg;
	TextView loading, connerror;
	ImageView refresh;
	Typeface arabic, english;
	ProgressBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.splash_activity);
		connerror = (TextView) findViewById(R.id.textView1);
		loading = (TextView) findViewById(R.id.textView2);
		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(getAssets(),
				"fonts/DJB Stinky Marker.ttf");
		Locale current = getResources().getConfiguration().locale;
		Toast.makeText(this, current.getDisplayLanguage(), Toast.LENGTH_LONG)
				.show();
		if (current.getDisplayLanguage().equals("Arabic")) {
			loading.setTypeface(arabic);
			connerror.setTypeface(arabic);
		} else if (current.getDisplayLanguage().equals("English")) {
			loading.setTypeface(english);
			connerror.setTypeface(english);
		}
		bg = (LinearLayout) findViewById(R.id.mainlay);
		trans = (LinearLayout) findViewById(R.id.seclay);
		refresh = (ImageView) findViewById(R.id.imageView3);
		refresh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				bar.setVisibility(View.VISIBLE);
				refresh.setVisibility(View.GONE);
				connerror.setVisibility(View.GONE);
				loading.setVisibility(View.VISIBLE);
				new Server(getApplicationContext(), refresh,loading ,connerror , bar , null , null).execute("cat");

			}
		});
		bar = (ProgressBar) findViewById(R.id.progressBar1);
		final Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
		fadeIn.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				
				// load the games
				new Server(getApplicationContext(), refresh ,loading , connerror, bar , null , null).execute("cat");
				
			}
		});
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				bg.setBackgroundResource(drawable.bg1);
				trans.setVisibility(View.VISIBLE);
				trans.startAnimation(fadeIn);
				bar.setVisibility(View.VISIBLE);
				loading.setVisibility(View.VISIBLE);
			}
		}, 700);
		super.onCreate(savedInstanceState);
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
