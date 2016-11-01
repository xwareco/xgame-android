package com.example.spellme;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Keep;
import android.widget.LinearLayout;
import android.widget.Toast;

import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

@Keep
public class Show1 implements IstateActions {

    @Override
    public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
        // TODO Auto-generated method stub
        Toast.makeText(C, I.getStringExtra("word"), Toast.LENGTH_SHORT).show();
        I.putExtra("Action", "NONE");
        I.putExtra("State", "Show2");
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
