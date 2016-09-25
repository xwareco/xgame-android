package com.example.spellmearabic;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Keep;
import android.widget.LinearLayout;
import android.widget.Toast;

import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

@Keep
public class S3 implements IstateActions {
    int level;

    @Override
    public void onStateEntry(LinearLayout layout, Intent I, Context C,
                             HeadPhone H) {
        I.putExtra("Action", "Right");
        level = I.getIntExtra("level", 0);
        level++;
        if (level >= 3)
            level = 0;
        I.putExtra("level", level);
        I.putExtra("fail", 0);
        int score = I.getIntExtra("Score", 0);
        score += 25;
        I.putExtra("Score", score);
        String Path = Environment.getExternalStorageDirectory().toString()
                + "/xGame/Games/الهجاء/Sound/correct.mp3";
        // score sound
        HeadPhone HP = new HeadPhone(C);
        HP.setLeftLevel(1);
        HP.setRightLevel(1);
        HP.play(Path, 0);
        System.out.print(score);
        int failnum = I.getIntExtra("failnum", 0);
        int gameTime = I.getIntExtra("GameTime", 0);
        int timeInSecond = I.getIntExtra("timeInSecond", 0);
        Toast.makeText(C, "لقد استهلكت : " + timeInSecond + " ثانية ",
                Toast.LENGTH_SHORT).show();

        if (failnum == 0 && timeInSecond >= (gameTime / 2)) {
            Toast.makeText(C, "أحسنت..لقد حصلت على مكافئة 30 ثانية",
                    Toast.LENGTH_LONG).show();
            timeInSecond = timeInSecond - 30;
            I.putExtra("timeInSecond", timeInSecond);
            I.putExtra("Action", "NONE");
            I.putExtra("State", "S1");
        } else if (failnum == 0 && timeInSecond >= (gameTime * 0.75)) {
            Toast.makeText(C, "أحسنت..لقد حصلت على مكافئة 60 ثانية",
                    Toast.LENGTH_LONG).show();
            timeInSecond = timeInSecond - 60;
            I.putExtra("timeInSecond", timeInSecond);
            I.putExtra("Action", "NONE");
            I.putExtra("State", "S1");
        } else {
            I.putExtra("Action", "NONE");
            I.putExtra("State", "S1");
        }

    }

    @Override
    public Intent loopBack(Context c, Intent I, HeadPhone H) {
        // TODO Auto-generated method stub
        return I;
    }

    @Override
    public void onStateExit(Context c, Intent I, HeadPhone H) {
        // TODO Auto-generated method stub

    }

}
