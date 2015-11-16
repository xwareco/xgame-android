package com.example.missingletter;


import java.util.Random;

import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

public class S1  implements IstateActions {
	String[] words = new String[]{"freedom","elephant","education","school","house",
			"egypt","love","peace","greeting","security"
			,"world","image","work","justice","battle"};
	//char [] letters = new char[]{};
	static int stringIndex;
	static String word;
	int letterposition;
	static char letter;
	static Button firstLetter;
	static Button secondLetter;
	static Button thirdLetter;
	static Button forthLetter;
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
		 I.putExtra("Action", "Right");
		 int level = I.getIntExtra("Level", 0);
			
			I.putExtra("Level", ++level);
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
		layout.removeView(speechWord);
		layout.removeView(layout2);
		
	}
	public void createUI(LinearLayout layout, final Intent I, final Context c) {
		
		flag = I.getBooleanExtra("Flag", true);
		if(flag)
		{
		Random random = new Random();
		stringIndex = random.nextInt(15);
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
			
			letter = I.getCharExtra("letter", '-');
			
		}
		///////////////
		 layout2 = new LinearLayout(c);
		LinearLayout.LayoutParams layoutCenterParent =
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT);
		
// edit test
	
		OvalShape ov = new OvalShape();
		
				ShapeDrawable bgedit = new ShapeDrawable(ov);
				bgedit.getPaint().setColor(0x99FF9900);
				bgedit.getPaint().setStrokeWidth(12);
				bgedit.setPadding(5, 5, 5, 5);
				
				
		LinearLayout.LayoutParams layoutEditParams =new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutEditParams.gravity = Gravity.CENTER_HORIZONTAL;
		layoutEditParams.topMargin = 18;
		
		speechWord =new  TextView(c);
		speechWord.setBackground(bgedit);
		//speechWord.setWidth(150);
		//speechWord.setHeight(90);
		char[] temparr = new char[(arr.length)*2];
		speechWord.setPadding(7, 10, 7, 7);
		speechWord.setTextSize(38);
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
		layout.addView(speechWord);
		LinearLayout.LayoutParams layoutCenterParams =
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER);
		layoutCenterParams.setMargins(5, 5, 5, 5);

		
		//layoutCenterParams.topMargin =150;
		firstLetter = new Button(c);
		secondLetter  = new Button(c);
	    thirdLetter  = new Button(c);
	    forthLetter  = new Button(c);
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
		int n = letterposition % 4;
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

				String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/MissingLetter/Sound/Button_best.mp3";
				//score sound
				HeadPhone HP = new HeadPhone(c);
				HP.setLeftLevel(1);
				HP.setRightLevel(1);
				if (HP.detectHeadPhones() == true)
					HP.play(Path, 0);
				if(firstLetter.getText().toString().equals(letter+""))
				{
					
						I.putExtra("Action", "NONE");
						I.putExtra("State", "S3");
					
				}
				else
				{
					
					I.putExtra("Action", "NONE");
					I.putExtra("State", "S2");
				}
				
			}
		});
secondLetter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/MissingLetter/Sound/Button_best.mp3";
				//score sound
				HeadPhone HP = new HeadPhone(c);
				HP.setLeftLevel(1);
				HP.setRightLevel(1);
				if (HP.detectHeadPhones() == true)
					HP.play(Path, 0);
				if(secondLetter.getText().toString().equals(letter+""))
				{
						I.putExtra("Action", "NONE");
						I.putExtra("State", "S3");
					
				}
				else
				{
					
					I.putExtra("Action", "NONE");
					I.putExtra("State", "S2");
				}
				
			}
		});
thirdLetter.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/MissingLetter/Sound/Button_best.mp3";
		//score sound
		HeadPhone HP = new HeadPhone(c);
		HP.setLeftLevel(1);
		HP.setRightLevel(1);
		if (HP.detectHeadPhones() == true)
			HP.play(Path, 0);
		if(thirdLetter.getText().toString().equals(letter+""))
		{
			
			I.putExtra("Action", "NONE");
			I.putExtra("State", "S3");
		
	}
	else
	{
		
		I.putExtra("Action", "NONE");
		I.putExtra("State", "S2");
	}
		
	}
});
forthLetter.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/MissingLetter/Sound/Button_best.mp3";
		//score sound
		HeadPhone HP = new HeadPhone(c);
		HP.setLeftLevel(1);
		HP.setRightLevel(1);
		if (HP.detectHeadPhones() == true)
			HP.play(Path, 0);
		if(forthLetter.getText().toString().equals(letter+""))
		{
			
				I.putExtra("Action", "NONE");
				I.putExtra("State", "S3");
			
		}
		else
		{
			
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
	
}
