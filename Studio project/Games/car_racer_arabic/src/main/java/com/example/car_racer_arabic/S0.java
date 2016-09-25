package com.example.car_racer_arabic;

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
        TextView tv = (TextView) layout.getChildAt(0);
        tv.setText("تحتاج هذه اللعبة إلى سماعات الأذن, قم بمسك جهازك في وضعية الشاشة العريضة قبل بدء اللعب, قم بقيادة السيارة عن طريق تحريك الجهاز يمينا ويسارا عند المنعطفات, انتبه حتى ﻻ تصطدم بأي عقبة في الطريق, للخروج من اللعبة اضغط زر العودة");
    }

    @Override
    public Intent loopBack(Context c, Intent I, HeadPhone H) {
        return I;

    }

    @Override
    public void onStateExit(Context c, Intent I, HeadPhone H) {
        // TODO Auto-generated method stub

    }

}
