package com.example.sentencizer;

import android.content.Intent;
import android.support.annotation.Keep;

import xware.engine_lib.interfaces.ItransitionActions;

@Keep
public class T01B implements ItransitionActions {

    @Override
    public boolean isConditionActivated(Intent I) {
        I.putExtra("GameTime", 180);
        I.putExtra("toastOrNot", true);
        return true;
    }

}
