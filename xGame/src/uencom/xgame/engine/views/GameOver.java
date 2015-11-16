package uencom.xgame.engine.views;


import java.util.Locale;

import uencom.xgame.engine.xGameParser;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class GameOver extends SherlockActivity {

	ImageView gamescore, tryAgain;
	TextView gameName;
	Typeface english;
	CallbackManager callbackManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		FacebookSdk.sdkInitialize(getApplicationContext());
		setContentView(R.layout.game_over_view);
		callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			
			@Override
			public void onSuccess(LoginResult result) {
				Toast.makeText(GameOver.this, "Successfully loged in", Toast.LENGTH_LONG).show();
				
			}
			
			@Override
			public void onError(FacebookException error) {
				Toast.makeText(GameOver.this, "Error, Cannot log you in", Toast.LENGTH_LONG).show();
				
			}
			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
		});
		SharedPreferences appSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		english = Typeface.createFromAsset(getAssets(),
				"fonts/DJB Stinky Marker.ttf");
		Locale current = getResources().getConfiguration().locale;
		gameName = (TextView) findViewById(R.id.textView1);
		if (current.getDisplayLanguage().equals("English")) {
			gameName.setTypeface(english);
		}
		gamescore = (ImageView) findViewById(R.id.imageView3);
		tryAgain = (ImageView) findViewById(R.id.imageView2);
		tryAgain.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent I = new Intent(GameOver.this, xGameParser.class);
				I.putExtra("Folder", getIntent().getStringExtra("Folder"));
				startActivity(I);
				overridePendingTransition(R.anim.transition11, R.anim.transition12);
			}
		});
		int Score = getIntent().getIntExtra("Score" , 0);
		Animation a = AnimationUtils.loadAnimation(this, R.anim.transition6);
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
		String name = getIntent().getStringExtra("gamename");
		System.out.println(name);
		gameName.setText(appSharedPrefs.getString("game", ""));
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
		//finish();
		super.onPause();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    callbackManager.onActivityResult(requestCode, resultCode, data);
	}
}
