package com.example.spell;

import java.util.Arrays;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

public class S0 implements IstateActions {
	String[] words = new String[]{"egypt","peace","greeting","shake","product","world","option",
			"school","house","interest","message",
			"image","growth","profit","work","battle","shout","margin","rubbed","depth","limit","shaking","doll","poetry",
			"order","payment","rush","shallow","love","sale",
			"attempt","scared","donkey","policeman","egypt","positive","Ellen","essential",
			"exchange","security","increase","market","opinion","mistake","objective",
			"output","advice","penalty","permission","justice","discussion","satisfied","education",
			"industry","deeply","promotion","invoice","selection","plates","freedom","elephant","possibly","offer",
			"practical","guarantee",
			"inventory","knowledge","loss"};
	static int wordnum = 0;
	
	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C,HeadPhone H) {
		// TODO Auto-generated method stub
		TextView tv = (TextView)layout.getChildAt(0);
	     tv.setText("A random word will be generated and spoken out for you, you have to spell the word correctly using your voice, "
	     		+ " Take care of the time, you will lose 10 points if you took more than 3 minutes to finish the game, otherwise you will be rewarded points according to your time."
	     		+ ", swipe left when you are ready to start the game");
	     wordnum = I.getIntExtra("Random", 0);
			if(wordnum == 0){
			 Random rand = new Random();
			 wordnum = rand.nextInt(words.length);
			}
			wordnum++;
			if(wordnum == words.length)
				wordnum = wordnum%words.length;
			
			 I.putExtra("Random", wordnum);
			 String word = words[wordnum];
		char[] chars = new char[word.length()];
		char[] tempChars =word.toCharArray();
		Arrays.fill(chars, '$');
		String workingWord = new String(chars);
		
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
