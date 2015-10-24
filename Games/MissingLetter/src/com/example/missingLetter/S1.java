package com.example.missingletter;


import uencom.xgame.interfaces.IstateActions;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class S1 extends Activity implements IstateActions {
	String[] words = new String[]{"freedom","elephant","education","school","house"};
	char [] letters = new char[]{};
	static int wordnum = 0;
	static String word;
	int letterposition;
	StringBuilder tempString;
	int count;
	@Override
	public void onStateEntry(LinearLayout layout, Intent I) {
		// TODO Auto-generated method stub
		
		 // setup parent LinearLayout
		LayoutParams parentLayoutParams =
				new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(parentLayoutParams);
		
		//first text view to show word
		word = words[wordnum];
		I.putExtra("word",word );
		letterposition = (int) Math.floor(Math.random()*word.length());
		I.putExtra("letterPosition", letterposition);
		tempString = new StringBuilder(word);
		tempString.setCharAt(letterposition, '$');
		TextView tv = new TextView(this);
		
		LinearLayout.LayoutParams layoutCenterParams =
				new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT );
		layoutCenterParams.setMargins(5, 5, 5, 5);
		layoutCenterParams.gravity = Gravity.CENTER;
		tv.setLayoutParams(layoutCenterParams);
		tv.setText(tempString.toString());
		tv.setBackgroundColor(Color.BLUE);
		layout.addView(tv);
		
		//setup Button to show letter
		LinearLayout.LayoutParams layoutButtonParams =
				new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT );
		layoutCenterParams.setMargins(2, 2, 2, 2);
		
	    count  = I.getIntExtra("Count", 0);
		count++;
		I.putExtra("Count", count);
	}

	@Override
	public Intent loopBack(Context c, Intent I) {
		// TODO Auto-generated method stub
		
		
	
			return I;
		
	}
	
	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub
		int Score = I.getIntExtra("Score", 0);
		Score++;
		I.putExtra("Score", Score);

	}

}
