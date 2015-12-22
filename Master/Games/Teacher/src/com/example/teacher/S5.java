package com.example.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S5 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		I.putExtra("Action", "Right");
		int level = I.getIntExtra("choicelevel", 0);
		level +=1;
		I.putExtra("choicelevel", level);
		I.putExtra("fail", 0);
			int score = I.getIntExtra("Score", 0);
			score += 25;
			I.putExtra("Score",score);
			
			System.out.print(score);
			if(level == 3)
			{
				int timeInSecond = I.getIntExtra("timeInSecond", 0);
				int Score = I.getIntExtra("Score", 0);
				if(timeInSecond <= 50)
					Score +=25;
				else if(timeInSecond <= 100&& timeInSecond>50)
					Score +=20;
				else if(timeInSecond <= 150&&timeInSecond>100)
					Score +=15;
				else if(timeInSecond <=200&&timeInSecond>150)
					Score +=10;
				else if(timeInSecond<=300&&timeInSecond>200)
					Score += 5;
				else
					Score -=5;
		
				
				I.putExtra("Score", Score);
				I.putExtra("Count", 20);
			}else
			{
				Toast.makeText(C, "your time till now: "+ I.getIntExtra("timeInSecond", 1)+" second go on you can do it", Toast.LENGTH_SHORT).show();
				String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Teacher/Sound/correct.mp3";
				//score sound
				HeadPhone HP = new HeadPhone(C);
				HP.setLeftLevel(1);
				HP.setRightLevel(1);
				if (HP.detectHeadPhones() == true)
					HP.play(Path, 0);
				
				I.putExtra("Action", "NONE");
				I.putExtra("State", "S4");
		       
			}
			
			
		
		
		
	}

	
	@Override
	public Intent loopBack(Context c, Intent I,HeadPhone H) {
		// TODO Auto-generated method stub
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I,HeadPhone H) {
		// TODO Auto-generated method stub

	}


}
