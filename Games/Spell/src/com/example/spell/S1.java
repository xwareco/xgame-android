package com.example.spell;

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
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;
import uencom.xgame.sound.TTS;
import uencom.xgame.speech.SpeechRecognition;

public class S1  implements IstateActions {
	String[] words = new String[]{"go","sad","education","school"};
	static int wordnum = 0;
	static String word;
	 
	

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		word = words[wordnum];
		 I.putExtra("word",word );
		 I.putExtra("letterPosition", -1);

		
		
		
		 
	}
	@Override
	public Intent loopBack(Context c, Intent I) {
		// TODO Auto-generated method stub
		Toast.makeText(c, word , Toast.LENGTH_SHORT).show();
		I.putExtra("word",word );
		 I.putExtra("letterPosition", -1);
		return I;
		
	}
	
	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub
		 wordnum++;

	}

	

}
