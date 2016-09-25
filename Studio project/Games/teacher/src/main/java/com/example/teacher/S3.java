package com.example.teacher;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class S3   implements IstateActions {
	
	
	
	ImageButton next;
	ImageButton previous;
	TextView speech;
	static LinearLayout layout;
	static LinearLayout layout2;
	static String word ;
	static String sentence ;
	 static boolean flag;
	 public Context context;
	 static HeadPhone Hc;
	@Override
	public void onStateEntry(LinearLayout layout,  Intent I,  Context c,HeadPhone H) {
		// TODO Auto-generated method stub
		context = c;
		Hc = H;

		 I.putExtra("Action", "Right");
		    BitmapDrawable b = (BitmapDrawable) layout.getBackground();
			b.setAlpha(155);
			layout.setBackground(b);
			this.layout = layout;
			
		    word = I.getStringExtra("word");
			sentence = I.getStringExtra("sentence");
			
			
			createUI(this.layout,I,c);
			
			
	}
	@Override
	public Intent loopBack(final Context c, final Intent I,HeadPhone H) {
		
		
		
		
			return I;
		
	}
	
	
	@Override
	public void onStateExit(Context c, Intent I,HeadPhone H) {
		// TODO Auto-generated method stub
		layout.removeView(layout2);

	}
public void createUI(LinearLayout layout, final Intent I, final Context c) {
	
	layout2 = new LinearLayout(c);
	layout2.setOrientation(LinearLayout.HORIZONTAL);
	layout2.setPadding(10, 0, 10, 0);
	LinearLayout.LayoutParams layoutCenterParent =
			new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
	layoutCenterParent.weight = 1;
	
	LinearLayout left = new LinearLayout(context);
	LinearLayout.LayoutParams leftParam =
			new LinearLayout.LayoutParams(
					0,
					LinearLayout.LayoutParams.MATCH_PARENT);
	leftParam.weight = 0.2F;
	leftParam.gravity = Gravity.LEFT;
	previous = new ImageButton(context);
	Drawable d = Drawable.createFromPath(Environment
				.getExternalStorageDirectory().toString()
				+ "/xGame/Games/"
				+ "Teacher" + "/Images/previous.png");
	LinearLayout.LayoutParams image =
			new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
	previous.setImageDrawable(d);
	previous.setContentDescription("back");
	previous.setLayoutParams(image);
	previous.setClickable(true);
	previous.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Teacher/Sound/Button.mp3";
			//score sound
			HeadPhone HP = new HeadPhone(context);
			HP.setLeftLevel(1);
			HP.setRightLevel(1);
			if (HP.detectHeadPhones() == true)
				HP.play(Path, 0);
			I.putExtra("Action", "NONE");
			I.putExtra("State", "S2");
		}
	});
	left.setLayoutParams(leftParam);
	left.addView(previous);
	layout2.addView(left);
	//=============================================================
		LinearLayout center = new LinearLayout(context);
		LinearLayout.LayoutParams CenterParam =
				new LinearLayout.LayoutParams(
						0,
						LinearLayout.LayoutParams.MATCH_PARENT);
		CenterParam.weight = 0.6F;
		CenterParam.gravity = Gravity.CENTER;
		speech = new TextView(context);
		Drawable d3 = Drawable.createFromPath(Environment
					.getExternalStorageDirectory().toString()
					+ "/xGame/Games/"
					+ "Teacher" + "/Images/word3.png");
		
		speech.setText(sentence);
		speech.setTextSize(30);
		speech.setTextColor(Color.BLUE);
		speech.setGravity(Gravity.CENTER);
		speech.setBackground(d3);
		speech.setLayoutParams(image);
		speech.setClickable(true);
		center.setLayoutParams(CenterParam);
		center.addView(speech);
		layout2.addView(center);
		layout2.setLayoutParams(layoutCenterParent);
	//==================================================================
	LinearLayout Right = new LinearLayout(context);
	LinearLayout.LayoutParams RightParam =
			new LinearLayout.LayoutParams(
					0,
					LinearLayout.LayoutParams.MATCH_PARENT);
	RightParam.weight = 0.2F;
	RightParam.gravity = Gravity.RIGHT;
	next = new ImageButton(context);
	Drawable d2 = Drawable.createFromPath(Environment
				.getExternalStorageDirectory().toString()
				+ "/xGame/Games/"
				+ "Teacher" + "/Images/next.png");
	next.setImageDrawable(d2);
	next.setContentDescription("next");
	next.setLayoutParams(image);
	next.setClickable(true);
    next.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String Path = Environment.getExternalStorageDirectory().toString() + "/xGame/Games/Teacher/Sound/Button.mp3";
			//score sound
			HeadPhone HP = new HeadPhone(context);
			HP.setLeftLevel(1);
			HP.setRightLevel(1);
			if (HP.detectHeadPhones() == true)
				HP.play(Path, 0);
			if(I.getIntExtra("level", 0) == 4)
			{
				I.putExtra("Action", "NONE");
				I.putExtra("State", "S4");
			}else
			{
			int level = I.getIntExtra("level", 0);
			I.putExtra("level", ++level);
			I.putExtra("Flag", true);
			I.putExtra("Action", "NONE");
			I.putExtra("State", "S1");
			}
		}
	});
	Right.setLayoutParams(RightParam);
	Right.addView(next);
	layout2.addView(Right);
	
	layout.addView(layout2);

	
}


} 



