package com.example.missingletter;


import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

public class S1  implements IstateActions {
	String[] words = new String[]{"egypt","peace","greeting","shake","product","world","option",
			"school","house","interest","message",
			"image","growth","profit","work","battle","shout","margin","rubbed","depth","limit","shaking","doll","poetry",
			"order","payment","rush","shallow","love","sale",
			"arrangement","attempt","possibility","scared","donkey","policeman","egypt","positive","Ellen","satellites","essential",
			"exchange","security","increase","market","opinion","mistake","objective",
			"output","advice","penalty","permission","justice","instructions","discussion","satisfied","education",
			"production","industry","deeply","promotion","invoice","selection","plates","freedom","elephant","possibly","offer",
			"practical","guarantee","improvement",
			"inventory","knowledge","loss"};
	//char [] letters = new char[]{};
	HeadPhone pre = null;
	static Timer timer;
	OvalShape timeoOv;
	ShapeDrawable timeBg;
	public static int time = 0;
	static TextView showTime;
	 public Context context;
	 static LinearLayout layout3;
	static HeadPhone Hc;
	static int stringIndex;
	static String word;
	int letterposition;
	static char letter;
	static TextView firstLetter;
	static TextView secondLetter;
	static TextView thirdLetter;
	static TextView forthLetter;
	static TextView speechWord;
	static LinearLayout layout;
	static String clickedLetter;
	static LinearLayout layout2;
	static char[]arr;
	 static boolean flag;
	 static int successNum = 1;

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		context = C;
		Hc =H;
		
		 I.putExtra("Action", "Right");
		
	     BitmapDrawable b = (BitmapDrawable) layout.getBackground();
		 b.setAlpha(189);
		 layout.setBackground(b);
		 this.layout = layout;
		 createUI(this.layout,I,C);
		
				
	}

	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub
		Hc.stopCurrentPlay();
		timer.cancel();
		layout.removeView(layout2);
		layout.removeView(layout3);
		
	}
	public void createUI(LinearLayout layout, final Intent I, final Context c) {
		
		flag = I.getBooleanExtra("Flag", true);
		if(flag)
		{
			int stringIndex = I.getIntExtra("Random", 0);
			if(stringIndex == 0){
			 Random rand = new Random();
			 stringIndex= rand.nextInt(words.length);
			}
			stringIndex++;
			if(stringIndex == words.length)
				stringIndex = stringIndex%words.length;
			
			 I.putExtra("Random", stringIndex);
		
		word = words[stringIndex];
		
		I.putExtra("word",word );
		letterposition = (int) Math.floor(Math.random()*word.length());
	    arr = word.toCharArray();
	    arr[letterposition] = '$';
		StringBuilder tempString = new StringBuilder(word);
		tempString.replace(letterposition, letterposition+1, "$");
		letter = word.charAt(letterposition);
		
		I.putExtra("letter", letter);
		}else
		{
			word = I.getStringExtra("word");
			letter = I.getCharExtra("letter", '-');
			
		}
		///////////////
		 layout2 = new LinearLayout(c);
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
			showTimeParams.weight =0.6F;
			showTimeParams.setMarginEnd(3);
			showTimeParams.setMarginStart(2);
			RoundRectShape rect2 = new RoundRectShape(
					  new float[] {30,30, 30,30, 30,30, 30,30},
					  null,
					  null);
			 timeBg= new ShapeDrawable(rect2);
			 
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
		LinearLayout.LayoutParams layoutCenterParent =
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT);
		
