package xware.xgame.gestures;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

public abstract class HandGestures implements
		GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

	// Variables
	private GestureDetector mDetector;
	private boolean right = false;

	public boolean isRight() {
		return right;
	}

	private boolean left = false;
	private boolean up = false;
	private boolean down = false;
	private float flingMin = 100;
	private float velocityMin = 100;

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		onTapTwice();
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		System.out.println("Iam here!!");
		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		onTapOnce();
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {

		return true;
	}

	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2,
			float velocityX, float velocityY) {

		if (event1 != null && event2 != null) {
			// calculate the change in X position within the fling gesture
			float horizontalDiff = event2.getX() - event1.getX();
			// calculate the change in Y position within the fling gesture
			float verticalDiff = event2.getY() - event1.getY();
			float absHDiff = Math.abs(horizontalDiff);
			float absVDiff = Math.abs(verticalDiff);
			float absVelocityX = Math.abs(velocityX);
			float absVelocityY = Math.abs(velocityY);
			if (absHDiff > absVDiff && absHDiff > flingMin
					&& absVelocityX > velocityMin) {
				// swipe right or left

				if (horizontalDiff > 0)
					right = true;
				else
					left = true;
			} else if (absHDiff < absVDiff && absVDiff > flingMin
					&& absVelocityY > velocityMin) {
				// swipe up or down
				if (verticalDiff > 0)
					down = true;
				else
					up = true;
			}
		}
		return callUserMethods();

	}

	private boolean callUserMethods() {
		// right swipe detected
		if (right)
			onSwipeRight();
		// left swipe detected
		else if (left)
			onSwipeleft();
		// up swipe detected
		else if (up)
			onSwipeUp();
		// down swipe detected
		else if (down)
			onSwipeDown();

		// set the booleans back to false to detect new swipes
		right = false;
		left = false;
		up = false;
		down = false;
		return true;

	}

	@Override
	public void onLongPress(MotionEvent e) {
		onLongTouch();

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {

		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {

		return false;
	}

	public void onTapTwice() {

	}

	public void onTapOnce() {

	}

	public void onSwipeRight() {

	}

	public void onSwipeleft() {

	}

	public void onSwipeUp() {

	}

	public void onSwipeDown() {

	}

	public void onLongTouch() {

	}

	public HandGestures(Context ctx) {
		mDetector = new GestureDetector(ctx, this);
		// Set the gesture detector as the double tap
		// listener.
		mDetector.setOnDoubleTapListener(this);
	}

	public void OnTouchEvent(MotionEvent ev) {
		this.mDetector.onTouchEvent(ev);
	}
}
