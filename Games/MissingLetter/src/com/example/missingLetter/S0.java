package com.example.missingLetter;

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
				"application store random word and show to you without one letter you should guess this letter from four letters also showed to you and complete the word ",Toast.LENGTH_LONG).show();
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I) {

	}

}
