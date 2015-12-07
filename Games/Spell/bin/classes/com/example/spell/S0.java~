package com.example.spell;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S0 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C,HeadPhone H) {
		// TODO Auto-generated method stub
		TextView tv = (TextView)layout.getChildAt(0);
	     tv.setText("A random word will be generated and spoken out for you, you have to spell the word using your voice.");

	}


	@Override
	public Intent loopBack(Context c, Intent I,HeadPhone H) {
		// TTS for how to play
		
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I,HeadPhone H) {

	}

	
}
