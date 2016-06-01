package com.example.tennis;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S0 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C,HeadPhone H) {
		// TODO Auto-generated method stub

		TextView tv = (TextView)layout.getChildAt(0);
	     tv.setText(" You are playing a tennis game against the computer, shoot the ball back by taping the screen once but if only sound is playing in both your left and right headphones, otherwise you will lose a point, swipe left when you are ready to start the game");

	}

	@Override
	public Intent loopBack(Context c, Intent I,HeadPhone H) {
		// TTS for how to play
		
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I,HeadPhone H) {

	}

}


