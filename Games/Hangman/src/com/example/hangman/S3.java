package com.example.hangman;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;

public class S3 implements IstateActions {
	static char letter ;
	static String word = "";
	static String workingWord;

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C) {
		I.putExtra("Action", "Right");
		letter =I.getCharExtra("letter", '$');
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
			workingWord = workingString.toString();
			I.putExtra("workingWord", workingWord);
			 Toast.makeText(C, "great this letter correct, now spell remaining letter", Toast.LENGTH_SHORT).show();
			if(word.equals(workingString))
			{
				I.putExtra("Action", "NONE");
				I.putExtra("State", "S5");
			}else
			{
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
	public Intent loopBack(Context c, Intent I) {
		// TODO Auto-generated method stub
		
		
		
		
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub

	}

	
}
