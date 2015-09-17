package uencom.xgame.engine;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HeadphoneTester extends Activity implements OnClickListener {
	Button right, left;
	HandGestures activityGesture;
    TextView instrcts;
    Typeface arabic , english;
    Animation fadeIn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.headphonetest);
		right = (Button) findViewById(R.id.button1);
		left = (Button) findViewById(R.id.button2);
		instrcts = (TextView)findViewById(R.id.textView1);
		fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
		instrcts.startAnimation(fadeIn);
		right.setOnClickListener(this);
		left.setOnClickListener(this);
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
		};
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.button1:
			HeadPhone hp = new HeadPhone(getApplicationContext(),
					R.raw.gameover);
			if (hp.detectHeadPhones() == true) {
				hp.setLeftLevel(1);
				hp.setRightLevel(0);
				hp.play("", 4);
			} else
				Toast.makeText(
						getApplicationContext(),
						"No wired headphone detected, Please contect your headphones",
						Toast.LENGTH_LONG).show();

			break;

		case R.id.button2:
			HeadPhone hp2 = new HeadPhone(getApplicationContext(),
					R.raw.gameover);
			if (hp2.detectHeadPhones() == true) {
				hp2.setLeftLevel(0);
				hp2.setRightLevel(1);
				hp2.play("", 4);
			} else
				Toast.makeText(
						getApplicationContext(),
						"No wired headphone detected, Please contect your headphones",
						Toast.LENGTH_LONG).show();
			break;
		}
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
