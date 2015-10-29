package com.example.goal;

import android.content.Intent;
import uencom.xgame.interfaces.ItransitionActions;

public class T12L implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		if(I.getStringExtra("Action") != "Left")
			{
			//out sound
			return false;
			}
		return true;
	}

}
