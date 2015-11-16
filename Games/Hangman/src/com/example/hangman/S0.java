package com.example.hangman;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S0 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		// TODO Auto-generated method stub
		TextView tv = (TextView)layout.getChildAt(0);
	     tv.setText("This is the classical hangman game, a word is given but with some letters missing, use your voice to tell the game about the letter you are guessing, you have 5 tries to save the man and form the word correctly, otherwise the man will be hanged!, swipe left when you are ready to start the game");

	}


	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {
		// TTS for how to play
		
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {

	}

	
}
