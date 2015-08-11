package uencom.xgame.xml;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import uencom.xgame.interfaces.IStateListener;
import uencom.xgame.interfaces.IstateActions;
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
	@SuppressWarnings("unused")
	private boolean up, down, right, left, tap, doubleTap, longTouch;
	private String transDetected;

	public State(String apk) {
		up = false;
		down = false;
		right = false;
		left = false;
		tap = false;
		doubleTap = false;
		longTouch = false;
		apkFileName = apk;
		listeners = new ArrayList<IStateListener>();
		functionsAndTransitions = new HashMap<String, String>();
		transDetected = "NoTransitionDetected";
		
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

	private Intent Call(String method, LinearLayout lay , Intent I) {
		try {
			if (method.equals("Exit")) {
				Object myObject = stateClass.getConstructor().newInstance();
				Method Exit = myObject.getClass().getMethod("onStateExit",
						new Class[] { Context.class , Intent.class });
				Exit.invoke(myObject, ctx , I);
			}
			else if (method.equals("Entry")) {
				Object myObject = stateClass.getConstructor().newInstance();
				Method Entry = myObject.getClass().getMethod("onStateEntry",
						new Class[] { LinearLayout.class  , Intent.class});
				Entry.invoke(myObject, lay , I);
			}
			else if (method.equals("Loop")) {
				Object myObject = stateClass.getConstructor().newInstance();
				Method Loop = myObject.getClass().getMethod("loopBack",
						new Class[] { Context.class  , Intent.class});
				I = (Intent) Loop.invoke(myObject, ctx , I);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return I;
	}

	@Override
	public void onStateEntry(LinearLayout layout , Intent I) {
		layout.setBackground(Drawable.createFromPath(imgPath));
		gameTextView.setText(text);
		Call("Entry", layout , I);

	}

	@Override
	public Intent loopBack(Context c , Intent I) {
		return Call("Loop", gameLayout , I);

	}

	@Override
	public void onStateExit(Context c , Intent I) {
		Call("Exit", gameLayout , I);
	}

	public HandGestures setHandGesture(HandGestures H) {
		if (functionsAndTransitions.containsKey("SwipeRight"))
			right = true;
		if (functionsAndTransitions.containsKey("SwipeLeft"))
			left = true;
		if (functionsAndTransitions.containsKey("SwipeDown"))
			down = true;
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
		};
		return H;
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

}
