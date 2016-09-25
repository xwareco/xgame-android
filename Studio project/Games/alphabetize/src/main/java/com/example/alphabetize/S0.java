package com.example.alphabetize;

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
		
				TextView tv = (TextView)layout.getChildAt(0);
			     tv.setText("You are given a list of 4 randomly sorted words, the goal is to sort this list alphabetically, click on the word you want to try sorting it correctly,"
			    		 + " To quit the game anytime, press the back button, now, choose the difficulty of the game, if you are a beginner, swipe left, if you are Intermediate, swipe right, if you are an expert, swipe down");
}

	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {

	}

}
