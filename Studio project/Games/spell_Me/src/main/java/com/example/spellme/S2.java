package com.example.spellme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Environment;
import android.speech.SpeechRecognizer;
import android.support.annotation.Keep;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

@Keep
public class S2 implements IstateActions {
    HeadPhone pre = null;
    static Timer timer;
    OvalShape timeoOv;
    ShapeDrawable timeBg;
    public static int time = 0;
    static TextView showTime;
    GridLayout letters;
    static TextView speechWord;
    static char[] myWord;
    static LinearLayout layout;
    static String workingWord;
    static String word;
    static ScrollView scroll;
    static LinearLayout layout2;
    static LinearLayout layout3;
    static HeadPhone Hc;
    public SpeechRecognizer sr;
    static char[] arr;
    StringBuilder str = new StringBuilder();
    TextView[] btn;
    TextView[] btn2;
    static boolean flag;
    static int successNum = 1;
    public Context context;

    @Override
    public void onStateEntry(LinearLayout layout, Intent I, Context c,
                             HeadPhone H) {
        // TODO Auto-generated method stub
        TextView tv = (TextView) layout.getChildAt(0);
        tv.setText(I.getStringExtra("word"));
        context = c;
        Hc = H;
        Boolean toastOrNot = I.getBooleanExtra("toastOrNot", false);
        if (toastOrNot == true) {
            Toast.makeText(context, "The game has started", Toast.LENGTH_SHORT)
                    .show();
            toastOrNot = false;
            I.putExtra("toastOrNot", toastOrNot);
        }
        time = I.getIntExtra("timeInSecond", 0);
        String Path = Environment.getExternalStorageDirectory().toString()
                + "/xGame/Games/Spell me/Sound/start.mp3";
        // score sound
        Hc = new HeadPhone(context);
        Hc.setLeftLevel(1);
        Hc.setRightLevel(1);
        Hc.play(Path, 0);
        I.putExtra("Action", "Right");
        BitmapDrawable b = (BitmapDrawable) layout.getBackground();
        b.setAlpha(155);
        layout.setBackground(b);
        S2.layout = layout;
        layout3 = new LinearLayout(c);
        LinearLayout.LayoutParams layoutParent = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParent.topMargin = 5;

        layout3.setWeightSum(1.0F);
        LinearLayout.LayoutParams showTimeParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        showTimeParams.gravity = Gravity.END;
        showTimeParams.setMarginEnd(3);
        showTimeParams.setMarginStart(2);
        showTimeParams.weight = 0.6F;
        RoundRectShape rect = new RoundRectShape(new float[]{30, 30, 30, 30,
                30, 30, 30, 30}, null, null);
        timeBg = new ShapeDrawable(rect);

        timeBg.getPaint().setColor(0x99463E3F);
        showTimeParams.topMargin = 5;
        showTime = new TextView(c);
        showTime.setTextSize(22);
        showTime.setGravity(Gravity.CENTER);
        showTime.setTextColor(Color.WHITE);
        showTime.setBackground(timeBg);

        showTime.setLayoutParams(showTimeParams);

        timer = new Timer();
        timer.schedule(new RemindTask(I), 0, // initial delay
                1 * 1000);
        RoundRectShape ov = new RoundRectShape(new float[]{30, 30, 30, 30,
                30, 30, 30, 30}, null, null);

        ShapeDrawable bgedit = new ShapeDrawable(ov);
        bgedit.getPaint().setColor(0x99FF9900);
        bgedit.setPadding(5, 5, 5, 5);

        LinearLayout.LayoutParams layoutEditParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        layoutEditParams.gravity = Gravity.START;
        layoutEditParams.weight = 0.4F;
        layoutEditParams.topMargin = 5;
        layoutEditParams.setMarginEnd(2);
        layoutEditParams.setMarginStart(3);
        speechWord = new TextView(c);
        speechWord.setBackground(bgedit);
        speechWord.setGravity(Gravity.CENTER);
        String working = I.getStringExtra("workingWord");
        char[] temparr = new char[working.length() * 2];
        int j = 0;
        for (int i = 1; i < (working.length() * 2); i += 2) {
            temparr[(i - 1)] = working.charAt(j);
            temparr[i] = '-';
            Log.d("string_show", temparr[i - 1] + " " + temparr[i]);

            j++;
        }
        temparr[(temparr.length) - 1] = ' ';
        working = new String(temparr);
        speechWord.setTextSize(38);
        speechWord.setText(working);
        speechWord.setTextColor(Color.BLUE);
        speechWord.setLayoutParams(layoutEditParams);
        layout3.addView(speechWord);
        layout3.addView(showTime);
        layout3.setLayoutParams(layoutParent);
        layout.addView(layout3);

        createUI(S2.layout, I, c);

    }

