package com.example.guessup;

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
	static int stored_number;
	static int showed_number; 
	int count;
	
	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C) {
		// TODO Auto-generated method stub
		Random  random = new Random();
		stored_number = random.nextInt(100);
		showed_number = random.nextInt(100);
	    Log.d("stored", stored_number+"");
	    Log.d("showed", showed_number+"");
	    count  = I.getIntExtra("Count", 0);
		count++;
		I.putExtra("Count", count);
	}
	@Override
	public Intent loopBack(Context c, Intent I) {
		// TODO Auto-generated method stub
		
		
		Toast.makeText(c,"guess the stored number relative to this number "+showed_number+" and swipe", Toast.LENGTH_LONG).show();
		
		
      String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Guess_Up/Sound/beep.mp3";
		
		
		String swipeAction = "NOT SET";
		
		if (showed_number > stored_number) {
			swipeAction = "SwipeRight";
			
		} else if (showed_number < stored_number) {
			swipeAction = "SwipeLeft";
			
		}else if(showed_number == stored_number)
		{
			swipeAction = "SingleTap";
		}
			
			
		I.putExtra("Action", swipeAction);
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
