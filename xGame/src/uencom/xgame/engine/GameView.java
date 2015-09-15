package uencom.xgame.engine;

import java.io.InputStream;
import java.net.URL;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import uencom.xgame.gestures.HandGestures;
import uencom.xgame.xgame.R;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameView extends SherlockActivity {

	Button play;
	HandGestures HG;
	ImageView gameImg;
	TextView gameName;
	AccessibilityManager manager;
	LinearLayout main;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.game_view);
		play = (Button) findViewById(R.id.button1);
		play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent I = new Intent(getApplicationContext() , xGameParser.class);
				I.putExtra("Folder", getIntent().getStringExtra("Folder"));
				startActivity(I);
				
			}
		});
		String x= getIntent().getStringExtra("Folder");
		String y = getIntent().getStringExtra("Name");
		String z = getIntent().getStringExtra("Logo");
		System.out.println(x + " " + y + " " + z);
		gameImg = (ImageView)findViewById(R.id.imageView1);
		gameName = (TextView)findViewById(R.id.textView1);
		gameName.setText(getIntent().getStringExtra("Name"));
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					InputStream is = new URL(getIntent().getStringExtra("Logo")).openStream();
					final Drawable logo = Drawable.createFromStream(is, "src");
					if (logo != null) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								gameImg.getLayoutParams().height = 256;
								gameImg.getLayoutParams().width = 256;
								gameImg.setImageDrawable(logo);

							}
						});

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 finally {
					final Animation a = AnimationUtils.loadAnimation(
							getApplicationContext(), R.anim.fadein);
					a.setDuration(1000);
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							gameImg.startAnimation(a);
							
						}
					});
					
				}
			}
		});
		t.start();
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


		play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent I = new Intent(getApplicationContext(), xGameParser.class);
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
}
