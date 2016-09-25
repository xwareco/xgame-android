package com.example.shootit;

import android.content.Intent;
import xware.engine_lib.interfaces.ItransitionActions;

public class T13 implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		if(I.getStringExtra("Action") == "Left")return true;
		return false;
	}

}
