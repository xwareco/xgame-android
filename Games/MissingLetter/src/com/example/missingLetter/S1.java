package com.example.missingLetter;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;
import uencom.xgame.sound.TTS;
import uencom.xgame.speech.SpeechRecognition;

public class S1 extends Activity implements IstateActions {
	String[] words = new String[]{"freedom","elephant","education","school","house"};
	char [] letters = new char[]{};
	static int wordnum = 0;
	static String word;
	int letterposition;
	StringBuilder tempString;
	int count;
	@Override
	public void onStateEntry(LinearLayout layout, Intent I) {
		// TODO Auto-generated method stub
		word = words[wordnum];
		 I.putExtra("word",word );
		 letterposition = (int) Math.floor(Math.random()*word.length());
		 I.putExtra("letterPosition", letterposition);
		tempString = new StringBuilder(word);
		tempString.setCharAt(letterposition, '$');
	    count  = I.getIntExtra("Count", 0);
		count++;
		I.putExtra("Count", count);
	}

	@Override
	public Intent loopBack(Context c, Intent I) {
		// TODO Auto-generated method stub
		
		
	
			return I;
		
	}
	
	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub
		int Score = I.getIntExtra("Score", 0);
		Score++;
		I.putExtra("Score", Score);

	}

}
