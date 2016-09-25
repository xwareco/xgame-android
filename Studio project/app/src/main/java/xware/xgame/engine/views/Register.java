package xware.xgame.engine.views;

import java.util.Locale;
import xware.xgame.engine.web.User;
import xware.xgame.xgame.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity {

	EditText name, pass;
	TextView nameTV, passTV;
	Typeface arabic, english;
	Button reg, login;
	Toolbar actionBar;
	SharedPreferences appSharedPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_view);
		appSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(Register.this);
		String lang = appSharedPrefs.getString("Lang","EN");
		actionBar = (Toolbar)findViewById(R.id.my_toolbar);
		//setSupportActionBar(actionBar);
		getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(getAssets(),
				"fonts/klavika-regular-opentype.otf");
		reg = (Button) findViewById(R.id.button2);
		login = (Button) findViewById(R.id.button1);
		name = (EditText) findViewById(R.id.editText1);
		pass = (EditText) findViewById(R.id.editText2);
		nameTV = (TextView) findViewById(R.id.textView2);
		passTV = (TextView) findViewById(R.id.textView3);
		//forgot = (TextView) findViewById(R.id.textView1);
		reg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!name.getText().toString().equals("")
						&& !pass.getText().toString().equals("")
						&& isEmailValid(name)) {
					new User(Register.this, name.getText().toString(), pass
							.getText().toString(), getIntent().getStringExtra(
							"TAG"), null, null, null, null).execute("register");

				} else {
					String textToToast = "Empty field(s), wrong password, or invalid Email format";
					String lang = appSharedPrefs.getString("Lang","EN");
					if (lang.equals("AR"))
						textToToast = "حدث خطأ..تأكد من ملئ جميع البيانات وكتابة بريدك اﻻلكتروني بطريقة صحيحة";
					Toast.makeText(Register.this, textToToast,
							Toast.LENGTH_LONG).show();
				}

			}
		});
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!name.getText().toString().equals("")
						&& !pass.getText().toString().equals("")
						&& isEmailValid(name)) {
					new User(Register.this, name.getText().toString(), pass
							.getText().toString(), getIntent().getStringExtra(
							"TAG"), null, null, null, null).execute("login");

				} else {
					String textToToast = "Empty field(s), wrong password, or invalid Email format";
					String lang = appSharedPrefs.getString("Lang","EN");
					if (lang.equals("AR"))
						textToToast = "حدث خطأ..تأكد من ملئ جميع البيانات وكتابة بريدك اﻻلكتروني بطريقة صحيحة";
					Toast.makeText(Register.this, textToToast,
							Toast.LENGTH_LONG).show();
				}

			}
		});
		/*forgot.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!name.getText().toString().equals("") && isEmailValid(name)) {
					new User(Register.this, null, appSharedPrefs.getString(
							"uPass", ""), appSharedPrefs.getString("uID", ""),
							null, null, null, null).execute("passRem");
				}
				else
				{
					String textToToast = "Empty email field, or invalid Email format";
					if (lang.equals("AR"))
						textToToast = "حدث خطأ..تأكد من كتابة بريدك اﻻلكتروني بطريقة صحيحة";
					Toast.makeText(Register.this, textToToast,
							Toast.LENGTH_LONG).show();
				}

			}
		});*/
		if (lang.equals("AR")) {

			nameTV.setTypeface(arabic);
			passTV.setTypeface(arabic);
			nameTV.setGravity(Gravity.RIGHT);
			passTV.setGravity(Gravity.RIGHT);
		} else if (lang.equals("EN")) {

			nameTV.setTypeface(english);
			passTV.setTypeface(english);
			nameTV.setGravity(Gravity.LEFT);
			passTV.setGravity(Gravity.LEFT);
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
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
