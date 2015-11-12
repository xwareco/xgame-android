package com.example.goal;

import android.content.Intent;
import uencom.xgame.interfaces.ItransitionActions;

public class T12 implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		if(I.getStringExtra("Action").equals("SingleTap"))
		{
		//out sound
			System.out.print(I.getStringExtra("Action"));
			System.out.print(I.getStringExtra("Action").equals("SingleTap"));
		return false;
		}
	return true;
	}

}