    @Override
    public Intent loopBack(final Context c, final Intent I, HeadPhone H) {

        // TODO Auto-generated method stub

        return I;

    }

    @Override
    public void onStateExit(Context c, Intent I, HeadPhone H) {
        // TODO Auto-generated method stub

        layout.removeView(layout3);
        layout.removeView(speechWord);
        layout.removeView(scroll);
        Hc.stopCurrentPlay();
        timer.cancel();
        // Hc.release();

    }

    public void createUI(LinearLayout layout, final Intent I, final Context c) {
        layout2 = new LinearLayout(c);
        scroll = new ScrollView(context);

        scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams layoutCenterParent = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        letters = new GridLayout(c);
        GridLayout.LayoutParams lettersParams = new GridLayout.LayoutParams();
        lettersParams.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        // lettersParams.columnSpec = GridLayout.spec(0); lettersParams.rowSpec
        // = GridLayout.spec(0);
        //

        letters.setColumnCount(5);

        letters.setOrientation(GridLayout.HORIZONTAL);
        letters.setLayoutParams(lettersParams);

        // ==================================================================

        OvalShape ov = new OvalShape();

        ShapeDrawable bg = new ShapeDrawable(ov);
        bg.getPaint().setColor(0x99463E3F);

        ShapeDrawable bg2 = new ShapeDrawable(ov);
        bg2.getPaint().setColor(Color.LTGRAY);
        StateListDrawable second = new StateListDrawable();
        second.addState(new int[]{android.R.attr.state_pressed}, bg2);
        second.addState(new int[]{android.R.attr.state_enabled}, bg);
        StateListDrawable third = new StateListDrawable();
        third.addState(new int[]{android.R.attr.state_pressed}, bg2);
        third.addState(new int[]{android.R.attr.state_enabled}, bg);
        StateListDrawable forth = new StateListDrawable();
        forth.addState(new int[]{android.R.attr.state_pressed}, bg2);
        forth.addState(new int[]{android.R.attr.state_enabled}, bg);
        String alphabet = "qwertyuiopasdfghjklzxcvbnm";
        btn = new TextView[26];
        System.out.println(btn.length);

        for (int step = 0; step < 25; step++) {

            btn[step] = new TextView(c);

            StateListDrawable first = new StateListDrawable();
            first.addState(new int[]{android.R.attr.state_pressed}, bg2);
            first.addState(new int[]{android.R.attr.state_enabled}, bg);
            btn[step].setBackground(first);
            btn[step].setGravity(Gravity.CENTER);
            btn[step].setTextColor(Color.WHITE);
            btn[step].setTextSize(15);
            Resources r = context.getResources();
            float w = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    55, r.getDisplayMetrics());
            float h = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    55, r.getDisplayMetrics());
            btn[step].setWidth((int) w);
            btn[step].setHeight((int) h);
            btn[step].setText(alphabet.charAt(step) + "");// workingString.charAt(step));
            letters.addView(btn[step]);
            btn[step].setOnClickListener(handleOnClick(btn[step], step, I));
        }
        btn[25] = new TextView(c);

        StateListDrawable first = new StateListDrawable();
        first.addState(new int[]{android.R.attr.state_pressed}, bg2);
        first.addState(new int[]{android.R.attr.state_enabled}, bg);
        btn[25].setBackground(first);
        btn[25].setText(alphabet.charAt(25) + "");
        btn[25].setTextSize(25);
        Resources res = context.getResources();
        float w = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55,
                res.getDisplayMetrics());
        float h = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55,
                res.getDisplayMetrics());
        btn[25].setWidth((int) w);
        btn[25].setHeight((int) h);
        btn[25].setGravity(Gravity.CENTER);
        btn[25].setTextColor(Color.WHITE);
        btn[25].setTextSize(15);
        Spec row4 = GridLayout.spec(6, 3);
        Spec colspan2 = GridLayout.spec(2, 1);
        letters.addView(btn[25], new GridLayout.LayoutParams(row4, colspan2));
        btn[25].setOnClickListener(handleOnClick(btn[25], 25, I));

        layout2.addView(letters);
        layout2.setLayoutParams(layoutCenterParent);
        layout2.setHorizontalGravity(Gravity.CENTER);
        layout2.setVerticalGravity(Gravity.BOTTOM);

        scroll.addView(layout2);
        layout.addView(scroll);
    }

    View.OnClickListener handleOnClick(final TextView button, final int index,
                                       final Intent I) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                String Path = Environment.getExternalStorageDirectory()
                        .toString() + "/xGame/Games/Spell me/Sound/Button.mp3";
                // score sound
                HeadPhone HP = new HeadPhone(context);
                HP.setLeftLevel(1);
                HP.setRightLevel(1);
                HP.play(Path, 0);

                workingWord = I.getStringExtra("workingWord");
                word = I.getStringExtra("word");
                myWord = I.getStringExtra("workingWord").toCharArray();
                for (int m = 0; m < workingWord.length(); m++) {
                    if (((workingWord.charAt(m) + "").equals("$"))) {
                        if ((btn[index].getText().charAt(0) + "")
                                .equalsIgnoreCase((word).charAt(m) + "")) {
                            myWord[m] = btn[index].getText().charAt(0);
                            workingWord = new String(myWord);
                            String showedWord = new String(myWord);
                            char[] temparr = new char[showedWord.length() * 2];
                            int j = 0;
                            for (int i = 1; i < (showedWord.length() * 2); i += 2) {
                                temparr[(i - 1)] = showedWord.charAt(j);
                                temparr[i] = '-';
                                Log.d("string_show", temparr[i - 1] + " "
                                        + temparr[i]);

                                j++;
                            }
                            temparr[(temparr.length) - 1] = ' ';
                            showedWord = new String(temparr);
                            speechWord.setText(showedWord);

                            I.putExtra("workingWord", workingWord);
                            LinearLayout toastLayout = new LinearLayout(context);
                            LinearLayout.LayoutParams layoutToastParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            toastLayout.setLayoutParams(layoutToastParams);
                            toastLayout.setBackgroundColor(Color.DKGRAY);
                            ImageButton image = new ImageButton(context);
                            LinearLayout.LayoutParams toastchield = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            // toastchield.rightMargin = 8;
                            Drawable d = Drawable.createFromPath(Environment
                                    .getExternalStorageDirectory().toString()
                                    + "/xGame/Games/"
                                    + "Spell me"
                                    + "/Images/ok.png");
                            image.setImageDrawable(d);
                            image.setContentDescription("Correct");
                            image.setLayoutParams(toastchield);
                            toastLayout.addView(image);
                            Toast toast = new Toast(context);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(toastLayout);
                            toast.show();

                            break;
                        } else {

                            I.putExtra("timeInSecond", time);
                            I.putExtra("Action", "NONE");
                            I.putExtra("State", "S4");
                            break;
                        }
                    }
                }

                if (!(workingWord.contains("$"))) {
                    if (workingWord.equals(word)) {
                        I.putExtra("timeInSecond", time);

                        I.putExtra("Action", "NONE");
                        I.putExtra("State", "S3");
                    } else {
                        I.putExtra("Action", "NONE");
                        I.putExtra("State", "S2");
                    }
                }

            }
        };
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    class RemindTask extends TimerTask {

        Intent I;

        public RemindTask(Intent i) {
            I = i;
        }

        public void run() {
            /*
			 * if (pre != null) { pre.release(); } Hc = new HeadPhone(context);
			 * String Path =
			 * Environment.getExternalStorageDirectory().toString() +
			 * "/xGame/Games/الهجاء/Sound/timer.mp3"; Hc.setLeftLevel(1);
			 * Hc.setRightLevel(1); Hc.play(Path, 0);
			 */
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

                    if (time % 60 == 0 && time < gameTime) {
                        int remainingTime = (gameTime / 60) - (time / 60);
                        Toast.makeText(context,
                                 remainingTime + " Minutes left",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
            pre = Hc;
        }

    }
}
