package uencom.xgame.engine;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import uencom.xgame.gestures.HandGestures;
import uencom.xgame.xgame.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GameView extends SherlockActivity {

	ImageView home, how, play;
	HandGestures HG;
	AccessibilityManager manager;
	LinearLayout main;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.game_view);
		home = (ImageView) findViewById(R.id.imageView2);
		how = (ImageView) findViewById(R.id.imageView3);
		play = (ImageView) findViewById(R.id.imageView4);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		setOnclicks();
		main = (LinearLayout) findViewById(R.id.gameLay);
		HG = new HandGestures(getApplicationContext()) {
			@Override
			public void onSwipeRight() {
				Toast.makeText(getApplicationContext(), "Right Swipe!",
						Toast.LENGTH_LONG).show();
				super.onSwipeRight();
			}
		};
		super.onCreate(savedInstanceState);
	}

	private void setOnclicks() {

		home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent I = new Intent(getApplicationContext(), MainView.class);
				startActivity(I);

			}
		});

		how.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent I = new Intent(getApplicationContext(), MainView.class);
				startActivity(I);

			}
		});

		play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent I = new Intent(getApplicationContext(), GameOver.class);
				startActivity(I);

			}
		});

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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent I = new Intent(this, MainView.class);
			startActivity(I);
			break;
		default:

			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		HG.OnTouchEvent(event);
		// Be sure to call the superclass implementation
		return main.onTouchEvent(event);
	}
}
