package com.example.guessup;

import android.content.Intent;
import uencom.xgame.interfaces.ItransitionActions;

public class T13 implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		if(I.getStringExtra("Action").equals("SingleTap"))
			{
			
			return true;
			}
		return false;
	}

}
