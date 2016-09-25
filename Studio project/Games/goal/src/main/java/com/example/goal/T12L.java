package com.example.goal;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.Keep;

import xware.engine_lib.interfaces.ItransitionActions;

@Keep
public class T12L implements ItransitionActions {

    @Override
    public boolean isConditionActivated(Intent I) {
        if (I.getStringExtra("Action").equals("SwipeLeft")) {
            // out sound
            MediaPlayer mp = new MediaPlayer();
            try {
                mp.setDataSource(Environment.getExternalStorageDirectory()
                        + "/xGame/Games/Goal/Sound/wrong.mp3");
                mp.prepare();
                mp.start();
            } catch (Exception e) {
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
        int gameTime = I.getIntExtra("GameTime", 0);
        int timeInSecond = I.getIntExtra("timeInSecond", 0);
        int totalTries = I.getIntExtra("Tries", 0);
        int success = I.getIntExtra("Success", 0);

        int reward = 0;
        success++;
        int percent = (success / totalTries) * 100;

        if (percent == 100 && timeInSecond <= (gameTime / 2) && totalTries >= 6) {
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

}
