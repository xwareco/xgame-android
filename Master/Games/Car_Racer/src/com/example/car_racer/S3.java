package com.example.car_racer;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.LinearLayout;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S3 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I) {
		// TODO Auto-generated method stub

	}

	@Override
	public Intent loopBack(Context c, Intent I) {
		String Path = Environment.getExternalStorageDirectory().toString()
				+ "/xGame/Games/Car Racer/Sound/car_border_crash.wav";
		HeadPhone HP = new HeadPhone(c);
		//HP.stopCurrentPlay();
		HP.setLeftLevel(1);
		HP.setRightLevel(0);
		int Score = I.getIntExtra("Score", 0);
		Score += 10;
		if (HP.detectHeadPhones() == true)
			HP.play(Path, 0);
		I.putExtra("Action", "");
		I.putExtra("Score", Score);
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub

	}

}
