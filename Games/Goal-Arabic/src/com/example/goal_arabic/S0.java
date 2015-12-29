package com.example.goal_arabic;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;
import xware.xgame.interfaces.IstateActions;
import xware.xgame.sound.HeadPhone;

public class S0 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
		// TODO Auto-generated method stub

		TextView tv = (TextView)layout.getChildAt(0);
	     tv.setText("الهداف هى لعبة ركلات جزاء,قم بسحب الشاشة الى اليمين او الى اليسار لكى تستطيع من تصويب الكرة فى اتجاه معاكس لاتجاه الحارس  حتى لا يستطيع من  التصدى للكره, "
	     		+ "لاحظ كلما احرزت هدفا كلما رفع هذا من نقاطك .ان كنت مستعدا هيا قم بسحب الشاشة ناحية الشمال لبدا اللعب.");

	}
	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {
		
		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {

	}

	

}
