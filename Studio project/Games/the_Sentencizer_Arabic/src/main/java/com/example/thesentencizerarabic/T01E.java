package com.example.thesentencizerarabic;

import android.content.Intent;
import android.support.annotation.Keep;

import xware.engine_lib.interfaces.ItransitionActions;

@Keep
public class T01E implements ItransitionActions {

    @Override
    public boolean isConditionActivated(Intent I) {
        I.putExtra("GameTime", 60);
        I.putExtra("toastOrNot", true);
        return true;
    }

}
