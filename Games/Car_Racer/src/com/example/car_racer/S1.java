package com.example.car_racer;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.LinearLayout;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sensors.Accelerometer;
import uencom.xgame.sound.HeadPhone;

public class S1 implements IstateActions {

	ArrayList<Obstacle> trackObstacles;
	Accelerometer inStateAcc;

	@Override
	public void onStateEntry(LinearLayout layout, Intent I) {
		// Generate obstacles
		generateObstacles(I);
		System.out.println(trackObstacles.size() + "Obstacle(s)");
		for (int i = 0; i < trackObstacles.size(); i++) {
			System.out.println("==Obstacle==");
			System.out.println(trackObstacles.get(i).getType() + " " + i
					+ "th obs");
			System.out.println(trackObstacles.get(i).getLocation());
			System.out.println("======================");
		}
		// init Accelerometer

	}

	private void generateObstacles(Intent I) {

		// define obstacles number
		int currentLocation = I.getIntExtra("Count", 0) + 10;
		int numOfObstacles = (190 - currentLocation) / 4;
		trackObstacles = new ArrayList<Obstacle>(numOfObstacles);

		// generate based on type and location
		Obstacle lastObs = null;
		for (int i = 0; i < numOfObstacles; i++) {
			// generate obs type
			Obstacle obs = new Obstacle();
			Random r = new Random();
			int tempType = r.nextInt(3);
			if (tempType == 0)
				obs.setType("turnleft");
			else if (tempType == 1)
				obs.setType("turnright");
			else if (tempType == 2)
				obs.setType("cow");
			else if (tempType == 3)
				obs.setType("girl");

			// generate obs location
			if (i == 0) {
				obs.setLocation(currentLocation + r.nextInt(11));
			} else {

				obs.setLocation(lastObs.getLocation() + 20);
			}
			trackObstacles.add(obs);
			lastObs = obs;
		}

	}

	@Override
	public Intent loopBack(Context c, Intent I) {
		int startTime = (int) System.currentTimeMillis();
		String Path = Environment.getExternalStorageDirectory().toString()
				+ "/xGame/Games/car_racer/Sound/car_running.wma";
		HeadPhone HP = new HeadPhone(c);
		HP.setLeftLevel(1);
		HP.setRightLevel(1);
		if (HP.detectHeadPhones() == true && HP.isPlaying() == false) {
			HP.stopCurrentPlay();
			HP.play(Path, 0);
		}
		Obstacle nearestObs = getNearestObstacle(I);
		if (nearestObs != null)
			System.out.println("Nesarest obstacle is: "
					+ nearestObs.getLocation() + " &" + nearestObs.getType());
		int currVPos = I.getIntExtra("Count", 0);
		int scoreSoFar = I.getIntExtra("Scroe", 0);
		System.out.println("ScoreBefore = " + scoreSoFar);
		int newScoreSoFar;
		int duration;
		currVPos++;
		int endTime = (int) System.currentTimeMillis();
		duration = endTime - startTime;
		newScoreSoFar = scoreSoFar + duration;
		System.out.println("Score = " + newScoreSoFar);
		I.putExtra("Action", "");
		I.putExtra("Count", currVPos);
		I.putExtra("Score", newScoreSoFar);
		return I;
	}

	private Obstacle getNearestObstacle(Intent I) {
		Obstacle result = null;
		int currentLocation = I.getIntExtra("Count", 0) + 10;
		if (trackObstacles != null) {
			System.out.println("Iam here!!");
			for (int i = 0; i < trackObstacles.size(); i++) {
				if (trackObstacles.get(i).getLocation() - currentLocation == 10)
					result = trackObstacles.get(i);
			}
		}
		return result;
	}

	@Override
	public void onStateExit(Context c, Intent I) {
		// TODO Auto-generated method stub

	}

}
