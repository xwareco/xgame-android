package com.example.eatthecheese;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Environment;
import android.util.StateSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;

public class S3 implements IstateActions {

	 static int failnum = 0;
	    static LinearLayout layout;
	    static Button tryAgain;
	    static LinearLayout layout2;
	   

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		I.putExtra("Action", "Right");
		
		BitmapDrawable b = (BitmapDrawable) layout.getBackground();
		b.setAlpha(155);
		layout.setBackground(b);

		String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Eat The Cheese/Sound/error.mp3";
		//score sound
		HeadPhone HP = new HeadPhone(C);
		HP.setLeftLevel(1);
		HP.setRightLevel(1);
		if (HP.detectHeadPhones() == true)
			HP.play(Path, 0);
		failnum = I.getIntExtra("failnum", 0);
		failnum++;
		I.putExtra("failnum", failnum);
		
		
		Toast.makeText(C, "You stil have "+(5-failnum)+" tries to go", Toast.LENGTH_LONG).show();
		this.layout = layout;
		if(failnum == 7)
		{
			String Path2 = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Eat The Cheese/Sound/finish.mp3";
			//score sound
			HeadPhone HP2 = new HeadPhone(C);
			HP2.setLeftLevel(1);
			HP2.setRightLevel(1);
			if (HP2.detectHeadPhones() == true)
				HP2.play(Path2, 0);
			int score = I.getIntExtra("Score", 1);
			String word = I.getStringExtra("word");
			score = ((score/word.length())*100) - failnum*5;
			if(score<0)
				score = 0;
			I.putExtra("Score",score );
			
			I.putExtra("Count", 20);
		//	I.putExtra("Action", "NONE");
		//	I.putExtra("State", "S6");
		}
		else
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
		layout.removeView(tryAgain);
		layout.removeView(layout2);
	}
	public void createUI(LinearLayout layout, final Intent I, final Context c) {
		 layout2 = new LinearLayout(c);
		LinearLayout.LayoutParams layoutCenterParent =
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT);


		
		
		LinearLayout.LayoutParams layoutCenterParams =
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
		

		
		//layoutCenterParams.topMargin =150;
		tryAgain = new Button(c);
		RoundRectShape rect = new RoundRectShape(
			  new float[] {30,30, 30,30, 30,30, 30,30},
			  null,
			  null);
	OvalShape ov = new OvalShape();
			ShapeDrawable bg = new ShapeDrawable(ov);
			
			bg.getPaint().setColor(Color.RED);
			
			StateListDrawable stld = new StateListDrawable();
			
			stld.addState(new int[] { android.R.attr.state_enabled }, bg);
			Drawable d = Drawable.createFromPath(Environment
					.getExternalStorageDirectory().toString()
					+ "/xGame/Games/"
					+ "Eat The Cheese" + "/Images/tryAgain.png");
			Bitmap b = drawableToBitmap(d);
			
			 Bitmap bc1 = Bitmap.createBitmap(b.getWidth() + 10, b.getHeight() + 10, Bitmap.Config.ARGB_8888);
		        Canvas c1 = new Canvas(bc1);
		        c1.drawBitmap(b, 0, 0, null);
		        Bitmap bc2 = Bitmap.createBitmap(b.getWidth() + 10, b.getHeight() + 10, Bitmap.Config.ARGB_8888);
		        Canvas c2 = new Canvas(bc2);
		        c2.drawBitmap(b, 10, 10, null);

		        stld = new StateListDrawable();
		        stld.addState(new int[] { android.R.attr.state_pressed },  new BitmapDrawable(c.getResources(),bc2));
		        stld.addState(StateSet.WILD_CARD, new BitmapDrawable(c.getResources(),bc1));
	tryAgain.setLayoutParams(layoutCenterParams);
	
	tryAgain.setBackground(stld);
	//startSpeech.setBackground(d);
	//startSpeech.setPadding(10, 10, 10, 10);
	tryAgain.setGravity(Gravity.CENTER);
	tryAgain.setHeight(150);
	tryAgain.setWidth(200);
	
	//startSpeech.setBackgroundColor(0x99FF00CC);
	//startSpeech.setBackground(states);
	//startSpeech.setBackground(c.getResources().getDrawable(R.drawable.text_background));
	tryAgain.setContentDescription("try Again");
	tryAgain.setTextColor(0x99FF2400);
		layout2.addView(tryAgain);
		
		layout2.setLayoutParams(layoutCenterParent);
		layout2.setHorizontalGravity(Gravity.CENTER);
		layout2.setVerticalGravity(Gravity.CENTER);
		layout.addView(layout2);
		
		
		
		
		tryAgain.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Spell Me/Sound/Button.mp3";
				//score sound
				HeadPhone HP = new HeadPhone(c);
				HP.setLeftLevel(1);
				HP.setRightLevel(1);
				if (HP.detectHeadPhones() == true)
					HP.play(Path, 0);
				// TODO Auto-generated method stub
				I.putExtra("Action", "NONE");
				I.putExtra("State", "S1");
			}
		});
		

		
	}
	public static Bitmap drawableToBitmap (Drawable drawable) {
	    Bitmap bitmap = null;
	 
	    if (drawable instanceof BitmapDrawable) {
	        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
	        if(bitmapDrawable.getBitmap() != null) {
	            return bitmapDrawable.getBitmap();
	        } 
	    } 
	 
	    if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
	        bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
	    } else { 
	        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
	    } 
	 
	    Canvas canvas = new Canvas(bitmap);
	    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	    drawable.draw(canvas);
	    return bitmap;
	} 
}

	
