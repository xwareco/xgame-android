package com.example.catch_the_beep_arabic;

import xware.engine_lib.interfaces.ItransitionActions;

import android.content.Intent;
import android.support.annotation.Keep;

@Keep
public class T01B implements ItransitionActions {

    @Override
    public boolean isConditionActivated(Intent I) {
        I.putExtra("GameTime", 90);
        I.putExtra("toastOrNot", true);
        return true;
    }

}
