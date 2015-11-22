package com.example.alphabetize;

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
import android.util.DisplayMetrics;
import android.util.StateSet;
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

public class S1  implements IstateActions {
	String[] words = new String[]{"freedom","elephant","education","school","house",
			"egypt","love","peace","greeting","security"
			,"world","image","work","justice","battle"};

	GridLayout letters ;
	static TextView speechWord;
	static String[] wordsArray;
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
	@Override
	public void onStateEntry(LinearLayout layout,  Intent I,  Context c,HeadPhone H) {
		// TODO Auto-generated method stub
		 I.putExtra("Action", "Right");
		    BitmapDrawable b = (BitmapDrawable) layout.getBackground();
			b.setAlpha(155);
			layout.setBackground(b);
			this.layout = layout;
			context = c;
			
			
			 Random rand = new Random();
			 int random = rand.nextInt(words.length);
			 wordsArray = new String[4];
			 for(int i = 0;i<wordsArray.length;i++)
			 {
				 wordsArray[i] = words[random];
				 random++;
				 if(random == words.length)
					 random = 0;
			 }
	
			
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
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
	layoutCenterParent.topMargin = 10;
	layoutCenterParent.leftMargin = 5;
	letters = new GridLayout(c);
	GridLayout.LayoutParams lettersParams = new GridLayout.LayoutParams();
	lettersParams.topMargin = 10;
	lettersParams.rightMargin=5;
	
	//lettersParams.columnSpec = GridLayout.spec(0); lettersParams.rowSpec = GridLayout.spec(0);
	//
	
	
	
	letters.setColumnCount(2);
	letters.setRowCount(4);

	letters.setOrientation(GridLayout.HORIZONTAL);
	letters.setLayoutParams(lettersParams);
	
	
	//==================================================================
	
	

	RoundRectShape rect = new RoundRectShape(
			  new float[] {30,30, 30,30, 30,30, 30,30},
			  null,
			  null);
	
			ShapeDrawable bg = new ShapeDrawable(rect);
			bg.getPaint().setColor(0x99210B61);
			ShapeDrawable bg2 = new ShapeDrawable(rect);
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
			btn = new Button[8];
			 System.out.println(wordsArray[0]+" "+wordsArray[1]);
			for ( int step = 0,i=0; step < 8; step++) {
				
				btn[step] = new Button(c);
				
				StateListDrawable first = new StateListDrawable();
				first.addState(new int[] { android.R.attr.state_pressed }, bg2);
				first.addState(new int[] { android.R.attr.state_enabled }, bg);
				btn[step].setBackground(first);
				btn[step].setWidth((getScreenWidth()/2)-10);
				btn[step].setHeight((getScreenWidth()/4)-10);
				
				btn[step].setTextSize(22);
			    if(step%2==0){
					btn[step].setText(wordsArray[i]+"");i++;
					btn[step].setOnClickListener(handleOnClick(btn[step], step,I));
			    }
				letters.addView(btn[step]);
				
			}
			
			
			
			
			
			layout2.addView(letters);
			layout2.setLayoutParams(layoutCenterParent);
			
			
			layout.addView(layout2);

}
View.OnClickListener handleOnClick(final Button button,final int index,final Intent I) {
    return new View.OnClickListener() {
        public void onClick(View v) {
        	boolean can = true;
        	if(!(btn[index].getText().toString().equals("")))
        	{
        	for(int m =0;m<8;m+=2)
        	{
        		System.out.println((btn[index].getText().toString().compareTo(btn[m].getText().toString())));
        		if(index !=m&& !(btn[m].getText().toString().equals(""))){
        		if((btn[index].getText().toString().compareTo(btn[m].getText().toString())>0))
        		   {System.out.println("entered");
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
   						+ "Alphabetize" + "/Images/error.png");
   				image.setImageDrawable(d);
   				image.setContentDescription("Error");
   				image.setLayoutParams(toastchield);
   		        toastLayout.addView(image);
                  	Toast toast = new Toast(context);
   				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
   				toast.setDuration(Toast.LENGTH_SHORT);
   				toast.setView(toastLayout);
   				toast.show();
   				int score = I.getIntExtra("Score", 0);
   				score -=5;
   				I.putExtra("Score", score);
   				int failnum = I.getIntExtra("failnum", 0);
   				failnum++;
   				I.putExtra("failnum", failnum);
   				if(failnum == 4)
   				{
   					I.putExtra("Count", 20);
   				}
        			can = false;
        			break;
        		   }
        		}
        	}
        	if(can)
        	{
        		for(int i =1;i<8;i+=2)
        		{
        			if(btn[i].getText().toString().equals(""))
        			{
        				btn[i].setText(btn[index].getText().toString());
        				btn[index].setText("");
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
        						+ "Alphabetize" + "/Images/ok.png");
        				image.setImageDrawable(d);
        				image.setContentDescription("Correct");
        				image.setLayoutParams(toastchield);
        		        toastLayout.addView(image);
                       	Toast toast = new Toast(context);
        				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        				toast.setDuration(Toast.LENGTH_SHORT);
        				toast.setView(toastLayout);
        				toast.show();
        				int score = I.getIntExtra("Score", 0);
        				score +=25;
        				I.putExtra("Score", score);
        				if(btn[0].getText().toString().equals("")&&btn[2].getText().toString().equals("")&&btn[4].getText().toString().equals("")&&btn[6].getText().toString().equals(""))
        					I.putExtra("Count", 20);
        				break;
        			}
        				
        			
        		}
        	}
        	can = true;
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



