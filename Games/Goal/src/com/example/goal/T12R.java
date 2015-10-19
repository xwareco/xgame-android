package com.example.goal;

import android.content.Intent;
import uencom.xgame.interfaces.ItransitionActions;

public class T12R implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		if(I.getStringExtra("Action") != "Right")
			{
			//out sound
			return false;
			}
		return true;
	}

}
