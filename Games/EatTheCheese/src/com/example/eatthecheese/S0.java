package com.example.eatthecheese;

import java.util.Arrays;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S0 implements IstateActions {
	String[] words = new String[]{"sad","room","school","arabic","egypt","man","love",
			"tired","back","star","good","collect","world","image","work","class","battle"};
	static int wordnum = 0;
	static String word;
	static String workingWord;
	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C,HeadPhone H) {
		// TODO Auto-generated method stub
		TextView tv = (TextView)layout.getChildAt(0);
	     tv.setText("The mouse is trying to get to the cheese to eat it without the cat notices it, help the mouse to eat the cheese by guessing the the letters of the following word using you voice, be careful as 7 wrong gusses will make the cat eats you, swipe left when you are ready to start the game");
	     Random  random = new Random();
			wordnum = random.nextInt(words.length);
			word = words[wordnum];
			char[] chars = new char[word.length()];
			char[] tempChars =word.toCharArray();
			Arrays.fill(chars, '$');
			int l =random.nextInt(chars.length);
			chars[l] = word.charAt(l);
			workingWord = new String(chars);

			I.putExtra("word", word);
			I.putExtra("workingWord", workingWord);
			I.putExtra("letterPosition", 0);
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
