package uencom.xgame.engine;

import uencom.xgame.xgame.R;
import android.app.Activity;
import android.os.Bundle;

public class SplashActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.splash_activity);
		new Server(this).execute();
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

}
