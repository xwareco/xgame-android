package com.example.catch_the_beep;

import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.LinearLayout;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S1 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout , Intent I) {
		// TODO Auto-generated method stub

	}

	@Override
	public Intent loopBack(Context c , Intent I) {
		int count = I.getIntExtra("Count", 0);
		count++;
		String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/catch_the_peeb/Sound/beep.mp3";
		System.out.println(Path);
		HeadPhone HP = new HeadPhone(c);
		//Intent I = new Intent();
		Random r = new Random();
		int num = r.nextInt(3);
		String swipeAction = "NOT SET";
		if (num == 0) {
			swipeAction = "Right";
			System.out.println(swipeAction);
			HP.setLeftLevel(0);
			HP.setRightLevel(1);
			if (HP.detectHeadPhones() == true)
				HP.play(Path, 0);

		} else if (num == 1) {
			swipeAction = "Left";
			System.out.println(swipeAction);
			HP.setLeftLevel(1);
			HP.setRightLevel(0);
			if (HP.detectHeadPhones() == true)
				HP.play(Path, 0);
		} else if (num == 2) {
			swipeAction = "Both";
			System.out.println(swipeAction);
			HP.setLeftLevel(1);
			HP.setRightLevel(1);
			if (HP.detectHeadPhones() == true)
				HP.play(Path, 0);
		}
		I.putExtra("Action", swipeAction);
		I.putExtra("Count", count);
		return I;
	}

	@Override
	public void onStateExit(Context c , Intent I) {
		int Score = I.getIntExtra("Score", 0);
		Score++;
		I.putExtra("Score", Score);

	}

}
