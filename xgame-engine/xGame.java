package xware.xgame.engine;



import xware.xgame.xgame.R;
import android.app.Application;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

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
