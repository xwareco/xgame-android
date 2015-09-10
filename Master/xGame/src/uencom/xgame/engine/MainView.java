package uencom.xgame.engine;

import java.io.File;
import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import uencom.xgame.gestures.HandGestures;
import uencom.xgame.web.GameCategory;
import uencom.xgame.xgame.R;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainView extends SherlockActivity implements OnNavigationListener {

	ImageView mainImage, next, pre, select;
	ArrayList<GameCategory> categories;
	boolean cat;
	// http://192.168.1.102/xgame-app/public/
	// http://xgameapp.com/games/
	private static final String IMAGE_PREFIX = "http://xgameapp.com/games/";
	// String[] games = { "Catch The Beep", "Snake" };
	// Integer[] images = { R.drawable.beep, R.drawable.beep };
	ListView list;
	int currentIndex;
	TextView header, footer;
	HandGestures HG;
	SpinnerAdapter adapter;
	ActionBar bar;

	// xGameAPI api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.main_view);
		list = (ListView) findViewById(R.id.listView1);
		Intent I = getIntent();
		String catJson = I.getStringExtra("catJSON");
		// System.out.println(catJson);
		// new User(this, "AlyMoanes@yahoo.com", "456789").execute("register");
		Gson g = new Gson();
		java.lang.reflect.Type type = new TypeToken<ArrayList<GameCategory>>() {
		}.getType();
		categories = g.fromJson(catJson, (java.lang.reflect.Type) type);
		xGameList adapter2 = new xGameList(MainView.this, categories.get(
				currentIndex).getGames());
		list.setAdapter(adapter2);

		bar = getSupportActionBar();
		bar.setDisplayShowTitleEnabled(false);
		adapter = ArrayAdapter.createFromResource(bar.getThemedContext(),
				R.array.nav_actions,
				android.R.layout.simple_spinner_dropdown_item);

		cat = true;
		currentIndex = 0;
		header = (TextView) findViewById(R.id.textView2);
		header.setText(categories.get(currentIndex).getName());
		footer = (TextView) findViewById(R.id.textView1);
		mainImage = (ImageView) findViewById(R.id.imageView1);
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
								mainImage.setImageDrawable(logo);

							}
						});

					}
				} finally {
					final Animation a = AnimationUtils.loadAnimation(
							getApplicationContext(), R.anim.transition1);
					a.setDuration(1000);
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
		pre = (ImageView) findViewById(R.id.imageView2);
		select = (ImageView) findViewById(R.id.imageView3);
		next = (ImageView) findViewById(R.id.imageView4);
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
				if (currentIndex < 0 && cat == true)
					currentIndex = categories.size() - 1;
				else if (currentIndex < 0 && cat == false)
					currentIndex = categories.get(currentIndex).getGames()
							.size() - 1;

				if (cat == true)
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
												mainImage
														.setImageDrawable(logo);

											}
										});

									}
								} finally {
									Animation a = AnimationUtils.loadAnimation(
											getApplicationContext(),
											R.anim.transition1);
									a.setDuration(1000);
									mainImage.startAnimation(a);
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
				if (currentIndex >= categories.size() && cat == true)
					currentIndex = 0;
				else if (currentIndex >= categories.get(currentIndex)
						.getGames().size()
						&& cat == false)
					currentIndex = 0;

				if (cat == true)
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
												mainImage
														.setImageDrawable(logo);

											}
										});

									}
								} finally {
									Animation a = AnimationUtils.loadAnimation(
											getApplicationContext(),
											R.anim.transition1);
									a.setDuration(1000);
									mainImage.startAnimation(a);
								}
							}
						});
						t.start();
					}
				setNames();
			}
		});

		select.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (cat == true) {
					/*
					 * Intent I = new Intent(getApplicationContext() ,
					 * xGameParser.class); I.putExtra("Folder",
					 * Environment.getExternalStorageDirectory() +
					 * "/xGame/Games/car_racer"); startActivity(I);
					 */
					cat = false;
					list.setVisibility(View.VISIBLE);
					if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
						mainImage.setVisibility(View.GONE);
					}
					select.setVisibility(View.GONE);
					currentIndex = 0;
					header.setVisibility(View.GONE);
					footer.setVisibility(View.GONE);
					pre.setVisibility(View.GONE);
					next.setVisibility(View.GONE);
					select.setVisibility(View.GONE);
					bar.setSelectedNavigationItem(2);
				}

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
							DownlodView.class);
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
		Toast.makeText(getApplicationContext(), item.getItemId(),
				Toast.LENGTH_LONG).show();

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {

		if (itemId == 0) {
			list.setVisibility(View.GONE);
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
				mainImage.setVisibility(View.VISIBLE);
			select.setVisibility(View.VISIBLE);
			currentIndex = 0;
			header.setVisibility(View.VISIBLE);
			footer.setVisibility(View.VISIBLE);
			pre.setVisibility(View.VISIBLE);
			next.setVisibility(View.VISIBLE);
			select.setVisibility(View.VISIBLE);
		}

		return true;
	}

}
