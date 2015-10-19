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

public class S2 extends Activity implements IstateActions {
	
	
	 
	
	static String resultString;
	private static final int REQUST_CODE = 989;
	@Override
	public void onStateEntry(LinearLayout layout, Intent I) {
		// TODO Auto-generated method stub
       int letterPosition = I.getIntExtra("letterPosition", 0);
       letterPosition++;
       I.putExtra("letterPosition", letterPosition);
	}

	@Override
	public Intent loopBack(Context c, Intent I) {
		// TODO Auto-generated method stub
		
						
				
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, REQUST_CODE);				
		I.putExtra("Letter", resultString.charAt(0));	
		
		
		
			return I;
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// check speech recognition result
		if (requestCode == REQUST_CODE && resultCode == RESULT_OK) {
			// store the returned word list as an ArrayList
			ArrayList<String> suggestedWords = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			 resultString = suggestedWords.get(0);
			
			Toast.makeText(this, resultString.charAt(0), Toast.LENGTH_LONG).show();
		} else
			Toast.makeText(this, "it does not recognize any letter",
					Toast.LENGTH_LONG).show();
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub
		int Score = I.getIntExtra("Score", 0);
		Score++;
		I.putExtra("Score", Score);
		

	}

}
