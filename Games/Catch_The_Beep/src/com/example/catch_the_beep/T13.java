package com.example.catch_the_beep;

import android.content.Intent;
import uencom.xgame.interfaces.ItransitionActions;

public class T13 implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		if(I.getStringExtra("Action") == "Left")return true;
		return false;
	}

}