// edit test
	
		RoundRectShape ov = new RoundRectShape(
				  new float[] {30,30, 30,30, 30,30, 30,30},
				  null,
				  null);
		
				ShapeDrawable bgedit = new ShapeDrawable(ov);
				bgedit.getPaint().setColor(0x99FF9900);
				bgedit.getPaint().setStrokeWidth(12);
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
		//speechWord.setWidth(150);
		//speechWord.setHeight(90);
		char[] temparr = new char[(arr.length)*2];
		speechWord.setGravity(Gravity.CENTER);
		speechWord.setTextSize(35);
		int j=0;
		for(int i=1;i<(word.length()*2);i+=2)
		{
			temparr [(i-1)]= arr[j];
			temparr[i] = '-';
			Log.d("string_show", temparr[i-1]+" "+temparr[i]);
			
			j++;
		}
		temparr[(temparr.length)-1] = ' ';
		speechWord.setText(new String(temparr));
		speechWord.setTextColor(Color.BLUE);
		speechWord.setLayoutParams(layoutEditParams);
		layout3.addView(speechWord);
		layout3.addView(showTime);
		layout3.setLayoutParams(layoutParent);
		layout.addView(layout3);
		LinearLayout.LayoutParams layoutCenterParams =
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						100,Gravity.CENTER);
		layoutCenterParams.setMargins(5, 5, 5, 5);

		
		//layoutCenterParams.topMargin =150;
		firstLetter = new TextView(c);
		secondLetter  = new TextView(c);
	    thirdLetter  = new TextView(c);
	    forthLetter  = new TextView(c);
		//startSpeech.setBackgroundColor(Color.RED);
		//startSpeech.setBackground(c.getResources().getDrawable(R.drawable.yellow_button));
		//Inflater inflater = new Inflater();
		//Button firstStyleBtn = (Button) inflater.inflate(R.layout.centerbutton, layout, false);
		
	   

		
		//startSpeech.setBackgroundResource(R.drawable.text_background);
		
	    RoundRectShape rect = new RoundRectShape(
				  new float[] {30,30, 30,30, 30,30, 30,30},
				  null,
				  null);
				ShapeDrawable bg = new ShapeDrawable(rect);
				bg.getPaint().setColor(0x990099FF);
				ShapeDrawable bg2 = new ShapeDrawable(rect);
				bg2.getPaint().setColor(Color.LTGRAY);
				StateListDrawable first = new StateListDrawable();
				first.addState(new int[] { android.R.attr.state_pressed }, bg2);
				first.addState(new int[] { android.R.attr.state_enabled }, bg);
				StateListDrawable second = new StateListDrawable();
				second.addState(new int[] { android.R.attr.state_pressed }, bg2);
				second.addState(new int[] { android.R.attr.state_enabled }, bg);
				StateListDrawable third = new StateListDrawable();
				third.addState(new int[] { android.R.attr.state_pressed }, bg2);
				third.addState(new int[] { android.R.attr.state_enabled }, bg);
				StateListDrawable forth = new StateListDrawable();
				forth.addState(new int[] { android.R.attr.state_pressed }, bg2);
				forth.addState(new int[] { android.R.attr.state_enabled }, bg);
				
		firstLetter.setLayoutParams(layoutCenterParams);
		secondLetter.setLayoutParams(layoutCenterParams);
		thirdLetter.setLayoutParams(layoutCenterParams);
		forthLetter.setLayoutParams(layoutCenterParams);
		
		firstLetter.setBackground(first);
		secondLetter.setBackground(second);
		thirdLetter.setBackground(third);
		forthLetter.setBackground(forth);
		firstLetter.setTextSize(120);
		secondLetter.setTextSize(120);
		thirdLetter.setTextSize(120);
		forthLetter.setTextSize(120);
		firstLetter.setHeight(100);
		secondLetter.setHeight(100);
		thirdLetter.setHeight(100);
		forthLetter.setHeight(100);
		firstLetter.setTextColor(Color.WHITE);
		secondLetter.setTextColor(Color.WHITE);
		thirdLetter.setTextColor(Color.WHITE);
		forthLetter.setTextColor(Color.WHITE);
		firstLetter.setGravity(Gravity.CENTER);
		secondLetter.setGravity(Gravity.CENTER);
		thirdLetter.setGravity(Gravity.CENTER);
		forthLetter.setGravity(Gravity.CENTER);
		//startSpeech.setPadding(10, 10, 10, 10);
		//startSpeech.setGravity(Gravity.CENTER);

		Random r = new Random();
		char ch = (char)(r.nextInt(26) + 'a');
		   firstLetter.setText(ch+"");
		   firstLetter.setTextSize(20);
		    ch = (char)(r.nextInt(26) + 'a');
		   secondLetter.setText(ch+"");
		   secondLetter.setTextSize(20);
		   ch = (char)(r.nextInt(26) + 'a');
		   thirdLetter.setText(ch+"");
		   thirdLetter.setTextSize(20);
		   ch = (char)(r.nextInt(26) + 'a');
		   forthLetter.setText(ch+"");
		   forthLetter.setTextSize(20);
		int n = new Random().nextInt(4);
		switch (n) {
		case 0:firstLetter.setText(letter+"");
			break;
		case 1:secondLetter.setText(letter+"");
	        break;
		case 2:thirdLetter.setText(letter+"");
		    break;
	    case 3:forthLetter.setText(letter+"");
            break;
		default:
			break;
		}
	  
		//startSpeech.setBackground(states);
		//startSpeech.setBackground(c.getResources().getDrawable(R.drawable.text_background));
		
		layout2.addView(firstLetter);
		layout2.addView(secondLetter);
		layout2.addView(thirdLetter);
		layout2.addView(forthLetter);
		firstLetter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Missing Letter/Sound/Button_best.mp3";
				//score sound
				HeadPhone HP = new HeadPhone(c);
				HP.setLeftLevel(1);
				HP.setRightLevel(1);
				if (HP.detectHeadPhones() == true)
					HP.play(Path, 0);
				if(firstLetter.getText().toString().equals(letter+""))
				{
					 I.putExtra("timeInSecond",time);
					
						I.putExtra("Action", "NONE");
						I.putExtra("State", "S3");
					
				}
				else
				{
					 I.putExtra("timeInSecond",time);
					
					I.putExtra("Action", "NONE");
					I.putExtra("State", "S2");
				}
				
			}
		});
