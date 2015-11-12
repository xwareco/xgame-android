package com.example.goal;

import android.content.Intent;
import uencom.xgame.interfaces.ItransitionActions;

public class T12L implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		if(I.getStringExtra("Action").equals("SwipeLeft"))
			{
			//out sound
			System.out.print(I.getStringExtra("Action"));
			System.out.print(I.getStringExtra("Action").equals("SwipeLeft"));
			return false;
			}
		System.out.print(I.getStringExtra("Action"));
		System.out.print(I.getStringExtra("Action").equals("SwipeLeft"));
		return true;
	}

}
