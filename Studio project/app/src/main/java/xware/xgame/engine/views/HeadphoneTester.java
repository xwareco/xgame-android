package xware.xgame.engine.views;

import java.util.Locale;

import xware.engine_lib.gestures.HandGestures;
import xware.engine_lib.sound.HeadPhone;
import xware.xgame.xgame.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class HeadphoneTester extends Activity {
	HandGestures activityGesture;
	TextView instrcts;
	Typeface arabic, english;
	Animation fadeIn;
	String whichHeadPhoneToTest = "right";
	SharedPreferences appSharedPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.headphonetest);
		instrcts = (TextView) findViewById(R.id.textView1);
		appSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(HeadphoneTester.this);
		fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
		instrcts.startAnimation(fadeIn);
		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(getAssets(),
				"fonts/klavika-regular-opentype.otf");
		String lang = appSharedPrefs.getString("Lang","EN");
		if (lang.equals("AR")) {
			instrcts.setTypeface(arabic);
		} else if (lang.equals("EN")) {
			instrcts.setTypeface(english);
		}
		activityGesture = new HandGestures(this) {
			@Override
			public void onSwipeDown() {
				finish();
				super.onSwipeDown();
			}

			@Override
			public void onTapOnce() {
				String lang = appSharedPrefs.getString("Lang","EN");
				HeadPhone hp = new HeadPhone(getApplicationContext(),
						R.raw.right);
				if (whichHeadPhoneToTest.equalsIgnoreCase("right")) {
					whichHeadPhoneToTest = "left";

					if (hp.detectHeadPhones() == true) {
						hp.setLeftLevel(0);
						hp.setRightLevel(1);
						hp.play("", 4);
						hp = new HeadPhone(getApplicationContext(), R.raw.left);

					} else {
						String textToToast = "No wired headphone detected, Please contect your headphones";
						if (lang.equals("AR"))
							textToToast = "من فضلك قم بتوصيل سماعات الأذن الخاصة بك";
						Toast.makeText(getApplicationContext(), textToToast,
								Toast.LENGTH_LONG).show();
					}
				} else if (whichHeadPhoneToTest.equalsIgnoreCase("left")) {
					hp = new HeadPhone(getApplicationContext(), R.raw.left);
					if (hp.detectHeadPhones() == true) {
						hp.setLeftLevel(1);
						hp.setRightLevel(0);
						hp.play("", 4);
						hp = new HeadPhone(getApplicationContext(), R.raw.right);
					} else {
						String textToToast = "No wired headphone detected, Please contect your headphones";
						if (lang.equals("AR"))
							textToToast = "من فضلك قم بتوصيل سماعات الأذن الخاصة بك";
						Toast.makeText(getApplicationContext(), textToToast,
								Toast.LENGTH_LONG).show();

						whichHeadPhoneToTest = "right";
					}
				}
				super.onTapOnce();
			}
		};
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPause() {
		finish();
		overridePendingTransition(R.anim.transition8, R.anim.transition7);
		super.onPause();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		activityGesture.OnTouchEvent(event);
		return super.onTouchEvent(event);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
}
