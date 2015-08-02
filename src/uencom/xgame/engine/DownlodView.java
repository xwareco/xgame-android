package uencom.xgame.engine;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import uencom.xgame.xgame.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class DownlodView extends SherlockActivity {
	LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.download_view);
		layout = (LinearLayout) findViewById(R.id.layout);
		layout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent I = new Intent(getApplicationContext(), GameView.class);
				startActivity(I);
			}
		});
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
