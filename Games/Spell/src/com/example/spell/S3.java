package com.example.spell;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import uencom.xgame.interfaces.IstateActions;

public class S3 implements IstateActions {
	static char letter ;
	static String word = "";
	static int letterPosition;

	@Override
	public void onStateEntry(LinearLayout layout, Intent I) {
		// TODO Auto-generated method stub
		letter = I.getStringExtra("letter").charAt(0);
		word = I.getStringExtra(word);
		letterPosition =Integer.parseInt(I.getStringExtra("letterPosition"));

	}

	@Override
	public Intent loopBack(Context c, Intent I) {
		// TODO Auto-generated method stub
		if(word.charAt(letterPosition) == letter)
		{
			int score = I.getIntExtra("score", 0);
			++score;
			I.putExtra("score",score);
			if(letterPosition == (word.length()-1))
			{
				I.putExtra("State", "S5");
			}else
				I.putExtra("State", "S2");
			
		}
		else
			I.putExtra("State", "S4");
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub

	}

}
