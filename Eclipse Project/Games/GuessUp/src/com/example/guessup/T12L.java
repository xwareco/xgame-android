package com.example.guessup;

import android.content.Intent;
import uencom.xgame.interfaces.ItransitionActions;

public class T12L implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		if(I.getStringExtra("Action").equals("SwipeLeft"))
		{
		//out sound
		return true;
		}
	return false;
	}

}
