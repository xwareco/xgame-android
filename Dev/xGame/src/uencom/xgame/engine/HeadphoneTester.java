package uencom.xgame.engine;

import uencom.xgame.gestures.HandGestures;
import uencom.xgame.sound.HeadPhone;
import uencom.xgame.xgame.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HeadphoneTester extends Activity implements OnClickListener {
	Button right, left;
	HandGestures activityGesture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.headphonetest);
		right = (Button) findViewById(R.id.button1);
		left = (Button) findViewById(R.id.button2);
		right.setOnClickListener(this);
		left.setOnClickListener(this);
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
}
