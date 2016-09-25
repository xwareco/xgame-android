package xware.engine_lib.interfaces;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Keep;
import android.widget.LinearLayout;
import xware.engine_lib.sound.HeadPhone;
@Keep
public interface IstateActions {

	public void onStateEntry(LinearLayout layout, Intent I, Context C, HeadPhone H);

	public Intent loopBack(Context c, Intent I, HeadPhone H);

	public void onStateExit(Context c, Intent I, HeadPhone H);

}
