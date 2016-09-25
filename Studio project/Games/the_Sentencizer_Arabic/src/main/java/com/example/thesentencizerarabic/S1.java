package com.example.thesentencizerarabic;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

@Keep
public class S1 implements IstateActions {
    String[] words = new String[]{"كَمْ تَبْلُغْ مِنَ العُمْر", "ما هُوَ لَونُكَ المُفَضَل ؟",
            "قُمْ باستِذكار دروسك أولا", "إعْمَلْ بِجِدٍ لِكَى تَنْجَحْ",
            "قُمْ بالذِهاب إلى مَدْرَسَتِكَ مُبَكِراً", "قُمْ بِتَرْتيب غُرفَتَك يومياً",
            "مِن فَضْلِكَ إغلِق الباب", "متى سوف تنتهى مِن واجبك ؟",
            "تَحلى بالصفات الحميده دائماً", "إستَثمِر وقتكَ في الأشياء المفيده",
            "صاحب الأخيار وابتعد عن الأشرار", "عامِل والديكَ مُعَاملةً حسنه",
            "عليكَ بِتَعَلُمْ اللُغة الانجليزيه"};
    public static long startTime;
    static Timer timer;
    GridLayout letters;
    OvalShape timeoOv;
    ShapeDrawable timeBg;
    public static int time = 0;
    static TextView showTime;
    static ScrollView scroll;
    static List<String> sentenceWords;
    static LinearLayout layout;
    static String workingWord;
    static String word;
    static LinearLayout layout2;
    public SpeechRecognizer sr;
    static char[] arr;
    Collator myArabicCollator;
    static String[] wordsArray;
    StringBuilder str = new StringBuilder();
    TextView[] btn;
    HeadPhone pre = null;
    static boolean flag;
    static int successNum = 1;
    public Context context;
    static HeadPhone Hc;

    @Override
    public void onStateEntry(LinearLayout layout, Intent I, Context c,
                             HeadPhone H) {
        Boolean toastOrNot = I.getBooleanExtra("toastOrNot", false);
        if (toastOrNot == true) {
            Toast.makeText(c, "لقد بدأت اللعبة", Toast.LENGTH_SHORT).show();
            toastOrNot = false;
            I.putExtra("toastOrNot", toastOrNot);
        }
        context = c;
        time = I.getIntExtra("timeInSecond", 0);
        Hc = H;
        String Path = Environment.getExternalStorageDirectory()
                .toString()
                + "/xGame/Games/تكوين الجمل/Sound/start.mp3";
        // score sound
        Hc = new HeadPhone(context);
        Hc.setLeftLevel(1);
        Hc.setRightLevel(1);
        Hc.play(Path, 0);
        myArabicCollator = Collator.getInstance(new Locale("ar"));
        LinearLayout.LayoutParams layoutEditParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutEditParams.gravity = Gravity.CENTER_HORIZONTAL;
        RoundRectShape rect = new RoundRectShape(new float[]{30, 30, 30, 30,
                30, 30, 30, 30}, null, null);
        timeBg = new ShapeDrawable(rect);

        timeBg.getPaint().setColor(0x99463E3F);
        layoutEditParams.topMargin = 4;
        showTime = new TextView(c);
        showTime.setTextSize(25);
        showTime.setTextColor(Color.WHITE);
        showTime.setBackground(timeBg);

        showTime.setLayoutParams(layoutEditParams);
        layout.addView(showTime);
        timer = new Timer();
        timer.schedule(new RemindTask(I), 0, // initial delay
                1 * 1000);

        I.putExtra("Action", "Right");
        BitmapDrawable b = (BitmapDrawable) layout.getBackground();
        b.setAlpha(155);
        layout.setBackground(b);
        S1.layout = layout;

        int random = I.getIntExtra("Random", 0);
        if (random == 0) {
            Random rand = new Random();
            random = rand.nextInt(words.length);
        }
        random++;
        if (random == words.length)
            random = random % words.length;

        I.putExtra("Random", random);

        wordsArray = new String[4];
        wordsArray = words[random].split(" ", 4);
        System.out.println("my array legnth" + wordsArray.length);
        sentenceWords = shuffle(wordsArray);

        createUI(S1.layout, I, c);

    }

