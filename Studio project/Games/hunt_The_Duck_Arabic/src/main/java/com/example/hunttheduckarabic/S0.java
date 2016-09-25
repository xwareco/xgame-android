package com.example.hunttheduckarabic;

import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Keep;
import android.widget.LinearLayout;
import android.widget.TextView;

@Keep
public class S0 implements IstateActions {

    @Override
    public void onStateEntry(LinearLayout layout, Intent I, Context C,
                             HeadPhone H) {
        TextView tv = (TextView) layout.getChildAt(0);
        tv.setText("تحتاج هذه اللُعبة إلى سَمَاعات الاًُذن..تتحرك البطة بجوارك, ﻻ تستطيع اصطياد البطة إﻻ إذا كان صوتها قادم من الأذنين اليُمنى و اليُسرى معا, إسحَب على الشاشة بإصبعين لأسفل ﻻصطياد البطة, للخروج من اللُعبة إضغَط زر العودة.. إختَر دَرَجة صُعوبة اللٌعبة لِبَدءِ اللَعِب, إذا كُنتَ مُبتدئ, إسحَب بإصبعين لليسار, إذا كُنتَ مُتَوسِط المُستوى, إسحَب بإصبعين لليمين, أما إذا كُنتَ خبير, إسحَب بإصبعين للأسفل");

    }

    @Override
    public Intent loopBack(Context c, Intent I, HeadPhone H) {

        return I;
    }

    @Override
    public void onStateExit(Context c, Intent I, HeadPhone H) {

    }

}
