package com.example.shootit;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;
import uencom.xgame.interfaces.ItransitionActions;

public class T13 implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		if(I.getStringExtra("Action") == "Left")return true;
		MediaPlayer mp = new MediaPlayer();
		try {
			mp.setDataSource(Environment.getExternalStorageDirectory()
					+ "/xGame/Games/Shoot it!/Sound/error.mp3");
			mp.prepare();
			mp.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return false;
	}

}
