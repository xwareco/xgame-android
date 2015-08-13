package com.example.car_racer;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.LinearLayout;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S1 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I) {
		// TODO Auto-generated method stub

	}

	@Override
	public Intent loopBack(Context c, Intent I) {
		int startTime = (int)System.currentTimeMillis();
		String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/car_racer/Sound/car_running.wma";
		HeadPhone HP = new HeadPhone(c);
		HP.setLeftLevel(1);
		HP.setRightLevel(1);
		if (HP.detectHeadPhones() == true && HP.isPlaying() == false)
		{
			HP.play(Path, 0);
		}
		int currVPos = I.getIntExtra("Count", 0);
		int scoreSoFar = I.getIntExtra("Scroe", 0);
		System.out.println("ScoreBefore = " + scoreSoFar);
		int newScoreSoFar;
		int duration;
		currVPos = (currVPos +1)*2;
		int endTime = (int)System.currentTimeMillis();
		duration = endTime - startTime;
		newScoreSoFar = scoreSoFar + duration;
		System.out.println("Score = " + newScoreSoFar);
		I.putExtra("Action", "");
		I.putExtra("Count", currVPos);
		I.putExtra("Score", newScoreSoFar);
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub

	}

}
