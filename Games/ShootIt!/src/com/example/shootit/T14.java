package com.example.shootit;

import android.content.Intent;
import android.util.Log;
import uencom.xgame.interfaces.ItransitionActions;

public class T14 implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		if(I.getStringExtra("Action") == "Both")return true;
		System.out.println("enter to false ");
		 Log.d("False Enter", "enter false stage");
		return false;
	}

}