    @Override
    public Intent loopBack(final Context c, final Intent I, HeadPhone H) {

        // TODO Auto-generated method stub

        return I;

    }

    @Override
    public void onStateExit(Context c, Intent I, HeadPhone H) {
        // TODO Auto-generated method stub
        I.putExtra("timeInSecond", time);
        layout.removeView(layout2);
        layout.removeView(showTime);
        layout.removeView(scroll);
        Hc.stopCurrentPlay();
        timer.cancel();

    }

    public void createUI(LinearLayout layout, final Intent I, final Context c) {

        layout2 = new LinearLayout(c);
        scroll = new ScrollView(context);

        scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        LinearLayout.LayoutParams layoutCenterParent = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        layoutCenterParent.topMargin = 10;
        layoutCenterParent.leftMargin = 5;
        letters = new GridLayout(c);
        letters.setHorizontalScrollBarEnabled(true);
        GridLayout.LayoutParams lettersParams = new GridLayout.LayoutParams();
        letters.setUseDefaultMargins(true);
        lettersParams.setMargins(0, 3, 3, 0);
        lettersParams.topMargin = 10;
        lettersParams.rightMargin = 5;

        // lettersParams.columnSpec = GridLayout.spec(0); lettersParams.rowSpec
        // = GridLayout.spec(0);
        //

        letters.setColumnCount(2);
        letters.setRowCount(4);

        letters.setOrientation(GridLayout.HORIZONTAL);
        letters.setLayoutParams(lettersParams);

        // ==================================================================

        OvalShape rect = new OvalShape();

        ShapeDrawable bg = new ShapeDrawable(rect);
        bg.getPaint().setColor(0x99728C00);
        ShapeDrawable bg2 = new ShapeDrawable(rect);
        bg2.getPaint().setColor(Color.LTGRAY);
        bg.getPaint().setColor(0x99254117);
        bg2.getPaint().setColor(Color.LTGRAY);

        btn = new TextView[8];
        System.out.println(sentenceWords.get(0) + "" + sentenceWords.get(1));
        for (int step = 0, i = 0; step < 8; step++) {

            btn[step] = new TextView(c);

            StateListDrawable first = new StateListDrawable();
            first.addState(new int[]{android.R.attr.state_pressed}, bg2);
            first.addState(new int[]{android.R.attr.state_enabled}, bg);
            btn[step].setBackground(first);
            btn[step].setWidth((getScreenWidth() / 2) - 15);
            btn[step].setHeight((getScreenWidth() / 4) - 10);
            btn[step].setTextColor(Color.WHITE);
            btn[step].setGravity(Gravity.CENTER);
            btn[step].setTextSize(22);
            if (step % 2 == 0) {
                btn[step].setText(sentenceWords.get(i));
                i++;
                btn[step].setOnClickListener(handleOnClick(btn[step], step, I));
            }

            letters.addView(btn[step]);

        }

        layout2.addView(letters);
        layout2.setLayoutParams(layoutCenterParent);

        scroll.addView(layout2);
        scroll.setLayoutParams(layoutCenterParent);
        layout.addView(scroll);

    }

