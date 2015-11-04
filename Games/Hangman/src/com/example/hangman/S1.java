package com.example.hangman;

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
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;
import uencom.xgame.sound.TTS;
import uencom.xgame.speech.SpeechRecognition;

public class S1  implements IstateActions {
	String[] words = new String[]{"arabic","egypt","man","love",
			"tired","back","sad","star","good","collect"};
	static int wordnum ;
	static String word;
	static String workingWord;
	 
	

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		//word = I.getStringExtra("word");
		//if(word == null)
		Random  random = new Random();
		wordnum = random.nextInt(10);
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
	public Intent loopBack(Context c, Intent I) {
		// TODO Auto-generated method stub
	
	Toast.makeText(c, workingWord, Toast.LENGTH_SHORT).show();
		return I;
		
	}
	
	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub
		

	}

	

}
