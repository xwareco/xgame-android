package com.example.wordmaster_arabic;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.widget.LinearLayout;
import android.widget.TextView;
import xware.xgame.interfaces.IstateActions;
import xware.xgame.sound.HeadPhone;

public class S0 implements IstateActions {

	

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		
		// TODO Auto-generated method stub
				TextView tv = (TextView)layout.getChildAt(0);
			     tv.setText("يتم انشاء كلمة عشوائية وتظهر اليك ولكن بحروف غير مرتبة , ما عليك هى تجميع هذه الحروف وتكوين الكلمة بالترتيب الصحيح, "
			     		+ " ولكن احترس من الوقت  فانك سوف تفقد 10 نقاط ان انهيت اللعبة بعد 5 دقائق, ولذلك  "
			     		+ " كلما انهيت اللعبة مبكرا كلما زادت نقاطك, ان كنت مستعدا فهيا قم بسحب الشاشة ناحية اليسار لتبدا باللعب");

			        
	}

	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {
		// TODO Auto-generated method stub
		
	}

}
