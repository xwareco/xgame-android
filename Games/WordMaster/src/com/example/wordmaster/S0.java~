package com.example.wordmaster;

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
			     tv.setText("Random word is generated and showed to you with scramble letters, try to unscramble the word by selecting the letters with the correct arrangement, swipe left when you are ready to start the game");
			        
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
