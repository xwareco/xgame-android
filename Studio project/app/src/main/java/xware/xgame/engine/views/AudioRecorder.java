package xware.xgame.engine.views;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import xware.xgame.engine.web.User;
import xware.xgame.xgame.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AudioRecorder extends Activity {

    ImageView play, stop, record, close;
    Button send, delete;
    TextView Status;
    MediaRecorder xGameAudioRecorder;
    String outputFile;
    Typeface arabic, english;
    SharedPreferences appSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.recordaudio);
        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(AudioRecorder.this);
        stop = (ImageView) findViewById(R.id.imageView1);
        record = (ImageView) findViewById(R.id.imageView2);
        play = (ImageView) findViewById(R.id.imageView3);
        close = (ImageView) findViewById(R.id.imageView5);
        play.setEnabled(false);
        stop.setEnabled(false);
        record.setEnabled(true);
        arabic = Typeface.createFromAsset(getAssets(),
                "fonts/Kharabeesh Font.ttf");
        english = Typeface.createFromAsset(getAssets(),
                "fonts/klavika-regular-opentype.otf");

        outputFile = Environment.getExternalStorageDirectory()
                + "/xGame/feedback/";
        File f = new File(outputFile);
        f.mkdirs();
        outputFile += "Audio_File.aac";

        send = (Button) findViewById(R.id.button1);
        delete = (Button) findViewById(R.id.button2);
        send.setEnabled(false);
        delete.setEnabled(false);

        Status = (TextView) findViewById(R.id.textView1);
        String lang = appSharedPrefs.getString("Lang", "EN");
        if (lang.equals("AR")) {
            Status.setTypeface(arabic);
        } else if (lang.equals("EN")) {
            Status.setTypeface(english);
        }

        xGameAudioRecorder = new MediaRecorder();
        xGameAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        xGameAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        xGameAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        xGameAudioRecorder.setOutputFile(outputFile);
        prepareOnClicks();
        super.onCreate(savedInstanceState);
    }

    private void prepareOnClicks() {
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    xGameAudioRecorder.prepare();
                    xGameAudioRecorder.start();
                    stop.setEnabled(true);
                    stop.setImageResource(R.drawable.pauseenabled);
                    send.setEnabled(false);
                    delete.setEnabled(false);
                    String lang = appSharedPrefs.getString("Lang", "EN");
                    if (lang.equals("AR")) {
                        Status.setText("جاري التسجيل..");
                    } else if (lang.equals("EN")) {
                        Status.setText("Recording..");
                    }
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                xGameAudioRecorder.stop();
                xGameAudioRecorder.release();
                xGameAudioRecorder = null;
                record.setEnabled(false);
                record.setImageResource(R.drawable.recording);
                stop.setEnabled(false);
                stop.setImageResource(R.drawable.pause);
                play.setEnabled(true);
                play.setImageResource(R.drawable.playing);
                send.setEnabled(true);
                delete.setEnabled(true);
                String lang = appSharedPrefs.getString("Lang", "EN");

                if (lang.equals("AR")) {
                    Status.setText("مستعد للإرسال");
                } else if (lang.equals("EN")) {
                    Status.setText("Ready to send");
                }

            }
        });

        play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MediaPlayer m = new MediaPlayer();
                send.setEnabled(false);
                delete.setEnabled(false);
                record.setEnabled(false);
                record.setImageResource(R.drawable.recording);
                try {
                    m.setDataSource(outputFile);
                    m.prepare();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                m.start();
                m.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        send.setEnabled(true);
                        delete.setEnabled(true);
                        record.setEnabled(true);
                        record.setImageResource(R.drawable.record);
                        // Status.setText("Ready..");
                        String lang = appSharedPrefs.getString("Lang","EN");
                        if (lang.equals("AR")) {
                            Status.setText("مستعد للإرسال");
                        } else if (lang.equals("EN")) {
                            Status.setText("Ready to send");
                        }

                    }
                });
                // Status.setText("Playing Audio File..");
                String lang = appSharedPrefs.getString("Lang","EN");
                if (lang.equals("AR")) {
                    Status.setText("جاري تشغيل الملف..");
                } else if (lang.equals("EN")) {
                    Status.setText("Playing Audio File..");
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // send & delete the file
                SharedPreferences appSharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getApplicationContext());
                String id = appSharedPrefs.getString("uID", "");

                try {
                    FileInputStream fstrm = new FileInputStream(outputFile);
                    new User(getApplicationContext(), null, null, id, null,
                            null, fstrm, null).execute("contact");
                    String textToToast = null;
                    String lang = appSharedPrefs.getString("Lang","EN");
                    if (lang.equals("AR")) {
                        textToToast = "شكرا ﻻتصالك بنا..يتم الان ارسال ملف الصوت الخاص بك..رأيك مهم لنا";
                    } else if (lang.equals("EN")) {
                        textToToast = "Thank you for contacting us, Your audio file is being uploaded to our server,  We will get back to you shortly";
                    }
                    Toast.makeText(
                            getApplicationContext(),
                            textToToast,
                            Toast.LENGTH_LONG).show();
                    finish();

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                File recAudioFile = new File(outputFile);
                if (recAudioFile.exists()) {
                    recAudioFile.delete();
                    // Status.setText("File deleted..");
                    String lang = appSharedPrefs.getString("Lang","EN");
                    if (lang.equals("AR")) {
                        Status.setText("تم مسح الملف");
                    } else if (lang.equals("EN")) {
                        Status.setText("File Deleted");
                    }
                    send.setEnabled(false);
                    delete.setEnabled(false);
                    record.setEnabled(true);
                    record.setImageResource(R.drawable.record);
                    stop.setEnabled(false);
                    stop.setImageResource(R.drawable.pause);
                    play.setEnabled(false);
                    play.setImageResource(R.drawable.playrecfile);
                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }
}
