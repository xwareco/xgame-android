package com.example.wordmaster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Environment;
import android.support.annotation.Keep;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

@Keep
public class S1 implements IstateActions {
    String[] words1 = new String[]{"cut","cup","die","end","fan","gas","gap","has","job","ask","aim"
            ,"bag","dry","get"

    };
    String[] words2 = new String[]{"word","role","love","love","sale","order","loss","work"
            ,"image","rush","tree","were","drug","earn","farm","moon"
    };
    String[] words3 = new String[]{"school","house","interest","message","egypt","positive",
            "output","advice","penalty","plates","freedom","growth","profit","battle","shout"};
    String[] words4 = new String[]{"opinion","mistake","peace","justice",
            "deeply","invoice","margin","rubbed","depth","limit","shaking","doll","poetry",
            "payment","shallow",
            "scared"};
    String[] words5 = new String[]{"possibly","offer",
            "practical","donkey","policeman",
            "exchange","security","increase","market",
            "greeting","shake","product","world","option",


    };
    public static long startTime;
    static Timer timer;
    OvalShape timeoOv;
    ShapeDrawable timeBg;
    public static int time = 0;
    static TextView showTime;
    public Context context;
    static HeadPhone Hc;
    static int stringIndex;
    static String word;
    static char letter;
    static LinearLayout layout;
    static String workingString;
    static LinearLayout layout2;
    static LinearLayout layout3;
    HeadPhone pre;
    static char[] arr;
    StringBuilder str = new StringBuilder();
    TextView[] btn;
    TextView[] btn2;
    static boolean flag;
    static int successNum = 1;
    Collator myArabicCollator;

    @Override
    public void onStateEntry(LinearLayout layout, Intent I, Context C,
                             HeadPhone H) {

        Boolean toastOrNot = I.getBooleanExtra("toastOrNot", false);
        if (toastOrNot == true) {
            Toast.makeText(C, "The game has started", Toast.LENGTH_SHORT).show();
            toastOrNot = false;
            I.putExtra("toastOrNot", toastOrNot);
        }

        context = C;
        Hc = H;
        String Path = Environment.getExternalStorageDirectory()
                .toString()
                + "/xGame/Games/The Word Master/Sound/start.mp3";
        // score sound
        Hc = new HeadPhone(context);
        Hc.setLeftLevel(1);
        Hc.setRightLevel(1);
        Hc.play(Path, 0);
        time = I.getIntExtra("timeInSecond", 0);
        myArabicCollator = Collator.getInstance(new Locale("en"));
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
        pre = null;
        timer = new Timer();
        timer.schedule(new RemindTask(I), 0, // initial delay
                1 * 1000);

        I.putExtra("Action", "Right");
        context = C;
        BitmapDrawable b = (BitmapDrawable) layout.getBackground();
        b.setAlpha(189);
        layout.setBackground(b);
        S1.layout = layout;
        createUI(S1.layout, I, C);

    }

    @Override
    public Intent loopBack(Context c, Intent I, HeadPhone H) {
        // TODO Auto-generated method stub
        return I;
    }

    @Override
    public void onStateExit(Context c, Intent I, HeadPhone H) {
        // TODO Auto-generated method stub
        I.putExtra("timeInSecond", time);
        layout.removeView(showTime);
        layout.removeView(layout2);
        layout.removeView(layout3);
        //Hc.stopCurrentPlay();
        timer.cancel();

    }

