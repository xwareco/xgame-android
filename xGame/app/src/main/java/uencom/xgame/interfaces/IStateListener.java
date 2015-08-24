package uencom.xgame.interfaces;

import uencom.xgame.xml.StateEvent;

public interface IStateListener {

	public void transRecieved(StateEvent event);
	public void transRecievedAcc(StateEvent event);

}
