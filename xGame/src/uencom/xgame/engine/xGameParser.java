package uencom.xgame.engine;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import uencom.xgame.interfaces.IStateListener;
import uencom.xgame.sensors.Accelerometer;
import uencom.xgame.sound.HeadPhone;
import uencom.xgame.xgame.R;
import uencom.xgame.xml.State;
import uencom.xgame.xml.StateEvent;
import uencom.xgame.xml.Transition;
import uencom.xgame.xml.XmlParser;
import uencom.xgame.engine.views.GameOver;
import uencom.xgame.engine.views.RankGetterSplash;
import uencom.xgame.gestures.HandGestures;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
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
	HeadPhone gameMediaPlayer;
	int gameScore, counterTime;
	Timer loopTimer;
	TimerTask loopTimerTask;
	Handler loopHandler;
	XmlParser parser;
	Intent engineIntent;
	String name, gameID;
	SharedPreferences appPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.xmldemo);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		appPrefs = PreferenceManager
				.getDefaultSharedPreferences(xGameParser.this);
		name = getIntent().getStringExtra("gamename");
		gameID = getIntent().getStringExtra("gameid");
		System.out.println("Parser: " + name);
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
							gameIntent.putExtra("Count",
									gameIntent.getIntExtra("Count", 0));
							gameIntent.putExtra("Score",
									gameIntent.getIntExtra("Score", 0));
							gameIntent = currentState.loopBack(
									xGameParser.this, gameIntent,
									gameMediaPlayer);
							System.out.println(gameIntent.getIntExtra("Count",
									0));
							System.out.println(gameIntent.getIntExtra("Score",
									0));
							if (gameIntent.getIntExtra("Count", 0) >= GAME_COUNT) {
								gameOver = true;
							}
							if (gameIntent.getStringExtra("Action") == "NONE") {
								String backTo = gameIntent
										.getStringExtra("State");
								runOnUiThread(new Runnable() {
									public void run() {

										currentState.onStateExit(
												xGameParser.this, gameIntent,
												gameMediaPlayer);
									}
								});
								currentState = getStartingState(backTo);
								runOnUiThread(new Runnable() {
									public void run() {
										currentState.onStateEntry(gameLayout,
												gameIntent, xGameParser.this,
												gameMediaPlayer);
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

							if (appPrefs.getString("uName", "").equals("")) {
								int score = gameIntent.getIntExtra("Score", 0);
								Intent gameOverActivity = new Intent(
										xGameParser.this, GameOver.class);
								gameOverActivity.putExtra("Score", score);
								gameOverActivity.putExtra("Folder", getIntent()
										.getStringExtra("Folder"));
								gameOverActivity.putExtra("gamename", name);
								startActivity(gameOverActivity);
								finish();
								overridePendingTransition(R.anim.transition10,
										R.anim.transition9);
							}

							else {
								int score = gameIntent.getIntExtra("Score", 0);
								Intent rankSplash = new Intent(
										xGameParser.this,
										RankGetterSplash.class);
								rankSplash.putExtra("Score", score);
								rankSplash.putExtra("Folder", getIntent()
										.getStringExtra("Folder"));
								rankSplash.putExtra("gamename", name);
								rankSplash.putExtra("gameid", gameID);
								startActivity(rankSplash);
								finish();
								overridePendingTransition(R.anim.transition5,
										R.anim.transition4);
							}

						}
					}
				});
			}
		};
	}

	private void startTheGame() {
		// Get tracker.
		Tracker t = ((xGame) this.getApplication()).getDefaultTracker();
		// Build and send an Event.
		t.send(new HitBuilders.EventBuilder()
		    .setCategory(name)
		    .setAction("Played")
		    .setLabel("start")
		    .build());
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
			Toast.makeText(xGameParser.this,
					"Error..The game cannot be parsed", Toast.LENGTH_LONG)
					.show();
		else {
			stateGesture = currentState.setHandGesture(stateGesture);
			gameMediaPlayer = new HeadPhone(xGameParser.this);
			gameIntent = new Intent();
			gameIntent.putExtra("Count", counterTime);
			definetheTask();
			loopTimer.schedule(loopTimerTask, 0, 3000);
			currentState.onStateEntry(gameLayout, gameIntent, xGameParser.this,
					gameMediaPlayer);
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
		System.out.println("Parser2: " + engineIntent.getStringExtra("Folder"));
		parser = new XmlParser(xGameParser.this, gameFolder, gameLayout);
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
		currentState.onStateExit(xGameParser.this, gameIntent, gameMediaPlayer);
		gameMediaPlayer.release();
		if (stateAccelerometer != null)
			stateAccelerometer.onAccelerometerPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		currentState.onStateEntry(gameLayout, gameIntent, xGameParser.this,
				gameMediaPlayer);
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		Editor ed = appPrefs.edit();
		int count = appPrefs.getInt("cnt", 0);
		count++;
		if (count == 1) {
			Toast.makeText(xGameParser.this,
					"Press Back again to exit the game", Toast.LENGTH_LONG)
					.show();
			ed.putInt("cnt", count);
			ed.commit();
		} else if (count == 2) {
			//currentState.onStateExit(xGameParser.this, gameIntent,
				//	gameMediaPlayer);
			

			count = 0;
			ed.putInt("cnt", count);
			ed.commit();
			finish();
			overridePendingTransition(R.anim.transition8, R.anim.transition7);
		}

		// super.onBackPressed();
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

							currentState.onStateExit(xGameParser.this,
									gameIntent, gameMediaPlayer);

						}
					});
					currentState = stateFromTransition(trans.get(i));
					runOnUiThread(new Runnable() {
						public void run() {
							currentState.onStateEntry(gameLayout, gameIntent,
									xGameParser.this, gameMediaPlayer);

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

							currentState.onStateExit(xGameParser.this,
									gameIntent, gameMediaPlayer);

						}
					});
					currentState = stateFromTransition(trans.get(i));
					runOnUiThread(new Runnable() {
						public void run() {

							currentState.onStateEntry(gameLayout, gameIntent,
									xGameParser.this, gameMediaPlayer);

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
