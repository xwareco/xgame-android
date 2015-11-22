package com.example.spellme;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.hardware.camera2.params.TonemapCurve;
import android.os.Bundle;
import android.os.Environment;
import android.provider.UserDictionary.Words;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;
import uencom.xgame.sound.TTS;
import uencom.xgame.speech.SpeechRecognition;

public class S2  implements IstateActions {
	

	GridLayout letters ;
	static TextView speechWord;
	static char[] myWord;
	static LinearLayout layout;
	static String workingWord ;
	static String word ;
	static LinearLayout layout2;
	public SpeechRecognizer sr;
	static char[]arr;
	StringBuilder str  = new StringBuilder();
	Button[] btn ;
	 Button[] btn2;
	 static boolean flag;
	 static int successNum = 1;
	 public Context context;
	 private float scale = 0;
	@Override
	public void onStateEntry(LinearLayout layout,  Intent I,  Context c,HeadPhone H) {
		// TODO Auto-generated method stub
		 I.putExtra("Action", "Right");
		    BitmapDrawable b = (BitmapDrawable) layout.getBackground();
			b.setAlpha(155);
			layout.setBackground(b);
			this.layout = layout;
			word = I.getStringExtra("word");
			myWord = (I.getStringExtra("workingWord")).toCharArray();
			workingWord = I.getStringExtra("workingWord");
			RoundRectShape ov = new RoundRectShape(
					  new float[] {30,30, 30,30, 30,30, 30,30},
					  null,
					  null);
			context = c;
			ShapeDrawable bgedit = new ShapeDrawable(ov);
			bgedit.getPaint().setColor(0x99FF9900);
			bgedit.getPaint().setStrokeWidth(12);
			bgedit.setPadding(5, 5, 5, 5);
			
			
	LinearLayout.LayoutParams layoutEditParams =new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT);
	layoutEditParams.gravity = Gravity.CENTER_HORIZONTAL;
	layoutEditParams.topMargin = 13;

	speechWord =new  TextView(c);
	speechWord.setBackground(bgedit);
	//speechWord.setWidth(150);
	//speechWord.setHeight(90);
	speechWord.setPadding(7, 10, 7, 7);
	speechWord.setTextSize(38);
	speechWord.setText(I.getStringExtra("workingWord"));
	speechWord.setTextColor(Color.BLUE);
	speechWord.setLayoutParams(layoutEditParams);
	layout.addView(speechWord);
			
			createUI(this.layout,I,c);
			
			
	}
	@Override
	public Intent loopBack(final Context c, final Intent I,HeadPhone H) {
		
		// TODO Auto-generated method stub
		
		
			return I;
		
	}
	
	
	@Override
	public void onStateExit(Context c, Intent I,HeadPhone H) {
		// TODO Auto-generated method stub
		
		layout.removeView(layout2);
		
		layout.removeView(speechWord);
		

	}
public void createUI(LinearLayout layout, final Intent I, final Context c) {
	layout2 = new LinearLayout(c);
	LinearLayout.LayoutParams layoutCenterParent =
			new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
	
	letters = new GridLayout(c);
	GridLayout.LayoutParams lettersParams = new GridLayout.LayoutParams();
	
	//lettersParams.columnSpec = GridLayout.spec(0); lettersParams.rowSpec = GridLayout.spec(0);
	//
	
	
	
	letters.setColumnCount(5);

	letters.setOrientation(GridLayout.HORIZONTAL);
	letters.setLayoutParams(lettersParams);
	
	
	//==================================================================
	
	

	OvalShape ov = new OvalShape();
			 
	
			ShapeDrawable bg = new ShapeDrawable(ov);
			bg.getPaint().setColor(0x99463E3F);
		
			ShapeDrawable bg2 = new ShapeDrawable(ov);
			bg2.getPaint().setColor(Color.LTGRAY);
			StateListDrawable second = new StateListDrawable();
			second.addState(new int[] { android.R.attr.state_pressed }, bg2);
			second.addState(new int[] { android.R.attr.state_enabled }, bg);
			StateListDrawable third = new StateListDrawable();
			third.addState(new int[] { android.R.attr.state_pressed }, bg2);
			third.addState(new int[] { android.R.attr.state_enabled }, bg);
			StateListDrawable forth = new StateListDrawable();
			forth.addState(new int[] { android.R.attr.state_pressed }, bg2);
			forth.addState(new int[] { android.R.attr.state_enabled }, bg);
			String alphabet = "abcdefghijklmnopqrstuvwxyz";
			 btn = new Button[26];
			 System.out.println(btn.length);
			 
			for ( int step = 0; step < 25; step++) {
				
				btn[step] = new Button(c);
				
				StateListDrawable first = new StateListDrawable();
				first.addState(new int[] { android.R.attr.state_pressed }, bg2);
				first.addState(new int[] { android.R.attr.state_enabled }, bg);
				btn[step].setBackground(first);
				
				Resources r = context.getResources();
				float w = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());
				float h = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());
				btn[step].setWidth((int)w);
				btn[step].setHeight((int)h);
				btn[step].setText(alphabet.charAt(step)+"");//workingString.charAt(step));
				letters.addView(btn[step]);
				btn[step].setOnClickListener(handleOnClick(btn[step], step,I));
			}
			btn[25] = new Button(c);
			
			StateListDrawable first = new StateListDrawable();
			first.addState(new int[] { android.R.attr.state_pressed }, bg2);
			first.addState(new int[] { android.R.attr.state_enabled }, bg);
			btn[25].setBackground(first);
			btn[25].setText(alphabet.charAt(25)+"");
			
			Resources r = context.getResources();
			float w = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());
			float h = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());
			btn[25].setWidth((int)w);
			btn[25].setHeight((int)h);
			Spec row4 = GridLayout.spec(6, 3);
			Spec colspan2 = GridLayout.spec(2, 1);
			letters.addView(btn[25],new GridLayout.LayoutParams(row4, colspan2));
			btn[25].setOnClickListener(handleOnClick(btn[25], 25,I));
			layout2.addView(letters);
			layout2.setLayoutParams(layoutCenterParent);
			layout2.setHorizontalGravity(Gravity.CENTER);
			layout2.setVerticalGravity(Gravity.BOTTOM);
			layout2.setHorizontalGravity(Gravity.CENTER);
			layout.addView(layout2);
}

