package com.example.thesentencizerarabic;

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
    public void onStateEntry(LinearLayout layout, Intent I, Context C,
                             HeadPhone H) {

        // TODO Auto-generated method stub
        TextView tv = (TextView) layout.getChildAt(0);
        tv.setText("لديكَ جُملة من 4 كَلِمات مُرتبة عشوائياً, قُم بترتيب هذه الكلمات بالضغط علىها لتكوين جملة مفيدة, للخروج من اللُعبة إضغَط زر العودة, إختَر دَرَجة صُعوبة اللٌعبة لِبَدءِ اللَعِب, إذا كُنتَ مُبتدئ, إسحَب بإصبعين لليسار, إذا كُنتَ مُتَوسِط المُستوى, إسحَب بإصبعين لليمين, أما إذا كُنتَ خبير, إسحَب بإصبعين للأسفل");
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
