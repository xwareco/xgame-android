package com.example.car_racer;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S0 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C,
			HeadPhone H) {
		TextView tv = (TextView) layout.getChildAt(0);
		tv.setText("Hold your phone on the landscape mode, you are driving your car through xGame roads, use your device's acceloremeter sensor to steer the car, turn right or left according the road turns, be careful not to hit the raod boarders, avoid hitting little girls and cows in your way, finish the race as fast as possible to get a high score, Don't forget to hold back your phone to portrait mode when the game is over, swipe left to start the game, Good luck.");

	}

	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {
		return I;

	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub

	}

}
