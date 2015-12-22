package com.example.shootit;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;


public class S0 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C,HeadPhone H) {
		
		TextView tv = (TextView)layout.getChildAt(0);
		tv.setText("You were sent to space to manage a war against aliens, you are driving your space ship and aliens are flying every where to shoot you, "
				+ "if the alien's sound is played from left to right swipe right to kill that alien, if the sound is played from right to left swipe left, "
				+ "if the sound is played in both left and right double tap the screen, good luck saving the earth!"
				+ ", swipe left when you are ready to start the game");
	}

	@Override
	public Intent loopBack(Context c, Intent I,HeadPhone H) {
		
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I,HeadPhone H) {

	}

}
