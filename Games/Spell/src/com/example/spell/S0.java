package com.example.spell;

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
				"you should listen to word and then spell it correctly",
				Toast.LENGTH_LONG).show();
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I) {

	}

}
