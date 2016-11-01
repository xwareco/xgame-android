package com.example.wordmaster;

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
public class S2 implements IstateActions {

    static int failnum = 0;
    static LinearLayout layout;
    static TextView tryAgain;
    static LinearLayout layout2;
    HeadPhone Hc;

    @Override
    public void onStateEntry(LinearLayout layout, Intent I, Context C,
                             HeadPhone H) {

        I.putExtra("Action", "Right");
        int gameTime = I.getIntExtra("GameTime", 0);
        int timeInSecond = I.getIntExtra("timeInSecond", 0);
        BitmapDrawable b = (BitmapDrawable) layout.getBackground();
        b.setAlpha(155);
        layout.setBackground(b);
        Hc = H;
        String Path = Environment.getExternalStorageDirectory().toString()
                + "/xGame/Games/The Word Master/Sound/error.mp3";
        // score sound
        if (Hc != null) Hc.release();
        Hc = new HeadPhone(C);
        Hc.setLeftLevel(1);
        Hc.setRightLevel(1);
        Hc.play(Path, 0);
        failnum = I.getIntExtra("failnum", 0);
        failnum++;
        I.putExtra("failnum", failnum);
        S2.layout = layout;
        if (failnum >= 3 && timeInSecond <= (gameTime / 5)) {
            Toast.makeText(C, "Another Wrong answer..Game over!",
                    Toast.LENGTH_LONG).show();
            I.putExtra("Count", 20);
        } else if (failnum >= 6 && timeInSecond <= (gameTime / 2)) {
            Toast.makeText(C, "Wrong answer..30 seconds have been taken as a penalty", Toast.LENGTH_LONG)
                    .show();
            timeInSecond = timeInSecond + 30;
            I.putExtra("timeInSecond", timeInSecond);
            I.putExtra("Action", "NONE");
            I.putExtra("State", "S1");
        } else if (failnum >= 9 && timeInSecond <= (gameTime * 0.75)) {
            Toast.makeText(C, "Wrong answer..60 seconds have been taken as a penalty", Toast.LENGTH_LONG)
                    .show();
            timeInSecond = timeInSecond + 60;
            I.putExtra("timeInSecond", timeInSecond);
            I.putExtra("Action", "NONE");
            I.putExtra("State", "S1");
        } else if (failnum >= 9 && timeInSecond >= (gameTime * 0.75)
                && timeInSecond < gameTime) {
            Toast.makeText(C, "Wrong answer..60 seconds have been taken as a penalty", Toast.LENGTH_LONG)
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
