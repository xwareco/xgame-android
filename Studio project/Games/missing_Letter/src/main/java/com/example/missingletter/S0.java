package com.example.missingletter;

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
		
		// TODO Auto-generated method stub
				TextView tv = (TextView)layout.getChildAt(0);
			     tv.setText("A word is randomly generated and spoken to you but with a one missing letter, four choices of letters are given to you to select the missing letter from them, navigate through the letters and choose one of them by clicking on it, "
				     		+ "To quit the game anytime, press the back button, now, choose the difficulty of the game, if you are a beginner, swipe left, if you are Intermediate, swipe right, if you are an expert, swipe down");

	}

	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub
		
	}

}
