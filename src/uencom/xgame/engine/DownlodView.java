package uencom.xgame.engine;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;

import uencom.xgame.web.Installer;
import uencom.xgame.xgame.R;
import android.os.Bundle;
import android.os.Environment;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DownlodView extends SherlockActivity {
	TextView gamename , status;
    LinearLayout lay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.download_view);
		gamename = (TextView)findViewById(R.id.textView1);
		status = (TextView)findViewById(R.id.textView2);
		lay = (LinearLayout)findViewById(R.id.layout);
		Animation a = AnimationUtils.loadAnimation(this, R.anim.transition5);
		lay.startAnimation(a);
		gamename.setText(getIntent().getStringExtra("gamename"));
		String unzipLocation = Environment
				.getExternalStorageDirectory().toString()
				+ "/xGame/Games/";
		new Installer(this, unzipLocation).execute("");
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPause() {

		finish();
		super.onPause();
	}
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		
		MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.actionbar, menu);
	    return true;
	}
}
