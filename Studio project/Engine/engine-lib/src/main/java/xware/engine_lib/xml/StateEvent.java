package xware.engine_lib.xml;

import android.support.annotation.Keep;

import java.util.EventObject;
@Keep
public class StateEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String transDetected;

	public StateEvent(Object source, String t) {
		super(source);
		transDetected = t;
	}

	public String getTransDetected() {
		return transDetected;
	}

}
