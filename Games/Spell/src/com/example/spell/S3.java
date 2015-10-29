package com.example.spell;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;

public class S3 implements IstateActions {
	static char letter ;
	static String word = "";
	static int letterPosition;

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C) {
		I.putExtra("Action", "Right");
		letter =I.getCharExtra("letter", '$');
		word = I.getStringExtra("word");
		letterPosition =I.getIntExtra("letterPosition", -1);
		

	}
	@Override
	public Intent loopBack(Context c, Intent I) {
		// TODO Auto-generated method stub
		letter = I.getCharExtra("letter", '$');
		word = I.getStringExtra("word");
		letterPosition =I.getIntExtra("letterPosition", -1);
		Toast.makeText(c, letter +"  "+word+"  "+letterPosition+"welcom", Toast.LENGTH_SHORT).show();
		char[] charArray = word.toCharArray();
		if(Character.toLowerCase(charArray[letterPosition]) == Character.toLowerCase(letter))
		
		{
			int score = I.getIntExtra("score", 0);
			++score;
			I.putExtra("score",score);
			System.out.print(score);
			if(letterPosition == (word.length()-1))
			{
				I.putExtra("Action", "NONE");
				
				I.putExtra("State", "S5");
			}else
			{
				
		        Toast.makeText(c, "great this letter correct, now spell remaining letter", Toast.LENGTH_SHORT).show();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.print("can not use thread");
				e.printStackTrace();
			}
			I.putExtra("Action", "NONE");
		    I.putExtra("State", "S2");
			}
			
		}else
		{
			I.putExtra("Action", "NONE");
			I.putExtra("State", "S4");
		}
		
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub

	}

	
}
