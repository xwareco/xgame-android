package com.example.car_racer;

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
				"Drive your car to the end line as fast as possible.Hold your device in landscape mode and drive your car the same as driving a real car.Be careful not to hit the track borders or your speed will be massively reduced.",
				Toast.LENGTH_LONG).show();
		return I;

	}

	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub

	}

}
