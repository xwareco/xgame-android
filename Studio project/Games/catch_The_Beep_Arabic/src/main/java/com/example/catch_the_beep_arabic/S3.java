package com.example.catch_the_beep_arabic;

import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Keep;
import android.widget.LinearLayout;
import android.widget.Toast;

@Keep
public class S3 implements IstateActions {

    @Override
    public void onStateEntry(LinearLayout layout, Intent I, Context C,
                             HeadPhone H) {
        int reward = I.getIntExtra("TimeReward", 0);
        if (reward == 30) {
            Toast.makeText(C, "أحسنت..لقد حصلت على مكافئة 30 ثانية",
                    Toast.LENGTH_LONG).show();
            reward = 0;
            I.putExtra("TimeReward", reward);
        }

        String Path = Environment.getExternalStorageDirectory().toString()
                + "/xGame/Games/رد الفعل/Sound/heaa.mp3";
        H = new HeadPhone(C);
        H.setLeftLevel(1);
        H.setRightLevel(1);
        H.play(Path, 0);
    }

    @Override
    public Intent loopBack(Context c, Intent I, HeadPhone H) {
        I.putExtra("Action", "NONE");
        I.putExtra("State", "S1");
        return I;
    }

    @Override
    public void onStateExit(Context c, Intent I, HeadPhone H) {
        // TODO Auto-generated method stub

    }

}
