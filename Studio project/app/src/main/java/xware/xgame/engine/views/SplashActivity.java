package xware.xgame.engine.views;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

import java.util.Locale;

import xware.xgame.engine.web.Server;
import xware.engine_lib.gestures.HandGestures;
import xware.xgame.xgame.R;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {

    LinearLayout trans, bg;
    TextView loading, connerror;
    ImageView refresh, offline;
    ListView offlineGames;
    Typeface arabic, english;
    boolean offlineClicked;
    HandGestures HG;
    ProgressBar bar;
    SharedPreferences appSharedPrefs;
    Editor prefsEditor;
    Animation fadeIn;
    Boolean isSplashNeededAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(SplashActivity.this);
        prefsEditor = appSharedPrefs.edit();
        AdaptUIToSystemLanguage();
        setContentView(R.layout.splash_activity);
        String lang = appSharedPrefs.getString("Lang", "EN");
        isSplashNeededAgain = true;
        bg = (LinearLayout) findViewById(R.id.mainlay);
        trans = (LinearLayout) findViewById(R.id.seclay);
        connerror = (TextView) findViewById(R.id.textView1);
        loading = (TextView) findViewById(R.id.textView2);
        bar = (ProgressBar) findViewById(R.id.progressBar1);
        offlineGames = (ListView) findViewById(R.id.listView1);
        offline = (ImageView) findViewById(R.id.imageView4);
        refresh = (ImageView) findViewById(R.id.imageView3);
        offlineClicked = false;


        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fadeIn.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation arg0) {

                // load the games
                new Server(SplashActivity.this, refresh, offline, loading,
                        connerror, bar, null, offlineGames).execute("cat");

				/*
                 * ActivityCompat.requestPermissions(SplashActivity.this, new
				 * String[]{Manifest.permission.READ_PHONE_STATE}, 1);
				 */

            }
        });


        if (Build.VERSION.SDK_INT < 23) {
            prefsEditor.putBoolean("per", true);
            prefsEditor.commit();
            isSplashNeededAgain = false;
            bg.setBackgroundResource(R.drawable.bg1);
            trans.setVisibility(View.VISIBLE);
            trans.startAnimation(fadeIn);
            bar.setVisibility(View.VISIBLE);
            loading.setVisibility(View.VISIBLE);
        } else {

            if (ContextCompat.checkSelfPermission(SplashActivity.this,
                    Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(SplashActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        0);
            } else {
                isSplashNeededAgain = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bg.setBackgroundResource(R.drawable.bg1);
                        trans.setVisibility(View.VISIBLE);
                        trans.startAnimation(fadeIn);
                        bar.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.VISIBLE);

                    }
                }, 700);
            }
        }

        arabic = Typeface.createFromAsset(getAssets(),
                "fonts/Kharabeesh Font.ttf");
        english = Typeface.createFromAsset(getAssets(),
                "fonts/klavika-regular-opentype.otf");
        if (lang.equals("AR")) {
            loading.setTypeface(arabic);
            connerror.setTypeface(arabic);
        } else if (lang.equals("EN")) {
            loading.setTypeface(english);
            connerror.setTypeface(english);
        }


        offline.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                offlineGames.setVisibility(View.VISIBLE);
                offline.setVisibility(View.GONE);
                connerror.setText(R.string.offlinestate);
                offlineClicked = true;
                String textToToast = "Swipe down to refresh";
                String lang = appSharedPrefs.getString("Lang", "EN");
                if (lang.equals("AR"))
                    textToToast = "لفحص اﻻتصال باﻻنترنت وتحديث القائمة..اسحب لأسفل";
                Toast.makeText(SplashActivity.this, textToToast,
                        Toast.LENGTH_LONG).show();

            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                bar.setVisibility(View.VISIBLE);
                refresh.setVisibility(View.GONE);
                offlineGames.setVisibility(View.GONE);
                offline.setVisibility(View.GONE);
                connerror.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                if (ContextCompat.checkSelfPermission(SplashActivity.this,
                        Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat
                            .requestPermissions(
                                    SplashActivity.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    0);
                }

            }
        });

        HG = new HandGestures(this) {
            @Override
            public void onSwipeDown() {
                if (offlineClicked == true) {
                    bar.setVisibility(View.VISIBLE);
                    refresh.setVisibility(View.GONE);
                    offlineGames.setVisibility(View.GONE);
                    offline.setVisibility(View.GONE);
                    connerror.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                    new Server(SplashActivity.this, refresh, offline, loading,
                            connerror, bar, null, offlineGames).execute("cat");
                }
                offlineClicked = false;
                super.onSwipeDown();
            }
        };

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
    }

    @Override
    protected void onPause() {
        if (isSplashNeededAgain == false)
            finish();
        super.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        HG.OnTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        HG.OnTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    final TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String devId = tm.getDeviceId();
                    prefsEditor.putString("devID", devId);
                    prefsEditor.commit();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bg.setBackgroundResource(R.drawable.bg1);
                            trans.setVisibility(View.VISIBLE);
                            bar.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.VISIBLE);
                        }
                    }, 700);
                    ;
                    if (ContextCompat.checkSelfPermission(SplashActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat
                                .requestPermissions(
                                        SplashActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        1);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    MediaPlayer mp;
                    mp = MediaPlayer.create(SplashActivity.this, R.raw.failed);
                    mp.start();
                    trans.setVisibility(View.VISIBLE);
                    bar.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    refresh.setVisibility(View.VISIBLE);
                    String toast = "Permission denied cannot load games, press the refresh button to request the permission again";
                    final Locale current = getResources().getConfiguration().locale;
                    String lang = appSharedPrefs.getString("Lang", "EN");
                    if (lang.equals("AR")) {
                        toast = "لم يتم إعطاء الإذن, تعذر عرض الألعاب, إضغط على زر النتشيط لطلب الإذن مرة أخرى";
                    }
                    Toast.makeText(getApplicationContext(), toast,
                            Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    prefsEditor.putBoolean("per", true);
                    prefsEditor.commit();
                    isSplashNeededAgain = false;

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    prefsEditor.putBoolean("per", false);
                    prefsEditor.commit();
                    isSplashNeededAgain = false;
                }
                trans.startAnimation(fadeIn);
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void AdaptUIToSystemLanguage() {
        Boolean firstUse = getIntent().getBooleanExtra("FirstUse", true);
        if (firstUse) {
            if (Locale.getDefault().getDisplayLanguage().equals("English")) {
                prefsEditor.putString("Lang", "EN");
                prefsEditor.commit();
                Locale myLocale = Locale.forLanguageTag("en-US");
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                Intent I;
                I = new Intent(SplashActivity.this, SplashActivity.class);
                I.putExtra("FirstUse", false);
                startActivity(I);
                overridePendingTransition(R.anim.transition5, R.anim.transition4);
                finish();
            } else if (Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                prefsEditor.putString("Lang", "AR");
                prefsEditor.commit();
                Locale myLocale = Locale.forLanguageTag("ar-EG");
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                Intent I;
                I = new Intent(SplashActivity.this, SplashActivity.class);
                I.putExtra("FirstUse", false);
                startActivity(I);
                overridePendingTransition(R.anim.transition5, R.anim.transition4);
                finish();
            }
        }
    }


}
