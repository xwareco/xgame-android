package com.example.guessup;

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
	     tv.setText("application store random number and show to you another random number you should guess that if showed number greater than stored number swipe right, else if it less than stored number swipe left, finally if its are equal touch screen single tap");

	}
	@Override
	public Intent loopBack(Context c, Intent I) {


		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I) {

	}


}
