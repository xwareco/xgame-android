package com.example.goal;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Keep;
import android.widget.LinearLayout;
import android.widget.Toast;

import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

@Keep
public class S2 implements IstateActions {

    @Override
    public void onStateEntry(LinearLayout layout, Intent I, Context C,
                             HeadPhone H) {

        int reward = I.getIntExtra("TimeReward", 0);
        if (reward == 30) {
            Toast.makeText(C, "Great!, 30 seconds have been added as a reward",
                    Toast.LENGTH_LONG).show();
            reward = 0;
            I.putExtra("TimeReward", reward);
        }

        String Path = Environment.getExternalStorageDirectory().toString()
                + "/xGame/Games/Goal/Sound/goal !!.mp3";
        // score sound
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

    }

}
