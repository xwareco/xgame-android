package com.example.goal;

import java.util.Random;

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
		// TODO Auto-generated method stub
		int count = I.getIntExtra("Count", 0);
		count++;
		String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Goal/Sound/beep.mp3";
		HeadPhone HP = new HeadPhone(c);
		Random r = new Random();
		int num = r.nextInt(3);
		String swipeAction = "NOT SET";
		HP.setLeftLevel(1);
		HP.setRightLevel(1);
		if (HP.detectHeadPhones() == true)
			HP.play(Path, 0);
		if (num == 0) {
			swipeAction = "Right";
			

		} else if (num == 1) {
			swipeAction = "Left";
			
		}else if(num == 2)
		{
			swipeAction = "center";
			
		}
			I.putExtra("Action", swipeAction);
			I.putExtra("Count", count);
			return I;
		
	}

	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub
		int Score = I.getIntExtra("Score", 0);
		Score++;
		I.putExtra("Score", Score);

	}

}
