package com.example.hangman;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;

public class S5 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C) {
		// TODO Auto-generated method stub
		I.putExtra("Action", "Right");
	}

	@Override
	public Intent loopBack(Context c, Intent I) {
		// TODO Auto-generated method stub
		Toast.makeText(c, "welcome back", Toast.LENGTH_SHORT).show();
		return null;
	}

	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub

	}

	
}
