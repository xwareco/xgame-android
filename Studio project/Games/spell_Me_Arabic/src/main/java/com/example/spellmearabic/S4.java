package com.example.spellmearabic;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.annotation.Keep;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

@Keep
public class S4 implements IstateActions {

    static int failnum = 0;
    static LinearLayout layout;
    static TextView tryAgain;
    static LinearLayout layout2;

    @Override
    public void onStateEntry(LinearLayout layout, Intent I, Context C,
                             HeadPhone H) {
        I.putExtra("Action", "Right");
        int gameTime = I.getIntExtra("GameTime", 0);
        int timeInSecond = I.getIntExtra("timeInSecond", 0);
        BitmapDrawable b = (BitmapDrawable) layout.getBackground();
        b.setAlpha(155);
        layout.setBackground(b);

        String Path = Environment.getExternalStorageDirectory().toString()
                + "/xGame/Games/الهجاء/Sound/error.mp3";
        // score sound
        HeadPhone HP = new HeadPhone(C);
        HP.setLeftLevel(1);
        HP.setRightLevel(1);
        HP.play(Path, 0);
        failnum = I.getIntExtra("failnum", 0);
        failnum++;
        I.putExtra("failnum", failnum);
        I.putExtra("fail", 0);
        S4.layout = layout;
        if (failnum >= 3 && timeInSecond <= (gameTime / 5)) {
            Toast.makeText(C, "لقد أخطأت مرة أخرى.. انتهت اللعبة",
                    Toast.LENGTH_LONG).show();
            I.putExtra("Count", 20);
        } else if (failnum >= 6 && timeInSecond <= (gameTime / 2)) {
            Toast.makeText(C, "لقد أخطأت..تم خصم 30 ثانية", Toast.LENGTH_LONG)
                    .show();
            timeInSecond = timeInSecond + 30;
            I.putExtra("timeInSecond", timeInSecond);
            I.putExtra("Action", "NONE");
            I.putExtra("State", "S1");
        } else if (failnum >= 9 && timeInSecond <= (gameTime * 0.75)) {
            Toast.makeText(C, "لقد أخطأت..تم خصم 60 ثانية", Toast.LENGTH_LONG)
                    .show();
            timeInSecond = timeInSecond + 60;
            I.putExtra("timeInSecond", timeInSecond);
            I.putExtra("Action", "NONE");
            I.putExtra("State", "S1");
        } else if (failnum >= 9 && timeInSecond >= (gameTime * 0.75)
                && timeInSecond < gameTime) {
            Toast.makeText(C, "لقد أخطأت..تم خصم 60 ثانية", Toast.LENGTH_LONG)
                    .show();
            timeInSecond = timeInSecond + 60;
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
        layout.removeView(tryAgain);
        layout.removeView(layout2);
    }
}
