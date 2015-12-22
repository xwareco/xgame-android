package xware.xgame.interfaces;

import xware.xgame.xml.StateEvent;

public interface IStateListener {

	public void transRecieved(StateEvent event);
	public void transRecievedAcc(StateEvent event);

}
