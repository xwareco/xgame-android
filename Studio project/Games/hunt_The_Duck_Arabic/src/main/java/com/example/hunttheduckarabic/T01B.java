package com.example.hunttheduckarabic;

import xware.engine_lib.interfaces.ItransitionActions;

import android.content.Intent;
import android.support.annotation.Keep;

@Keep
public class T01B implements ItransitionActions {

    @Override
    public boolean isConditionActivated(Intent I) {
        I.putExtra("GameTime", 90);
        return true;
    }

}
