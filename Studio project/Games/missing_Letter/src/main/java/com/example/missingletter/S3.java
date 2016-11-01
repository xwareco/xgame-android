package com.example.missingletter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.annotation.Keep;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

@Keep
public class S3 implements IstateActions {
    static LinearLayout layout;
    static Button tryAgain;
    static LinearLayout layout2;

    @Override
    public void onStateEntry(LinearLayout layout, Intent I, Context C,
                             HeadPhone H) {
        // TODO Auto-generated method stub
        I.putExtra("Action", "Right");
        int Score = I.getIntExtra("Score", 0);
        Score += 7.5;
        I.putExtra("Score", Score);
        I.putExtra("Flag", true);
        BitmapDrawable b = (BitmapDrawable) layout.getBackground();
        b.setAlpha(155);
        layout.setBackground(b);

        String Path = Environment.getExternalStorageDirectory().toString()
                + "/xGame/Games/Missing Letter/Sound/correct.mp3";
        // score sound
        HeadPhone HP = new HeadPhone(C);
        HP.setLeftLevel(1);
        HP.setRightLevel(1);
        HP.play(Path, 0);
        /*int level = I.getIntExtra("Level", 0);
		level++;
		if(level == 5)level = 0;*/
        int failnum = I.getIntExtra("failnum", 0);
        int gameTime = I.getIntExtra("GameTime", 0);
        int timeInSecond = I.getIntExtra("timeInSecond", 0);


        if (failnum == 0 && timeInSecond >= (gameTime / 2)) {
            Toast.makeText(C, "Well done!, 30 seconds have been added as a reward",
                    Toast.LENGTH_LONG).show();
            timeInSecond = timeInSecond - 30;
            I.putExtra("timeInSecond", timeInSecond);
            I.putExtra("Action", "NONE");
            I.putExtra("State", "S1");
        } else if (failnum == 0 && timeInSecond >= (gameTime * 0.75)) {
            Toast.makeText(C, "Well done!, 60 seconds have been added as a reward",
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
