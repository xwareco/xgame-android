package uencom.xgame.engine.views;


import java.util.Locale;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import uencom.xgame.engine.xGameParser;
import uencom.xgame.gestures.HandGestures;
import uencom.xgame.xgame.R;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameView extends SherlockActivity {

	HandGestures HG;
	ImageView gameImg, play, home;
	TextView gameName;
	AccessibilityManager manager;
	LinearLayout main;
	Typeface english;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.game_view);
		english = Typeface.createFromAsset(getAssets(),
				"fonts/DJB Stinky Marker.ttf");
		Locale current = getResources().getConfiguration().locale;
		play = (ImageView) findViewById(R.id.img);
		home = (ImageView) findViewById(R.id.img2);
		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent I = new Intent(getApplicationContext(),
						xGameParser.class);
				I.putExtra("Folder", getIntent().getStringExtra("Folder"));
				I.putExtra("gamename", getIntent().getStringExtra("Name"));
				startActivity(I);

			}
		});
		home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent I = new Intent(getApplicationContext(),
						SplashActivity.class);
				startActivity(I);
			}
		});
		String x = getIntent().getStringExtra("Folder");
		String y = getIntent().getStringExtra("Name");
		String z = getIntent().getStringExtra("Logo");
		System.out.println(x + " " + y + " " + z);
		gameImg = (ImageView) findViewById(R.id.imageView1);

		gameName = (TextView) findViewById(R.id.textView1);
		if (current.getDisplayLanguage().equals("English")) {
			gameName.setTypeface(english);
		}
		gameName.setText(getIntent().getStringExtra("Name"));
		gameImg.setImageURI(Uri.parse(Environment
				.getExternalStorageDirectory().toString()
				+ "/xGame/Games/"
				+ getIntent().getStringExtra("Name") + "/Images/logo.png"));
		/*
		 * Thread t = new Thread(new Runnable() {
		 * 
		 * @Override public void run() { try { InputStream is = new
		 * URL(getIntent().getStringExtra("Logo")) .openStream(); final Drawable
		 * logo = Drawable.createFromStream(is, "src"); if (logo != null) {
		 * runOnUiThread(new Runnable() {
		 * 
		 * @Override public void run() { gameImg.getLayoutParams().height = 256;
		 * gameImg.getLayoutParams().width = 256;
		 * gameImg.setImageDrawable(logo);
		 * 
		 * } });
		 * 
		 * } } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } finally { final Animation a =
		 * AnimationUtils.loadAnimation( getApplicationContext(),
		 * R.anim.fadein); a.setDuration(1000); runOnUiThread(new Runnable() {
		 * 
		 * @Override public void run() { gameImg.startAnimation(a);
		 * 
		 * } });
		 * 
		 * } } }); t.start();
		 */
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
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

		play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent I = new Intent(getApplicationContext(),
						xGameParser.class);
				I.putExtra("Folder", getIntent().getStringExtra("Folder"));
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

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
}
