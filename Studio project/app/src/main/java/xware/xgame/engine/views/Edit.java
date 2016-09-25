package xware.xgame.engine.views;

import java.util.Locale;
import xware.xgame.engine.web.User;
import xware.xgame.xgame.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Edit extends Activity {

	TextView email, newEmail, pass;
	ImageView edit;
	Button update, logout;
	EditText newMail, passEditText;
	Typeface arabic, english;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit);

		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(getAssets(),
				"fonts/klavika-regular-opentype.otf");

		final SharedPreferences appSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		final Editor prefsEditor = appSharedPrefs.edit();
		System.out.println(appSharedPrefs.getString("uPass", ""));
		email = (TextView) findViewById(R.id.textView1);
		newEmail = (TextView) findViewById(R.id.textView2);
		pass = (TextView) findViewById(R.id.textView3);
		// email.setTypeface(english);
		newEmail.setTypeface(english);
		pass.setTypeface(english);
		email.setText(appSharedPrefs.getString("uName", ""));

		edit = (ImageView) findViewById(R.id.imageView1);
		logout = (Button) findViewById(R.id.button2);
		update = (Button) findViewById(R.id.button1);

		newMail = (EditText) findViewById(R.id.editText1);
		passEditText = (EditText) findViewById(R.id.editText2);
		String lang = appSharedPrefs.getString("Lang","EN");
		if (lang.equals("AR")) {
			newEmail.setTypeface(arabic);
			pass.setTypeface(arabic);
			newEmail.setGravity(Gravity.RIGHT);
			pass.setGravity(Gravity.RIGHT);
		} else if (lang.equals("EN")) {
			newEmail.setTypeface(english);
			pass.setTypeface(english);
			newEmail.setGravity(Gravity.LEFT);
			pass.setGravity(Gravity.LEFT);
		}
		edit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
						ViewGroup.LayoutParams.MATCH_PARENT);

				newEmail.setVisibility(View.VISIBLE);
				newMail.setVisibility(View.VISIBLE);
				update.setVisibility(View.VISIBLE);
				pass.setVisibility(View.VISIBLE);
				passEditText.setVisibility(View.VISIBLE);

			}
		});

		update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!newMail.getText().toString().equals("")
						&& !passEditText.getText().toString().equals("")
						&& passEditText.getText().toString()
								.equals(appSharedPrefs.getString("uPass", ""))
						&& isEmailValid(newMail) == true) {
					new User(Edit.this, newMail.getText().toString(),
							appSharedPrefs.getString("uPass", ""),
							appSharedPrefs.getString("uID", ""), null, null,
							null, email).execute("change");
					newEmail.setText("");
					passEditText.setText("");
				}

				else {
					String textToToast = "Empty field(s), wrong password, or invalid Email format";
					String lang = appSharedPrefs.getString("Lang","EN");
					if (lang.equals("AR"))
						textToToast = "حدث خطأ..تأكد من ملئ جميع البيانات وكتابة بريدك اﻻلكتروني بطريقة صحيحة";
					Toast.makeText(Edit.this, textToToast, Toast.LENGTH_LONG)
							.show();
				}

			}
		});

		logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				prefsEditor.putString("uID", "");
				prefsEditor.putString("uName", "");
				prefsEditor.putString("uPass", "");
				prefsEditor.commit();
				String textToToast = "Successfully loged out";
				String lang = appSharedPrefs.getString("Lang","EN");
				if (lang.equals("AR"))
					textToToast = "تم تسجيل الخروج بنجاح";
				Toast.makeText(Edit.this, textToToast, Toast.LENGTH_LONG)
						.show();
                finish();
			}
		});

		super.onCreate(savedInstanceState);
	}

	public boolean isEmailValid(EditText et) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(
				et.getText().toString()).matches();
	}

}
