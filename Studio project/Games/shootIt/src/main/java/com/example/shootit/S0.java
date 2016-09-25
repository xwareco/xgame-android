package com.example.shootit;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

public class S0 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C,
			HeadPhone H) {

		TextView tv = (TextView) layout.getChildAt(0);
		tv.setText("You were sent to space to manage a war against aliens, you are driving your space ship and aliens are flying every where to shoot you, if the alien's sound is played from left to right swipe right to kill that alien, if the sound is played from right to left swipe left, if the sound is played in both left and right double tap the screen, good luck saving the earth!.");
		Button btnTag = new Button(C);
        btnTag.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnTag.setText("Test Button");
        btnTag.setEnabled(true);
        //btnTag.setId(1);
        Drawable d = Drawable.createFromPath(Environment
				.getExternalStorageDirectory().toString()
				+ "/xGame/Games/"
				+ "Shoot it!" + "/Images/test.png");
        btnTag.setBackground(d);
        
        layout.addView(btnTag);
	}

	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {

		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {

	}

}
