package xware.xgame.interfaces;

import xware.xgame.sound.HeadPhone;
import android.content.Context;
import android.content.Intent;
import android.widget.LinearLayout;

public interface IstateActions {

	public void onStateEntry(LinearLayout layout , Intent I , Context C, HeadPhone H);

	public Intent loopBack(Context c , Intent I, HeadPhone H);

	public void onStateExit(Context c , Intent I, HeadPhone H);

}
