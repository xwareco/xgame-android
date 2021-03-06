package xware.xgame.engine.views;

import java.util.Locale;

import xware.xgame.engine.web.Server;
import xware.xgame.gestures.HandGestures;
import xware.xgame.xgame.R;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {

	LinearLayout trans, bg;
	TextView loading, connerror;
	ImageView refresh, offline;
	ListView offlineGames;
	Typeface arabic, english;
	boolean offlineClicked;
	HandGestures HG;
	ProgressBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.splash_activity);
		offlineClicked = false;

		connerror = (TextView) findViewById(R.id.textView1);
		loading = (TextView) findViewById(R.id.textView2);
		bar = (ProgressBar) findViewById(R.id.progressBar1);
		offlineGames = (ListView) findViewById(R.id.listView1);
		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(getAssets(),
				"fonts/klavika-regular-opentype.otf");
		Locale current = getResources().getConfiguration().locale;
		if (current.getDisplayLanguage().equals("Arabic")) {
			loading.setTypeface(arabic);
			connerror.setTypeface(arabic);
		} else if (current.getDisplayLanguage().equals("English")) {
			loading.setTypeface(english);
			connerror.setTypeface(english);
		}
		bg = (LinearLayout) findViewById(R.id.mainlay);
		trans = (LinearLayout) findViewById(R.id.seclay);
		offline = (ImageView) findViewById(R.id.imageView4);
		offline.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				offlineGames.setVisibility(View.VISIBLE);
				offline.setVisibility(View.GONE);
				connerror.setText(R.string.offlinestate);
				offlineClicked = true;
				Toast.makeText(SplashActivity.this, "Swipe down to refresh", Toast.LENGTH_LONG).show();

			}
		});
		refresh = (ImageView) findViewById(R.id.imageView3);
		refresh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				bar.setVisibility(View.VISIBLE);
				refresh.setVisibility(View.GONE);
				offlineGames.setVisibility(View.GONE);
				offline.setVisibility(View.GONE);
				connerror.setVisibility(View.GONE);
				loading.setVisibility(View.VISIBLE);
				new Server(SplashActivity.this, refresh, offline, loading,
						connerror, bar, null, offlineGames).execute("cat");

			}
		});

		HG = new HandGestures(this) {
			@Override
			public void onSwipeDown() {
				if (offlineClicked == true) {
					bar.setVisibility(View.VISIBLE);
					refresh.setVisibility(View.GONE);
					offlineGames.setVisibility(View.GONE);
					offline.setVisibility(View.GONE);
					connerror.setVisibility(View.GONE);
					loading.setVisibility(View.VISIBLE);
					new Server(SplashActivity.this, refresh, offline, loading,
							connerror, bar, null, offlineGames).execute("cat");
				}
				offlineClicked = false;
				super.onSwipeDown();
			}
		};
		final Animation fadeIn = AnimationUtils.loadAnimation(this,
				R.anim.fadein);
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
				new Server(SplashActivity.this, refresh, offline, loading,
						connerror, bar, null, offlineGames).execute("cat");

			}
		});
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				bg.setBackgroundResource(R.drawable.bg1);
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

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		HG.OnTouchEvent(event);
		return super.onTouchEvent(event);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		HG.OnTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

}
