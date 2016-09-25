package com.example.catch_the_beep;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Environment;
import android.support.annotation.Keep;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import xware.engine_lib.sound.HeadPhone;

@Keep
public class S1 implements xware.engine_lib.interfaces.IstateActions {

    HeadPhone   Hc = null;
    public static long startTime;
    static Timer timer;
    static TextView showTime;
    static LinearLayout layout;
    ShapeDrawable timeBg;
    public static int time = 0;
    public Context context;

    @Override
    public void onStateEntry(LinearLayout layout, Intent I, Context C,
                             HeadPhone H) {

        Boolean toastOrNot = I.getBooleanExtra("toastOrNot", false);
        if (toastOrNot) {
            Toast.makeText(C, "The game has started", Toast.LENGTH_SHORT).show();
            toastOrNot = false;
            I.putExtra("toastOrNot", toastOrNot);
        }
        context = C;
        time = I.getIntExtra("timeInSecond", 0);
        Hc = H;
        String Path = Environment.getExternalStorageDirectory()
                .toString()
                + "/xGame/Games/Catch The Beeb/Sound/start.mp3";
        // score sound
        Hc = new HeadPhone(context);
        Hc.setLeftLevel(1);
        Hc.setRightLevel(1);
        Hc.play(Path, 0);
        LinearLayout.LayoutParams layoutEditParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutEditParams.gravity = Gravity.CENTER_HORIZONTAL;
        RoundRectShape rect = new RoundRectShape(new float[]{30, 30, 30, 30,
                30, 30, 30, 30}, null, null);
        timeBg = new ShapeDrawable(rect);

        timeBg.getPaint().setColor(0x99463E3F);
        layoutEditParams.topMargin = 4;
        showTime = new TextView(C);
        showTime.setTextSize(25);
        showTime.setTextColor(Color.WHITE);
        showTime.setBackground(timeBg);

        showTime.setLayoutParams(layoutEditParams);
        layout.addView(showTime);
        timer = new Timer();
        timer.schedule(new RemindTask(I, H), 0, // initial delay
                1000);
        startTime = System.nanoTime();
        I.putExtra("Action", "Right");
        BitmapDrawable b = (BitmapDrawable) layout.getBackground();
        b.setAlpha(155);
        layout.setBackground(b);
        S1.layout = layout;

    }

    @Override
    public Intent loopBack(Context c, Intent I, HeadPhone H) {
        if (H != null) {
            System.out.println("Beeb released");
            H.release();
        }
        int totalTries = I.getIntExtra("Tries", 0);
        totalTries++;
        H = new HeadPhone(c);
        String Path = Environment.getExternalStorageDirectory().toString()
                + "/xGame/Games/Catch The Beeb/Sound/beep.mp3";
        System.out.println(Path);
        Random r = new Random();
        int num = r.nextInt(3);
        String swipeAction = "NOT SET";
        if (num == 0) {
            swipeAction = "Right";
            System.out.println(swipeAction);
            H.setLeftLevel(0);
            H.setRightLevel(1);
            H.play(Path, 0);

        } else if (num == 1) {
            swipeAction = "Left";
            System.out.println(swipeAction);
            H.setLeftLevel(1);
            H.setRightLevel(0);
            H.play(Path, 0);
        } else if (num == 2) {
            swipeAction = "Both";
            System.out.println(swipeAction);
            H.setLeftLevel(1);
            H.setRightLevel(1);
            H.play(Path, 0);
        }
        //pre2 = H;
        I.putExtra("Action", swipeAction);
        I.putExtra("Tries", totalTries);
        return I;
    }

    @Override
    public void onStateExit(Context c, Intent I, HeadPhone H) {
        layout.removeView(showTime);
        //H.stopCurrentPlay();
        H.release();
        timer.cancel();
        int Score = I.getIntExtra("Score", 0);
        Score += 5;
        I.putExtra("Score", Score);

    }

    class RemindTask extends TimerTask {
        Intent I;
        HeadPhone H;

        public RemindTask(Intent i, HeadPhone h) {
            I = i;
            H = h;
        }

        public void run() {

            final int gameTime = I.getIntExtra("GameTime", 0);
            time++;
            if (time >= gameTime)
                I.putExtra("Count", 20);
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Drawable background = showTime.getBackground();
                    if (background instanceof ShapeDrawable) {
                        ((ShapeDrawable) background).getPaint().setColor(
                                Color.GRAY);
                        showTime.setTextColor(Color.GREEN);

                    } else if (background instanceof GradientDrawable) {
                        ((GradientDrawable) background).setColor(0x99FF0000);
                    }

                    showTime.setText(Integer.toString(time));
                    I.putExtra("timeInSecond", time);
                    if (time % 15 == 0 && time < gameTime) {
                        int remainingTime = gameTime - time;
                        Toast.makeText(context,
                                "You have:" + remainingTime + " Seconds left ",
                                Toast.LENGTH_LONG).show();
                    }

                }
            });
            //pre = H;
        }

    }

}