    public void createUI(LinearLayout layout, final Intent I, final Context c) {
        String[] words;

        int level = I.getIntExtra("Level", 0);
        switch (level) {
            case 0: {
                words = words1;
            }

            break;
            case 1: {
                words = words1;
            }

            break;
            case 2: {
                words = words2;
            }

            break;
            case 3: {
                words = words2;
            }

            break;
            case 4: {
                words = words3;
            }

            break;
            case 5: {
                words = words3;
            }

            break;
            case 6: {
                words = words4;
            }

            break;
            case 7: {
                words = words4;
            }

            break;
            case 8: {
                words = words5;
            }

            break;
            case 9: {
                words = words5;
            }

            break;
            case 10: {
                words = words5;
            }

            break;

            default: {
                words = words1;
            }
            break;
        }

        Random random = new Random();
        stringIndex = random.nextInt(words.length);
        word = words[stringIndex];
        workingString = S1.shuffle(word);
        Log.d("MyString", workingString);
        I.putExtra("word", word);

        // /////////////
        layout2 = new LinearLayout(c);
        layout3 = new LinearLayout(c);
        LinearLayout.LayoutParams layoutCenterParent = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutCenterParent.topMargin = 120;

        LinearLayout.LayoutParams layoutCenterParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        layoutCenterParams.setMargins(5, 5, 5, 5);

        RoundRectShape rect = new RoundRectShape(new float[]{30, 30, 30, 30,
                30, 30, 30, 30}, null, null);

        ShapeDrawable bg = new ShapeDrawable(rect);
        bg.getPaint().setColor(0x990099FF);
        ShapeDrawable bg2 = new ShapeDrawable(rect);
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

        btn = new TextView[workingString.length()];
        btn2 = new TextView[workingString.length()];
        for (int i = 0; i < btn2.length; i++) {
            btn2[i] = new TextView(c);
            btn2[i].setLayoutParams(layoutCenterParams);
            btn2[i].setGravity(Gravity.CENTER);
            btn2[i].setTextColor(Color.WHITE);
            btn2[i].setHeight(120);
            btn2[i].setTextSize(30);
            StateListDrawable first = new StateListDrawable();
            first.addState(new int[]{android.R.attr.state_pressed}, bg2);
            first.addState(new int[]{android.R.attr.state_enabled}, bg);
            if (i == 3)
                btn2[i].setBackground(third);
            else
                btn2[i].setBackground(first);

            btn2[i].setText("-");
            layout3.addView(btn2[i]);

        }
        for (int step = 0; step < btn.length; step++) {
            btn[step] = new TextView(c);
            btn[step].setLayoutParams(layoutCenterParams);
            btn[step].setGravity(Gravity.CENTER);
            btn[step].setTextColor(Color.WHITE);
            btn[step].setHeight(120);
            btn[step].setTextSize(30);
            StateListDrawable first = new StateListDrawable();
            first.addState(new int[]{android.R.attr.state_pressed}, bg2);
            first.addState(new int[]{android.R.attr.state_enabled}, bg);
            if (step == 3)
                btn[step].setBackground(second);
            else
                btn[step].setBackground(first);
            btn[step].setText(workingString.charAt(step) + "");// workingString.charAt(step));
            layout2.addView(btn[step]);

            btn[step].setOnClickListener(handleOnClick(btn[step], step, I));

        }

        layout2.setLayoutParams(layoutCenterParent);
        layout2.setHorizontalGravity(Gravity.CENTER);
        // layout2.setVerticalGravity(Gravity.CENTER);
        layout.addView(layout2);

        layout3.setLayoutParams(layoutCenterParent);
        layout3.setHorizontalGravity(Gravity.CENTER);
        // layout3.setVerticalGravity(Gravity.CENTER);
        layout.addView(layout3);
    }

    View.OnClickListener handleOnClick(final TextView button, final int index,
                                       final Intent I) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                String Path = Environment.getExternalStorageDirectory()
                        .toString()
                        + "/xGame/Games/The Word Master/Sound/Button2.mp3";
                // score sound
                Hc = new HeadPhone(context);
                Hc.setLeftLevel(1);
                Hc.setRightLevel(1);
                Hc.play(Path, 0);
                for (int m = 0; m < workingString.length(); m++) {
                    if (btn2[m].getText().toString().equals("-")) {
                        btn2[m].setText(btn[index].getText().toString());

                        if (myArabicCollator.compare(btn2[m].getText()
                                .toString(), word.substring(m, m+1)) == 0) {
                            LinearLayout toastLayout = new LinearLayout(context);
                            btn[index].setText("");
                            btn[index].setVisibility(View.INVISIBLE);
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
                                    + "The Word Master"
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
                        } else {
                            I.putExtra("Action", "NONE");
                            I.putExtra("State", "S2");
                        }
                        str.append(btn2[m].getText().toString());

                        if (m == (workingString.length()-1)) {
                            if (str.toString().equals(word)) {
                                I.putExtra("Action", "NONE");
                                I.putExtra("State", "S3");
                            } else {
                                I.putExtra("Action", "NONE");
                                I.putExtra("State", "S2");
                            }
                        }
                        break;
                    }

                }
            }
        };

    }

    public static String shuffle(String input) {
        List<Character> characters = new ArrayList<Character>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while (characters.size() != 0) {
            int randPicker = (int) (Math.random() * characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }

    class RemindTask extends TimerTask {
        Intent I;

        public RemindTask(Intent i) {
            I = i;
        }

        public void run() {
            /*if (Hc != null) {
				Hc.release();
			}
			Hc = new HeadPhone(context);
			String Path = Environment.getExternalStorageDirectory().toString()
					+ "/xGame/Games/مكون الكلمة/Sound/timer.mp3";
			Hc.setLeftLevel(1);
			Hc.setRightLevel(1);
			Hc.play(Path, 0);*/
            time++;
            final int gameTime = I.getIntExtra("GameTime", 0);
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

        }

    }
}