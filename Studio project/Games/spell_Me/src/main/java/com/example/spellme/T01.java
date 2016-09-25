package com.example.spellme;

import android.content.Intent;
import xware.engine_lib.interfaces.ItransitionActions;

public class T01 implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		return true;
	}

}
