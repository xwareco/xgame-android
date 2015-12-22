package com.example.tennis;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import uencom.xgame.interfaces.ItransitionActions;

public class T11R implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		// out sound
					MediaPlayer mp = new MediaPlayer();
					try {
						mp.setDataSource(Environment.getExternalStorageDirectory()
								+ "/xGame/Games/Tennis/Sound/wrong.mp3");
						mp.prepare();
						mp.start();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.print(I.getStringExtra("Action"));
					System.out.print(I.getStringExtra("Action").equals("SingleTap"));
					return false;
	}

}
