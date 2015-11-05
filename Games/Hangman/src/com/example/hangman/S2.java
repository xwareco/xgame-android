package com.example.hangman;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.zip.Inflater;

import com.actionbarsherlock.internal.ResourcesCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import javaclasses.CustomShapeDrawable;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;
import uencom.xgame.sound.TTS;
import uencom.xgame.speech.SpeechRecognition;

public class S2  implements IstateActions {
	
	static Button startSpeech;
	static TextView speechWord;
	static LinearLayout layout;
	static String resultString;
	static LinearLayout layout2;
	public SpeechRecognizer sr;
	@Override
	public void onStateEntry(LinearLayout layout,  Intent I,  Context c, HeadPhone H) {
		// TODO Auto-generated method stub
	    I.putExtra("Action", "Right");
	    BitmapDrawable b = (BitmapDrawable) layout.getBackground();
		b.setAlpha(155);
		layout.setBackground(b);
		this.layout = layout;
		RoundRectShape ov = new RoundRectShape(
				  new float[] {30,30, 30,30, 30,30, 30,30},
				  null,
				  null);
		
		ShapeDrawable bgedit = new ShapeDrawable(ov);
		bgedit.getPaint().setColor(0x99FF9900);
		bgedit.getPaint().setStrokeWidth(12);
		bgedit.setPadding(5, 5, 5, 5);
		
		
LinearLayout.LayoutParams layoutEditParams =new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.WRAP_CONTENT);
layoutEditParams.gravity = Gravity.CENTER_HORIZONTAL;
layoutEditParams.topMargin = 13;

speechWord =new  TextView(c);
speechWord.setBackground(bgedit);
//speechWord.setWidth(150);
//speechWord.setHeight(90);
speechWord.setPadding(7, 10, 7, 7);
speechWord.setTextSize(38);
speechWord.setText(I.getStringExtra("workingWord"));
speechWord.setTextColor(Color.BLUE);
speechWord.setLayoutParams(layoutEditParams);
layout.addView(speechWord);
		
		createUI(this.layout,I,c);
		
		
	}
	@Override
	public Intent loopBack(final Context c, final Intent I, HeadPhone H) {
		
		// TODO Auto-generated method stub
		return I;
		
	}
	
	
	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub
		
		layout.removeView(layout2);
		layout.removeView(startSpeech);
		layout.removeView(speechWord);
	
		

	}
public void createUI(LinearLayout layout, final Intent I, final Context c) {
	 layout2 = new LinearLayout(c);
	LinearLayout.LayoutParams layoutCenterParent =
			new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);


	
	
	LinearLayout.LayoutParams layoutCenterParams =
			new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
	

	
	//layoutCenterParams.topMargin =150;
	startSpeech = new Button(c);
	//startSpeech.setBackgroundColor(Color.RED);
	//startSpeech.setBackground(c.getResources().getDrawable(R.drawable.yellow_button));
	//Inflater inflater = new Inflater();
	//Button firstStyleBtn = (Button) inflater.inflate(R.layout.centerbutton, layout, false);
	
   

	
	//startSpeech.setBackgroundResource(R.drawable.text_background);
	RoundRectShape rect = new RoundRectShape(
			  new float[] {30,30, 30,30, 30,30, 30,30},
			  null,
			  null);
	OvalShape ov = new OvalShape();
			ShapeDrawable bg = new ShapeDrawable(ov);
			bg.getPaint().setColor(0x990099FF);
			ShapeDrawable bg2 = new ShapeDrawable(ov);
			bg2.getPaint().setColor(Color.LTGRAY);
			StateListDrawable stld = new StateListDrawable();
			stld.addState(new int[] { android.R.attr.state_pressed }, bg2);
			stld.addState(new int[] { android.R.attr.state_enabled }, bg);
			
	startSpeech.setLayoutParams(layoutCenterParams);
	
	startSpeech.setBackground(stld);
	startSpeech.setPadding(10, 10, 10, 10);
	startSpeech.setGravity(Gravity.CENTER);
	startSpeech.setHeight(150);
	startSpeech.setWidth(200);
	//startSpeech.setBackground(states);
	//startSpeech.setBackground(c.getResources().getDrawable(R.drawable.text_background));
	startSpeech.setText("start Speech");
	layout2.addView(startSpeech);
	
	layout2.setLayoutParams(layoutCenterParent);
	layout2.setHorizontalGravity(Gravity.CENTER);
	layout2.setVerticalGravity(Gravity.CENTER);
	layout.addView(layout2);
	
	sr = SpeechRecognizer.createSpeechRecognizer(c);
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
