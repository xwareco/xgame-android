package com.example.missingletter;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

public class S0 implements IstateActions {

	

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		
		// TODO Auto-generated method stub
				TextView tv = (TextView)layout.getChildAt(0);
			     tv.setText("A word is randomly generated and spoken to you but with a one missing letter, four choices of letters are given to you to select the missing letter from them, navigate through the letters and choose one of them by clicking on it, "
			    		 + " Take care of the time, you will lose 10 points if you took more than 5 minutes to finish the game, otherwise you will be rewarded points according to your time."
				     		+ ", swipe left when you are ready to start the game");

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
