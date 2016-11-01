package com.example.tennis;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.Keep;

import xware.engine_lib.interfaces.ItransitionActions;

@Keep
public class T12 implements ItransitionActions {

    @Override
    public boolean isConditionActivated(Intent I) {
        if (I.getStringExtra("Action") .equals("SingleTap")) {
            int gameTime = I.getIntExtra("GameTime", 0);
            int timeInSecond = I.getIntExtra("timeInSecond", 0);
            int totalTries = I.getIntExtra("Tries", 0);
            int success = I.getIntExtra("Success", 0);
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
        } else {
            // out sound
            MediaPlayer mp = new MediaPlayer();
            try {
                mp.setDataSource(Environment.getExternalStorageDirectory()
                        + "/xGame/Games/Tennis/Sound/error.mp3");
                mp.prepare();
                mp.start();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.print(I.getStringExtra("Action"));
            System.out.print(I.getStringExtra("Action").equals("SingleTap"));

        }
        return false;
    }
}
