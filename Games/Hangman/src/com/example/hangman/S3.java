package com.example.hangman;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S3 implements IstateActions {
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
			for(int i=0;i<oldString.length;i++)
			{
				if(letter == oldString[i])
				{
					workingString[i] = letter;
				}
			}
			workingWord = new String(workingString);
			I.putExtra("workingWord", workingWord.toString());
			
			if(word.equals(workingWord))
			{
				int Score = I.getIntExtra("Score", 0);
				Score = 20;
				I.putExtra("Score", Score);
				int count = I.getIntExtra("Count", 0);
				count = 20;
				I.putExtra("Count", count);
			//	I.putExtra("Action", "NONE");
			//	I.putExtra("State", "S5");
			}else
			{
			
			Toast.makeText(C, "great this letter correct, now spell remaining letter", Toast.LENGTH_SHORT).show();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
