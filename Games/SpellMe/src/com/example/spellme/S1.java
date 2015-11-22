package com.example.spellme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;
import uencom.xgame.sound.TTS;
import uencom.xgame.speech.SpeechRecognition;

public class S1  implements IstateActions {
	String[] words = new String[]{"freedom","elephant","education","school","house",
			"egypt","love","peace","greeting","security"
			,"world","image","work","justice","collect","class","battle"};
	static int wordnum = 0;
	static String word;
	static String workingWord;
	

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C,HeadPhone H) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		BitmapDrawable b = (BitmapDrawable) layout.getBackground();
		b.setAlpha(100);
		layout.setBackground(b);
		Random  random = new Random();
		wordnum = random.nextInt(17);
		word = words[wordnum];
		char[] chars = new char[word.length()];
		char[] tempChars =word.toCharArray();
		Arrays.fill(chars, '$');
		workingWord = new String(chars);

		I.putExtra("word", word);
		I.putExtra("workingWord", workingWord);
		
		
		 
	}
	@Override
	public Intent loopBack(Context c, Intent I,HeadPhone H) {
		// TODO Auto-generated method stub
		Toast.makeText(c, word , Toast.LENGTH_SHORT).show();
		
		
		return I;
		
	}
	
	@Override
	public void onStateExit(Context c, Intent I,HeadPhone H) {
		// TODO Auto-generated method stub
		

	}

	

}
