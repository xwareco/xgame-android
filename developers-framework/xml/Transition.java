package xware.xgame.xml;

import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import xware.xgame.interfaces.ItransitionActions;

public class Transition implements ItransitionActions {
	private String id;
	private String from;
	private String to;
	private String apkFileName;
	private Context ctx;
	private String className;
	private String path;
	private DexClassLoader classLoader;
	private Class<?> transitionClass;
	
	public Transition(String apk)
	{
		apkFileName = apk;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public boolean isConditionActivated(Intent I) {
		try {
			Object myObject = transitionClass.getConstructor().newInstance();
			Method Cond = myObject.getClass().getMethod("isConditionActivated",
					new Class[] { Intent.class });
			boolean res = (Boolean) Cond.invoke(myObject, I);
			if (res == false)
				return false;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	@SuppressLint("NewApi")
	public void setClassLoader(String Path) {
		path = Path + "/Source/" + apkFileName;
		classLoader = new DexClassLoader(path,
				ctx.getDir("temp", 1).toString(), null, getClass()
						.getClassLoader());
		try {
			transitionClass = classLoader.loadClass(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
