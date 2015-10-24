package com.example.guessup;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.LinearLayout;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S2 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I) {
		// TODO Auto-generated method stub

	}

	@Override
	public Intent loopBack(Context c, Intent I) {
		// TODO Auto-generated method stub
		String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Guess_Up/Sound/beep.mp3";
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
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub
		
	}

}
