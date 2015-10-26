package com.example.car_racer;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import uencom.xgame.interfaces.IstateActions;

public class S0 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C) {
		// TODO Auto-generated method stub

	}

	@Override
	public Intent loopBack(Context c, Intent I) {
		return I;

	}

	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub

	}

}
