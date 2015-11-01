package uencom.xgame.engine.views;

import java.util.Locale;

import uencom.xgame.gestures.HandGestures;
import uencom.xgame.sound.HeadPhone;
import uencom.xgame.xgame.R;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

public class HeadphoneTester extends Activity {
	HandGestures activityGesture;
    TextView instrcts;
    Typeface arabic , english;
    Animation fadeIn;
    String whichHeadPhoneToTest = "right";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.headphonetest);
		instrcts = (TextView)findViewById(R.id.textView1);
		fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
		instrcts.startAnimation(fadeIn);
		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(getAssets(),
				"fonts/DJB Stinky Marker.ttf");
		Locale current = getResources().getConfiguration().locale;
		if (current.getDisplayLanguage().equals("Arabic")) {
			instrcts.setTypeface(arabic);
		} else if (current.getDisplayLanguage().equals("English")) {
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
				HeadPhone hp = new HeadPhone(getApplicationContext(),
						R.raw.right);
				if (whichHeadPhoneToTest.equalsIgnoreCase("right")) {
					whichHeadPhoneToTest = "left";
					 
					if (hp.detectHeadPhones() == true) {
						hp.setLeftLevel(0);
						hp.setRightLevel(1);
						hp.play("", 4);
						hp = new HeadPhone(getApplicationContext(),
								R.raw.left);

					} else
						Toast.makeText(
								getApplicationContext(),
								"No wired headphone detected, Please contect your headphones",
								Toast.LENGTH_LONG).show();
				}
				else if(whichHeadPhoneToTest.equalsIgnoreCase("left"))
				{
					hp = new HeadPhone(getApplicationContext(),
							R.raw.left);
					if (hp.detectHeadPhones() == true) {
						hp.setLeftLevel(1);
						hp.setRightLevel(0);
						hp.play("", 4);
						hp = new HeadPhone(getApplicationContext(),
								R.raw.right);
					} else
						Toast.makeText(
								getApplicationContext(),
								"No wired headphone detected, Please contect your headphones",
								Toast.LENGTH_LONG).show();
					whichHeadPhoneToTest = "right";
				}
				super.onTapOnce();
			}
		};
		super.onCreate(savedInstanceState);
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
