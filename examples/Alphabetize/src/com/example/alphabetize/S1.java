package com.example.alphabetize;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import android.hardware.camera2.params.TonemapCurve;
import android.os.Bundle;
import android.os.Environment;
import android.provider.UserDictionary.Words;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.StateSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;
import uencom.xgame.sound.TTS;
import uencom.xgame.speech.SpeechRecognition;

public class S1   implements IstateActions {
	String[] words1 = new String[]{
			"egypt","peace","greeting","shake","product","world","option",
			"school","house","interest","message",
			"image","growth","profit","work","battle","shout"
			
			};
	String[] words2 = new String[]{"margin","rubbed","depth","limit","shaking","doll","poetry",
			"order","payment","rush","shallow","love","sale",
			"arrangement","attempt","possibility","scared"
			};
	String[] words3 = new String[]{"donkey","policeman","egypt","positive","Ellen","satellites","essential",
			"exchange","security","increase","market","opinion","mistake","objective",
			"output","advice","penalty"};
	String[] words4 = new String[]{"permission","justice","instructions","discussion","satisfied","education",
			"production","industry","deeply","promotion","invoice"};
	String[] words5 = new String[]{"selection","plates","freedom","elephant","possibly","offer",
			"practical","guarantee","improvement",
			"inventory","knowledge","loss"
			};
	static ScrollView scroll;
	HeadPhone pre = null;
	public static long startTime;
	static Timer timer;
	GridLayout letters ;
	static TextView speechWord;
	static TextView showTime;
	static String[] wordsArray;
	static LinearLayout layout;
	static String workingWord ;
	static String word ;
	OvalShape timeoOv;
	ShapeDrawable timeBg;
	public static int time = 0;
	static LinearLayout layout2;
	public SpeechRecognizer sr;
	static char[]arr;
	StringBuilder str  = new StringBuilder();
	TextView[] btn ;
	TextView[] btn2;
	 static boolean flag;
	 static int successNum = 1;
	 public Context context;
	 static HeadPhone Hc;
	@Override
	public void onStateEntry(LinearLayout layout,  Intent I,  Context c,HeadPhone H) {
		// TODO Auto-generated method stub
		context = c;
		Hc = H;
LinearLayout.LayoutParams layoutEditParams =new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.WRAP_CONTENT);
layoutEditParams.gravity = Gravity.CENTER_HORIZONTAL;
RoundRectShape rect = new RoundRectShape(
		  new float[] {30,30, 30,30, 30,30, 30,30},
		  null,
		  null);
 timeBg= new ShapeDrawable(rect);
 
 timeBg.getPaint().setColor(0x99463E3F);
layoutEditParams.topMargin = 4;
showTime = new TextView(c);
showTime.setTextSize(25);
showTime.setTextColor(Color.WHITE);
showTime.setBackground(timeBg);

showTime.setLayoutParams(layoutEditParams);
layout.addView(showTime);
timer = new Timer();
timer.schedule(new RemindTask(),
           0,        //initial delay
           1*1000);
		startTime = System.nanoTime();
		 I.putExtra("Action", "Right");
		    BitmapDrawable b = (BitmapDrawable) layout.getBackground();
			b.setAlpha(155);
			layout.setBackground(b);
			this.layout = layout;
			
			String[] words;
			
			 Random rand = new Random();
			 int level = I.getIntExtra("Level", 0);
			 switch (level) {
			case 0: {words =words1; }
				
				break;
			case 1: {words =words2; }
			
			break;
           case 2: {words =words3; }
			
			break;
			case 3: {words =words4; }
			
			break;
			case 4: {words =words5; }
			
			break;
			

			default:
			{words =words1; }
			break;
			}
			 int random = rand.nextInt(words.length);
			 wordsArray = new String[4];
			 for(int i = 0;i<wordsArray.length;i++)
			 {
				 wordsArray[i] = words[random];
				 random++;
				 if(random == words.length)
					 random = 0;
			 }
	
			
			createUI(this.layout,I,c);
			
			
	}
	@Override
	public Intent loopBack(final Context c, final Intent I,HeadPhone H) {
		
		
		
		
			return I;
		
	}
	
	
	@Override
	public void onStateExit(Context c, Intent I,HeadPhone H) {
		// TODO Auto-generated method stub
		 
		layout.removeView(layout2);
		layout.removeView(showTime);
		layout.removeView(speechWord);
		layout.removeView(scroll);
		Hc.stopCurrentPlay();
		 timer.cancel();

	}
