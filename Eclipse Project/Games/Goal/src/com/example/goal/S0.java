package com.example.goal;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import xware.xgame.interfaces.IstateActions;
import xware.xgame.sound.HeadPhone;

public class S0 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		// TODO Auto-generated method stub

		TextView tv = (TextView)layout.getChildAt(0);
	     tv.setText("This is a penalty kicks football game, swipe right or left to shoot the ball either to the right corner or to the left corner, Score a goal and don't let the keeper save the ball, The more goals you score the more score you will get, swipe left when you are ready to start the game");

	}
	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {
		
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {

	}

	

}
