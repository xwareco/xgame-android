package xware.engine_lib.interfaces;

import android.support.annotation.Keep;

import xware.engine_lib.xml.StateEvent;
@Keep
public interface StateListener {

	public void transRecieved(StateEvent event);

}
