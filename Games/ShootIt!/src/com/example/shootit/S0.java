package com.example.shootit;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import uencom.xgame.interfaces.IstateActions;

public class S0 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I) {
		TextView tv = (TextView)layout.getChildAt(0);
		tv.setText("This is the how to play instructions");

	}

	@Override
	public Intent loopBack(Context c, Intent I) {
		
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I) {

	}

}
