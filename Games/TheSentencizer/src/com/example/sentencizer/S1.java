package com.example.sentencizer;

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
	String[] words = new String[]{"how old are you","what is your name","study your lessons firstly",
			"do one is best","hard work to success","go to school early"
			};

	GridLayout letters ;
	static TextView speechWord;
	static List<String> sentenceWords;
	static LinearLayout layout;
	static String workingWord ;
	static String word ;
	static LinearLayout layout2;
	public SpeechRecognizer sr;
	static char[]arr;
	static String[] wordsArray;
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
			int random = I.getIntExtra("Random", 0);
			if(random == 0){
			 Random rand = new Random();
			  random= rand.nextInt(words.length);
			}
			random++;
			if(random == words.length)
			     random = random%words.length;
			
			 I.putExtra("Random", random);
			 
			 wordsArray = new String[4];
			 wordsArray = words[random].split(" ",4);
			sentenceWords = shuffle(wordsArray);
	
			
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
	
	

	OvalShape rect = new OvalShape();
			  
	
			ShapeDrawable bg = new ShapeDrawable(rect);
			bg.getPaint().setColor(0x99728C00);
			ShapeDrawable bg2 = new ShapeDrawable(rect);
			bg2.getPaint().setColor(Color.LTGRAY);
			ShapeDrawable bg3 = new ShapeDrawable(rect);
			bg.getPaint().setColor(0x99254117);
			ShapeDrawable bg4 = new ShapeDrawable(rect);
			bg2.getPaint().setColor(Color.LTGRAY);
			
			
			
			btn = new Button[8];
			 System.out.println(sentenceWords.get(0)+""+sentenceWords.get(1));
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
					btn[step].setText(sentenceWords.get(i));i++;	
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
        	LinearLayout errorToast = new LinearLayout(context);
			LinearLayout.LayoutParams layoutToastParams =
					new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.MATCH_PARENT);
			errorToast.setLayoutParams(layoutToastParams);
			errorToast.setBackgroundColor(Color.DKGRAY);
			ImageButton errorImage = new ImageButton(context);
			LinearLayout.LayoutParams toastchield =
					new LinearLayout.LayoutParams(
							LinearLayout.LayoutParams.WRAP_CONTENT,
							LinearLayout.LayoutParams.WRAP_CONTENT);
		//	toastchield.rightMargin = 8;
			Drawable er = Drawable.createFromPath(Environment
					.getExternalStorageDirectory().toString()
					+ "/xGame/Games/"
					+ "The Sentencizer" + "/Images/error.png");
			errorImage.setImageDrawable(er);
			errorImage.setContentDescription("Correct");
			errorImage.setLayoutParams(toastchield);
			errorToast.addView(errorImage);
           	Toast errorToasting = new Toast(context);
           	errorToasting.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
           	errorToasting.setDuration(Toast.LENGTH_SHORT);
           	errorToasting.setView(errorToast);
           	//========================================================
           	LinearLayout toastLayout = new LinearLayout(context);
				toastLayout.setLayoutParams(layoutToastParams);
				toastLayout.setBackgroundColor(Color.DKGRAY);
				ImageButton image = new ImageButton(context);
				Drawable d = Drawable.createFromPath(Environment
						.getExternalStorageDirectory().toString()
						+ "/xGame/Games/"
						+ "The Sentencizer" + "/Images/ok.png");
				image.setImageDrawable(d);
				image.setContentDescription("Error");
				image.setLayoutParams(toastchield);
		        toastLayout.addView(image);
              	Toast toast = new Toast(context);
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.setDuration(Toast.LENGTH_SHORT);
				toast.setView(toastLayout);
				for(int x=0;x<1;x++){
					
				
        	if(!(btn[index].getText().toString().equals("")))
        	{
        	
        		if(btn[1].getText().toString().equals(""))
        		{
        			if(btn[index].getText().toString().equals(wordsArray[0]))
        			{
        				 toast.show();
        				 btn[1].setText(btn[index].getText().toString());
        				 int score = I.getIntExtra("Score", 0);
        				 I.putExtra("Score", ++score);
        				 break;
        			}
        			
        			else{
        				errorToasting.show();
        				int fail = I.getIntExtra("fail", 0);
        				fail++;
        				I.putExtra("fail", fail);
        				if(fail == 2){
        					I.putExtra("Action", "NONE");
        					I.putExtra("State", "S2");
        				}
        				break;
        			}
        		}
        		if(btn[3].getText().toString().equals(""))
        		{
        			if(btn[index].getText().toString().equals(wordsArray[1]))
        				 {
        				toast.show();
       				    btn[3].setText(btn[index].getText().toString());
       				 int score = I.getIntExtra("Score", 0);
    				 I.putExtra("Score", ++score);
       				     break;
        				 }
        			else{
        				errorToasting.show();
        				int fail = I.getIntExtra("fail", 0);
        				fail++;
        				I.putExtra("fail", fail);
        				if(fail == 2){
        					I.putExtra("Action", "NONE");
    					I.putExtra("State", "S2");
        				}
        				break;
        			}
        		}
        		if(btn[5].getText().toString().equals(""))
        		{
        			if(btn[index].getText().toString().equals(wordsArray[2]))
        				 {
        				toast.show();
       				 btn[5].setText(btn[index].getText().toString());
       				 int score = I.getIntExtra("Score", 0);
    				 I.putExtra("Score", ++score);
       				 break;
        				 }
        			else{
        				errorToasting.show();
        				int fail = I.getIntExtra("fail", 0);
        				fail++;
        				I.putExtra("fail", fail);
        				if(fail == 2)
        					{
        					I.putExtra("Action", "NONE");
        					I.putExtra("State", "S2");
        					}
        				break;
        			}
        		}
        		if(btn[7].getText().toString().equals(""))
        		{
        			if(btn[index].getText().toString().equals(wordsArray[3]))
        			{
        				toast.show();
   				       btn[7].setText(btn[index].getText().toString());
   				       int score = I.getIntExtra("Score", 0);
   				       score = score+2;
   				       I.putExtra("Score", score);
   				    I.putExtra("Action", "NONE");
					I.putExtra("State", "S3");
   				         break;
        			}
        			else{
        				errorToasting.show();
        				int fail = I.getIntExtra("fail", 0);
        				fail++;
        				I.putExtra("fail", fail);
        				if(fail == 2)
        					{
        					I.putExtra("Action", "NONE");
        					I.putExtra("State", "S2");
        					}
        				break;
        			}
        		}
        		
        		
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
public static List<String> shuffle(String[] input){
    List<String> strings = new ArrayList<String>();
    List<String> output = new ArrayList<String>();
    for(int i =0;i<input.length;i++)
    {
    	output.add(input[i]);
    }
    
    
    while(output.size()!=0){
        int randPicker = (int)(Math.random()*output.size());
        strings.add(output.remove(randPicker));
        
    }
    return strings;
}

} 



