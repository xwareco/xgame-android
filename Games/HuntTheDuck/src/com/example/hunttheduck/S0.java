package com.example.hunttheduck;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S0 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		TextView tv = (TextView) layout.getChildAt(0);
		tv.setText("The duck is flying beside you,shoot it when you can by double tapping the screen,you can only shot it when it's sound is played in both left and right headphones. swipe left when you are ready to start the game");

	}

	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {

		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {

	}

}
