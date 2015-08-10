package com.example.catch_the_beep;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;

public class S0 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I) {
		// TODO Auto-generated method stub

	}

	@Override
	public Intent loopBack(Context c, Intent I) {
		// TTS for how to play
		Toast.makeText(
				c,
				"hear the beep and swipe if you heared the beep in the right swipe right.if you heared the beep in the left swipe left.if you heared the beep in both right and left double tap the screen..swipe left to exit the how to play state.",
				Toast.LENGTH_LONG).show();
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I) {

	}

}
