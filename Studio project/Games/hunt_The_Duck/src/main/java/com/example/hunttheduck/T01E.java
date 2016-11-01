package com.example.hunttheduck;

import android.content.Intent;
import android.support.annotation.Keep;

import xware.engine_lib.interfaces.ItransitionActions;

@Keep
public class T01E implements ItransitionActions {

    @Override
    public boolean isConditionActivated(Intent I) {
        I.putExtra("GameTime", 30);
        return true;
    }

}
