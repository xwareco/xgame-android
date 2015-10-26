package com.example.shootit;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import uencom.xgame.interfaces.IstateActions;

public class S3 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout , Intent I, Context C) {
		// TODO Auto-generated method stub
        //play victory sound
	}

	@Override
	public Intent loopBack(Context c , Intent I) {
		I.putExtra("Action", "NONE");
		I.putExtra("State", "S1");
		return I;
	}

	@Override
	public void onStateExit(Context c , Intent I) {
		// TODO Auto-generated method stub

	}

}
