package com.example.spellme;

import android.content.Intent;
import android.support.annotation.Keep;

import xware.engine_lib.interfaces.ItransitionActions;

@Keep
public class T01I implements ItransitionActions {

    @Override
    public boolean isConditionActivated(Intent I) {
        I.putExtra("GameTime", 420);
        I.putExtra("toastOrNot", true);
        return true;
    }

}
