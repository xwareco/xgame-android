package com.example.car_racer;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.LinearLayout;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S2 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		// TODO Auto-generated method stub

	}

	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {
		String Path = Environment.getExternalStorageDirectory().toString()
				+ "/xGame/Games/Car Racer/Sound/car_border_crash.wav";
		
		H.setLeftLevel(0);
		H.setRightLevel(1);
		int Score = I.getIntExtra("Score", 0);
		Score += 10;
		if (H.detectHeadPhones() == true)
			H.play(Path, 0);
		I.putExtra("Action", "");
		I.putExtra("Score", Score);
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {
		H.stopCurrentPlay();

	}

}
