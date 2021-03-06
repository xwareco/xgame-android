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
	public void onStateEntry(LinearLayout layout, Intent I, Context C,
			HeadPhone H) {
		String Path = Environment.getExternalStorageDirectory().toString()
				+ "/xGame/Games/Car Racer/Sound/car_running.mp3";
		H.setLeftLevel(1);
		H.setRightLevel(1);
		if (H.detectHeadPhones() == true) {
			H.stopCurrentPlay();
			H.play(Path, 1);
		}
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
		int currentLocation = I.getIntExtra("Count", 0) + 2;
		int numOfObstacles = (200 - currentLocation) / 4;
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
				obs.setLocation(currentLocation + r.nextInt(5));
			} else {

				obs.setLocation(lastObs.getLocation() + 5);
			}
			res.add(obs);
			lastObs = obs;
		}
		return res;
	}

	@SuppressWarnings("unused")
	@Override
	public Intent loopBack(Context c, Intent I, HeadPhone H) {
		int startTime = (int) System.currentTimeMillis();
		System.out.println("Iam called!!");
		int currVPos = I.getIntExtra("Count", 0);
		System.out.println("Count: " + currVPos);
		int penaltyTime = 0;

		Obstacle nearestObs = getOrRemoveNearestObstacle(I, 0, 0, H);
		// init Accelerometer
		boolean right = false;
		boolean left = false;
		boolean any = false;
		if (nearestObs != null) {
			System.out.println("Obs is ready!!");
			String obsType = nearestObs.getType();
			if (obsType.equals("turnleft")) {
				System.out.println("left");
				// stop current play & play sound
				String Path2 = Environment.getExternalStorageDirectory()
						.toString() + "/xGame/Games/Car Racer/Sound/turn.mp3";
				HeadPhone HP = new HeadPhone(c);

				if (HP.detectHeadPhones() == true) {
					H.stopCurrentPlay();
					HP.setLeftLevel(1);
					HP.setRightLevel(0);
					HP.play(Path2, 0);
				}
				left = true;
			} else if (obsType.equals("turnright")) {
				System.out.println("right");
				// stop current play & play sound
				String Path2 = Environment.getExternalStorageDirectory()
						.toString() + "/xGame/Games/Car Racer/Sound/turn.mp3";
				HeadPhone HP = new HeadPhone(c);
				if (HP.detectHeadPhones() == true) {
					H.stopCurrentPlay();
					HP.setLeftLevel(0);
					HP.setRightLevel(1);
					HP.play(Path2, 0);
				}
				right = true;
			} else if (obsType.equals("cow")) {
				System.out.println("cow");
				// stop current play & play sound
				String Path2 = Environment.getExternalStorageDirectory()
						.toString() + "/xGame/Games/Car Racer/Sound/cow.mp3";
				HeadPhone HP = new HeadPhone(c);
				if (HP.detectHeadPhones() == true) {
					H.stopCurrentPlay();
					HP.setLeftLevel(1);
					HP.setRightLevel(1);
					HP.play(Path2, 0);
				}
				any = true;
			} else if (obsType.equals("girl")) {
				System.out.println("girl");
				// stop current play & play sound
				String Path2 = Environment.getExternalStorageDirectory()
						.toString() + "/xGame/Games/Car Racer/Sound/girl.mp3";
				HeadPhone HP = new HeadPhone(c);
				if (HP.detectHeadPhones() == true) {
					H.stopCurrentPlay();
					HP.setLeftLevel(1);
					HP.setRightLevel(1);
					HP.play(Path2, 0);
				}
				any = true;
			}
			int index = I.getIntExtra("NearInd", 0);
			Accelerometer inStateAcc = initAcc(c, right, left, any, index, I, H);
			System.out.println("Nearest: " + nearestObs.getLocation() + "cur: "
					+ currVPos);
			System.out.println("R: " + right);
			System.out.println("L: " + left);
			System.out.println("A: " + any);
			if (currVPos == nearestObs.getLocation()
					&& (right == true || left == true || any == true)) {
				if (nearestObs.getType().equals("cow")
						|| nearestObs.getType().equals("girl")) {
					currVPos = 300;
					H.stopCurrentPlay();
					String Path2 = Environment.getExternalStorageDirectory()
							.toString() + "/xGame/Games/Car Racer/Sound/gameover.mp3";
					H.play(Path2, 0);
				} else if (nearestObs.getType().equals("turnleft")
						|| nearestObs.getType().equals("turnright"))
					penaltyTime = 50;
			}

		}
		int scoreSoFar = I.getIntExtra("Score", 0);
		System.out.println("ScoreBefore = " + scoreSoFar);
		int newScoreSoFar;
		int duration;
		currVPos++;
		if(currVPos == 200)
		{
			H.stopCurrentPlay();
			String Path2 = Environment.getExternalStorageDirectory()
					.toString() + "/xGame/Games/Car Racer/Sound/success.mp3";
			H.play(Path2, 0);
		}
		int endTime = (int) System.currentTimeMillis();
		duration = endTime - startTime;
		newScoreSoFar = (scoreSoFar + duration) - penaltyTime;
		System.out.println("Score = " + newScoreSoFar);
		I.putExtra("Action", "");
		I.putExtra("Count", currVPos);
		I.putExtra("Score", newScoreSoFar);
		return I;
	}

	private Accelerometer initAcc(final Context c, final boolean right,
			final boolean left, final boolean any, final int obsInd,
			final Intent I, final HeadPhone H) {
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
				if (right == true || any == true) {
					getOrRemoveNearestObstacle(I, obsInd, 1, H);
					System.out.println("right turn acc");

				}

			}

			@Override
			public void onYGoodLeft() {

				if (left == true) {
					getOrRemoveNearestObstacle(I, obsInd, 1, H);
					System.out.println("left turn acc");

				}

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

	private Obstacle getOrRemoveNearestObstacle(Intent I, int index, int flag,
			HeadPhone H) {
		Obstacle result = null;
		String obsJson = I.getStringExtra("obs");
		Gson g = new Gson();
		java.lang.reflect.Type type = new TypeToken<ArrayList<Obstacle>>() {
		}.getType();
		ArrayList<Obstacle> trackObstacles = g.fromJson(obsJson,
				(java.lang.reflect.Type) type);
		if (flag == 0) {
			int currentLocation = I.getIntExtra("Count", 0);
			if (trackObstacles != null) {

				for (int i = 0; i < trackObstacles.size(); i++) {
					System.out.println("obs new: "
							+ trackObstacles.get(i).getLocation() + "\n");
					if (trackObstacles.get(i).getLocation() - currentLocation <= 3) {
						result = trackObstacles.get(i);
						I.putExtra("NearInd", i);
					}

				}
			}
		} else {
			int currentLocation = I.getIntExtra("Count", 0);
			int diff = trackObstacles.get(index).getLocation()
					- currentLocation;
			if (diff <= 5 && diff > 0) {
				trackObstacles.remove(index);
				String Path = Environment.getExternalStorageDirectory()
						.toString()
						+ "/xGame/Games/Car Racer/Sound/car_running.mp3";
				H.setLeftLevel(1);
				H.setRightLevel(1);
				if (H.detectHeadPhones() == true) {
					H.stopCurrentPlay();
					H.play(Path, 1);
				}
				String newObsJson = g.toJson(trackObstacles);
				I.putExtra("obs", newObsJson);
			}
		}
		return result;
	}

	@Override
	public void onStateExit(Context c, Intent I, HeadPhone H) {
		H.stopCurrentPlay();

	}

}
