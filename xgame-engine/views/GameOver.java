package xware.xgame.engine.views;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;
import xware.xgame.engine.Scorer;
import xware.xgame.engine.xGameParser;
import xware.xgame.xgame.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
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
	Typeface english, nums;
	CallbackManager callbackManager;
	SharedPreferences appSharedPrefs;
	ArrayList<Scorer> gameTopScorers;
	String userRank;
	boolean cheer;
	RelativeLayout mapLay;
	LinearLayout scorer1, scorer2, scorer3, scorer4, subLay1, subLay2, subLay3,
			subLay4;
	TextView scorer1Rank, scorer1Email, scorer1Score, scorer2Rank,
			scorer2Email, scorer2Score, scorer3Rank, scorer3Email,
			scorer3Score, scorer4Rank, scorer4Email, scorer4Score;
	ProgressBar one, two, three, four;
	Thread scorer1Thread, scorer2Thread, scorer3Thread, scorer4Thread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		FacebookSdk.sdkInitialize(getApplicationContext());
		setContentView(R.layout.game_over_view);
		mapLay = (RelativeLayout) findViewById(R.id.mapLay);
		String TAG = "xware.xgame.xgame";
		try {
			Log.d(TAG, "keyHash: start");
			PackageInfo info = this.getPackageManager().getPackageInfo(TAG,
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String keyHash = Base64.encodeToString(md.digest(),
						Base64.DEFAULT);
				Log.d(TAG, "keyHash: " + keyHash);

			}
			Log.d(TAG, "keyHash: end");
		} catch (NameNotFoundException e) {
			Log.d(TAG, "keyHash: name:" + e);
		} catch (NoSuchAlgorithmException e) {
			Log.d(TAG, "keyHash: name:" + e);
		}

		scorer1 = (LinearLayout) findViewById(R.id.scorer1);
		scorer2 = (LinearLayout) findViewById(R.id.scorer2);
		scorer3 = (LinearLayout) findViewById(R.id.scorer3);
		scorer4 = (LinearLayout) findViewById(R.id.scorer4);
		subLay1 = (LinearLayout) findViewById(R.id.subLay1);
		subLay2 = (LinearLayout) findViewById(R.id.subLay2);
		subLay3 = (LinearLayout) findViewById(R.id.subLay3);
		subLay4 = (LinearLayout) findViewById(R.id.subLay4);

		scorer1Rank = (TextView) findViewById(R.id.scorer1Rank);
		scorer2Rank = (TextView) findViewById(R.id.scorer2Rank);
		scorer3Rank = (TextView) findViewById(R.id.scorer3Rank);
		scorer4Rank = (TextView) findViewById(R.id.scorer4Rank);
		scorer1Email = (TextView) findViewById(R.id.scorer1Email);
		scorer2Email = (TextView) findViewById(R.id.scorer2Email);
		scorer3Email = (TextView) findViewById(R.id.scorer3Email);
		scorer4Email = (TextView) findViewById(R.id.scorer4Email);
		scorer1Score = (TextView) findViewById(R.id.scorer1Score);
		scorer2Score = (TextView) findViewById(R.id.scorer2Score);
		scorer3Score = (TextView) findViewById(R.id.scorer3Score);
		scorer4Score = (TextView) findViewById(R.id.scorer4Score);

		one = (ProgressBar) findViewById(R.id.progressBar1);
		two = (ProgressBar) findViewById(R.id.progressBar2);
		three = (ProgressBar) findViewById(R.id.progressBar3);
		four = (ProgressBar) findViewById(R.id.progressBar4);

		scorer1Thread = new Thread(new Runnable() {

			@Override
			public void run() {
				updateScorer1();

			}

		});
		scorer2Thread = new Thread(new Runnable() {

			@Override
			public void run() {
				updateScorer2();

			}

		});
		scorer3Thread = new Thread(new Runnable() {

			@Override
			public void run() {
				updateScorer3();

			}

		});
		scorer4Thread = new Thread(new Runnable() {

			@Override
			public void run() {
				updateScorer4();

			}

		});

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
				"fonts/klavika-regular-opentype.otf");
		nums = Typeface.createFromAsset(getAssets(), "fonts/DigitalDream.ttf");
		scorer1Rank.setTypeface(nums);
		scorer2Rank.setTypeface(nums);
		scorer3Rank.setTypeface(nums);
		scorer4Rank.setTypeface(nums);
		scorer1Score.setTypeface(nums);
		scorer2Score.setTypeface(nums);
		scorer3Score.setTypeface(nums);
		scorer4Score.setTypeface(nums);

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
				Animation fadeOut = AnimationUtils.loadAnimation(GameOver.this,
						R.anim.fadeout);

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
						if (rank.getVisibility() == View.VISIBLE) {
							rank.setVisibility(View.GONE);
							if (gameTopScorers.size() == 0) {
								gameOver.setVisibility(View.VISIBLE);
								Toast.makeText(GameOver.this,
										"Cannot get scorers now",
										Toast.LENGTH_LONG).show();
							} else if (gameTopScorers.size() == 1) {
								scorer1Thread.start();
							} else if (gameTopScorers.size() == 2) {
								scorer1Thread.start();
								scorer2Thread.start();
							} else if (gameTopScorers.size() == 3) {
								scorer1Thread.start();
								scorer2Thread.start();
								scorer3Thread.start();
							} else if (gameTopScorers.size() == 4) {
								scorer1Thread.start();
								scorer2Thread.start();
								scorer3Thread.start();
								scorer4Thread.start();
							}
						} else {
							rank.setVisibility(View.VISIBLE);
							scorer1.setVisibility(View.GONE);
							scorer2.setVisibility(View.GONE);
							scorer3.setVisibility(View.GONE);
							scorer4.setVisibility(View.GONE);
							scorer1Thread.interrupt();
							scorer2Thread.interrupt();
							scorer3Thread.interrupt();
							scorer4Thread.interrupt();
							gamescore.setClickable(false);
						}
						Animation fadeIn = AnimationUtils.loadAnimation(
								GameOver.this, R.anim.fadein);
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

	private void updateScorer1() {
		// update data
		scorer1Rank.setText(gameTopScorers.get(0).getRank());
		scorer1Email.setText(gameTopScorers.get(0).getUserMail());
		scorer1Score.setText(gameTopScorers.get(0).getScore());
		one.setMax(105);
		one.setProgress(0);
		// update weight
		if (Integer.parseInt(gameTopScorers.get(0).getRank()) > 999) {
			scorer1Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 50f));
			subLay1.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 87f));
			scorer1Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));
		} else if (Integer.parseInt(gameTopScorers.get(0).getRank()) > 99) {
			scorer1Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 35f));
			subLay1.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 102f));
			scorer1Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));

		} else if (Integer.parseInt(gameTopScorers.get(0).getRank()) > 9) {
			scorer1Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 25f));
			subLay1.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 112f));
			scorer1Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));

		} else if (Integer.parseInt(gameTopScorers.get(0).getRank()) <= 9) {
			scorer1Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));
			subLay1.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 124f));
			scorer1Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));

		}
		/*
		 * System.out .println("weight1: " + ((LinearLayout.LayoutParams)
		 * scorer1Rank .getLayoutParams()).weight + " weight2: " +
		 * ((LinearLayout.LayoutParams) subLay1 .getLayoutParams()).weight +
		 * " weight3: " + ((LinearLayout.LayoutParams) scorer1Score
		 * .getLayoutParams()).weight);
		 */
		// update drawable
		if (gameTopScorers.get(0).getRank().equals("1")) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar);
			one.setProgressDrawable(d);
			scorer1Rank.setTextColor(Color.parseColor("#FFD700"));
			scorer1Score.setTextColor(Color.parseColor("#FFD700"));
		} else if (gameTopScorers.get(0).getRank().equals("2")) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar2);
			one.setProgressDrawable(d);
			scorer1Rank.setTextColor(Color.parseColor("#C0C0C0"));
			scorer1Score.setTextColor(Color.parseColor("#C0C0C0"));
		} else if (gameTopScorers.get(0).getRank().equals("3")) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar3);
			one.setProgressDrawable(d);
			scorer1Rank.setTextColor(Color.parseColor("#CD7F32"));
			scorer1Score.setTextColor(Color.parseColor("#CD7F32"));
		} else {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar5);
			one.setProgressDrawable(d);
			scorer1Rank.setTextColor(Color.BLACK);
			scorer1Score.setTextColor(Color.BLACK);
		}
		if (appSharedPrefs.getString("uName", "").equals(
				gameTopScorers.get(0).getUserMail())
				&& Integer.parseInt(gameTopScorers.get(0).getRank()) > 3) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar4);
			one.setProgressDrawable(d);
			scorer1Rank.setTextColor(Color.GREEN);
			scorer1Score.setTextColor(Color.GREEN);
		}
		if (appSharedPrefs.getString("uName", "").equals(
				gameTopScorers.get(0).getUserMail())) {
			scorer1Email.setText("you");
			scorer1Email.setTypeface(null, Typeface.BOLD);
		}
		// show
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				scorer1.setVisibility(View.VISIBLE);

			}
		});

		// animate progress
		int Duration = 3000;
		long interval = Duration
				/ Integer.parseInt(gameTopScorers.get(0).getScore());
		for (int i = 0; i < Integer.parseInt(gameTopScorers.get(0).getScore()); i++) {
			one.setProgress(i);
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void updateScorer2() {
		// update data
		scorer2Rank.setText(gameTopScorers.get(1).getRank());
		scorer2Email.setText(gameTopScorers.get(1).getUserMail());
		scorer2Score.setText(gameTopScorers.get(1).getScore());
		two.setMax(105);
		two.setProgress(0);
		// update weight
		if (Integer.parseInt(gameTopScorers.get(1).getRank()) > 999) {
			scorer2Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 50f));
			subLay2.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 87f));
			scorer2Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));
		} else if (Integer.parseInt(gameTopScorers.get(1).getRank()) > 99) {
			scorer2Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 35f));
			subLay2.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 102f));
			scorer2Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));

		} else if (Integer.parseInt(gameTopScorers.get(1).getRank()) > 9) {
			scorer2Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 25f));
			subLay2.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 112f));
			scorer2Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));

		} else if (Integer.parseInt(gameTopScorers.get(1).getRank()) <= 9) {
			scorer2Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));
			subLay2.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 124f));
			scorer2Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));

		}
		/*
		 * System.out .println("weight1: " + ((LinearLayout.LayoutParams)
		 * scorer1Rank .getLayoutParams()).weight + " weight2: " +
		 * ((LinearLayout.LayoutParams) subLay1 .getLayoutParams()).weight +
		 * " weight3: " + ((LinearLayout.LayoutParams) scorer1Score
		 * .getLayoutParams()).weight);
		 */
		// update drawable
		if (gameTopScorers.get(1).getRank().equals("1")) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar);
			two.setProgressDrawable(d);
			scorer2Rank.setTextColor(Color.parseColor("#FFD700"));
			scorer2Score.setTextColor(Color.parseColor("#FFD700"));
		} else if (gameTopScorers.get(1).getRank().equals("2")) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar2);
			two.setProgressDrawable(d);
			scorer2Rank.setTextColor(Color.parseColor("#C0C0C0"));
			scorer2Score.setTextColor(Color.parseColor("#C0C0C0"));
		} else if (gameTopScorers.get(1).getRank().equals("3")) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar3);
			two.setProgressDrawable(d);
			scorer2Rank.setTextColor(Color.parseColor("#CD7F32"));
			scorer2Score.setTextColor(Color.parseColor("#CD7F32"));
		} else {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar4);
			two.setProgressDrawable(d);
			scorer2Rank.setTextColor(Color.BLACK);
			scorer2Score.setTextColor(Color.BLACK);
		}
		if (appSharedPrefs.getString("uName", "").equals(
				gameTopScorers.get(1).getUserMail())
				&& Integer.parseInt(gameTopScorers.get(1).getRank()) > 3) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar5);
			two.setProgressDrawable(d);
			scorer2Rank.setTextColor(Color.GREEN);
			scorer2Score.setTextColor(Color.GREEN);
		}
		if (appSharedPrefs.getString("uName", "").equals(
				gameTopScorers.get(1).getUserMail())) {
			scorer2Email.setText("you");
			scorer2Email.setTypeface(null, Typeface.BOLD);
		}
		// show
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				scorer2.setVisibility(View.VISIBLE);

			}
		});

		// animate progress
		int Duration = 3000;
		long interval = Duration
				/ Integer.parseInt(gameTopScorers.get(1).getScore());
		for (int i = 0; i < Integer.parseInt(gameTopScorers.get(1).getScore()); i++) {
			two.setProgress(i);
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void updateScorer3() {
		// update data
		scorer3Rank.setText(gameTopScorers.get(2).getRank());
		scorer3Email.setText(gameTopScorers.get(2).getUserMail());
		scorer3Score.setText(gameTopScorers.get(2).getScore());
		three.setMax(105);
		three.setProgress(0);
		// update weight
		if (Integer.parseInt(gameTopScorers.get(2).getRank()) > 999) {
			scorer3Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 50f));
			subLay3.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 87f));
			scorer3Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));
		} else if (Integer.parseInt(gameTopScorers.get(2).getRank()) > 99) {
			scorer3Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 35f));
			subLay3.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 102f));
			scorer3Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));

		} else if (Integer.parseInt(gameTopScorers.get(2).getRank()) > 9) {
			scorer3Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 25f));
			subLay3.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 112f));
			scorer3Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));

		} else if (Integer.parseInt(gameTopScorers.get(2).getRank()) <= 9) {
			scorer3Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));
			subLay3.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 124f));
			scorer3Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));

		}
		/*
		 * System.out .println("weight1: " + ((LinearLayout.LayoutParams)
		 * scorer1Rank .getLayoutParams()).weight + " weight2: " +
		 * ((LinearLayout.LayoutParams) subLay1 .getLayoutParams()).weight +
		 * " weight3: " + ((LinearLayout.LayoutParams) scorer1Score
		 * .getLayoutParams()).weight);
		 */
		// update drawable
		if (gameTopScorers.get(2).getRank().equals("1")) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar);
			three.setProgressDrawable(d);
			scorer3Rank.setTextColor(Color.parseColor("#FFD700"));
			scorer3Score.setTextColor(Color.parseColor("#FFD700"));
		} else if (gameTopScorers.get(2).getRank().equals("2")) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar2);
			three.setProgressDrawable(d);
			scorer3Rank.setTextColor(Color.parseColor("#C0C0C0"));
			scorer3Score.setTextColor(Color.parseColor("#C0C0C0"));
		} else if (gameTopScorers.get(2).getRank().equals("3")) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar3);
			three.setProgressDrawable(d);
			scorer3Rank.setTextColor(Color.parseColor("#CD7F32"));
			scorer3Score.setTextColor(Color.parseColor("#CD7F32"));
		} else {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar4);
			three.setProgressDrawable(d);
			scorer3Rank.setTextColor(Color.BLACK);
			scorer3Score.setTextColor(Color.BLACK);
		}
		if (appSharedPrefs.getString("uName", "").equals(
				gameTopScorers.get(2).getUserMail())
				&& Integer.parseInt(gameTopScorers.get(2).getRank()) > 3) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar5);
			three.setProgressDrawable(d);
			scorer3Rank.setTextColor(Color.GREEN);
			scorer3Score.setTextColor(Color.GREEN);
		}
		if (appSharedPrefs.getString("uName", "").equals(
				gameTopScorers.get(2).getUserMail())) {
			scorer3Email.setText("you");
			scorer3Email.setTypeface(null, Typeface.BOLD);
		}
		// show
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				scorer3.setVisibility(View.VISIBLE);

			}
		});

		// animate progress
		int Duration = 3000;
		long interval = Duration
				/ Integer.parseInt(gameTopScorers.get(2).getScore());
		for (int i = 0; i < Integer.parseInt(gameTopScorers.get(2).getScore()); i++) {
			three.setProgress(i);
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void updateScorer4() {
		// update data
		scorer4Rank.setText(gameTopScorers.get(3).getRank());
		scorer4Email.setText(gameTopScorers.get(3).getUserMail());
		scorer4Score.setText(gameTopScorers.get(3).getScore());
		four.setMax(105);
		four.setProgress(0);
		// update weight
		if (Integer.parseInt(gameTopScorers.get(3).getRank()) > 999) {
			scorer4Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 50f));
			subLay4.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 87f));
			scorer4Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));
		} else if (Integer.parseInt(gameTopScorers.get(3).getRank()) > 99) {
			scorer4Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 35f));
			subLay4.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 102f));
			scorer4Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));

		} else if (Integer.parseInt(gameTopScorers.get(3).getRank()) > 9) {
			scorer4Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 25f));
			subLay4.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 112f));
			scorer4Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));

		} else if (Integer.parseInt(gameTopScorers.get(3).getRank()) <= 9) {
			scorer4Rank.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));
			subLay4.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 124f));
			scorer4Score.setLayoutParams(new LinearLayout.LayoutParams(0,
					LayoutParams.WRAP_CONTENT, 13f));

		}
		/*
		 * System.out .println("weight1: " + ((LinearLayout.LayoutParams)
		 * scorer1Rank .getLayoutParams()).weight + " weight2: " +
		 * ((LinearLayout.LayoutParams) subLay1 .getLayoutParams()).weight +
		 * " weight3: " + ((LinearLayout.LayoutParams) scorer1Score
		 * .getLayoutParams()).weight);
		 */
		// update drawable
		if (gameTopScorers.get(3).getRank().equals("1")) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar);
			four.setProgressDrawable(d);
			scorer4Rank.setTextColor(Color.parseColor("#FFD700"));
			scorer4Score.setTextColor(Color.parseColor("#FFD700"));
		} else if (gameTopScorers.get(3).getRank().equals("2")) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar2);
			four.setProgressDrawable(d);
			scorer4Rank.setTextColor(Color.parseColor("#C0C0C0"));
			scorer4Score.setTextColor(Color.parseColor("#C0C0C0"));
		} else if (gameTopScorers.get(3).getRank().equals("3")) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar3);
			four.setProgressDrawable(d);
			scorer4Rank.setTextColor(Color.parseColor("#CD7F32"));
			scorer4Score.setTextColor(Color.parseColor("#CD7F32"));
		} else {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar4);
			four.setProgressDrawable(d);
			scorer4Rank.setTextColor(Color.BLACK);
			scorer4Score.setTextColor(Color.BLACK);
		}
		if (appSharedPrefs.getString("uName", "").equals(
				gameTopScorers.get(3).getUserMail())
				&& Integer.parseInt(gameTopScorers.get(3).getRank()) > 3) {
			Drawable d = getResources().getDrawable(
					R.drawable.custom_progressbar5);
			four.setProgressDrawable(d);
			scorer4Rank.setTextColor(Color.GREEN);
			scorer4Score.setTextColor(Color.GREEN);
		}
		if (appSharedPrefs.getString("uName", "").equals(
				gameTopScorers.get(3).getUserMail())) {
			scorer4Email.setText("you");
			scorer4Email.setTypeface(null, Typeface.BOLD);
		}
		// show
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				scorer4.setVisibility(View.VISIBLE);

			}
		});

		// animate progress
		int Duration = 3000;
		long interval = Duration
				/ Integer.parseInt(gameTopScorers.get(3).getScore());
		for (int i = 0; i < Integer.parseInt(gameTopScorers.get(3).getScore()); i++) {
			four.setProgress(i);
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
