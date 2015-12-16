package uencom.xgame.engine.views;

import java.util.ArrayList;
import java.util.Locale;
import uencom.xgame.engine.Scorer;
import uencom.xgame.engine.xGameParser;
import uencom.xgame.xgame.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GameOver extends SherlockActivity {

	ImageView gamescore, tryAgain, cheerForUser, gameOver;
	TextView gameName, rank;
	Typeface english;
	CallbackManager callbackManager;
	SharedPreferences appSharedPrefs;
	ArrayList<Scorer> gameTopScorers;
	String userRank;
	boolean cheer;
	RelativeLayout mapLay;
	LinearLayout scorer1, scorer2, scorer3, scorer4;
	TextView scorer1Rank, scorer1Email, scorer1Score, scorer2Rank,
			scorer2Email, scorer2Score, scorer3Rank, scorer3Email,
			scorer3Score, scorer4Rank, scorer4Email, scorer4Score;
	ProgressBar one , two, three, four;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		FacebookSdk.sdkInitialize(getApplicationContext());
		setContentView(R.layout.game_over_view);
		mapLay = (RelativeLayout)findViewById(R.id.mapLay);
		
		scorer1 = (LinearLayout)findViewById(R.id.scorer1);
		scorer2 = (LinearLayout)findViewById(R.id.scorer2);
		scorer3 = (LinearLayout)findViewById(R.id.scorer3);
		scorer4 = (LinearLayout)findViewById(R.id.scorer4);
		
		scorer1Rank = (TextView)findViewById(R.id.scorer1Rank);
		scorer2Rank = (TextView)findViewById(R.id.scorer2Rank);
		scorer3Rank = (TextView)findViewById(R.id.scorer3Rank);
		scorer4Rank = (TextView)findViewById(R.id.scorer4Rank);
		scorer1Email = (TextView)findViewById(R.id.scorer1Email);
		scorer2Email = (TextView)findViewById(R.id.scorer2Email);
		scorer3Email = (TextView)findViewById(R.id.scorer3Email);
		scorer4Email = (TextView)findViewById(R.id.scorer4Email);
		scorer1Score = (TextView)findViewById(R.id.scorer1Score);
		scorer2Score = (TextView)findViewById(R.id.scorer2Score);
		scorer3Score = (TextView)findViewById(R.id.scorer3Score);
		scorer4Score = (TextView)findViewById(R.id.scorer4Score);
		
		one = (ProgressBar)findViewById(R.id.progressBar1);
		two = (ProgressBar)findViewById(R.id.progressBar2);
		three = (ProgressBar)findViewById(R.id.progressBar3);
		four = (ProgressBar)findViewById(R.id.progressBar4);
		
		cheer = getIntent().getBooleanExtra("cheer", false);
		cheerForUser = (ImageView) findViewById(R.id.img3);
		cheerForUser.bringToFront();
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.cheer);
		anim.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				cheerForUser.setVisibility(View.GONE);

			}
		});
		System.out.println("Cheer = " + cheer);
		if (cheer == true) {
			cheerForUser.setVisibility(View.VISIBLE);
			cheerForUser.startAnimation(anim);
			MediaPlayer mp = MediaPlayer.create(this, R.raw.cheer);
			MediaPlayer mp2 = MediaPlayer.create(this, R.raw.congrat);
			mp.start();
			mp2.start();

		}
		callbackManager = CallbackManager.Factory.create();
		final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
		final ShareButton shareButton = (ShareButton) findViewById(R.id.share_button);

		loginButton.setReadPermissions("user_friends");
		loginButton.registerCallback(callbackManager,
				new FacebookCallback<LoginResult>() {

					@Override
					public void onSuccess(LoginResult result) {
						Toast.makeText(GameOver.this, "Successfully loged in",
								Toast.LENGTH_LONG).show();
						shareButton.setVisibility(View.VISIBLE);
						loginButton.setVisibility(View.GONE);

					}

					@Override
					public void onError(FacebookException error) {
						Toast.makeText(GameOver.this,
								"Error, Cannot log you in", Toast.LENGTH_LONG)
								.show();

					}

					@Override
					public void onCancel() {
						// TODO Auto-generated method stub

					}
				});
		appSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());

		if (loginButton.getText().equals("Log out")) {
			shareButton.setVisibility(View.VISIBLE);
			loginButton.setVisibility(View.GONE);
		}

		english = Typeface.createFromAsset(getAssets(),
				"fonts/DJB Stinky Marker.ttf");
		Locale current = getResources().getConfiguration().locale;
		gameName = (TextView) findViewById(R.id.textView1);
		rank = (TextView) findViewById(R.id.textView2);
		if (current.getDisplayLanguage().equals("English")) {
			gameName.setTypeface(english);
		}
		rank.setTypeface(english);

		gamescore = (ImageView) findViewById(R.id.imageView3);
		gameOver = (ImageView) findViewById(R.id.imageView1);
		if (!appSharedPrefs.getString("uName", "").equals("")) {
			if (!loginButton.getText().equals("Log out"))
				loginButton.setVisibility(View.VISIBLE);
			gamescore.setVisibility(View.VISIBLE);
			
			String topJSon = appSharedPrefs.getString("scorers", "");
			Gson g = new Gson();
			java.lang.reflect.Type type = new TypeToken<ArrayList<Scorer>>() {
			}.getType();
			gameTopScorers = g.fromJson(topJSon, (java.lang.reflect.Type) type);
			System.out.println("Top Scorers Data: \n");
			for (int i = 0; i < gameTopScorers.size(); i++) {
				System.out.println("Rank: " + gameTopScorers.get(i).getRank()
						+ " Name: " + gameTopScorers.get(i).getUserMail()
						+ " Score: " + gameTopScorers.get(i).getScore());
			}
			userRank = getIntent().getStringExtra("rank");
			String userRankFacebook = "";
			gameOver.setVisibility(View.GONE);
			if (userRank.equals("1")) {
				userRankFacebook = userRank + "st";
				rank.setText(userRankFacebook);
				rank.setTextColor(Color.parseColor("#FFD700"));
				rank.setContentDescription("First place");
			}

			else if (userRank.equals("2")) {
				userRankFacebook = userRank + "nd";
				rank.setText(userRankFacebook);
				rank.setTextColor(Color.parseColor("#C0C0C0"));
				rank.setContentDescription("Second place");
			} else if (userRank.equals("3")) {
				userRankFacebook = userRank + "rd";
				rank.setText(userRankFacebook);
				rank.setTextColor(Color.parseColor("#CD7F32"));
				rank.setContentDescription("Third place");
			} else {
				userRankFacebook = userRank + "th";
				rank.setText(userRankFacebook);
				rank.setTextColor(Color.parseColor("#FF808000"));
				rank.setContentDescription(userRankFacebook);
			}
			gameOver.setVisibility(View.GONE);
			rank.setVisibility(View.VISIBLE);

			ShareLinkContent linkContent = new ShareLinkContent.Builder()
					.setContentTitle(userRankFacebook + " Place")
					.setContentDescription(
							"I have played "
									+ appSharedPrefs.getString("game", "")
									+ "\ngame and got a new highscore \njoin xGame now and try to beat me!")
					.setContentUrl(Uri.parse("http://scoreboard.xgameapp.com/"))
					.build();
			shareButton.setShareContent(linkContent);
		}
		tryAgain = (ImageView) findViewById(R.id.imageView2);
		tryAgain.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent I = new Intent(GameOver.this, xGameParser.class);
				I.putExtra("Folder", getIntent().getStringExtra("Folder"));
				startActivity(I);
				finish();
				overridePendingTransition(R.anim.transition11,
						R.anim.transition12);
			}
		});
		int Score = getIntent().getIntExtra("Score", 0);
		Animation a = AnimationUtils.loadAnimation(this, R.anim.transition6);
		// int Score = getIntent().getIntExtra("Score", 0);
		gamescore.startAnimation(a);
		gamescore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Animation fadeOut = AnimationUtils.loadAnimation(GameOver.this, R.anim.fadeout);
				fadeOut.setAnimationListener(new Animation.AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation arg0) {
						rank.setVisibility(View.GONE);
						scorer1.setVisibility(View.VISIBLE);
						scorer2.setVisibility(View.VISIBLE);
						scorer3.setVisibility(View.VISIBLE);
						scorer4.setVisibility(View.VISIBLE);
						Animation fadeIn = AnimationUtils.loadAnimation(GameOver.this, R.anim.fadein);
						mapLay.startAnimation(fadeIn);
						
					}
				});
				mapLay.startAnimation(fadeOut);

				}
				

			
		});
		String name = getIntent().getStringExtra("gamename");
		System.out.println(name);
		gameName.setText(appSharedPrefs.getString("game", ""));
		Toast.makeText(this, "Your Score is: " + Score, Toast.LENGTH_LONG)
				.show();
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

		super.onPause();
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.transition10, R.anim.transition9);
		super.onBackPressed();
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
