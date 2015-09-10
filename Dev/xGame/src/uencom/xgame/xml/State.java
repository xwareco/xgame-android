package uencom.xgame.xml;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import uencom.xgame.interfaces.IStateListener;
import uencom.xgame.interfaces.IstateActions;
import uencom.xgame.sensors.Accelerometer;
import uencom.xgame.gestures.HandGestures;
import dalvik.system.DexClassLoader;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class State implements IstateActions {

	private String id;
	private String describtion;
	private String text;
	private String imgPath;
	private String className;
	private String apkFileName;
	private String path;
	private Context ctx;
	private DexClassLoader classLoader;
	private LinearLayout gameLayout;
	private TextView gameTextView;
	private Class<?> stateClass;
	private Map<String, String> functionsAndTransitions;
	private ArrayList<IStateListener> listeners;
	private boolean up, down, right, left, tap, doubleTap, longTouch, zSimpleR,
			zSimpleL, zHugeR, zHugeL, ySimpleR, ySimpleL, yHugeR, yHugeL,
			xSimpleR, xSimpleL, xHugeR, xHugeL;
	private String transDetected, transDetectedAcc;

	public State(String apk) {
		up = false;
		down = false;
		right = false;
		left = false;
		tap = false;
		doubleTap = false;
		longTouch = false;
		apkFileName = apk;
		zSimpleL = false;
		zSimpleR = false;
		zHugeL = false;
		zHugeR = false;
		ySimpleL = false;
		ySimpleR = false;
		yHugeL = false;
		yHugeR = false;
		xSimpleL = false;
		xSimpleR = false;
		xHugeL = false;
		xHugeR = false;
		listeners = new ArrayList<IStateListener>();
		functionsAndTransitions = new HashMap<String, String>();
		transDetected = "NoTransitionDetected";
		transDetectedAcc = "NoTransitionDetected";

	}

	public Map<String, String> getFunctionsAndTransitions() {
		return functionsAndTransitions;
	}

	public void setTransDetectedAcc(String transDetectedAcc) {
		this.transDetectedAcc = transDetectedAcc;
	}

	public void addToMap(String key, String Value) {
		functionsAndTransitions.put(key, Value);
	}

	public String getFromMap(String key) {
		return functionsAndTransitions.get(key);
	}

	public void setV(LinearLayout lay) {
		gameLayout = lay;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescribtion() {
		return describtion;
	}

	public void setDescribtion(String desc) {
		describtion = desc;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setCtx(Context ct) {
		ctx = ct;
		gameTextView = (TextView) gameLayout.getChildAt(0);
		gameTextView.setGravity(Gravity.CENTER_HORIZONTAL);
		gameTextView.setTextSize(15f);
		gameTextView.setTextColor(Color.BLACK);
		gameTextView.setTypeface(null, Typeface.BOLD_ITALIC);
	}

	public Context getCtx() {
		return ctx;
	}

	public void setClassLoader(String Path) {
		path = Path + "/Source/" + apkFileName;
		classLoader = new DexClassLoader(path,
				ctx.getDir("temp", 1).toString(), null, getClass()
						.getClassLoader());
		try {
			stateClass = classLoader.loadClass(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		path = Path;
	}

	public void setTransDetected(String transDetected) {
		this.transDetected = transDetected;
	}

	public String getTransDetected() {
		return transDetected;
	}

	private Intent Call(String method, LinearLayout lay, Intent I) {
		try {
			if (method.equals("Exit")) {
				Object myObject = stateClass.getConstructor().newInstance();
				Method Exit = myObject.getClass().getMethod("onStateExit",
						new Class[] { Context.class, Intent.class });
				Exit.invoke(myObject, ctx, I);
			} else if (method.equals("Entry")) {
				Object myObject = stateClass.getConstructor().newInstance();
				Method Entry = myObject.getClass().getMethod("onStateEntry",
						new Class[] { LinearLayout.class, Intent.class });
				Entry.invoke(myObject, lay, I);
			} else if (method.equals("Loop")) {
				Object myObject = stateClass.getConstructor().newInstance();
				Method Loop = myObject.getClass().getMethod("loopBack",
						new Class[] { Context.class, Intent.class });
				I = (Intent) Loop.invoke(myObject, ctx, I);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return I;
	}

	@Override
	public void onStateEntry(LinearLayout layout, Intent I) {
		if (!this.id.equals("S0"))
			layout.setBackground(Drawable.createFromPath(imgPath));
		else
			layout.setBackgroundColor(Color.GREEN);
		gameTextView.setTextColor(Color.WHITE);
		gameTextView.setText(text);
		Call("Entry", layout, I);

	}

	@Override
	public Intent loopBack(Context c, Intent I) {
		return Call("Loop", gameLayout, I);

	}

	@Override
	public void onStateExit(Context c, Intent I) {
		Call("Exit", gameLayout, I);
	}

	public HandGestures setHandGesture(HandGestures H) {
		if (functionsAndTransitions.containsKey("SwipeRight"))
			right = true;
		if (functionsAndTransitions.containsKey("SwipeLeft"))
			left = true;
		if (functionsAndTransitions.containsKey("SwipeDown"))
			down = true;
		if (functionsAndTransitions.containsKey("SwipeUp"))
			up = true;
		if (functionsAndTransitions.containsKey("SingleTap"))
			tap = true;
		if (functionsAndTransitions.containsKey("DoubleTap"))
			doubleTap = true;
		if (functionsAndTransitions.containsKey("LongPress"))
			longTouch = true;
		H = new HandGestures(this.ctx) {
			@Override
			public void onSwipeRight() {
				if (right == true)
					transDetected = functionsAndTransitions.get("SwipeRight");
				fireStateEvent();
				super.onSwipeRight();
			}

			@Override
			public void onSwipeleft() {
				if (left == true)
					transDetected = functionsAndTransitions.get("SwipeLeft");
				fireStateEvent();
				super.onSwipeleft();
			}

			@Override
			public void onSwipeDown() {
				if (down == true)
					transDetected = functionsAndTransitions.get("SwipeDown");
				fireStateEvent();
				super.onSwipeDown();
			}

			@Override
			public void onSwipeUp() {
				if (up == true)
					transDetected = functionsAndTransitions.get("SwipeUp");
				fireStateEvent();
				super.onSwipeUp();
			}

			@Override
			public void onTapOnce() {
				if (tap == true)
					transDetected = functionsAndTransitions.get("SingleTap");
				fireStateEvent();
				super.onTapOnce();
			}

			@Override
			public void onTapTwice() {
				if (doubleTap == true)
					transDetected = functionsAndTransitions.get("DoubleTap");
				fireStateEvent();
				super.onTapTwice();
			}

			@Override
			public void onLongTouch() {
				if (longTouch == true)
					transDetected = functionsAndTransitions.get("LongPress");
				fireStateEvent();
				super.onLongTouch();
			}
		};
		return H;
	}

	public Accelerometer setAccelerometerGestures(Accelerometer acc) {
		if (functionsAndTransitions.containsKey("ZGoodRight"))
			zSimpleR = true;
		if (functionsAndTransitions.containsKey("ZGoodLeft"))
			zSimpleL = true;
		if (functionsAndTransitions.containsKey("ZHugeRight"))
			zHugeR = true;
		if (functionsAndTransitions.containsKey("ZHugeLeft"))
			zHugeL = true;
		if (functionsAndTransitions.containsKey("YGoodRight"))
			ySimpleR = true;
		if (functionsAndTransitions.containsKey("YGoodLeft"))
			ySimpleL = true;
		if (functionsAndTransitions.containsKey("YHugeRight"))
			yHugeR = true;
		if (functionsAndTransitions.containsKey("YHugeLeft"))
			yHugeL = true;

		if (functionsAndTransitions.containsKey("XGoodRight"))
			xSimpleR = true;
		if (functionsAndTransitions.containsKey("XGoodLeft"))
			xSimpleL = true;
		if (functionsAndTransitions.containsKey("XHugeRight"))
			xHugeR = true;
		if (functionsAndTransitions.containsKey("XHugeLeft"))
			xHugeL = true;

		acc = new Accelerometer(this.ctx, 0, 75f, 0) {

			@Override
			public void onZHugeRight() {
				if (zHugeR == true)
					transDetectedAcc = functionsAndTransitions
							.get("ZHugeRight");
				fireStateEventAcc();
				// System.out.println("Huge right");
			}

			@Override
			public void onZHugeLeft() {
				if (zHugeL == true)
					transDetectedAcc = functionsAndTransitions.get("ZHugeLeft");
				fireStateEventAcc();
				// System.out.println("Huge left");
			}

			@Override
			public void onZGoodRight() {
				if (zSimpleR == true)
					transDetectedAcc = functionsAndTransitions
							.get("ZSimpleRight");
				fireStateEventAcc();
				// System.out.println("Simple right");
			}

			@Override
			public void onZGoodLeft() {
				if (zSimpleL == true)
					transDetectedAcc = functionsAndTransitions
							.get("ZSimpleLeft");
				fireStateEventAcc();
				// System.out.println("Simple left");
			}

			@Override
			public void onYHugeRight() {
				System.out.println(yHugeR);
				if (yHugeR == true) {
					transDetectedAcc = functionsAndTransitions
							.get("YHugeRight");
				}
				fireStateEventAcc();
				// yHugeR = false;

				// System.out.println("Huge right Y");

			}

			@Override
			public void onYHugeLeft() {
				if (yHugeL == true) {
					transDetectedAcc = functionsAndTransitions.get("YHugeLeft");
					fireStateEventAcc();
					yHugeL = false;
				}
				// System.out.println("Huge left Y");

			}

			@Override
			public void onYGoodRight() {
				if (ySimpleR == true) {
					transDetectedAcc = functionsAndTransitions
							.get("YGoodRight");
					fireStateEventAcc();
					ySimpleR = false;
				}
				// System.out.println("Simple right Y");

			}

			@Override
			public void onYGoodLeft() {
				if (ySimpleL == true) {
					transDetectedAcc = functionsAndTransitions.get("YGoodLeft");
					fireStateEventAcc();
					ySimpleL = false;
				}
				// System.out.println("Simple left Y");

			}

			@Override
			public void onXHugeRight() {
				if (xHugeR == true)
					transDetectedAcc = functionsAndTransitions
							.get("XHugeRight");
				fireStateEventAcc();
				// System.out.println("Huge right X");
			}

			@Override
			public void onXHugeLeft() {
				if (xHugeL == true)
					transDetectedAcc = functionsAndTransitions.get("XHugeLeft");
				fireStateEventAcc();
				// System.out.println("Huge left X");

			}

			@Override
			public void onXGoodRight() {
				if (xSimpleR == true)
					transDetectedAcc = functionsAndTransitions
							.get("XGoodRight");
				fireStateEventAcc();
				// System.out.println("Simple right X");

			}

			@Override
			public void onXGoodLeft() {
				if (xSimpleL == true)
					transDetectedAcc = functionsAndTransitions.get("XGoodLeft");
				fireStateEventAcc();
				// System.out.println("Simple left X");

			}
		};
		return acc;
	}

	public synchronized void addStateListener(IStateListener l) {
		listeners.add(l);
	}

	public synchronized void removeStateListener(IStateListener l) {
		listeners.remove(l);
	}

	private synchronized void fireStateEvent() {
		StateEvent state = new StateEvent(this, transDetected);
		Iterator<?> iter = ((ArrayList<IStateListener>) listeners).iterator();
		while (iter.hasNext()) {
			((IStateListener) iter.next()).transRecieved(state);
		}
	}

	private synchronized void fireStateEventAcc() {
		StateEvent state = new StateEvent(this, transDetectedAcc);
		Iterator<?> iter = ((ArrayList<IStateListener>) listeners).iterator();
		while (iter.hasNext()) {
			((IStateListener) iter.next()).transRecievedAcc(state);
		}
		setTransDetectedAcc("NoTransitionDetected");
	}

}
