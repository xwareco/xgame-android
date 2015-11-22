package com.example.spell;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
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

public class S4 extends Activity implements IstateActions {
	
    static int failnum = 0;
    static LinearLayout layout;
    static Button tryAgain;
    static LinearLayout layout2;
	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		// TODO Auto-generated method stub
		I.putExtra("Action", "Right");
		 BitmapDrawable b = (BitmapDrawable) layout.getBackground();
			b.setAlpha(155);
			layout.setBackground(b);

			String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Spell/Sound/error.mp3";
			//score sound
			HeadPhone HP = new HeadPhone(C);
			HP.setLeftLevel(1);
			HP.setRightLevel(1);
			if (HP.detectHeadPhones() == true)
				HP.play(Path, 0);
		failnum = I.getIntExtra("failnum", 0);
		failnum++;
		I.putExtra("failnum", failnum);
		Toast.makeText(C, "You stil have "+(5-failnum)+" tries to go", Toast.LENGTH_LONG).show();
		this.layout = layout;
		if(failnum == 5)
		{
			int score = I.getIntExtra("Score", 0);
			String word = I.getStringExtra("word");
			I.putExtra("Score",( score/word.length())*100);
			I.putExtra("Count", 20);
		//	I.putExtra("Action", "NONE");
		//	I.putExtra("State", "S6");
		}
		else
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
		layout.removeView(tryAgain);
		layout.removeView(layout2);
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
		tryAgain = new Button(c);
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
				
				tryAgain.setLayoutParams(layoutCenterParams);
		
				tryAgain.setBackground(stld);
				tryAgain.setPadding(10, 10, 10, 10);
				tryAgain.setGravity(Gravity.CENTER);
				tryAgain.setHeight(150);
				tryAgain.setWidth(200);
		//startSpeech.setBackground(states);
		//startSpeech.setBackground(c.getResources().getDrawable(R.drawable.text_background));
				tryAgain.setText("Try  Again");
		layout2.addView(tryAgain);
		
		layout2.setLayoutParams(layoutCenterParent);
		layout2.setHorizontalGravity(Gravity.CENTER);
		layout2.setVerticalGravity(Gravity.CENTER);
		layout.addView(layout2);
		
		
		
		
		tryAgain.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Hangman/Sound/Button.mp3";
				//score sound
				HeadPhone HP = new HeadPhone(c);
				HP.setLeftLevel(1);
				HP.setRightLevel(1);
				if (HP.detectHeadPhones() == true)
					HP.play(Path, 0);
				I.putExtra("Action", "NONE");
				I.putExtra("State", "S2");
			}
		});
		

		
	}
}
