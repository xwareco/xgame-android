package com.example.catch_the_beep;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import xware.xgame.interfaces.ItransitionActions;

public class T14 implements ItransitionActions {

	@Override
	public boolean isConditionActivated(Intent I) {
		if(I.getStringExtra("Action") == "Both")return true;
		MediaPlayer mp = new MediaPlayer();
		try {
			mp.setDataSource(Environment.getExternalStorageDirectory()
					+ "/xGame/Games/Catch The Beeb/Sound/wrong.mp3");
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
