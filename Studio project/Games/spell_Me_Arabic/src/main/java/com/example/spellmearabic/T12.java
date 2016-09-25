package com.example.spellmearabic;

import android.content.Intent;
import android.support.annotation.Keep;

import xware.engine_lib.interfaces.ItransitionActions;

@Keep
public class T12 implements ItransitionActions {

    @Override
    public boolean isConditionActivated(Intent I) {

        return true;
    }

}
