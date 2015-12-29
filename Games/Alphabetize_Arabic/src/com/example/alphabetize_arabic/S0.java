package com.example.alphabetize_arabic;

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
			     tv.setText(" سيكون لديك قائمة من 4 كلمات مرتبة بشكل عشوائي ودورك  هو فرز هذه القائمة وترتيب الكلمات حسب الترتيب الأبجدي، انقر على الكلمة التي لها درجة اعلى فى الترتيب الابجدى"
			     		+ "ولكن لاحظ عليك الاهتمام بالوقت، لانك سوف تفقد 10 نقاط إذا أخذت أكثر من 5 دقائق لإنهاء اللعبة ,"
			     		+ "كلما انهيت اللعبة فى وقت اقل كلما ارتفت نقاطك , ان كنت مستعدا هيا قم بسحب الشاشة الى اليسار لبدا اللعب.");
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
