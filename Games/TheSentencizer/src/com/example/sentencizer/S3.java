package com.example.sentencizer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

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

		String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/The Sentencizer/Sound/correct.mp3";
		//score sound
		HeadPhone HP = new HeadPhone(C);
		HP.setLeftLevel(1);
		HP.setRightLevel(1);
		if (HP.detectHeadPhones() == true)
			HP.play(Path, 0);
		int level = I.getIntExtra("Level", 0);
		I.putExtra("Level", ++level);
		if(level == 4)
		{
			int failnum = I.getIntExtra("failnum", 0);
			int Score = I.getIntExtra("Score", 0);
			Score = (Score*5)-(failnum*4);
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