public void createUI(LinearLayout layout, final Intent I, final Context c) {
	
	layout2 = new LinearLayout(c);
	scroll = new ScrollView(context);
	scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT));
	LinearLayout.LayoutParams layoutCenterParent =
			new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
	
	layoutCenterParent.topMargin = 10;
	layoutCenterParent.leftMargin = 5;
	
	letters = new GridLayout(c);
	
	GridLayout.LayoutParams lettersParams = new GridLayout.LayoutParams();
	letters.setUseDefaultMargins(true);
	lettersParams.setMargins(0, 5, 5, 0);
	letters.setColumnCount(2);
	
	letters.setOrientation(GridLayout.HORIZONTAL);
	letters.setLayoutParams(lettersParams);
	
	
	//==================================================================
	
	

	RoundRectShape rect = new RoundRectShape(
			  new float[] {30,30, 30,30, 30,30, 30,30},
			  null,
			  null);
	
			ShapeDrawable bg = new ShapeDrawable(rect);
			bg.getPaint().setColor(0x99210B61);
			ShapeDrawable bg2 = new ShapeDrawable(rect);
			bg2.getPaint().setColor(Color.LTGRAY);
			
			StateListDrawable second = new StateListDrawable();
			second.addState(new int[] { android.R.attr.state_pressed }, bg2);
			second.addState(new int[] { android.R.attr.state_enabled }, bg);
			StateListDrawable third = new StateListDrawable();
			third.addState(new int[] { android.R.attr.state_pressed }, bg2);
			third.addState(new int[] { android.R.attr.state_enabled }, bg);
			StateListDrawable forth = new StateListDrawable();
			forth.addState(new int[] { android.R.attr.state_pressed }, bg2);
			forth.addState(new int[] { android.R.attr.state_enabled }, bg);
			btn = new TextView[8];
			 System.out.println(wordsArray[0]+" "+wordsArray[1]);
			for ( int step = 0,i=0; step < 8; step++) {
				
				btn[step] = new TextView(c);
				btn[step].setGravity(Gravity.CENTER);
				
				StateListDrawable first = new StateListDrawable();
				first.addState(new int[] { android.R.attr.state_pressed }, bg2);
				first.addState(new int[] { android.R.attr.state_enabled }, bg);
				btn[step].setBackground(first);
				btn[step].setWidth((getScreenWidth()/2)-20);
				btn[step].setHeight((getScreenHeight()/8)-10);
				btn[step].setTextColor(Color.WHITE);
				btn[step].setTextSize(25);
				
			    if(step%2==0){
					btn[step].setText(wordsArray[i]+"");i++;
					btn[step].setOnClickListener(handleOnClick(btn[step], step,I));
			    }
				letters.addView(btn[step]);
				
			}
			layout2.addView(letters);
			layout2.setLayoutParams(layoutCenterParent);
			scroll.setLayoutParams(layoutCenterParent);
			scroll.addView(layout2);
			layout.addView(scroll);
			

}
View.OnClickListener handleOnClick(final TextView button,final int index,final Intent I) {
    return new View.OnClickListener() {
        public void onClick(View v) {
        	
        	boolean can = true;
        	if(!(btn[index].getText().toString().equals("")))
        	{
        	for(int m =0;m<8;m+=2)
        	{
        		System.out.println((btn[index].getText().toString().compareTo(btn[m].getText().toString())));
        		if(index !=m&& !(btn[m].getText().toString().equals(""))){
        		if((btn[index].getText().toString().compareTo(btn[m].getText().toString())>0))
        		   {System.out.println("entered");
        		   String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Alphabetize/Sound/Button.mp3";
           		//score sound
           		HeadPhone HP = new HeadPhone(context);
           		HP.setLeftLevel(1);
           		HP.setRightLevel(1);
           		if (HP.detectHeadPhones() == true)
           			HP.play(Path, 0);
        		   LinearLayout toastLayout = new LinearLayout(context);
   				LinearLayout.LayoutParams layoutToastParams =
   						new LinearLayout.LayoutParams(
   								LinearLayout.LayoutParams.MATCH_PARENT,
   								LinearLayout.LayoutParams.MATCH_PARENT);
   				toastLayout.setLayoutParams(layoutToastParams);
   				toastLayout.setBackgroundColor(Color.DKGRAY);
   				ImageButton image = new ImageButton(context);
   				LinearLayout.LayoutParams toastchield =
   						new LinearLayout.LayoutParams(
   								LinearLayout.LayoutParams.WRAP_CONTENT,
   								LinearLayout.LayoutParams.WRAP_CONTENT);
   			//	toastchield.rightMargin = 8;
   				Drawable d = Drawable.createFromPath(Environment
   						.getExternalStorageDirectory().toString()
   						+ "/xGame/Games/"
   						+ "Alphabetize" + "/Images/error.png");
   				image.setImageDrawable(d);
   				image.setContentDescription("Error");
   				image.setLayoutParams(toastchield);
   		        toastLayout.addView(image);
                  	Toast toast = new Toast(context);
   				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
   				toast.setDuration(Toast.LENGTH_SHORT);
   				toast.setView(toastLayout);
   				toast.show();
   				int score = I.getIntExtra("Score", 0);
   				
   				I.putExtra("Score", score);
   				int failnum = I.getIntExtra("fail", 0);
   				failnum++;
   				I.putExtra("fail", failnum);
   				if(failnum == 2)
   				{
   					I.putExtra("timeInSecond",time);
   					
   					
   					
   					I.putExtra("Action", "NONE");
					I.putExtra("State", "S2");
   				}
        			can = false;
        			break;
        		   }
        		}
        	}
        	if(can)
        	{
        		for(int i =1;i<8;i+=2)
        		{
        			if(btn[i].getText().toString().equals(""))
        			{
        				btn[i].setText(btn[index].getText().toString());
        				btn[index].setText("");
        				String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Alphabetize/Sound/Button_best.mp3";
        	    		//score sound
        	    		HeadPhone HP = new HeadPhone(context);
        	    		HP.setLeftLevel(1);
        	    		HP.setRightLevel(1);
        	    		if (HP.detectHeadPhones() == true)
        	    			HP.play(Path, 0);
        				LinearLayout toastLayout = new LinearLayout(context);
        				LinearLayout.LayoutParams layoutToastParams =
        						new LinearLayout.LayoutParams(
        								LinearLayout.LayoutParams.MATCH_PARENT,
        								LinearLayout.LayoutParams.MATCH_PARENT);
        				toastLayout.setLayoutParams(layoutToastParams);
        				toastLayout.setBackgroundColor(Color.DKGRAY);
        				ImageButton image = new ImageButton(context);
        				LinearLayout.LayoutParams toastchield =
        						new LinearLayout.LayoutParams(
        								LinearLayout.LayoutParams.WRAP_CONTENT,
        								LinearLayout.LayoutParams.WRAP_CONTENT);
        			//	toastchield.rightMargin = 8;
        				Drawable d = Drawable.createFromPath(Environment
        						.getExternalStorageDirectory().toString()
        						+ "/xGame/Games/"
        						+ "Alphabetize" + "/Images/ok.png");
        				image.setImageDrawable(d);
        				image.setContentDescription("Correct");
        				image.setLayoutParams(toastchield);
        		        toastLayout.addView(image);
                       	Toast toast = new Toast(context);
        				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        				toast.setDuration(Toast.LENGTH_SHORT);
        				toast.setView(toastLayout);
        				toast.show();
        				
        				if(btn[0].getText().toString().equals("")&&btn[2].getText().toString().equals("")&&btn[4].getText().toString().equals("")&&btn[6].getText().toString().equals(""))
        				{
        					I.putExtra("timeInSecond",time);
           					Toast.makeText(context, "your time till now: "+ time+" second go on you can do it", Toast.LENGTH_SHORT).show();
           					
        					I.putExtra("Action", "NONE");
        					I.putExtra("State", "S3");
        				}
        				break;
        			}
        				
        			
        		}
        	}
        	can = true;
        }
        }
    };
 
}
public static int getScreenWidth() {
    return Resources.getSystem().getDisplayMetrics().widthPixels;
}

public static int getScreenHeight() {
    return Resources.getSystem().getDisplayMetrics().heightPixels;
}


class RemindTask extends TimerTask {
	

    public void run() {
    	if(pre !=null)
    	{
    		pre.release();
    	}
    	Hc = new HeadPhone(context);
    	String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Alphabetize/Sound/timer.mp3";
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
      				showTime.setTextColor(Color.GREEN);
      			}
      			else if(time > 200&&time<=300 )
      			{
      				  ((ShapeDrawable)background).getPaint().setColor(Color.GRAY);
      				showTime.setTextColor(Color.YELLOW);
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
        		  showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"(+25 points)");
  			}
  			else if(time > 50&&time<=100)
  			{
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"(+20 points)");
  			}
  			else if(time > 100&&time<=150 )
  			{
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"(+15 points)");
  			}
  			else if(time > 150&&time<=200 ){
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"(+10 points)");
  			}
  			else if(time > 200&&time<=300 ){
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"(+5 points)");
  			}
  			else if(time > 300){
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"(-10 points)");
  			}
             
          }
      });
    pre = Hc;
    }
   
	
}
} 



