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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.util.Log;
import android.util.StateSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import xware.xgame.interfaces.IstateActions;
import xware.xgame.sound.HeadPhone;
import xware.xgame.sound.TTS;
import xware.xgame.speech.SpeechRecognition;

public class S2  implements IstateActions {
	
	static TextView startSpeech;
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
String workingWord = I.getStringExtra("workingWord");
char[] temparr = new char[workingWord.length()*2];
int j=0;
for(int i=1;i<(workingWord.length()*2);i+=2)
{
	temparr [(i-1)]= workingWord.charAt(j);
	temparr[i] = '-';
	Log.d("string_show", temparr[i-1]+" "+temparr[i]);
	
	j++;
}
temparr[(temparr.length)-1] = ' ';
workingWord = new String(temparr);
speechWord.setPadding(7, 10, 7, 7);
speechWord.setTextSize(38);
speechWord.setText(workingWord);
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
	
	startSpeech = new TextView(c);
		RoundRectShape rect = new RoundRectShape(
			  new float[] {30,30, 30,30, 30,30, 30,30},
			  null,
			  null);
	OvalShape ov = new OvalShape();
			ShapeDrawable bg = new ShapeDrawable(ov);
			
			bg.getPaint().setColor(Color.RED);
			
			StateListDrawable stld = new StateListDrawable();
			
			stld.addState(new int[] { android.R.attr.state_enabled }, bg);
			Drawable d = Drawable.createFromPath(Environment
					.getExternalStorageDirectory().toString()
					+ "/xGame/Games/"
					+ "Hangman" + "/Images/speech.png");
			Bitmap b = drawableToBitmap(d);
			
			 Bitmap bc1 = Bitmap.createBitmap(b.getWidth() + 20, b.getHeight() + 20, Bitmap.Config.ARGB_8888);
		        Canvas c1 = new Canvas(bc1);
		        c1.drawBitmap(b, 0, 0, null);
		        Bitmap bc2 = Bitmap.createBitmap(b.getWidth() + 20, b.getHeight() + 20, Bitmap.Config.ARGB_8888);
		        Canvas c2 = new Canvas(bc2);
		        c2.drawBitmap(b, 20, 20, null);

		        stld = new StateListDrawable();
		        stld.addState(new int[] { android.R.attr.state_pressed },  new BitmapDrawable(c.getResources(),bc2));
		        stld.addState(StateSet.WILD_CARD, new BitmapDrawable(c.getResources(),bc1));
	startSpeech.setLayoutParams(layoutCenterParams);
	
	startSpeech.setBackground(stld);
	
	startSpeech.setGravity(Gravity.CENTER);
	startSpeech.setHeight(250);
	startSpeech.setWidth(250);
	
	startSpeech.setContentDescription("Speech");
	startSpeech.setTextColor(0x990099FF);
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
			 Toast.makeText(c,"You said  " +resultString.charAt(0)+"", Toast.LENGTH_SHORT).show();
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

public static Bitmap drawableToBitmap (Drawable drawable) {
    Bitmap bitmap = null;
 
    if (drawable instanceof BitmapDrawable) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        if(bitmapDrawable.getBitmap() != null) {
            return bitmapDrawable.getBitmap();
        } 
    } 
 
    if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
        bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
    } else { 
        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    } 
 
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);
    return bitmap;
} 


}
