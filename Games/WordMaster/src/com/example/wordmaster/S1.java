package com.example.wordmaster;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class S1  implements IstateActions{
	String[] words = new String[]{"text","book","mouse","school","house",
			"egypt","love","peace","happy","dream"
			,"world","image","work","game","face"};
	//char [] letters = new char[]{};
	static int stringIndex;
	static String word;
	public Context context;
	static char letter;
	static LinearLayout layout;
	static String workingString;
	static LinearLayout layout2;
	static LinearLayout layout3;
	
	static char[]arr;
	StringBuilder str  = new StringBuilder();
	Button[] btn ;
	 Button[] btn2;
	 static boolean flag;
	 static int successNum = 1;

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		 I.putExtra("Action", "Right");
		 int level = I.getIntExtra("Level", 0);
			context = C;
			I.putExtra("Level", ++level);
	     BitmapDrawable b = (BitmapDrawable) layout.getBackground();
		 b.setAlpha(189);
		 layout.setBackground(b);
		 this.layout = layout;
		 createUI(this.layout,I,C);
		
				
	}

	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub
	
		layout.removeView(layout2);
		layout.removeView(layout3);
		
	}
	public void createUI(LinearLayout layout, final Intent I, final Context c) {
		
		flag = I.getBooleanExtra("Flag", true);
		if(flag)
		{
		Random random = new Random();
		stringIndex = random.nextInt(15);
		word = words[stringIndex];
		S1 obj = new S1();
		workingString = obj.shuffle(word);
		Log.d("MyString", workingString);
		I.putExtra("word",word );
		
		
		}else
		{
			
			letter = I.getCharExtra("letter", '-');
			
		}
		///////////////
		 layout2 = new LinearLayout(c);
		 layout3 = new LinearLayout(c);
		LinearLayout.LayoutParams layoutCenterParent =
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutCenterParent.topMargin = 120;
	
		
		LinearLayout.LayoutParams layoutCenterParams =
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER);
		layoutCenterParams.setMargins(5, 5, 5, 5);

	
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
				
				 btn = new Button[ workingString.length()];
				 btn2 = new Button[ workingString.length()];
				for (int i = 0; i < btn2.length; i++) {
					btn2[i] = new Button(c);
					btn2[i].setLayoutParams(layoutCenterParams);
					StateListDrawable first = new StateListDrawable();
					first.addState(new int[] { android.R.attr.state_pressed }, bg2);
					first.addState(new int[] { android.R.attr.state_enabled }, bg);
					if(i==3)
						btn2[i].setBackground(third);
					else
						btn2[i].setBackground(first);
					
					
					btn2[i].setText("-");
					layout3.addView(btn2[i]);
					
					
					
				}
				for ( int step = 0; step < btn.length; step++) {
					btn[step] = new Button(c);
					btn[step].setLayoutParams(layoutCenterParams);
					StateListDrawable first = new StateListDrawable();
					first.addState(new int[] { android.R.attr.state_pressed }, bg2);
					first.addState(new int[] { android.R.attr.state_enabled }, bg);
					if(step==3)
						btn[step].setBackground(second);
					else
						btn[step].setBackground(first);
					btn[step].setText(workingString.charAt(step)+"");//workingString.charAt(step));
					layout2.addView(btn[step]);
				   
					
					btn[step].setOnClickListener(handleOnClick(btn[step], step,I));
					
				}
				
		layout2.setLayoutParams(layoutCenterParent);
		layout2.setHorizontalGravity(Gravity.CENTER);
		//layout2.setVerticalGravity(Gravity.CENTER);
		layout.addView(layout2);
		
		layout3.setLayoutParams(layoutCenterParent);
		layout3.setHorizontalGravity(Gravity.CENTER);
		//layout3.setVerticalGravity(Gravity.CENTER);
		layout.addView(layout3);
	}

	View.OnClickListener handleOnClick(final Button button,final int index,final Intent I) {
	    return new View.OnClickListener() {
	        public void onClick(View v) {
	        	
	        	for(int m =0;m<workingString.length();m++)
	        	{
	        		if(btn2[m].getText().toString().equals("-"))
	        		{
	        			btn2[m].setText(btn[index].getText().toString());
	        			
	        			
	        			if(btn2[m].getText().toString().equals(word.substring(m, m+1))){
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
	        						+ "The Word Master" + "/Images/ok.png");
	        				image.setImageDrawable(d);
	        				image.setLayoutParams(toastchield);
	        				TextView toastText = new TextView(context);
	        				toastText.setText("OK");
	        				toastText.setTextSize(22);
	        				toastText.setPadding(2, 25, 2,0);
	        				toastText.setTextColor(Color.WHITE);
	        				toastText.setGravity(Gravity.CENTER);
	        				LinearLayout.LayoutParams TextParams =
	        						new LinearLayout.LayoutParams(
	        								LinearLayout.LayoutParams.WRAP_CONTENT,
	        								LinearLayout.LayoutParams.WRAP_CONTENT,Gravity.CENTER_VERTICAL);
	        				
	        				toastText.setLayoutParams(TextParams);
	        				toastLayout.addView(image);
	        				toastLayout.addView(toastText);
	        				Toast toast = new Toast(context);
	        				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
	        				toast.setDuration(Toast.LENGTH_SHORT);
	        				toast.setView(toastLayout);
	        				toast.show();
	        			}else
	        			{
	        				I.putExtra("Action", "NONE");
	        				I.putExtra("State", "S2");
	        			}
	        			str.append(btn2[m].getText().toString());
	        			
	        			if(m==(workingString.length()-1))
		        		{
		        			if(str.toString().equals(word))
		        			{
		        				I.putExtra("Action", "NONE");
		        				I.putExtra("State", "S3");
		        			}else
		        			{
		        				I.putExtra("Action", "NONE");
		        				I.putExtra("State", "S2");
		        			}
		        		}
	        			break;
	        		}
	        		
	        	}
	        }
	    };
	  
	}
	 public static String shuffle(String input){
	        List<Character> characters = new ArrayList<Character>();
	        for(char c:input.toCharArray()){
	            characters.add(c);
	        }
	        StringBuilder output = new StringBuilder(input.length());
	        while(characters.size()!=0){
	            int randPicker = (int)(Math.random()*characters.size());
	            output.append(characters.remove(randPicker));
	        }
	        return output.toString();
	    }
	
}