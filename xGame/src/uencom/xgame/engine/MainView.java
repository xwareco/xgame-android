package uencom.xgame.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uencom.xgame.gestures.HandGestures;
import uencom.xgame.engine.web.Game;
import uencom.xgame.engine.web.GameCategory;
import uencom.xgame.xgame.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainView extends SherlockActivity implements OnNavigationListener {

	ImageView mainImage, next, pre, select;
	ArrayList<GameCategory> categories;
	boolean cat;
	LinearLayout trans;
	// http://192.168.1.102/xgame-app/public/
	// http://xgameapp.com/games/
	private static final String IMAGE_PREFIX = "http://xgameapp.com/games/";
	// String[] games = { "Catch The Beep", "Snake" };
	// Integer[] images = { R.drawable.beep, R.drawable.beep };
	ListView list;
	int currentIndex;
	TextView header, loading;
	HandGestures HG;
	SpinnerAdapter adapter;
	ActionBar bar;
	ProgressBar proBar;
	Typeface arabic, english;

	// xGameAPI api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_view);
		list = (ListView) findViewById(R.id.listView1);
		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(getAssets(),
				"fonts/DJB Stinky Marker.ttf");
		Locale current = getResources().getConfiguration().locale;
		
		
		Intent I = getIntent();
		String catJson = I.getStringExtra("catJSON");
		// System.out.println(catJson);
		// new User(this, "AlyMoanes@yahoo.com", "456789").execute("register");
		Gson g = new Gson();
		java.lang.reflect.Type type = new TypeToken<ArrayList<GameCategory>>() {
		}.getType();
		categories = g.fromJson(catJson, (java.lang.reflect.Type) type);

		bar = getSupportActionBar();
		bar.setDisplayShowTitleEnabled(false);
		adapter = ArrayAdapter.createFromResource(bar.getThemedContext(),
				R.array.nav_actions,
				android.R.layout.simple_spinner_dropdown_item);

		cat = true;
		currentIndex = 0;
		header = (TextView) findViewById(R.id.textView1);
		header.setText(categories.get(currentIndex).getName());
		header.setTextColor(Color.GREEN);
		header.setTextSize(45);
		loading = (TextView) findViewById(R.id.textView2);
		if (current.getDisplayLanguage().equals("Arabic")) {
			loading.setTypeface(arabic);
			header.setTypeface(english);
		} else if (current.getDisplayLanguage().equals("English")) {
			loading.setTypeface(english);
			header.setTypeface(english);
		}
		proBar = (ProgressBar) findViewById(R.id.progressBar1);
		mainImage = (ImageView) findViewById(R.id.catImg);
		trans = (LinearLayout) findViewById(R.id.translay);
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					final Drawable logo = categories.get(currentIndex)
							.setCategoryLogo();
					if (logo != null) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								
								mainImage.getLayoutParams().height = 256;
								mainImage.getLayoutParams().width = 256;
								mainImage.layout(0,10,0,0);
								mainImage.setImageDrawable(logo);

							}
						});

					}
				} finally {
					final Animation a = AnimationUtils.loadAnimation(
							getApplicationContext(), R.anim.fadein);
					a.setAnimationListener(new Animation.AnimationListener() {

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
							// TODO Auto-generated method stub
							new Server(getApplicationContext(), null, null,
									null, proBar, trans, list).execute("game",
									String.valueOf(currentIndex),
									String.valueOf(0));
						}
					});
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							mainImage.startAnimation(a);

						}
					});

				}
			}
		});
		t.start();
		pre = (ImageView) findViewById(R.id.leftArrow);
		next = (ImageView) findViewById(R.id.rightArrow);
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		bar.setListNavigationCallbacks(adapter, this);
		initonClicks();
		super.onCreate(savedInstanceState);
	}

	private void initonClicks() {

		pre.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				currentIndex--;
				if (currentIndex < 0)
					currentIndex = categories.size() - 1;

				if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
					Thread t = new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								final Drawable logo = categories.get(
										currentIndex).setCategoryLogo();
								if (logo != null) {
									runOnUiThread(new Runnable() {

										@Override
										public void run() {
											mainImage.setImageDrawable(logo);

										}
									});

								}
							} finally {
								final Animation a = AnimationUtils
										.loadAnimation(getApplicationContext(),
												R.anim.fadein);

								a.setAnimationListener(new Animation.AnimationListener() {

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
										// TODO Auto-generated method stub
										trans.setVisibility(View.VISIBLE);
										loading.setVisibility(View.VISIBLE);
										proBar.setVisibility(View.VISIBLE);
										new Server(getApplicationContext(),
												null, null, null, proBar,
												trans, list).execute("game",
												String.valueOf(currentIndex),
												String.valueOf(0));
									}
								});
								runOnUiThread(new Runnable() {

									@Override
									public void run() {
										mainImage.startAnimation(a);

									}
								});
							}
						}
					});
					t.start();
					setNames();
				}
			}

		});

		next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				currentIndex++;
				if (currentIndex >= categories.size())
					currentIndex = 0;

				if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
					Thread t = new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								final Drawable logo = categories.get(
										currentIndex).setCategoryLogo();
								if (logo != null) {
									runOnUiThread(new Runnable() {

										@Override
										public void run() {
											mainImage.setImageDrawable(logo);

										}
									});

								}
							} finally {
								final Animation a = AnimationUtils
										.loadAnimation(getApplicationContext(),
												R.anim.fadein);
								a.setAnimationListener(new Animation.AnimationListener() {

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
										// TODO Auto-generated method stub
										trans.setVisibility(View.VISIBLE);
										loading.setVisibility(View.VISIBLE);
										proBar.setVisibility(View.VISIBLE);
										new Server(getApplicationContext(),
												null, null, null, proBar,
												trans, list).execute("game",
												String.valueOf(currentIndex),
												String.valueOf(0));
									}
								});
								runOnUiThread(new Runnable() {

									@Override
									public void run() {
										mainImage.startAnimation(a);

									}
								});

							}
						}
					});
					t.start();
				}
				setNames();
			}
		});

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				checkDirs();
				String ifGameExistsLocation = Environment
						.getExternalStorageDirectory().toString()
						+ "/xGame/Games/"
						+ categories.get(currentIndex).getGames().get(arg2)
								.getName();

				// System.out.println(unzipLocation);
				if (!new File(ifGameExistsLocation).exists()) {

					Intent I = new Intent(getApplicationContext(),
							SplashActivity.class);
					I.putExtra("gamename", categories.get(currentIndex)
							.getGames().get(arg2).getName());
					final String logUrl = IMAGE_PREFIX
							+ categories
									.get(currentIndex)
									.getGames()
									.get(arg2)
									.getFileName()
									.substring(
											0,
											categories.get(currentIndex)
													.getGames().get(arg2)
													.getFileName()
													.lastIndexOf('.'))
							+ "/"
							+ categories.get(currentIndex).getGames().get(arg2)
									.getImgPath();
					final String downUrl = IMAGE_PREFIX
							+ categories
									.get(currentIndex)
									.getGames()
									.get(arg2)
									.getFileName()
									.substring(
											0,
											categories.get(currentIndex)
													.getGames().get(arg2)
													.getFileName()
													.lastIndexOf('.'))
							+ "/"
							+ categories.get(currentIndex).getGames().get(arg2)
									.getFileName();
					I.putExtra("URL", logUrl);
					I.putExtra("Download", downUrl);
					startActivity(I);

				}

				else {

					Intent I = new Intent(getApplicationContext(),
							GameView.class);
					I.putExtra("Folder", ifGameExistsLocation);
					I.putExtra("Name", categories.get(currentIndex).getGames()
							.get(arg2).getName());
					I.putExtra(
							"Logo",
							IMAGE_PREFIX
									+ categories
											.get(currentIndex)
											.getGames()
											.get(arg2)
											.getFileName()
											.substring(
													0,
													categories
															.get(currentIndex)
															.getGames()
															.get(arg2)
															.getFileName()
															.lastIndexOf('.'))
									+ "/"
									+ categories.get(currentIndex).getGames()
											.get(arg2).getImgPath());
					startActivity(I);
				}

			}

			private void checkDirs() {
				String path1;
				path1 = Environment.getExternalStorageDirectory()
						+ "/xGame/Games";
				File f = new File(path1);
				if (!f.isDirectory()) {
					f.mkdirs();
				}
			}
		});

	}

	private void setNames() {
		if (cat == true) {
			header.setText(categories.get(currentIndex).getName());
		} else {
			header.setText(categories.get(currentIndex).getGames()
					.get(currentIndex).getName());
		}

	}

	@Override
	protected void onPause() {
		// finish();
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
		if(item.getItemId() == R.id.action_contactUs)
		{
			Intent I = new Intent(getApplicationContext() , HeadphoneTester.class);
		}
		else if(item.getItemId() == R.id.action_testhead)
		{
			Intent I = new Intent(getApplicationContext() , HeadphoneTester.class);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {

		if (itemId == 0) {
			list.setVisibility(View.GONE);
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
				mainImage.setVisibility(View.VISIBLE);
			
			currentIndex = 0;
			header.setVisibility(View.VISIBLE);
			pre.setVisibility(View.VISIBLE);
			next.setVisibility(View.VISIBLE);
			
		}

		return true;
	}

}
