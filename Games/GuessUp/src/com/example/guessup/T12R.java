package com.example.guessup;

import android.content.Intent;
import uencom.xgame.interfaces.ItransitionActions;

public class T12R implements ItransitionActions {

	public boolean isConditionActivated(Intent I) {
		if(I.getStringExtra("Action").equals("SwipeRight"))
		{
		//out sound
		return true;
		}
	return false;
	}

}
