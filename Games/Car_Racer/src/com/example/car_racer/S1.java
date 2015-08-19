package com.example.car_racer;

import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.LinearLayout;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sensors.Accelerometer;
import uencom.xgame.sound.HeadPhone;

public class S1 implements IstateActions {

	@Override
	public void onStateEntry(LinearLayout layout, Intent I) {
		ArrayList<Obstacle> trackObstacles;
		// Generate obstacles
		trackObstacles = generateObstacles(I);
		Gson g = new Gson();
		String obsJson = g.toJson(trackObstacles);
		I.putExtra("obs", obsJson);
		System.out.println(trackObstacles.size() + "Obstacle(s)");
		for (int i = 0; i < trackObstacles.size(); i++) {
			System.out.println("==Obstacle==");
			System.out.println(trackObstacles.get(i).getType() + " " + i
					+ "th obs");
			System.out.println(trackObstacles.get(i).getLocation());
			System.out.println("======================");
		}

	}

	private ArrayList<Obstacle> generateObstacles(Intent I) {

		// define obstacles number
		int currentLocation = I.getIntExtra("Count", 0) + 10;
		int numOfObstacles = (190 - currentLocation) / 4;
		ArrayList<Obstacle> res = new ArrayList<Obstacle>(numOfObstacles);

		// generate based on type and location
		Obstacle lastObs = null;
		for (int i = 0; i < numOfObstacles; i++) {
			// generate obs type
			Obstacle obs = new Obstacle();
			Random r = new Random();
			int tempType = r.nextInt(4);
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
			res.add(obs);
			lastObs = obs;
		}
		return res;
	}

	@SuppressWarnings("unused")
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
		// init Accelerometer
		boolean right = false;
		boolean left = false;
		boolean any = false;
		String obsType = nearestObs.getType();
		if (obsType.equals("turnleft")) {
			// stop current play & play sound
			left = true;
		}
		if (obsType.equals("turnright")) {
			// stop current play & play sound
			right = true;
		}
		if (obsType.equals("cow") || obsType.equals("girl")) {
			// stop current play & play sound
			any = true;
		}

		Accelerometer inStateAcc = initAcc(c, right, left, any);
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

	private Accelerometer initAcc(final Context c, final boolean right,
			final boolean left, final boolean any) {
		Accelerometer Acc = new Accelerometer(c, 0, 75f, 0) {

			@Override
			public void onZHugeRight() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onZHugeLeft() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onZGoodRight() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onZGoodLeft() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onYHugeRight() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onYHugeLeft() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onYGoodRight() {
				if (right == true || any == true)
					new HeadPhone(c).stopCurrentPlay();

			}

			@Override
			public void onYGoodLeft() {
				if (left == true || any == true)
					new HeadPhone(c).stopCurrentPlay();

			}

			@Override
			public void onXHugeRight() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onXHugeLeft() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onXGoodRight() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onXGoodLeft() {
				// TODO Auto-generated method stub

			}
		};
		return Acc;
	}

	private Obstacle getNearestObstacle(Intent I) {
		Obstacle result = null;
		String obsJson = I.getStringExtra("obs");
		Gson g = new Gson();
		java.lang.reflect.Type type = new TypeToken<ArrayList<Obstacle>>() {
		}.getType();
		ArrayList<Obstacle> trackObstacles = g.fromJson(obsJson,
				(java.lang.reflect.Type) type);
		int currentLocation = I.getIntExtra("Count", 0);
		if (trackObstacles != null) {

			for (int i = 0; i < trackObstacles.size(); i++) {
				if (trackObstacles.get(i).getLocation() - currentLocation <= 10
						&& trackObstacles.get(i).getLocation()
								- currentLocation >= 0)
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
