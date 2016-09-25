package com.example.spellme;


import java.util.Arrays;
import java.util.Random;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.LinearLayout;
import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

public class S1  implements IstateActions {
	String[] words1 = new String[]{
			"egypt","peace","greeting","shake","product","world","option",
			"school","house","interest","message",
			"image","growth","profit","work","battle","shout","order","payment","rush","love","sale",
			"attempt","scared","loss","offer"
			
			};
	String[] words2 = new String[]{"margin","rubbed","depth","limit","shaking","doll","poetry",
			"donkey","policeman","egypt","positive",
			"exchange","security","increase","market","opinion","mistake","objective",
			"output","advice","penalty"
			};
	
	String[] words3 = new String[]{"permission","justice","satisfied","education",
			"production","industry","arrangement","deeply","promotion","invoice","selection","plates","freedom","elephant","possibly",
			"practical","guarantee","shallow",
			"inventory","knowledge"};
	
	 int wordnum = 0;
	 
	
	

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C,HeadPhone H) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		I.putExtra("Action", "Right");
		BitmapDrawable b = (BitmapDrawable) layout.getBackground();
		b.setAlpha(100);
		layout.setBackground(b);
		String[] words;
		int level = I.getIntExtra("level", 0);
		Log.d("level", level+"");
		switch (level) {
		case 0: {words = words1; }
		break;
		case 1: {words = words2; }
		break;
        case 2: {words = words3; }
		break;
		default:
		{words =words3; }
		break;
		}
		 
		
		Random rand = new Random();
		wordnum = rand.nextInt(words.length);
		String word = words[wordnum];
		char[] chars = new char[word.length()];
		char[] tempChars =word.toCharArray();
		Arrays.fill(chars, '$');
		 String workingWord = new String(chars);

		I.putExtra("word", word);
		I.putExtra("workingWord", workingWord);
		
		
		 
	}
	@Override
	public Intent loopBack(Context c, Intent I,HeadPhone H) {
		// TODO Auto-generated method stub
		
		
		
		return I;
		
	}
	
	@Override
	public void onStateExit(Context c, Intent I,HeadPhone H) {
		// TODO Auto-generated method stub
		

	}

	

}
