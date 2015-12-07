package com.example.eatthecheese;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;
import uencom.xgame.sound.TTS;
import uencom.xgame.speech.SpeechRecognition;

public class S1  implements IstateActions {
	HeadPhone pre = null;
	static Timer timer;
	OvalShape timeoOv;
	ShapeDrawable timeBg;
	public static int time = 0;
	static TextView showTime;
	static TextView startSpeech;
	static TextView speechWord;
	static LinearLayout layout;
	static String resultString;
	static LinearLayout layout2;
	public SpeechRecognizer sr;
	public Context context;
	static LinearLayout layout3;
	static HeadPhone Hc;
	@Override
	public void onStateEntry(LinearLayout layout,  Intent I,  Context c,HeadPhone H) {
		// TODO Auto-generated method stub
		context = c;
		 Hc =H;
		 
		 I.putExtra("Action", "Right");
		    BitmapDrawable b = (BitmapDrawable) layout.getBackground();
			b.setAlpha(155);
			layout.setBackground(b);
			this.layout = layout;
			layout3 = new LinearLayout(c);
			LinearLayout.LayoutParams layoutParent =
					new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParent.topMargin = 5;
			
			layout3.setWeightSum(1.0F);
			LinearLayout.LayoutParams showTimeParams =new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			showTimeParams.gravity = Gravity.RIGHT;
			showTimeParams.setMarginEnd(3);
			showTimeParams.setMarginStart(2);
			showTimeParams.weight =0.6F;
			RoundRectShape rect = new RoundRectShape(
					  new float[] {30,30, 30,30, 30,30, 30,30},
					  null,
					  null);
			 timeBg= new ShapeDrawable(rect);
			 
			 timeBg.getPaint().setColor(0x99463E3F);
			showTimeParams.topMargin = 5;
			showTime = new TextView(c);
			showTime.setTextSize(22);
			showTime.setGravity(Gravity.CENTER);
			showTime.setTextColor(Color.WHITE);
			showTime.setBackground(timeBg);

			showTime.setLayoutParams(showTimeParams);
			
			timer = new Timer();
			timer.schedule(new RemindTask(),
			           0,        //initial delay
			           1*1000);
			RoundRectShape ov = new RoundRectShape(
					  new float[] {30,30, 30,30, 30,30, 30,30},
					  null,
					  null);
			
			ShapeDrawable bgedit = new ShapeDrawable(ov);
			bgedit.getPaint().setColor(0x99FF9900);
			bgedit.setPadding(5, 5, 5, 5);
			
			
	LinearLayout.LayoutParams layoutEditParams =new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.MATCH_PARENT);
	layoutEditParams.gravity = Gravity.LEFT;
	layoutEditParams.weight =0.4F;
	layoutEditParams.topMargin = 5;
	layoutEditParams.setMarginEnd(2);
	layoutEditParams.setMarginStart(3);
	speechWord =new  TextView(c);
	speechWord.setBackground(bgedit);
	speechWord.setGravity(Gravity.CENTER);
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
	speechWord.setTextSize(38);
	speechWord.setText(workingWord);
	speechWord.setTextColor(Color.BLUE);
	speechWord.setLayoutParams(layoutEditParams);
	layout3.addView(speechWord);
	layout3.addView(showTime);
	layout3.setLayoutParams(layoutParent);
	layout.addView(layout3);
			
			createUI(this.layout,I,c,H);
			
			
	}
	@Override
	public Intent loopBack(final Context c, final Intent I,HeadPhone H) {
		
		// TODO Auto-generated method stub
		
       
		
			return I;
		
	}
	
	
	@Override
	public void onStateExit(Context c, Intent I,HeadPhone H) {
		// TODO Auto-generated method stub
		
		layout.removeView(layout2);
		layout.removeView(layout3);
		Hc.stopCurrentPlay();
		timer.cancel();
		

	}
public void createUI(LinearLayout layout, final Intent I, final Context c,final HeadPhone H) {
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
					+ "Eat The Cheese" + "/Images/speech.png");
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
	startSpeech.setHeight(150);
	startSpeech.setWidth(200);
	
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
			
			 I.putExtra("timeInSecond",time);
			 
			 I.putExtra("letter", resultString.charAt(0));
			 I.putExtra("Action", "NONE");
			 I.putExtra("State", "S2");
               
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

class RemindTask extends TimerTask {
	

    public void run() {
    	if(pre !=null)
    	{
    		pre.release();
    	}
    	Hc = new HeadPhone(context);
    	String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Eat The Cheese/Sound/timer.mp3";
		Hc.setLeftLevel(1);
   		Hc.setRightLevel(1);
		Hc.play(Path, 0);
      time++;
      ((Activity) context).runOnUiThread(new Runnable() {
          @Override
          public void run() {
        	  Drawable background = showTime.getBackground();
      		if (background instanceof ShapeDrawable) {
      			if(time<=100)
      			{
      				  ((ShapeDrawable)background).getPaint().setColor(Color.GRAY);
      				  showTime.setTextColor(Color.GREEN);
      			}
      			else if(time > 100&&time<=200)
      			{
      				  ((ShapeDrawable)background).getPaint().setColor(Color.GRAY);
      				  showTime.setTextColor(Color.YELLOW);
      			}
      			else if(time > 200&&time<=300 )
      			{
      				  ((ShapeDrawable)background).getPaint().setColor(Color.GRAY);
      				  showTime.setTextColor(0x99B40404);
      			}
      			else if(time > 300 ){
      				  ((ShapeDrawable)background).getPaint().setColor(Color.RED);
      				  showTime.setTextColor(Color.WHITE);
      			}
      			
      			
      		
      		} else if (background instanceof GradientDrawable) {
      		    ((GradientDrawable)background).setColor(0x99FF0000);
      		}
        	  
        	  if(time<=50)
        	  {
        		  showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"\n(+25 points)");
  			}
  			else if(time > 50&&time<=100)
  			{
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"\n(+20 points)");
  			}
  			else if(time > 100&&time<=150 )
  			{
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"\n(+15 points)");
  			}
  			else if(time > 150&&time<=200 ){
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"\n(+10 points)");
  			}
  			else if(time > 200&&time<=300 ){
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"\n(+5 points)");
  			}
  			else if(time > 300){
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"\n(-10 points)");
  			}
             
          }
      });
    pre = Hc;
    }
   
	
}
}
