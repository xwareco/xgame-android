package com.example.spell;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.LinearLayout;
import android.widget.Toast;
import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

public class S3 implements IstateActions {
	static char letter ;
	static String word = "";
	static int letterPosition;
	static char[] temp;

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C,HeadPhone H) {
		I.putExtra("Action", "Right");
		letter =I.getCharExtra("letter", '$');
		word = I.getStringExtra("word");
		letterPosition =I.getIntExtra("letterPosition", 0);
		char[] charArray = word.toCharArray();
		if(Character.toLowerCase(charArray[letterPosition]) == Character.toLowerCase(letter))
		
		{
			int score = I.getIntExtra("Score", 0);
			++score;
			I.putExtra("Score",score);
			System.out.print(score);
			if(letterPosition == (word.length()-1))
			{

				int fail = I.getIntExtra("failnum", 0);
				int Score = I.getIntExtra("Score", 0);
				Score = 75-(fail*2);
				int timeInSecond = I.getIntExtra("timeInSecond", 0);
				if(timeInSecond <= 50)
					Score +=25;
				else if(timeInSecond <= 80&& timeInSecond>50)
					Score +=20;
				else if(timeInSecond <= 100&&timeInSecond>80)
					Score +=15;
				else if(timeInSecond <=150&&timeInSecond>100)
					Score +=10;
				else if(timeInSecond<=180&&timeInSecond>150)
					Score += 5;
				else
					Score -=5;
				
				I.putExtra("Score", Score);
				I.putExtra("Count", 20);
			}else
			{
				Toast.makeText(C, "your time till now: "+ I.getIntExtra("timeInSecond", 1)+" second go on you can do it", Toast.LENGTH_SHORT).show();
				String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Spell/Sound/correct.mp3";
				//score sound
				HeadPhone HP = new HeadPhone(C);
				HP.setLeftLevel(1);
				HP.setRightLevel(1);
				if (HP.detectHeadPhones() == true)
					HP.play(Path, 0);
				temp = I.getStringExtra("workingWord").toCharArray();
				 temp[letterPosition] = letter;
				 I.putExtra("workingWord", new String(temp));
				I.putExtra("letterPosition", ++letterPosition);
				I.putExtra("Action", "NONE");
				I.putExtra("State", "S2");
		        //Toast.makeText(C, "great this letter correct, now spell remaining letter", Toast.LENGTH_SHORT).show();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.print("can not use thread");
				e.printStackTrace();
			}
			}
			
		}else
		{
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
