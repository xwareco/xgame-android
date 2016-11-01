package com.example.hunttheduck;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Keep;
import android.widget.LinearLayout;
import android.widget.TextView;
import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

@Keep
public class S0 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		TextView tv = (TextView) layout.getChildAt(0);
		tv.setText("This game needs the headphones, The duck is flying beside you,shoot it when you can by swipping down, you can only shot it when it's sound is played in both left and right headphones, To quit the game anytime, press the back button, now, choose the difficulty of the game, if you are a beginner, swipe left, if you are Intermediate, swipe right, if you are an expert, swipe down");

	}

	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {

		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {

	}

}
