package com.example.goal;

import android.content.Intent;
import uencom.xgame.interfaces.ItransitionActions;

public class T12R implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		if(I.getStringExtra("Action").equals("SwipeRight"))
			{
			//out sound
			System.out.print(I.getStringExtra("Action"));
			System.out.print(I.getStringExtra("Action").equals("SwipeRight"));
			return false;
			}
		System.out.print(I.getStringExtra("Action"));
		System.out.print(I.getStringExtra("Action").equals("SwipeRight"));
		return true;
	}

}