secondLetter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Missing Letter/Sound/Button_best.mp3";
				//score sound
				HeadPhone HP = new HeadPhone(c);
				HP.setLeftLevel(1);
				HP.setRightLevel(1);
				if (HP.detectHeadPhones() == true)
					HP.play(Path, 0);
				if(secondLetter.getText().toString().equals(letter+""))
				{
					 I.putExtra("timeInSecond",time);
					
						I.putExtra("Action", "NONE");
						I.putExtra("State", "S3");
					
				}
				else
				{
					 I.putExtra("timeInSecond",time);
					
					I.putExtra("Action", "NONE");
					I.putExtra("State", "S2");
				}
				
			}
		});
thirdLetter.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Missing Letter/Sound/Button_best.mp3";
		//score sound
		HeadPhone HP = new HeadPhone(c);
		HP.setLeftLevel(1);
		HP.setRightLevel(1);
		if (HP.detectHeadPhones() == true)
			HP.play(Path, 0);
		if(thirdLetter.getText().toString().equals(letter+""))
		{
			 I.putExtra("timeInSecond",time);
			
			I.putExtra("Action", "NONE");
			I.putExtra("State", "S3");
		
	}
	else
	{
		 I.putExtra("timeInSecond",time);
		
		I.putExtra("Action", "NONE");
		I.putExtra("State", "S2");
	}
		
	}
});
forthLetter.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Missing Letter/Sound/Button_best.mp3";
		//score sound
		HeadPhone HP = new HeadPhone(c);
		HP.setLeftLevel(1);
		HP.setRightLevel(1);
		if (HP.detectHeadPhones() == true)
			HP.play(Path, 0);
		if(forthLetter.getText().toString().equals(letter+""))
		{
			 I.putExtra("timeInSecond",time);
			 
				I.putExtra("Action", "NONE");
				I.putExtra("State", "S3");
			
		}
		else
		{
			 I.putExtra("timeInSecond",time);
			
			I.putExtra("Action", "NONE");
			I.putExtra("State", "S2");
		}
		
	}
});
		
		layout2.setLayoutParams(layoutCenterParent);
		layout2.setHorizontalGravity(Gravity.CENTER);
		layout2.setVerticalGravity(Gravity.CENTER);
		layout.addView(layout2);
	}

class RemindTask extends TimerTask {
	

    public void run() {
    	if(pre !=null)
    	{
    		pre.release();
    	}
    	Hc = new HeadPhone(context);
    	String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/The Word Master/Sound/timer.mp3";
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
