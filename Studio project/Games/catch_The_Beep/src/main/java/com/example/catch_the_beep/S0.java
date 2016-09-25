package com.example.catch_the_beep;

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
        TextView tv = (TextView) layout.getChildAt(0);
        tv.setText("Listen to the beep sound in your headphones, swipe right if the sound from the right, swipe left if the sound from the left, or Double tap if the sound from both headphones, " +
                "To quit the game anytime, press the back button, now, choose the difficulty of the game, if you are a beginner, swipe left, if you are Intermediate, swipe right, if you are an expert, swipe down");
    }

    @Override
    public Intent loopBack(Context c, Intent I, xware.engine_lib.sound.HeadPhone H) {

        return I;
    }

    @Override
    public void onStateExit(Context c, Intent I, xware.engine_lib.sound.HeadPhone H) {

    }

}
