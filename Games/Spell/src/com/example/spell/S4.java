package com.example.spell;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Notification.Action;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;

public class S4 extends Activity implements IstateActions {
	

	private static final int REQUST_CODE = 990;
	static String resultString;
	@Override
	public void onStateEntry(LinearLayout layout, Intent I) {
		// TODO Auto-generated method stub

	}

	@Override
	public Intent loopBack(Context c, Intent I) {
		// TODO Auto-generated method stub
		int numOfFailed = I.getIntExtra("numOfFailed", 0);
		++numOfFailed;
		if(numOfFailed == 5)
			I.putExtra("State", "S6");
		else
		{
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
	        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
	        startActivityForResult(intent, REQUST_CODE);
	        I.putExtra("Letter", resultString.charAt(0));
	        I.putExtra("State", "S3");
		}
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

	}

}
