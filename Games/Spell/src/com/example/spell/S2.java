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
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;
import uencom.xgame.sound.TTS;
import uencom.xgame.speech.SpeechRecognition;

public class S2  implements IstateActions {
	
	static Button startSpeech;
	static LinearLayout layout;
	static int letterPosition;
	static String resultString;

	@Override
	public void onStateEntry(LinearLayout layout,  Intent I,  Context c) {
		// TODO Auto-generated method stub
		    I.putExtra("Action", "Right");
		    
		this.layout = layout;
		   letterPosition = I.getIntExtra("letterPosition", 0);
	       letterPosition++;
	       
	       
		
			createUI(this.layout,I,c);
			
	}
	@Override
	public Intent loopBack(final Context c, final Intent I) {
		
		// TODO Auto-generated method stub
		
		
			return I;
		
	}
	
	
	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub
		I.putExtra("letterPosition", letterPosition);
		layout.removeView(startSpeech);
		int Score = I.getIntExtra("Score", 0);
		Score++;
		I.putExtra("Score", Score);
		

	}
public void createUI(LinearLayout layout, final Intent I, final Context c) {
	FrameLayout.LayoutParams parentLayoutParams =
			new FrameLayout.LayoutParams(
			LayoutParams.MATCH_PARENT,
			LayoutParams.MATCH_PARENT);
	layout.setOrientation(LinearLayout.VERTICAL);
	layout.setLayoutParams(parentLayoutParams);
	FrameLayout.LayoutParams layoutCenterParams =
			new FrameLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT,
			LayoutParams.WRAP_CONTENT );
	layoutCenterParams.setMargins(5, 5, 5, 5);
	layoutCenterParams.gravity = Gravity.CENTER;
	startSpeech = new Button(c);
	startSpeech.setBackgroundColor(Color.RED);
	startSpeech.setLayoutParams(layoutCenterParams);
	startSpeech.setText("start Speech");
	layout.addView(startSpeech);
	final SpeechRecognizer sr = SpeechRecognizer.createSpeechRecognizer(c);
	sr.setRecognitionListener(new RecognitionListener() {

		@Override
		public void onRmsChanged(float rmsdB) {

		}


		

		@Override
		public void onError(int error) {
			// Handle any errors here

		}

		@Override
		public void onEndOfSpeech() {

		}

		@Override
		public void onBufferReceived(byte[] buffer) {
			

		}

		@Override
		public void onBeginningOfSpeech() {
			

		}

		@Override
		public void onEvent(int eventType, Bundle params) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPartialResults(Bundle partialResults) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onReadyForSpeech(Bundle params) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onResults(Bundle results) {
			// TODO Auto-generated method stub
			 ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			 resultString = (String) data.get(0);
			 Toast.makeText(c, resultString.charAt(0)+"  that return", Toast.LENGTH_SHORT).show();
			 I.putExtra("letter", resultString.charAt(0));
			 I.putExtra("Action", "NONE");
			 I.putExtra("State", "S3");
               
		}
	});
	startSpeech.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
					"voice.recognition.test");
			intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
			sr.startListening(intent);
		}
	});
	
	
}


}