View.OnClickListener handleOnClick(final Button button,final int index,final Intent I) {
    return new View.OnClickListener() {
        public void onClick(View v) {
        	
        	for(int m =0;m<workingWord.length();m++)
        	{
        		if(((workingWord.charAt(m)+"").equals("$")))
        		{
        			if((btn[index].getText().charAt(0)+"").equals((word).charAt(m)+""))
        			{
        			myWord[m] =btn[index].getText().charAt(0);
        			workingWord = new String(myWord);
        			speechWord.setText(workingWord);
        			I.putExtra("workingWord", workingWord);
        			LinearLayout toastLayout = new LinearLayout(context);
        				LinearLayout.LayoutParams layoutToastParams =
        						new LinearLayout.LayoutParams(
        								LinearLayout.LayoutParams.MATCH_PARENT,
        								LinearLayout.LayoutParams.MATCH_PARENT);
        				toastLayout.setLayoutParams(layoutToastParams);
        				toastLayout.setBackgroundColor(Color.DKGRAY);
        				ImageButton image = new ImageButton(context);
        				LinearLayout.LayoutParams toastchield =
        						new LinearLayout.LayoutParams(
        								LinearLayout.LayoutParams.WRAP_CONTENT,
        								LinearLayout.LayoutParams.WRAP_CONTENT);
        			//	toastchield.rightMargin = 8;
        				Drawable d = Drawable.createFromPath(Environment
        						.getExternalStorageDirectory().toString()
        						+ "/xGame/Games/"
        						+ "Spell Me" + "/Images/ok.png");
        				image.setImageDrawable(d);
        				image.setContentDescription("Correct");
        				image.setLayoutParams(toastchield);
        		        toastLayout.addView(image);
                       	Toast toast = new Toast(context);
        				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        				toast.setDuration(Toast.LENGTH_SHORT);
        				toast.setView(toastLayout);
        				toast.show();
        				int score = I.getIntExtra("Score", 1);
        				score++;
        				I.putExtra("Score", score);
        				break;
        			}else
        			{
    					I.putExtra("Action", "NONE");
    					I.putExtra("State", "S4");
    					break;
    				}
        		}
        	}
        			
        	if(!(workingWord.contains("$")))
        	{
        	if(workingWord.equals(word))
    		{
    			int failnum = I.getIntExtra("failnum", 0);
    			int score = I.getIntExtra("Score", 1);
    			score = ((score/word.length())*100) - failnum*5;
    			I.putExtra("Score",score );
    			I.putExtra("Count", 20);
    		}else
    		{
    			I.putExtra("Action", "NONE");
    			I.putExtra("State", "S2");
    		}
        	}
        		
        	
        }
    };
} 

public static int getScreenWidth() {
    return Resources.getSystem().getDisplayMetrics().widthPixels;
}

public static int getScreenHeight() {
    return Resources.getSystem().getDisplayMetrics().heightPixels;
}
}
