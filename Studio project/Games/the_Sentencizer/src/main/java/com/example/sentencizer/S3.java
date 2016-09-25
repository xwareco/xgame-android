package com.example.sentencizer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

public class S3 implements IstateActions {
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
		int score = I.getIntExtra("Score", 0);
        score += 20;
        I.putExtra("Score", score);
		String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/The Sentencizer/Sound/correct.mp3";
		//score sound
		HeadPhone HP = new HeadPhone(C);
		HP.setLeftLevel(1);
		HP.setRightLevel(1);
		I.putExtra("fail", 0);
		if (HP.detectHeadPhones() == true)
			HP.play(Path, 0);
		int timeInSecond = I.getIntExtra("timeInSecond", 0);
		Toast.makeText(C, "لقد استهلكت : "+ timeInSecond+" ثانية ", Toast.LENGTH_SHORT).show();
		int level = I.getIntExtra("Level", 0);
		I.putExtra("Level", ++level);
		if(level == 4)
		{
			int Score = I.getIntExtra("Score", 0);
			if(timeInSecond <= 50)
				Score +=20;
			else if(timeInSecond <= 100&& timeInSecond>50)
				Score +=15;
			else if(timeInSecond <= 150&&timeInSecond>100)
				Score +=10;
			else if(timeInSecond <=200&&timeInSecond>150)
				Score +=10;
			else if(timeInSecond<=300&&timeInSecond>200)
				Score += 5;
			else
				Score -=10;
			int failnum = I.getIntExtra("failnum", 0);
			Score = Score - ((failnum) *3);
			I.putExtra("Score", Score);
			I.putExtra("Count", 20);
		}else
		{
		I.putExtra("Action", "NONE");
	    I.putExtra("State", "S1");
		}

	}

	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub

	}

}
