package uencom.xgame.engine.views;

import java.util.Locale;

import uencom.xgame.engine.web.User;
import uencom.xgame.xgame.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class GameOver extends SherlockActivity {

	ImageView gamescore, tryAgain;
	TextView gameName;
	Typeface english;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.game_over_view);
		english = Typeface.createFromAsset(getAssets(),
				"fonts/DJB Stinky Marker.ttf");
		Locale current = getResources().getConfiguration().locale;
		gameName = (TextView) findViewById(R.id.textView1);
		if (current.getDisplayLanguage().equals("English")) {
			gameName.setTypeface(english);
		}
		gamescore = (ImageView) findViewById(R.id.imageView3);
		tryAgain = (ImageView) findViewById(R.id.imageView2);
		int Score = getIntent().getIntExtra("Score" , 0);
		Animation a = AnimationUtils.loadAnimation(this, R.anim.transition4);
		// int Score = getIntent().getIntExtra("Score", 0);
		gamescore.startAnimation(a);
		gamescore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent I;
				SharedPreferences appSharedPrefs = PreferenceManager
						.getDefaultSharedPreferences(getApplicationContext());
				Editor prefsEditor = appSharedPrefs.edit();
				String uID = appSharedPrefs.getString("uID", "");
				String uName = appSharedPrefs.getString("uName", "");
				String uPass = appSharedPrefs.getString("uPass", "");
				System.out.println(uID + " " + uName + " " + uPass);
				if (!uID.equals("") && !uName.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"Welcome " + uName
									+ " ,We are very glad to hear your feedback",
							Toast.LENGTH_LONG).show();
					I = new Intent(getApplicationContext(), ContactUs.class);// score board
					I.putExtra("ID", uID);
					I.putExtra("Name", uName);
					
				} else {
					String fileContents = User.readFromFile();
					if (fileContents.equals("")) {
						I = new Intent(getApplicationContext(), Register.class);
						I.putExtra("TAG", "gameover");
						Toast.makeText(
								getApplicationContext(),
								"You need to register first before you can contact us",
								Toast.LENGTH_LONG).show();
					}
					// 9,ali,123
					else
					{
						uID = fileContents.substring(0,fileContents.indexOf(','));
						uName = fileContents.substring(fileContents.indexOf(',')+1,fileContents.lastIndexOf(','));
					    uPass = fileContents.substring(fileContents.lastIndexOf(',')+1,fileContents.length()-1);
						System.out.println(uID + " " + uName + " Pass: " + uPass);
						prefsEditor.putString("uID", uID);
						prefsEditor.putString("uName", uName);
						prefsEditor.putString("uPass", uPass);
						prefsEditor.commit();
						Toast.makeText(
								getApplicationContext(),
								"Welcome " + uName
										+ " ,We are very glad to hear your feedback",
								Toast.LENGTH_LONG).show();
						I = new Intent(getApplicationContext(), ContactUs.class);// contact
						I.putExtra("ID", uID);
						I.putExtra("Name", uName);
						I.putExtra("Pass", uPass);
					}
					
				}
				// Close the drawer
				// mDrawerLayout.closeDrawer(mDrawerPane);
				startActivity(I);

			}
		});
		gameName.setText(getIntent().getStringExtra("gamename"));
		Toast.makeText(this, "Your Score is: " + Score, Toast.LENGTH_LONG).show();
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		super.onCreate(savedInstanceState);
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
	protected void onPause() {
		finish();
		super.onPause();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
}
