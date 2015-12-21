package xware.xgame.engine;



import uencom.xgame.xgame.R;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import android.app.Application;

public class xGame extends Application {
	
	private Tracker mTracker;
	
	synchronized public Tracker getDefaultTracker() {
	    if (mTracker == null) {
	      GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
	      // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
	      mTracker = analytics.newTracker(R.xml.global_tracker);
	    }
	    return mTracker;
	  }

}
