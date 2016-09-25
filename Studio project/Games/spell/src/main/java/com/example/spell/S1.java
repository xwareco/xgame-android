package com.example.spell;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.widget.LinearLayout;
import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

public class S1  implements IstateActions {
	
	

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C,HeadPhone H) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		 I.putExtra("Action", "Right");
		BitmapDrawable b = (BitmapDrawable) layout.getBackground();
		b.setAlpha(100);
		layout.setBackground(b);
		
		
		
		 
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
