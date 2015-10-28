package com.example.goal;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;

public class S0 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C) {
		// TODO Auto-generated method stub

		TextView tv = (TextView)layout.getChildAt(0);
	     tv.setText("to score goal in this game you should hear the beep and swipe right if you heared the beep in the right, or swip left if you heared the beep in the left.");

	}
	@Override
	public Intent loopBack(Context c, Intent I) {
		
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I) {

	}

	

}