    View.OnClickListener handleOnClick(final TextView button, final int index,
                                       final Intent I) {
        return new View.OnClickListener() {
            public void onClick(View v) {
                LinearLayout errorToast = new LinearLayout(context);
                LinearLayout.LayoutParams layoutToastParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                errorToast.setLayoutParams(layoutToastParams);
                errorToast.setBackgroundColor(Color.DKGRAY);
                ImageButton errorImage = new ImageButton(context);
                LinearLayout.LayoutParams toastchield = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                // toastchield.rightMargin = 8;
                Drawable er = Drawable
                        .createFromPath(Environment
                                .getExternalStorageDirectory().toString()
                                + "/xGame/Games/"
                                + "تكوين الجمل"
                                + "/Images/error.png");
                errorImage.setImageDrawable(er);
                errorImage.setContentDescription("أَخْطَئْت");
                errorImage.setLayoutParams(toastchield);
                errorToast.addView(errorImage);
                Toast errorToasting = new Toast(context);
                errorToasting.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                errorToasting.setDuration(Toast.LENGTH_SHORT);
                errorToasting.setView(errorToast);
                // ========================================================
                LinearLayout toastLayout = new LinearLayout(context);
                toastLayout.setLayoutParams(layoutToastParams);
                toastLayout.setBackgroundColor(Color.DKGRAY);
                ImageButton image = new ImageButton(context);
                Drawable d = Drawable.createFromPath(Environment
                        .getExternalStorageDirectory().toString()
                        + "/xGame/Games/" + "تكوين الجمل" + "/Images/ok.png");
                image.setImageDrawable(d);
                image.setContentDescription("اختيار صحيح");
                image.setLayoutParams(toastchield);
                toastLayout.addView(image);
                Toast toast = new Toast(context);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(toastLayout);
                HeadPhone HP = null;
                for (int x = 0; x < 1; x++) {
                    if (HP != null) {
                        HP.release();
                    }
                    if (!(btn[index].getText().toString().equals(""))) {

                        if (btn[1].getText().toString().equals("")) {
                            if (myArabicCollator.compare(btn[index].getText()
                                    .toString(), wordsArray[0]) == 0) {
                                toast.show();
                                btn[1].setText(btn[index].getText().toString());
                                btn[index].setText("");
                                String Path = Environment
                                        .getExternalStorageDirectory()
                                        .toString()
                                        + "/xGame/Games/تكوين الجمل/Sound/Button_best.mp3";
                                // score sound
                                HP = new HeadPhone(context);
                                HP.setLeftLevel(1);
                                HP.setRightLevel(1);
                                HP.play(Path, 0);
                                break;
                            } else {
                                String Path = Environment
                                        .getExternalStorageDirectory()
                                        .toString()
                                        + "/xGame/Games/تكوين الجمل/Sound/Button.mp3";
                                // score sound
                                HP = new HeadPhone(context);
                                HP.setLeftLevel(1);
                                HP.setRightLevel(1);
                                HP.play(Path, 0);
                                errorToasting.show();
                                int fail = I.getIntExtra("fail", 0);
                                fail++;
                                I.putExtra("fail", fail);
                                if (fail == 2) {
                                    I.putExtra("Action", "NONE");
                                    I.putExtra("State", "S2");
                                }
                                break;
                            }
                        }
                        if (btn[3].getText().toString().equals("")) {
                            if (myArabicCollator.compare(btn[index].getText()
                                    .toString(), wordsArray[1]) == 0) {
                                toast.show();
                                btn[3].setText(btn[index].getText().toString());
                                btn[index].setText("");
                                String Path = Environment
                                        .getExternalStorageDirectory()
                                        .toString()
                                        + "/xGame/Games/تكوين الجمل/Sound/Button_best.mp3";
                                // score sound
                                HP = new HeadPhone(context);
                                HP.setLeftLevel(1);
                                HP.setRightLevel(1);
                                HP.play(Path, 0);
                                break;
                            } else {
                                String Path = Environment
                                        .getExternalStorageDirectory()
                                        .toString()
                                        + "/xGame/Games/تكوين الجمل/Sound/Button.mp3";
                                // score sound
                                HP = new HeadPhone(context);
                                HP.setLeftLevel(1);
                                HP.setRightLevel(1);
                                HP.play(Path, 0);
                                errorToasting.show();
                                int fail = I.getIntExtra("fail", 0);
                                fail++;
                                I.putExtra("fail", fail);
                                if (fail == 2) {
                                    I.putExtra("Action", "NONE");
                                    I.putExtra("State", "S2");
                                }
                                break;
                            }
                        }
                        if (btn[5].getText().toString().equals("")) {
                            if (myArabicCollator.compare(btn[index].getText()
                                    .toString(), wordsArray[2]) == 0) {
                                toast.show();
                                btn[5].setText(btn[index].getText().toString());
                                btn[index].setText("");
                                String Path = Environment
                                        .getExternalStorageDirectory()
                                        .toString()
                                        + "/xGame/Games/تكوين الجمل/Sound/Button_best.mp3";
                                // score sound
                                HP = new HeadPhone(context);
                                HP.setLeftLevel(1);
                                HP.setRightLevel(1);
                                HP.play(Path, 0);
                                break;
                            } else {
                                String Path = Environment
                                        .getExternalStorageDirectory()
                                        .toString()
                                        + "/xGame/Games/تكوين الجمل/Sound/Button.mp3";
                                // score sound
                                HP = new HeadPhone(context);
                                HP.setLeftLevel(1);
                                HP.setRightLevel(1);
                                HP.play(Path, 0);
                                errorToasting.show();
                                int fail = I.getIntExtra("fail", 0);
                                fail++;
                                I.putExtra("fail", fail);
                                if (fail == 2) {
                                    I.putExtra("Action", "NONE");
                                    I.putExtra("State", "S2");
                                }
                                break;
                            }
                        }
                        if (btn[7].getText().toString().equals("")) {
                            if (myArabicCollator.compare(btn[index].getText()
                                    .toString(), wordsArray[3]) == 0) {
                                toast.show();
                                btn[7].setText(btn[index].getText().toString());
                                btn[index].setText("");
                                String Path = Environment
                                        .getExternalStorageDirectory()
                                        .toString()
                                        + "/xGame/Games/تكوين الجمل/Sound/Button_best.mp3";
                                // score sound
                                HP = new HeadPhone(context);
                                HP.setLeftLevel(1);
                                HP.setRightLevel(1);
                                HP.play(Path, 0);
                                I.putExtra("Action", "NONE");
                                I.putExtra("State", "S3");
                                break;
                            } else {
                                String Path = Environment
                                        .getExternalStorageDirectory()
                                        .toString()
                                        + "/xGame/Games/تكوين الجمل/Sound/Button.mp3";
                                // score sound
                                HP = new HeadPhone(context);
                                HP.setLeftLevel(1);
                                HP.setRightLevel(1);
                                HP.play(Path, 0);
                                errorToasting.show();
                                int fail = I.getIntExtra("fail", 0);
                                fail++;
                                I.putExtra("fail", fail);
                                if (fail == 2) {
                                    I.putExtra("Action", "NONE");
                                    I.putExtra("State", "S2");
                                }
                                break;
                            }
                        }

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

    public static List<String> shuffle(String[] input) {
        List<String> strings = new ArrayList<String>();
        List<String> output = new ArrayList<String>();
        for (int i = 0; i < input.length; i++) {
            output.add(input[i]);
        }

        while (output.size() != 0) {
            int randPicker = (int) (Math.random() * output.size());
            strings.add(output.remove(randPicker));

        }
        return strings;
    }

    class RemindTask extends TimerTask {
        Intent I;

        public RemindTask(Intent i) {
            I = i;
        }

        public void run() {
            /*if (pre != null) {
				pre.release();
			}
			Hc = new HeadPhone(context);
			String Path = Environment.getExternalStorageDirectory().toString()
					+ "/xGame/Games/تكوين الجمل/Sound/timer.mp3";
			Hc.setLeftLevel(1);
			Hc.setRightLevel(1);
			Hc.play(Path, 0);*/
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

                    if (time % 15 == 0 && time < gameTime) {
                        int remainingTime = gameTime - time;
                        Toast.makeText(context,
                                " باقي من الزمن" + remainingTime + " ثانية ",
                                Toast.LENGTH_LONG).show();
                    }

                }
            });

        }

    }

}
