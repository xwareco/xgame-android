package uencom.xgame.engine.views;

import java.util.Locale;
import uencom.xgame.engine.web.User;
import uencom.xgame.xgame.R;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class Register extends SherlockActivity {

	ImageView regapi, close;
	EditText name, pass;
	TextView  nameTV, passTV;
	Typeface arabic, english;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.register_view);
		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(getAssets(),
				"fonts/DJB Stinky Marker.ttf");
		Locale current = getResources().getConfiguration().locale;
		name = (EditText) findViewById(R.id.editText1);
		pass = (EditText) findViewById(R.id.editText2);
		nameTV = (TextView) findViewById(R.id.textView2);
		passTV = (TextView) findViewById(R.id.textView3);
		close = (ImageView) findViewById(R.id.imageView5);
		close.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();

			}
		});
		regapi = (ImageView) findViewById(R.id.imageView1);
		regapi.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!name.getText().toString().equals("")
						&& !pass.getText().toString().equals("")
						&& isEmailValid(name)) {
					new User(Register.this, name.getText().toString(), pass
							.getText().toString(), getIntent().getStringExtra(
							"TAG"), null, null).execute("register");

				} else {
					Toast.makeText(getApplicationContext(),
							"Empty field or invalid email address",
							Toast.LENGTH_LONG).show();
				}

			}
		});
		if (current.getDisplayLanguage().equals("Arabic")) {
			
			nameTV.setTypeface(arabic);
			passTV.setTypeface(arabic);
		} else if (current.getDisplayLanguage().equals("English")) {
			
			nameTV.setTypeface(english);
			passTV.setTypeface(english);
		}
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
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	public boolean isEmailValid(EditText et) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(
				et.getText().toString()).matches();
	}

	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}
}
