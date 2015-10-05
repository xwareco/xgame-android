package uencom.xgame.engine.views;

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
import uencom.xgame.engine.navxgameAdapter;
import uencom.xgame.engine.navxgameList;
import uencom.xgame.engine.onDeviceGameChecker;
import uencom.xgame.engine.web.Game;
import uencom.xgame.engine.web.GameCategory;
import uencom.xgame.engine.web.Installer;
import uencom.xgame.engine.web.Server;
import uencom.xgame.xgame.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainView extends SherlockActivity implements OnNavigationListener {

	ImageView mainImage, next, pre, select;
	ArrayList<GameCategory> categories;
	ArrayList<Game> games;
	ListView mDrawerList;
	RelativeLayout mDrawerPane;
	RelativeLayout rellay;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;

	ArrayList<navxgameList> mNavItems = new ArrayList<navxgameList>();
	boolean cat;
	LinearLayout trans;
	// http://192.168.1.102/xgame-app/public/
	// http://xgameapp.com/games/
	private static final String IMAGE_PREFIX = "http://xgameapp.com/games/";
	// String[] games = { "Catch The Beep", "Snake" };
	// Integer[] images = { R.drawable.beep, R.drawable.beep };
	ListView list;
	int currentIndex, lastIndex;
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

		mNavItems.add(new navxgameList("Games List", R.drawable.games));
		mNavItems.add(new navxgameList("Register", R.drawable.reg));
		mNavItems.add(new navxgameList("About Us", R.drawable.about));
		mNavItems.add(new navxgameList("Contact Us", R.drawable.env));
		// DrawerLayout
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		// Populate the Navigtion Drawer with options
		// mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
		rellay = (RelativeLayout) findViewById(R.id.rellay);
		mDrawerList = (ListView) findViewById(R.id.navList);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.options, R.string.drawer_open, R.string.app_name);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		navxgameAdapter navAdapter = new navxgameAdapter(this, mNavItems);
		mDrawerList.setAdapter(navAdapter);

		// Drawer Item click listeners
		mDrawerList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						selectItemFromDrawer(position);
					}

				});
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
		bar.setDisplayShowTitleEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		cat = true;
		currentIndex = 0;
		lastIndex = -1;
		header = (TextView) findViewById(R.id.textView1);
		header.setText(categories.get(currentIndex).getName());
		loading = (TextView) findViewById(R.id.textView2);
		if (current.getDisplayLanguage().equals("Arabic")) {
			loading.setTypeface(arabic);
			header.setTypeface(english);
		} else if (current.getDisplayLanguage().equals("English")) {
			loading.setTypeface(english);
			header.setTypeface(english);
		}
		proBar = (ProgressBar) findViewById(R.id.progressBar2);
		mainImage = (ImageView) findViewById(R.id.catImg);
		trans = (LinearLayout) findViewById(R.id.translay);
		pre = (ImageView) findViewById(R.id.leftArrow);
		next = (ImageView) findViewById(R.id.rightArrow);
		new Server(MainView.this, null, null, loading, null, proBar, trans,
				list).execute("game", categories.get(currentIndex).getId(),
				String.valueOf(0));
		initonClicks();
		super.onCreate(savedInstanceState);
	}

	private void initonClicks() {

		pre.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				lastIndex = currentIndex;
				currentIndex--;
				if (currentIndex < 0)
					currentIndex = categories.size() - 1;

				if (currentIndex != lastIndex) {
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
												null, null, null, null, proBar,
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
				lastIndex = currentIndex;
				currentIndex++;
				if (currentIndex >= categories.size())
					currentIndex = 0;

				if (currentIndex != lastIndex) {
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
										new Server(MainView.this, null, null,
												null, null, proBar, trans, list)
												.execute("game", String
														.valueOf(currentIndex),
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
				SharedPreferences appSharedPrefs = PreferenceManager
						.getDefaultSharedPreferences(getApplicationContext());
				String json = appSharedPrefs.getString("games", "");
				Gson g = new Gson();
				java.lang.reflect.Type type = new TypeToken<ArrayList<Game>>() {
				}.getType();
				games = g.fromJson(json, (java.lang.reflect.Type) type);

				String ifGameExistsLocation = Environment
						.getExternalStorageDirectory().toString()
						+ "/xGame/Games/" + games.get(arg2).getName();

				 
				if (!new File(ifGameExistsLocation).exists()) {

					final String logUrl = IMAGE_PREFIX
							+ games.get(arg2)
									.getFileName()
									.substring(
											0,
											games.get(arg2).getFileName()
													.lastIndexOf('.')) + "/"
							+ games.get(arg2).getImgPath();
					final String downUrl = IMAGE_PREFIX
							+ games.get(arg2)
									.getFileName()
									.substring(
											0,
											games.get(arg2).getFileName()
													.lastIndexOf('.')) + "/"
							+ games.get(arg2).getFileName();
					String unzipLocation = Environment
							.getExternalStorageDirectory().toString()
							+ "/xGame/Games/";

					new Installer(MainView.this, unzipLocation, logUrl, games
							.get(arg2).getName()).execute(downUrl);

				}

				else {
					onDeviceGameChecker installations = new onDeviceGameChecker(
							MainView.this);
					if (installations.isOfflineGameExists(games.get(arg2)
							.getName()) == null) {
						Intent I = new Intent(getApplicationContext(),
								GameView.class);
						I.putExtra("Folder", ifGameExistsLocation);
						I.putExtra("Name", games.get(arg2).getName());
						I.putExtra(
								"Logo",
								IMAGE_PREFIX
										+ games.get(arg2)
												.getFileName()
												.substring(
														0,
														games.get(arg2)
																.getFileName()
																.lastIndexOf(
																		'.'))
										+ "/" + games.get(arg2).getImgPath());
						startActivity(I);
					} else {
						Toast.makeText(
								getApplicationContext(),
								"This installation is corrupted..The game will be downloaded again",
								Toast.LENGTH_LONG).show();
						final String logUrl = IMAGE_PREFIX
								+ games.get(arg2)
										.getFileName()
										.substring(
												0,
												games.get(arg2).getFileName()
														.lastIndexOf('.')) + "/"
								+ games.get(arg2).getImgPath();
						final String downUrl = IMAGE_PREFIX
								+ games.get(arg2)
										.getFileName()
										.substring(
												0,
												games.get(arg2).getFileName()
														.lastIndexOf('.')) + "/"
								+ games.get(arg2).getFileName();
						String unzipLocation = Environment
								.getExternalStorageDirectory().toString()
								+ "/xGame/Games/";

						new Installer(MainView.this, unzipLocation, logUrl, games
								.get(arg2).getName()).execute(downUrl);
					}
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
		/*
		 * if (item.getItemId() == android.R.id.home) {
		 * 
		 * if (mDrawerLayout.isDrawerOpen(rellay)) {
		 * mDrawerLayout.closeDrawer(rellay); } else {
		 * mDrawerLayout.openDrawer(rellay); } }
		 */

		if (item.getItemId() == R.id.action_testhead) {
			Intent I = new Intent(getApplicationContext(),
					HeadphoneTester.class);
			startActivity(I);
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

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		mDrawerToggle.syncState();
		super.onPostCreate(savedInstanceState);
	}

	private void selectItemFromDrawer(int position) {
		Intent I = null;
		// if(position == 0) I = new Intent(this , SplashActivity.class);
		if (position == 1)
			I = new Intent(this, Register.class);
		else if (position == 2)
			I = new Intent(this, AboutUs.class);// about
		else if (position == 3)
			I = new Intent(this, ContactUs.class);// contact
		// Close the drawer
		// mDrawerLayout.closeDrawer(mDrawerPane);
		startActivity(I);

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

}
