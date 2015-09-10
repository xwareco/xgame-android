package uencom.xgame.xgame;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

public class Splash extends Activity {
	MediaPlayer introPlayer;
	Intent messagingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.xgamesplash);
		introPlayer = MediaPlayer.create(this, R.raw.getready);
		messagingIntent = new Intent(this, XMLDemo.class);
		messagingIntent.putExtra("Folder", Environment.getExternalStorageDirectory()
				.toString() + "/catch_the_peeb");
		introPlayer.start();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				startActivity(messagingIntent);
				finish();
				overridePendingTransition(R.anim.transition1, R.anim.transition2);
			}
		}, 8500);
		super.onCreate(savedInstanceState);
	}
}
