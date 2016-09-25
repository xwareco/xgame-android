package com.example.teacher;



import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class S4  implements IstateActions{
	
	public static long startTime;
	static Timer timer;
	OvalShape timeoOv;
	static ScrollView scroll;
	ShapeDrawable timeBg;
	public static int time = 0;
	static TextView showTime;
	public Context context;
	 static HeadPhone Hc;
	static int stringIndex;
	 static String sentence;
	static String[]  choiceWord = new String[5];
	static String[] choiceSentence =new String[5];
	 static String word;
	static LinearLayout layout;
	static LinearLayout layout3;
	 HeadPhone pre;
	StringBuilder str  = new StringBuilder();
	static TextView[] btn2 = new TextView[3];
	static TextView btn ;
	
	 static boolean flag;
	 static int successNum = 1;

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		context = C;
		Hc = H;
		this.layout = layout;
		choiceWord = I.getStringArrayExtra("choiceWord");
		choiceSentence = I.getStringArrayExtra("choiceSentence");
		System.out.println(Arrays.toString(choiceWord)+"");
		System.out.println(Arrays.toString(choiceSentence)+"");
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
		showTime = new TextView(C);
		showTime.setTextSize(25);
		showTime.setTextColor(Color.WHITE);
		showTime.setBackground(timeBg);

		showTime.setLayoutParams(layoutEditParams);
		layout.addView(showTime);
		pre = null;
		timer = new Timer();
		timer.schedule(new RemindTask(),
		           0,        //initial delay
		           1*1000);
				
		 I.putExtra("Action", "Right");
	     BitmapDrawable b = (BitmapDrawable) layout.getBackground();
		 b.setAlpha(189);
		 layout.setBackground(b);
		 stringIndex = I.getIntExtra("choiceRandom", 0);
			if(stringIndex == 0){
			 Random rand = new Random();
			 stringIndex= rand.nextInt(choiceSentence.length);
			}
			System.out.println(choiceSentence.length+"");
			stringIndex++;
			if(stringIndex == choiceSentence.length)
				stringIndex = stringIndex%choiceSentence.length;
			
			 I.putExtra("choiceRandom", stringIndex);
			 System.out.println(stringIndex+"");
			  word = choiceWord[stringIndex];
			 sentence = choiceSentence[stringIndex];
			
			System.out.println(word+"");
			System.out.println(sentence+"");
			 LinearLayout.LayoutParams layoutEditParams1 =new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				layoutEditParams1.gravity = Gravity.CENTER_HORIZONTAL;
				RoundRectShape rect1 = new RoundRectShape(
						  new float[] {30,30, 30,30, 30,30, 30,30},
						  null,
						  null);
				ShapeDrawable sentenceBg= new ShapeDrawable(rect1);
				
				
				
				sentenceBg.getPaint().setColor(0x990099FF);
				layoutEditParams1.setMargins(5, 100, 5, 100);
				btn = new TextView(C);
				btn.setTextSize(20);
				btn.setGravity(Gravity.CENTER);
				btn.setHeight(120);
				btn.setTextColor(Color.WHITE);
				btn.setBackground(sentenceBg);
				btn.setText(sentence.replace(word, "....."));
				btn.setLayoutParams(layoutEditParams1);
				layout.addView(btn);
		       createUI(layout,I,C);
		
				
	}

	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub
		I.putExtra("timeInSecond",time);
		layout.removeView(showTime);
		layout.removeView(btn);
		layout.removeView(scroll);
		layout.removeView(layout3);
		Hc.stopCurrentPlay();
		
		 timer.cancel();

	}
	public void createUI(LinearLayout layout, final Intent I, final Context c) {
		
		
		
		///////////////
		
		 layout3 = new LinearLayout(c);
		 scroll = new ScrollView(context);
			
			scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
			                                             LayoutParams.MATCH_PARENT));
		
		layout3.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams layoutCenterParent =
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT);
		layoutCenterParent.topMargin = 70;
	
		
		LinearLayout.LayoutParams layoutCenterParams =
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER);
		layoutCenterParams.setMargins(15, 5, 15, 5);

	
		RoundRectShape rect = new RoundRectShape(
				  new float[] {30,30, 30,30, 30,30, 30,30},
				  null,
				  null);
		
				ShapeDrawable bg = new ShapeDrawable(rect);
				bg.getPaint().setColor(0x990099FF);
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
				
				
				 
				for (int i = 0; i < 3; i++) {
					btn2[i] = new TextView(c);
					btn2[i].setLayoutParams(layoutCenterParams);
					btn2[i].setGravity(Gravity.CENTER);
					btn2[i].setTextColor(Color.WHITE);
				    btn2[i].setHeight(100);
				    btn2[i].setTextSize(30);
				    
				    
					StateListDrawable first = new StateListDrawable();
					first.addState(new int[] { android.R.attr.state_pressed }, bg2);
					first.addState(new int[] { android.R.attr.state_enabled }, bg);
					
					layout3.addView(btn2[i]);
					
					if(i==3)
						btn2[i].setBackground(third);
					else
						btn2[i].setBackground(first);
					btn2[i].setOnClickListener(handleOnClick(btn2[i], i,I));
				}
				int r = new Random().nextInt(3);
				btn2[r].setText(word);
				btn2[(++r)%3].setText(choiceWord[(stringIndex+1)%5]);
				btn2[(++r)%3].setText(choiceWord[(stringIndex+2)%5]);
					
					
				

				
		
		
		layout3.setLayoutParams(layoutCenterParent);
		layout3.setHorizontalGravity(Gravity.CENTER);
		//layout3.setVerticalGravity(Gravity.CENTER);
		scroll.addView(layout3);
		layout.addView(scroll);
	}

	View.OnClickListener handleOnClick(final TextView button,final int index,final Intent I) {
	    return new View.OnClickListener() {
	        public void onClick(View v) {
	        	String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Teacher/Sound/Button2.mp3";
	    		//score sound
	    		HeadPhone HP = new HeadPhone(context);
	    		HP.setLeftLevel(1);
	    		HP.setRightLevel(1);
	    		if (HP.detectHeadPhones() == true)
	    			HP.play(Path, 0);
	        	if(btn2[index].getText().toString().equals(choiceWord[stringIndex]))
	        				
	        	{	
	        				
	        				
	        				I.putExtra("Action", "NONE");
	        				I.putExtra("State", "S5");
	        			}
	        	else{
		        				I.putExtra("Action", "NONE");
		        				I.putExtra("State", "S6");
		        			}
	        		
	        		
	        	
	        }
	    };
	  
	}
	

class RemindTask extends TimerTask {
	

    public void run() {
    	if(pre !=null)
    	{
    		pre.release();
    	}
    	Hc = new HeadPhone(context);
    	String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Teacher/Sound/timer.mp3";
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
  				showTime.setText(String.format("%02d", time/60)+":"+String.format("%02d", time%60)+"(+15 points)");
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