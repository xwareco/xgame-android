package com.example.spell;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

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
				int failnum = I.getIntExtra("failnum", 0);
				I.putExtra("Score",(( score/word.length())*100)-(failnum*5));
				I.putExtra("Count", 20);
			}else
			{
				
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
