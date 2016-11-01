package com.example.tennis;

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
        tv.setText("This game needs the headphones, You are playing a tennis game against the computer, shoot the ball back by taping the screen once but if only sound is playing in both your left and right headphones, otherwise you will lose a point, To quit the game anytime, press the back button, now, choose the difficulty of the game, if you are a beginner, swipe left, if you are Intermediate, swipe right, if you are an expert, swipe down");

    }

    @Override
    public Intent loopBack(Context c, Intent I, HeadPhone H) {
        // TTS for how to play

        return I;
    }

    @Override
    public void onStateExit(Context c, Intent I, HeadPhone H) {

    }

}


