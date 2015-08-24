package uencom.xgame.xgame;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import uencom.xgame.interfaces.IStateListener;
import uencom.xgame.xml.State;
import uencom.xgame.xml.StateEvent;
import uencom.xgame.xml.Transition;
import uencom.xgame.xml.XmlParser;
import uencom.xgame.gestures.HandGestures;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class XMLDemo extends Activity implements IStateListener {

	TextView data;
	LinearLayout gameLayout;
	State s0, s1, currentState;
	Transition t01, t10;
	HandGestures stateGesture, engineGesture;
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
							gameIntent = currentState.loopBack(
									getApplicationContext(), gameIntent);
							if (gameIntent.getIntExtra("Count", 0) == 20) {
								gameOver = true;
								TextView TV = (TextView) gameLayout
										.getChildAt(0);
								TV.setText("Game Over..Your Score is: "
										+ gameIntent.getIntExtra("Score", 0)
										+ "\nTap once to start a new game\nswipe left to quit");
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
								stateGesture = currentState.setHandGesture(stateGesture);
								currentState.setTransDetected("NoTransitionDetected");
							}
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
			loopTimer.schedule(loopTimerTask, 0, 1500);
			currentState.onStateEntry(gameLayout, gameIntent);
		}

	}

	private void initViewAndGameDate() {
		data = (TextView) findViewById(R.id.textView1);
		engineIntent = getIntent();
		gameScore = 0;
		counterTime = 0;
		gameLayout = (LinearLayout) findViewById(R.id.lLayout);
		trans = new ArrayList<Transition>();
		states = new ArrayList<State>();
		gameOver = false;
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
		else
			engineGesture.OnTouchEvent(event);
		return super.onTouchEvent(event);
	}

	public State stateFromTransition(Transition t) {

		for (int i = 0; i < states.size(); i++) {
			if (t.getTo() == states.get(i).getId())
				return states.get(i);
		}
		return null;
	}

	@Override
	protected void onPause() {
		loopTimerTask.cancel();
		loopTimer.cancel();
		super.onPause();
	}

	@Override
	public void transRecieved(StateEvent event) {
		String detectedTransitionId = event.getTransDetected();
		if (!detectedTransitionId.equals("NoTransitionDetected")) {
			for (int i = 0; i < trans.size(); i++) {
				if (trans.get(i).getId() == detectedTransitionId
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
					currentState.setTransDetected("NoTransitionDetected");

				}
			}
		}

	}

	@Override
	public void transRecievedAcc(StateEvent event) {
		// TODO Auto-generated method stub
		
	}
}
