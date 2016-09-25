package com.example.spellmearabic;

import java.util.Arrays;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Keep;
import android.util.Log;
import android.widget.LinearLayout;

import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

@Keep
public class S1 implements IstateActions {
    String[] words1 = new String[]{
            "مصر", "سلام", "تحية", "اهتزاز", "منتج", "عالم", "اختيار",
            "مدرسه", "منزل", "مصلحه", "رِساله",
            "صوره", "نمو", "ارباح", "عمل", "معركة", "صراخ", "امر", "دفع", "تعجل", "حب", "تخفيض",
            "محاوله", "خائف", "خساره", "عرض"

    };
    String[] words2 = new String[]{"مسافه", "مطاط", "عمق", "حدود", "عروسه", "قصيده",
            "حمار", "ضابط", "ايجابي",
            "تبادل", "امن", "زياده", "سوق", "جائز", "خاطئ", "هدف",
            "ناتج", "نصيحه", "جزاء"
    };

    String[] words3 = new String[]{"تصريح", "عداله", "راضي", "تعليم",
            "صناعه", "ترتيب", "عميق", "ترقيه", "اختبار", "اطباق", "حريه", "فيل",
            "عملي", "ضمان", "ظل",
            "مخزن", "معرفه"};

    int wordnum = 0;


    @Override
    public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        I.putExtra("Action", "Right");
        BitmapDrawable b = (BitmapDrawable) layout.getBackground();
        b.setAlpha(100);
        layout.setBackground(b);
        String[] words;
        int level = I.getIntExtra("level", 0);
        Log.d("level", level + "");
        switch (level) {
            case 0: {
                words = words1;
            }
            break;
            case 1: {
                words = words2;
            }
            break;
            case 2: {
                words = words3;
            }
            break;
            default: {
                words = words3;
            }
            break;
        }


        Random rand = new Random();
        wordnum = rand.nextInt(words.length);
        String word = words[wordnum];
        char[] chars = new char[word.length()];
        Arrays.fill(chars, '$');
        String workingWord = new String(chars);

        I.putExtra("word", word);
        I.putExtra("workingWord", workingWord);


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
