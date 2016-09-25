package com.example.eatthecheesearabic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Keep;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

import xware.engine_lib.interfaces.IstateActions;
import xware.engine_lib.sound.HeadPhone;

@Keep
public class S0 implements IstateActions {
    String[] words = new String[]{"مصر", "سلام", "تحية", "اهتزاز", "منتج", "عالم", "خيار",
            "مدرسة", "منزل", "اهتمام", "رسالة",
            "صورة", "نمو", "ربح", "عمل", "معركة", "صراخ", "فجوة", "مطاط", "عمق", "حدود", "كرسي", "دمية", "قصيدة",
            "أمر", "دفع", "سرعة", "ظل", "حب", "بيع",
            "محاولة", "خائف", "حمار", "شرطي", "أمريكا", "ايجابي", "سلبي", "ضروري",
            "تبادل", "أمن", "زيادة", "سوق", "رأي", "خطأ", "هدف",
            "مخرجات", "نصيحة", "جزاء", "تصريح", "عدالة", "مناقشة", "راضي", "تعليم",
            "صناعة", "عميق", "ترقية", "إيصال", "إختيار", "أطباق", "حرية", "فيل", "إمكانية", "عرض",
            "عملي", "مضمون",
            "مخازن", "معرفة", "خسارة"};
    static int wordnum = 0;
    static String word;
    static String workingWord;

    @Override
    public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H) {
        // TODO Auto-generated method stub
        TextView tv = (TextView) layout.getChildAt(0);
        tv.setText("ساعد الفأر في الحصول على الجبنة من دون ملاحظة القطة, حاول تخمين الكلمات التي ستظهر ثم قم بنطق أحد حروفها حتى تكتمل, انتبه, 7 محاوﻻت خاطئة وستنتهي اللعبة, للخروج اضغط زر العودة,لبدء اللعبة اسحب لليسار");

        wordnum = I.getIntExtra("Random", 0);
        if (wordnum == 0) {
            Random random = new Random();
            wordnum = random.nextInt(words.length);
        }
        wordnum++;
        if (wordnum == words.length)
            wordnum = wordnum % words.length;

        I.putExtra("Random", wordnum);
        word = words[wordnum];
        char[] chars = new char[word.length()];
        char[] tempChars = word.toCharArray();
        Arrays.fill(chars, '$');
        Random random = new Random();
        int l = random.nextInt(chars.length);
        chars[l] = word.charAt(l);
        workingWord = new String(chars);

        I.putExtra("word", word);
        I.putExtra("workingWord", workingWord);
        I.putExtra("letterPosition", 0);
    }


    @Override
    public Intent loopBack(Context c, Intent I, HeadPhone H) {
        // TTS for how to play

        return I;
    }

    @Override
    public void onStateExit(Context c, Intent I, HeadPhone H) {

    }


}
