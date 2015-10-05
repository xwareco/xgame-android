package com.example.goal;

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
				"to score goal in this game you should hear the beep and swipe right if you heared the beep in the right, or swip left if you heared the beep in the left.",
				Toast.LENGTH_LONG).show();
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I) {

	}

}
