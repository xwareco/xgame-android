package com.example.shootit;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.Keep;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
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
        tv.setText("This game needs the headphones, You were sent to space to manage a war against aliens, you are driving your space ship and aliens are flying every where to shoot you, if the alien's sound is played from left to right swipe right to kill that alien, if the sound is played from right to left swipe left, if the sound is played in both left and right double tap the screen, good luck saving the earth!,  To quit the game anytime, press the back button, now, choose the difficulty of the game, if you are a beginner, swipe left, if you are Intermediate, swipe right, if you are an expert, swipe down");
    }

    @Override
    public Intent loopBack(Context c, Intent I, HeadPhone H) {

        return I;
    }

    @Override
    public void onStateExit(Context c, Intent I, HeadPhone H) {

    }

}
