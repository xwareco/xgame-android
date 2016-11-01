package com.example.tennis;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.Keep;

import xware.engine_lib.interfaces.ItransitionActions;
@Keep
public class T11L implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		// out sound
		MediaPlayer mp = new MediaPlayer();
		try {
			mp.setDataSource(Environment.getExternalStorageDirectory()
					+ "/xGame/Games/Shoot it/Sound/error.mp3");
			mp.prepare();
			mp.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// penalty conditions
		int Score = I.getIntExtra("Score", 0);
		Score -= 5;
		if (Score < 0) {
			Score = 0;
		}
		I.putExtra("Score", Score);
		return false;
		
	}

}
