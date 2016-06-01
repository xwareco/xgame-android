package com.example.eatthecheese;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S2 implements IstateActions {
	static char letter ;
	static String word = "";
	static String workingWord;

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		I.putExtra("Action", "Right");
		 BitmapDrawable b = (BitmapDrawable) layout.getBackground();
			b.setAlpha(155);
			layout.setBackground(b);
		letter =I.getCharExtra("letter", '$');
		letter = Character.toLowerCase(letter);
		word = I.getStringExtra("word");
		workingWord = I.getStringExtra("workingWord");
		char[] oldString = word.toCharArray();
		char[] workingString = workingWord.toCharArray();
		if(word.contains(letter+""))
		{
			if(!(workingWord.contains(letter+"")))
			{
				
				 Toast.makeText(C, "your time till now: "+ I.getIntExtra("timeInSecond", 1)+" second go on you can do it", Toast.LENGTH_SHORT).show();
				int Score = I.getIntExtra("Score", 0);
				Score++;
				I.putExtra("Score", Score);
			for(int i=0;i<oldString.length;i++)
			{
				if(letter == oldString[i])
				{
					workingString[i] = letter;
				}
			}
			}
			workingWord = new String(workingString);
			I.putExtra("workingWord", workingWord.toString());
			
			if(word.equals(workingWord))
			{
				
				int fail = I.getIntExtra("failnum", 0);
				int Score = I.getIntExtra("Score", 0);
				Score = 75-(fail*2);
				int timeInSecond = I.getIntExtra("timeInSecond", 0);
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
			//	I.putExtra("Action", "NONE");
			//	I.putExtra("State", "S5");
			}else
			{try {
				Thread.sleep(3000);
				String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Eat The Cheese/Sound/correct.mp3";
				//score sound
				HeadPhone HP = new HeadPhone(C);
				HP.setLeftLevel(1);
				HP.setRightLevel(1);
				if (HP.detectHeadPhones() == true)
					HP.play(Path, 0);
				I.putExtra("Action", "NONE");
				I.putExtra("State", "S1");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			}
		}else
		{
			I.putExtra("Action", "NONE");
			I.putExtra("State", "S3");
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