package com.example.hangman;

import java.util.Arrays;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import xware.xgame.interfaces.IstateActions;
import xware.xgame.sound.HeadPhone;

public class S0 implements IstateActions {
	String[] words = new String[]{"school","margin","rubbed","depth","limit","shaking","doll","poetry",
			"order","payment","rush","shallow",
			"attempt","arabic","egypt",
			"tired","back","star","good","collect","world",
			"output","advice","penalty","image","work","class","battle","donkey","policeman","positive",
			"exchange","security","increase","market","opinion","mistake","objective",
			"output","advice","penalty"};
	static int wordnum ;
	static String word;
	static String workingWord;

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		// TODO Auto-generated method stub
		TextView tv = (TextView)layout.getChildAt(0);
	     tv.setText("This is the classical hangman game, a word is given but with some letters missing, use your voice to tell the game about the letter you are guessing, you have 5 tries to save the man and form the word correctly, otherwise the man will be hanged!, swipe left when you are ready to start the game");
	     wordnum = I.getIntExtra("Random", 0);
			if(wordnum == 0){
			 Random rand = new Random();
			 wordnum= rand.nextInt(words.length);
			}
			wordnum++;
			if(wordnum == words.length)
				wordnum = wordnum%words.length;
			
			 I.putExtra("Random", wordnum);
			word = words[wordnum];
			char[] chars = new char[word.length()];
			char[] tempChars =word.toCharArray();
			Arrays.fill(chars, '$');
			chars[0] = tempChars[0];
			chars[(chars.length)-1] = tempChars[(tempChars.length)-1];
			workingWord = new String(chars);
			
			I.putExtra("word", word);
			I.putExtra("workingWord", workingWord);
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
