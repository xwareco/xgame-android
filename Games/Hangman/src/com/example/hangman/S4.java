package com.example.hangman;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Notification.Action;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;

public class S4 extends Activity implements IstateActions {
	
    static int failnum = 0;
	

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C) {
		// TODO Auto-generated method stub
		I.putExtra("Action", "Right");
		failnum = I.getIntExtra("failnum", 0);
		failnum++;
		if(failnum == 5)
		{
			I.putExtra("Action", "NONE");
			I.putExtra("State", "S6");
		}
		I.putExtra("failnum", failnum);
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
