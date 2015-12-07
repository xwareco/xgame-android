package com.example.teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
import android.graphics.drawable.GradientDrawable.Orientation;
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
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
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
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sound.HeadPhone;
import uencom.xgame.sound.TTS;
import uencom.xgame.speech.SpeechRecognition;

public class S1   implements IstateActions {
	String[] words1 = new String[]{
			"job","around","plants","world","apart","options",
			"bike","like","work"
			
			};
	String[] sentence1 = new String[]{"He got job as waiter.","I did few jobs around the house."
			,"It’s my job to water the plants.","She’s travelled all over the world."
			,"His whole world fell apart when she left.","menu doesn’t have many options."
			,"I ride my bike to school.","I like school.","He works as waiter"};
	
	String[] words2 = new String[]{"waiter","school","house","margin","poems",
			"order"
			};
	
	String[] sentence2 = new String[]{"He got job as waiter.","I ride my bike to school.","I like my house."
			,"You can make notes in the margin","love poems","Has the waiter taken your order?"};
	
	String[] words3 = new String[]{"positive","satellites","essential",
			"security","increase"};

    String[] sentence3 = new String[]{"She has a very positive attitude.","weather satellite","Computers are essential part of our lives."
	        ,"airport security","Smoking increases the risk","Has the waiter taken your order?"};

	
	
	ImageButton next;
	ImageButton previous;
	TextView speech;
	static LinearLayout layout;
	static LinearLayout layout2;
	static String word ;
	static String sentence ;
	static String[] wordsArray;
	static String[] sentenceArray;
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
			
			flag = I.getBooleanExtra("Flag",true);
			
			if(flag){
			 Random rand = new Random();
			 int level = I.getIntExtra("level", 0);
			 switch (level) {
			case 0: {wordsArray =words1; sentenceArray = sentence1; }
				
				break;
			case 1: {wordsArray =words2; sentenceArray = sentence2;}
			
			break;
           case 2: {wordsArray =words3; sentenceArray = sentence3;}
			
			break;
			default:
			        {wordsArray =words1; sentenceArray = sentence1;}
			break;
			}
			 int random = rand.nextInt(wordsArray.length);
			 word = wordsArray[random];
			 sentence = sentenceArray[random];
			I.putExtra("word", word);
			I.putExtra("sentence", sentence);
			}
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
	layout2.setPadding(10, 10, 10, 10);
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
	previous.setClickable(false);
	previous.setEnabled(false);
	
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
					+ "Teacher" + "/Images/word.png");
		speech.setText(word);
		speech.setTextSize(50);
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
			I.putExtra("Flag",false);
			I.putExtra("Action", "NONE");
			I.putExtra("State", "S2");
		}
	});
	Right.setLayoutParams(RightParam);
	Right.addView(next);
	layout2.addView(Right);
	
	layout.addView(layout2);

			


 
}


} 



