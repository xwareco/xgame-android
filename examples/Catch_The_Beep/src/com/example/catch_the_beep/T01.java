package com.example.catch_the_beep;

import android.content.Intent;
import uencom.xgame.interfaces.ItransitionActions;

public class T01 implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		return true;
	}

}
