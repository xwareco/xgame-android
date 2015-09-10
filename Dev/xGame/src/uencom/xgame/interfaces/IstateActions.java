package uencom.xgame.interfaces;

import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

public interface IstateActions {

	public void onStateEntry(LinearLayout layout , Intent I);

	public Intent loopBack(Context c , Intent I);

	public void onStateExit(Context c , Intent I);

}
