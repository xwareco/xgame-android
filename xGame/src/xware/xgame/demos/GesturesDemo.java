package xware.xgame.demos;

import xware.xgame.gestures.HandGestures;
import xware.xgame.xgame.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GesturesDemo extends Activity {

	HandGestures HG;
	LinearLayout mainLayout;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.gesturesdemo);
		mainLayout = (LinearLayout)findViewById(R.id.mainlayout);
		mainLayout.setOnHoverListener(new View.OnHoverListener() {
			
			@Override
			public boolean onHover(View v, MotionEvent event) {
				
				return onTouchEvent(event);
			}
		});
		initHandGestures(this);
		super.onCreate(savedInstanceState);
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		HG.OnTouchEvent(event);
		// Be sure to call the superclass implementation
		return super.onTouchEvent(event);
	}

	private void initHandGestures(Context ctx) {
		HG = new HandGestures(ctx) {
			@Override
			public void onTapTwice() {
				Toast.makeText(getApplicationContext(), "Double tap", Toast.LENGTH_LONG).show();
				super.onTapTwice();
			}

			@Override
			public void onTapOnce() {
				Toast.makeText(getApplicationContext(), "Single tap", Toast.LENGTH_LONG).show();
				super.onTapOnce();
			}

			@Override
			public void onSwipeRight() {
				Toast.makeText(getApplicationContext(), "Right swipe", Toast.LENGTH_LONG).show();
				super.onSwipeRight();
			}

			@Override
			public void onSwipeleft() {
				Toast.makeText(getApplicationContext(), "Left swipe", Toast.LENGTH_LONG).show();
				super.onSwipeleft();
			}

			@Override
			public void onSwipeUp() {
				Toast.makeText(getApplicationContext(), "Up swipe", Toast.LENGTH_LONG).show();
				super.onSwipeUp();
			}

			@Override
			public void onSwipeDown() {
				Toast.makeText(getApplicationContext(), "Down swipe", Toast.LENGTH_LONG).show();
				super.onSwipeDown();
			}

			@Override
			public void onLongTouch() {
				Toast.makeText(getApplicationContext(), "Long tap", Toast.LENGTH_LONG).show();
				super.onLongTouch();
			}
		};
	}

}
