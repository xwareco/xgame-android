package com.example.spellme;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S3 implements IstateActions {
	int level ;

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C,HeadPhone H) {
		I.putExtra("Action", "Right");
		level = I.getIntExtra("level", 1);
		level++;
		I.putExtra("level", level);
	
			int score = I.getIntExtra("Score", 0);
			score += 20;
			I.putExtra("Score",score);
			System.out.print(score);
			if(level == 6)
			{
				I.putExtra("Count", 20);
			}else
			{
				
				String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Spell Me/Sound/correct.mp3";
				//score sound
				HeadPhone HP = new HeadPhone(C);
				HP.setLeftLevel(1);
				HP.setRightLevel(1);
				if (HP.detectHeadPhones() == true)
					HP.play(Path, 0);
				
				I.putExtra("Action", "NONE");
				I.putExtra("State", "S1");
		        //Toast.makeText(C, "great this letter correct, now spell remaining letter", Toast.LENGTH_SHORT).show();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.print("can not use thread");
				e.printStackTrace();
			}
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
