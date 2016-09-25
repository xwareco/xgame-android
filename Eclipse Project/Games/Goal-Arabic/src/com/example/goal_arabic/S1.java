package com.example.goal_arabic;

import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.LinearLayout;
import xware.xgame.interfaces.IstateActions;
import xware.xgame.sound.HeadPhone;

public class S1 extends Thread implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C,
			HeadPhone H) {
		int count = I.getIntExtra("Count", 0);
		count++;
		String swipeAction = "NOT SET";
		Random r = new Random();
		int num = r.nextInt(3);
		if (num == 0) {
			swipeAction = "SwipeRight";
		} else if (num == 1) {
			swipeAction = "SwipeLeft";
		} else if (num == 2) {
			swipeAction = "SingleTap";
		}

		I.putExtra("Action", swipeAction);
		I.putExtra("Count", count);

	}

	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub

		String Path = Environment.getExternalStorageDirectory().toString()
				+ "/xGame/Games/الهداف/Sound/he shoots he scores.mp3";
		HeadPhone HP = new HeadPhone(c);

		HP.setLeftLevel(1);
		HP.setRightLevel(1);
		if (HP.detectHeadPhones() == true)
			HP.play(Path, 0);

		return I;

	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub
		int Score = I.getIntExtra("Score", 0);
		Score += 5;
		I.putExtra("Score", Score);

	}

}
