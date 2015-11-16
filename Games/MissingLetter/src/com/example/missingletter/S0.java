package com.example.missingletter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.widget.LinearLayout;
import android.widget.TextView;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S0 implements IstateActions {

	

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		
		// TODO Auto-generated method stub
				TextView tv = (TextView)layout.getChildAt(0);
			     tv.setText("A word is randomly generated and spoken to you but with a one missing letter, four choices of letters are given to you to select the missing letter from, navigate through the letters and choose one of them by clicking on it. swipe left when you are ready to start the game");
			        
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
