package uencom.xgame.engine;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import uencom.xgame.interfaces.IStateListener;
import uencom.xgame.sensors.Accelerometer;
import uencom.xgame.xgame.R;
import uencom.xgame.xml.State;
import uencom.xgame.xml.StateEvent;
import uencom.xgame.xml.Transition;
import uencom.xgame.xml.XmlParser;
import uencom.xgame.gestures.HandGestures;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnHoverListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class xGameParser extends Activity implements IStateListener {

	TextView data;
	LinearLayout gameLayout;
	State s0, s1, currentState;
	Transition t01, t10;
	HandGestures stateGesture, engineGesture;
	String SCREEN_ORIENTATION;
	int GAME_COUNT;
	Accelerometer stateAccelerometer;
	String apkFileName = "";
	boolean gameOver;
	String gameFolder;
	ArrayList<Transition> trans;
	ArrayList<State> states;
	Intent gameIntent;
	int gameScore, counterTime;
	Timer loopTimer;
	TimerTask loopTimerTask;
	Handler loopHandler;
	XmlParser parser;
	Intent engineIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.xmldemo);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initViewAndGameDate();
		startTheGame();
		super.onCreate(savedInstanceState);
	}

	private void definetheTask() {
		loopTimer = new Timer();
		loopHandler = new Handler();
		loopTimerTask = new TimerTask() {
			@Override
			public void run() {
				loopHandler.post(new Runnable() {
					@Override
					public void run() {
						if (gameOver == false) {
							gameIntent.putExtra("Count", gameIntent.getIntExtra("Count", 0));
							gameIntent.putExtra("Score", gameIntent.getIntExtra("Score", 0));
							gameIntent = currentState.loopBack(
									getApplicationContext(), gameIntent);
							System.out.println(gameIntent.getIntExtra("Count", 0));
							System.out.println(gameIntent.getIntExtra("Score", 0));
							if (gameIntent.getIntExtra("Count", 0) >= GAME_COUNT) {
								gameOver = true;
							}
							if (gameIntent.getStringExtra("Action") == "NONE") {
								String backTo = gameIntent
										.getStringExtra("State");
								runOnUiThread(new Runnable() {
									public void run() {

										currentState.onStateExit(
												getApplicationContext(),
												gameIntent);
									}
								});
								currentState = getStartingState(backTo);
								runOnUiThread(new Runnable() {
									public void run() {
										currentState.onStateEntry(gameLayout,
												gameIntent);
									}
								});
								stateGesture = currentState
										.setHandGesture(stateGesture);
								stateAccelerometer = currentState
										.setAccelerometerGestures(stateAccelerometer);
								currentState
										.setTransDetected("NoTransitionDetected");
							}
						} else {
							int score = gameIntent.getIntExtra("Score", 0);
							Intent gameOverActivity = new Intent(
									getApplicationContext(), GameOver.class);
							gameOverActivity.putExtra("Score", score);
							gameOverActivity.putExtra("gamename", getIntent().getStringExtra("gamename"));
							startActivity(gameOverActivity);
							finish();
						}
					}
				});
			}
		};
	}

	private void startTheGame() {
		parser.parse();
		states = parser.getStatesList();
		trans = parser.getTransitionsList();
		if (parser.environmentVariables.get("ORIENTATION").equals("Landscape")) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			System.out.println("Landscape view");
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			System.out.println("Portrait view");
		}
		GAME_COUNT = Integer.parseInt(parser.environmentVariables
				.get("GAME_COUNT"));
		for (int i = 0; i < states.size(); i++)
			states.get(i).addStateListener(this);

		currentState = getStartingState("S0");
		if (currentState == null)
			Toast.makeText(getApplicationContext(),
					"Error..The game cannot be parsed", Toast.LENGTH_LONG)
					.show();
		else {
			stateGesture = currentState.setHandGesture(stateGesture);
			gameIntent = new Intent();
			gameIntent.putExtra("Count", counterTime);
			definetheTask();
			loopTimer.schedule(loopTimerTask, 0, 3000);
			currentState.onStateEntry(gameLayout, gameIntent);
		}

	}

	@SuppressLint("NewApi")
	private void initViewAndGameDate() {
		data = (TextView) findViewById(R.id.textView1);
		SCREEN_ORIENTATION = "Portrait";
		engineIntent = getIntent();
		gameScore = 0;
		counterTime = 0;
		gameLayout = (LinearLayout) findViewById(R.id.lLayout);
		trans = new ArrayList<Transition>();
		states = new ArrayList<State>();
		gameOver = false;
		gameLayout.setOnHoverListener(new OnHoverListener() {

			@Override
			public boolean onHover(View v, MotionEvent event) {

				return onTouchEvent(event);
			}
		});
		gameFolder = engineIntent.getStringExtra("Folder");

		parser = new XmlParser(getApplicationContext(), gameFolder, gameLayout);
		engineGesture = new HandGestures(this) {
			@Override
			public void onTapOnce() {
				gameOver = false;
				gameIntent = new Intent();
				super.onTapOnce();
			}

			@Override
			public void onSwipeleft() {
				finish();
				super.onSwipeleft();
			}
		};

	}

	private State getStartingState(String id) {
		for (int i = 0; i < states.size(); i++) {
			if (states.get(i).getId().equals(id)) {
				return states.get(i);
			}
		}
		return null;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (gameOver == false)
			stateGesture.OnTouchEvent(event);
		return super.onTouchEvent(event);
	}

	public State stateFromTransition(Transition t) {

		for (int i = 0; i < states.size(); i++) {
			if (t.getTo().equals(states.get(i).getId())) {

				return states.get(i);
			}
		}
		return null;
	}

	@Override
	protected void onPause() {
		loopTimerTask.cancel();
		loopTimer.cancel();
		if (stateAccelerometer != null)
			stateAccelerometer.onAccelerometerPause();
		super.onPause();
	}

	@Override
	public void transRecieved(StateEvent event) {
		String detectedTransitionId = event.getTransDetected();
		if (!detectedTransitionId.equals("NoTransitionDetected")) {

			for (int i = 0; i < trans.size(); i++) {
				if (trans.get(i).getId().equals(detectedTransitionId)
						&& trans.get(i).isConditionActivated(gameIntent) == true) {

					runOnUiThread(new Runnable() {
						public void run() {

							currentState.onStateExit(getApplicationContext(),
									gameIntent);

						}
					});
					currentState = stateFromTransition(trans.get(i));
					runOnUiThread(new Runnable() {
						public void run() {
							currentState.onStateEntry(gameLayout, gameIntent);

						}
					});
					stateGesture = currentState.setHandGesture(stateGesture);
					stateAccelerometer = currentState
							.setAccelerometerGestures(stateAccelerometer);
					currentState.setTransDetected("NoTransitionDetected");
					currentState.setTransDetectedAcc("NoTransitionDetected");

				}
			}
		}

	}

	@Override
	public void transRecievedAcc(StateEvent event) {
		String detectedTransitionId = event.getTransDetected();
		if (!detectedTransitionId.equals("NoTransitionDetected")) {

			for (int i = 0; i < trans.size(); i++) {
				if (trans.get(i).getId().equals(detectedTransitionId)
						&& trans.get(i).isConditionActivated(gameIntent) == true) {

					// currentState.lock();
					runOnUiThread(new Runnable() {
						public void run() {

							currentState.onStateExit(getApplicationContext(),
									gameIntent);

						}
					});
					currentState = stateFromTransition(trans.get(i));
					runOnUiThread(new Runnable() {
						public void run() {

							currentState.onStateEntry(gameLayout, gameIntent);

						}
					});
					stateAccelerometer = currentState
							.setAccelerometerGestures(stateAccelerometer);
					currentState.setTransDetectedAcc("NoTransitionDetected");

				}
			}
		}

	}
}
