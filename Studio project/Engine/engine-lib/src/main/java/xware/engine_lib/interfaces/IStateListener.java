package xware.engine_lib.interfaces;

import android.support.annotation.Keep;

import xware.engine_lib.xml.StateEvent;
@Keep
public interface IStateListener {

	public void transRecieved(StateEvent event);
	public void transRecievedAcc(StateEvent event);

}
