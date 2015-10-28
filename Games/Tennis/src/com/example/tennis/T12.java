package com.example.tennis;

import android.content.Intent;
import uencom.xgame.interfaces.ItransitionActions;

public class T12 implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		if(I.getStringExtra("Action") == "SingleTap")
			{
			
			return true;
			}
		return false;
	}

}
