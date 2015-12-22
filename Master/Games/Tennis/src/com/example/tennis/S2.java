package com.example.tennis;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.LinearLayout;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S2 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I,Context C,HeadPhone H) {
		// TODO Auto-generated method stub

	}

	@Override
	public Intent loopBack(Context c, Intent I,HeadPhone H) {
		// TODO Auto-generated method stub
		String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Tennis/Sound/Goal.mp3";
		HeadPhone HP = new HeadPhone(c);
		 HP.setLeftLevel(1);
	      HP.setRightLevel(1);
	      if (HP.detectHeadPhones() == true)
		  HP.play(Path, 0);
		I.putExtra("Action", "NONE");
		I.putExtra("State", "S1");
		//score sound
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I,HeadPhone H) {
		// TODO Auto-generated method stub

	}

}
