package com.example.shootit;

import android.content.Intent;
import xware.engine_lib.interfaces.ItransitionActions;

public class T14 implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		if(I.getStringExtra("Action") == "Both")return true;
		return false;
	}

}
