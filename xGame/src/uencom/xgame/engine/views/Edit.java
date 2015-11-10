package uencom.xgame.engine.views;

import uencom.xgame.engine.web.User;
import uencom.xgame.xgame.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Edit extends Activity {

	TextView email, newEmail, pass;
	ImageView edit;
	Button forgot, update;
	EditText newMail, passEditText;
	Typeface arabic, english;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.edit);
		
		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(getAssets(),
				"fonts/DJB Stinky Marker.ttf");
		final SharedPreferences appSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		System.out.println(appSharedPrefs.getString("uPass", ""));
		email = (TextView) findViewById(R.id.textView1);
		newEmail = (TextView) findViewById(R.id.textView2);
		pass = (TextView) findViewById(R.id.textView3);
		// email.setTypeface(english);
		newEmail.setTypeface(english);
		pass.setTypeface(english);
		email.setText(appSharedPrefs.getString("uName", ""));

		edit = (ImageView) findViewById(R.id.imageView1);
		forgot = (Button) findViewById(R.id.button2);
		update = (Button) findViewById(R.id.button1);

		newMail = (EditText) findViewById(R.id.editText1);
		passEditText = (EditText) findViewById(R.id.editText2);

		edit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
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
								.equals(appSharedPrefs.getString("uPass", "")) && isEmailValid(newMail) == true) {
					new User(Edit.this, newMail.getText().toString(),
							appSharedPrefs.getString("uPass", ""),
							appSharedPrefs.getString("uID", ""), null, email).execute("change");
					newEmail.setText("");
					passEditText.setText("");
				}

				else {
					Toast.makeText(Edit.this,
							"Empty field(s), wrong password, or invalid Email format",
							Toast.LENGTH_LONG).show();
				}

			}
		});
		super.onCreate(savedInstanceState);
	}
	
	public boolean isEmailValid(EditText et) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(
				et.getText().toString()).matches();
	}

}
