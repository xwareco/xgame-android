package xware.xgame.engine.views;

import java.util.Locale;

import xware.xgame.xgame.R;
import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {
	TextView title;
	ImageView voice, text;
	Typeface arabic, english;
	Animation a;
	Boolean isRecordAudioPermessionGranted;
	Toolbar actionBar;
	SharedPreferences appSharedPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.contactus);
		actionBar = (Toolbar)findViewById(R.id.my_toolbar);
		setSupportActionBar(actionBar);
		appSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(ContactUs.this);
		isRecordAudioPermessionGranted = false;
		if(Build.VERSION.SDK_INT < VERSION_CODES.M)
			isRecordAudioPermessionGranted = true;
		if (ContextCompat.checkSelfPermission(ContactUs.this,
				Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

			ActivityCompat.requestPermissions(ContactUs.this,
					new String[] { Manifest.permission.RECORD_AUDIO }, 0);
		} else {
			isRecordAudioPermessionGranted = true;
		}
		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(getAssets(),
				"fonts/klavika-regular-opentype.otf");
		a = AnimationUtils.loadAnimation(this, R.anim.fadein);
		title = (TextView) findViewById(R.id.textView3);
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		voice = (ImageView) findViewById(R.id.imageView2);
		text = (ImageView) findViewById(R.id.imageView1);
		text.startAnimation(a);
		voice.startAnimation(a);
		String lang = appSharedPrefs.getString("Lang","EN");
		if (lang.equals("AR")) {
			title.setTypeface(arabic);
		} else if (lang.equals("EN")) {
			title.setTypeface(english);

		}
		voice.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isRecordAudioPermessionGranted == true) {
					Intent I = new Intent(getApplicationContext(),
							AudioRecorder.class);
					startActivity(I);
				} else {
					String toast = "Permission denied, cannot use this feature";
					String lang = appSharedPrefs.getString("Lang","EN");
					if (lang.equals("AR")) {
						toast = "لم يتم السماح باسخدام هذه الخاصية";
					}
					Toast.makeText(getApplicationContext(), toast,
							Toast.LENGTH_LONG).show();
				}

			}
		});
		text.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent I = new Intent(getApplicationContext(),
						TextMessageSender.class);
				startActivity(I);

			}
		});
		getSupportActionBar().setHomeButtonEnabled(true);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		/*if (item.getItemId() == R.id.action_testhead) {
			Intent I = new Intent(getApplicationContext(),
					HeadphoneTester.class);
			startActivity(I);
		} else if (item.getItemId() == android.R.id.home) {
			finish();
			overridePendingTransition(R.anim.transition8, R.anim.transition7);
		}*/

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.transition8, R.anim.transition7);
		super.onBackPressed();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
			String[] permissions, int[] grantResults) {
		switch (requestCode) {
		case 0: {
			// If request is cancelled, the result arrays are empty.
			if (grantResults.length > 0
					&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				isRecordAudioPermessionGranted = true;

			} else {

				// permission denied, boo! Disable the
				// functionality that depends on this permission.
				isRecordAudioPermessionGranted = false;

			}
			return;
		}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
}
