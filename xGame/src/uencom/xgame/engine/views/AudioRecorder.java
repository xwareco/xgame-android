package uencom.xgame.engine.views;

import java.io.File;
import java.io.IOException;

import uencom.xgame.xgame.R;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AudioRecorder extends Activity {

	ImageView play, stop, record , close;
	Button send, delete;
	TextView Status;
	MediaRecorder xGameAudioRecorder;
	String outputFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.recordaudio);
		stop = (ImageView) findViewById(R.id.imageView1);
		record = (ImageView) findViewById(R.id.imageView2);
		play = (ImageView) findViewById(R.id.imageView3);
		close = (ImageView) findViewById(R.id.imageView5);
		play.setEnabled(false);
		stop.setEnabled(false);
		record.setEnabled(true);

		outputFile = Environment.getExternalStorageDirectory()
				+ "/xGame/feedback/";
		File f = new File(outputFile);
		f.mkdirs();
		outputFile += "Audio_File.mp4";

		send = (Button) findViewById(R.id.button1);
		delete = (Button) findViewById(R.id.button2);
		send.setEnabled(false);
		delete.setEnabled(false);

		Status = (TextView) findViewById(R.id.textView1);

		xGameAudioRecorder = new MediaRecorder();
		xGameAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		xGameAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		xGameAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.MPEG_4);
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
					Status.setText("Recording..");
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
				record.setImageResource(R.drawable.record);
				stop.setEnabled(false);
				stop.setImageResource(R.drawable.pause);
				play.setEnabled(true);
				play.setImageResource(R.drawable.playing);
				send.setEnabled(true);
				delete.setEnabled(true);
				Status.setText("Ready to send");

			}
		});

		play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				MediaPlayer m = new MediaPlayer();
				send.setEnabled(false);
				delete.setEnabled(false);
				record.setEnabled(false);
				record.setImageResource(R.drawable.record);
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
						send.setEnabled(false);
						delete.setEnabled(false);
						record.setEnabled(false);
						record.setImageResource(R.drawable.recording);
						Status.setText("Ready..");
					}
				});
				Status.setText("Playing Audio File..");
			}
		});

		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// send & delete the file

			}
		});

		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				File recAudioFile = new File(outputFile);
				if (recAudioFile.exists())
				{
					recAudioFile.delete();
					Status.setText("File deleted..");
					send.setEnabled(false);
					delete.setEnabled(false);
					record.setEnabled(true);
					record.setImageResource(R.drawable.recording);
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
