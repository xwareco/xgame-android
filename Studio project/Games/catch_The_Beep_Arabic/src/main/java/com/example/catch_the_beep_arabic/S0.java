package com.example.catch_the_beep_arabic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Keep;
import android.widget.LinearLayout;
import android.widget.TextView;
@Keep
public class S0 implements xware.engine_lib.interfaces.IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I, Context C,
			xware.engine_lib.sound.HeadPhone H) {
		// TODO Auto-generated method stub
		TextView tv = (TextView) layout.getChildAt(0);
		tv.setText("تحتاج هذه اللُعبة إلى سَمَاعات الاًُذن..إستمع إلى صوت النغمة, إذا كان الصوت قادم من الأذن اليُمنى, إسحَب بإصبعين لليمين, إذا كان الصوت قادم من الأذن اليُسرى, إسحَب بإصبعين لليسار,إذا كان الصوت قادم من الإتجاهين, إسحَب بإصبعين لأسفل, للخروج من اللُعبة إضغَط زر العودة, إختَر دَرَجة صُعوبة اللٌعبة لِبَدءِ اللَعِب, إذا كُنتَ مُبتدئ, إسحَب بإصبعين لليسار, إذا كُنتَ مُتَوسِط المُستوى, إسحَب بإصبعين لليمين, أما إذا كُنتَ خبير, إسحَب بإصبعين للأسفل");
	}

	@Override
	public Intent loopBack(Context c, Intent I, xware.engine_lib.sound.HeadPhone H) {

		return I;
	}

	@Override
	public void onStateExit(Context c, Intent I, xware.engine_lib.sound.HeadPhone H) {

	}

}
