package com.example.catch_the_beep_arabic;

import xware.engine_lib.interfaces.ItransitionActions;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.Keep;

@Keep
public class T12 implements ItransitionActions {

    @Override
    public boolean isConditionActivated(Intent I) {
        int gameTime = I.getIntExtra("GameTime", 0);
        int timeInSecond = I.getIntExtra("timeInSecond", 0);
        int totalTries = I.getIntExtra("Tries", 0);
        int success = I.getIntExtra("Success", 0);
        if (I.getStringExtra("Action") == "Right") {
            int reward = 0;
            success++;
            int percent = (success / totalTries) * 100;

            if (percent == 100 && timeInSecond <= (gameTime / 2)
                    && totalTries == 6) {
                reward = 30;
                timeInSecond = timeInSecond - 30;
                if (timeInSecond < 0) {
                    timeInSecond = 1;
                }
                I.putExtra("timeInSecond", timeInSecond);
                I.putExtra("TimeReward", reward);
            }

            I.putExtra("Success", success);
            return true;
        }
        // out sound
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(Environment.getExternalStorageDirectory()
                    + "/xGame/Games/رد الفعل/Sound/wrong.mp3");
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // penalty conditions
        int Score = I.getIntExtra("Score", 0);
        Score -= 5;
        if (Score < 0) {
            Score = 0;
        }
        I.putExtra("Score", Score);
        return false;

    }

}
