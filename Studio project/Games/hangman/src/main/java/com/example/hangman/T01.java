package com.example.hangman;

import android.content.Intent;
import android.support.annotation.Keep;

import xware.engine_lib.interfaces.ItransitionActions;

@Keep
public class T01 implements ItransitionActions {

    @Override
    public boolean isConditionActivated(Intent I) {
        return true;
    }

}
