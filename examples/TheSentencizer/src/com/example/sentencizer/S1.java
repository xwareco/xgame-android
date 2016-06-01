package com.example.sentencizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Environment;
import android.speech.SpeechRecognizer;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S1  implements IstateActions {
	String[] words = new String[]{"how old are you","what is your name","study your lessons firstly",
			"hard work to success","go to school early","Tidy up your room","Did you lock the door"
			,"When’s your homework due","You beat me again!","Time to get up!","Where are you hurt?"
			,"Be nice to your mom","you should learn English "
			};
	public static long startTime;
	static Timer timer;
	GridLayout letters ;
	OvalShape timeoOv;
	ShapeDrawable timeBg;
	public static int time = 0;
	static TextView showTime;
	static ScrollView scroll;
	static List<String> sentenceWords;
	static LinearLayout layout;
	static String workingWord ;
	static String word ;
	static LinearLayout layout2;
	public SpeechRecognizer sr;
	static char[]arr;
	static String[] wordsArray;
	StringBuilder str  = new StringBuilder();
	TextView[] btn ;
	HeadPhone pre = null;
	 static boolean flag;
	 static int successNum = 1;
	 public Context context;
	 static HeadPhone Hc;
	@Override
	public void onStateEntry(LinearLayout layout,  Intent I,  Context c,HeadPhone H) {
		// TODO Auto-generated method stub
		context = c;
		Hc = H;
		LinearLayout.LayoutParams layoutEditParams =new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutEditParams.gravity = Gravity.CENTER_HORIZONTAL;
		RoundRectShape rect = new RoundRectShape(
				  new float[] {30,30, 30,30, 30,30, 30,30},
				  null,
				  null);
		 timeBg= new ShapeDrawable(rect);
		 
		 timeBg.getPaint().setColor(0x99463E3F);
		layoutEditParams.topMargin = 4;
		showTime = new TextView(c);
		showTime.setTextSize(25);
		showTime.setTextColor(Color.WHITE);
		showTime.setBackground(timeBg);

		showTime.setLayoutParams(layoutEditParams);
		layout.addView(showTime);
		timer = new Timer();
		timer.schedule(new RemindTask(),
		           0,        //initial delay
		           1*1000);
				
		 I.putExtra("Action", "Right");
		    BitmapDrawable b = (BitmapDrawable) layout.getBackground();
			b.setAlpha(155);
			layout.setBackground(b);
			this.layout = layout;
			
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
		I.putExtra("timeInSecond",time);
		layout.removeView(layout2);
		layout.removeView(showTime);
		layout.removeView(scroll);
		Hc.stopCurrentPlay();
		 timer.cancel();

	}
public void createUI(LinearLayout layout, final Intent I, final Context c) {
	
	layout2 = new LinearLayout(c);
scroll = new ScrollView(context);
	
	scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
	                                             LayoutParams.MATCH_PARENT));
	LinearLayout.LayoutParams layoutCenterParent =
			new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
	layoutCenterParent.topMargin = 10;
	layoutCenterParent.leftMargin = 5;
	letters = new GridLayout(c);
	letters.setHorizontalScrollBarEnabled(true);
	GridLayout.LayoutParams lettersParams = new GridLayout.LayoutParams();
	letters.setUseDefaultMargins(true);
	lettersParams.setMargins(0, 3, 3, 0);
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
			
			
			
			btn = new TextView[8];
			 System.out.println(sentenceWords.get(0)+""+sentenceWords.get(1));
			for ( int step = 0,i=0; step < 8; step++) {
				
				btn[step] = new TextView(c);
				
				StateListDrawable first = new StateListDrawable();
				first.addState(new int[] { android.R.attr.state_pressed }, bg2);
				first.addState(new int[] { android.R.attr.state_enabled }, bg);
				btn[step].setBackground(first);
				btn[step].setWidth((getScreenWidth()/2)-15);
				btn[step].setHeight((getScreenWidth()/4)-10);
				btn[step].setTextColor(Color.WHITE);
				btn[step].setGravity(Gravity.CENTER);
				btn[step].setTextSize(22);
			    if(step%2==0){
					btn[step].setText(sentenceWords.get(i));i++;	
					btn[step].setOnClickListener(handleOnClick(btn[step], step,I));
			    }
			    	
				letters.addView(btn[step]);
				
			}
			
			
			
			
			
			layout2.addView(letters);
			layout2.setLayoutParams(layoutCenterParent);
			
			
			scroll.addView(layout2);
			scroll.setLayoutParams(layoutCenterParent);
			layout.addView(scroll);

}
View.OnClickListener handleOnClick(final TextView button,final int index,final Intent I) {
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
			errorImage.setContentDescription("error");
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
				image.setContentDescription("Correct");
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
        				 btn[index].setText("");
        				
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
       				 btn[index].setText("");
       				 
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
       				btn[index].setText("");
       				 
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
   				    btn[index].setText("");
   				      
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

class RemindTask extends TimerTask {
	

    public void run() {
    	if(pre !=null)
    	{
    		pre.release();
    	}
    	Hc = new HeadPhone(context);
    	String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/The Sentencizer/Sound/timer.mp3";
		Hc.setLeftLevel(1);
   		Hc.setRightLevel(1);
		Hc.play(Path, 0);
      time++;
      ((Activity) context).runOnUiThread(new Runnable() {
          @Override
          public void run() {
        	  Drawable background = showTime.getBackground();
      		if (background instanceof ShapeDrawable) {
      			if(time<=100)
      			{
      				  ((ShapeDrawable)background).getPaint().setColor(Color.GRAY);
      				  showTime.setTextColor(Color.GREEN);
      			}
      			else if(time > 100&&time<=200)
      			{
      				  ((ShapeDrawable)background).getPaint().setColor(Color.GRAY);
      				showTime.setTextColor(Color.GREEN);
      			}
      			else if(time > 200&&time<=300 )
      			{
      				  ((ShapeDrawable)background).getPaint().setColor(Color.GRAY);
      				showTime.setTextColor(Color.YELLOW);
      			}
      			else if(time > 300 ){
      				  ((ShapeDrawable)background).getPaint().setColor(Color.RED);
      				  showTime.setTextColor(Color.WHITE);
      			}
      			
      			
      		
      		} else if (background instanceof GradientDrawable) {
      		    ((GradientDrawable)background).setColor(0x99FF0000);
      		}
        	  
        	  if(time<=50)
        	  {
        		  showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"(+20 points)");
  			}
  			else if(time > 50&&time<=100)
  			{
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"(+15 points)");
  			}
  			else if(time > 100&&time<=150 )
  			{
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"(+10 points)");
  			}
  			else if(time > 150&&time<=200 ){
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"(+10 points)");
  			}
  			else if(time > 200&&time<=300 ){
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"(+5 points)");
  			}
  			else if(time > 300){
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"(-10 points)");
  			}
             
          }
      });
    pre = Hc;
    }
   
	
}

} 



