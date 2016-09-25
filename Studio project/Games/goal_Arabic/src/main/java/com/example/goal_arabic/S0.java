package com.example.goal_arabic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Keep;
import android.widget.LinearLayout;
import android.widget.TextView;

import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;
@Keep
public class S0 implements IstateActions {

    @Override
    public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
        // TODO Auto-generated method stub

        TextView tv = (TextView) layout.getChildAt(0);
        tv.setText("تحتاج هذه اللُعبة إلى سَمَاعات الاًُذن..الهداف هي لُعبة ضربات جَزاء, سدِدِ الكرة عكس اتجاه الحارس الذي تستمع له عن طريقِ السَحب يميناً أو يساراً بإصبعين أو النقر على الشاشة مرتين بإصبع واحد, حاول أن تسجل أكبر عدد من الأهداف قبل انتهاء الوقت, للخروج من اللُعبة إضغَط زر العودة, إختَر دَرَجة صُعوبة اللٌعبة لِبَدءِ اللَعِب, إذا كُنتَ مُبتدئ, إسحَب بإصبعين لليسار, إذا كُنتَ مُتَوسِط المُستوى, إسحَب بإصبعين لليمين, أما إذا كُنتَ خبير, إسحَب بإصبعين للأسفل");
    }

    @Override
    public Intent loopBack(Context c, Intent I, HeadPhone H) {

        return I;
    }

    @Override
    public void onStateExit(Context c, Intent I, HeadPhone H) {

    }


}
