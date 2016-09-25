package xware.xgame.engine.views;

import java.util.Locale;

import xware.xgame.engine.web.User;
import xware.xgame.xgame.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TextMessageSender extends Activity {

	EditText title, msg;
	TextView titleTV, msgTV;
	Button send, close;
	Typeface arabic, english;
	SharedPreferences appSharedPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.textmessenger);
		appSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(TextMessageSender.this);
		getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		title = (EditText) findViewById(R.id.editText1);
		msg = (EditText) findViewById(R.id.editText2);
		titleTV = (TextView) findViewById(R.id.textView1);
		msgTV = (TextView) findViewById(R.id.textView2);
		close = (Button) findViewById(R.id.button2);
		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(getAssets(),
				"fonts/klavika-regular-opentype.otf");
		String lang = appSharedPrefs.getString("Lang","EN");
		if (lang.equals("AR")) {
			titleTV.setTypeface(arabic);
			msgTV.setTypeface(arabic);
			titleTV.setGravity(Gravity.RIGHT);
			msgTV.setGravity(Gravity.RIGHT);
		} else if (lang.equals("EN")) {
			titleTV.setTypeface(english);
			msgTV.setTypeface(english);
			titleTV.setGravity(Gravity.LEFT);
			msgTV.setGravity(Gravity.LEFT);
		}
		send = (Button) findViewById(R.id.button1);
		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String titleText = title.getText().toString();
				String Message = msg.getText().toString();
				String lang = appSharedPrefs.getString("Lang", "EN");
				if (titleText.equals("") || Message.equals("")) {
					String textToToast = "Please fill in all the fields";

					if(lang.equals("AR"))
						textToToast = "من فضلك قم بملئ كافة البيانات";
					Toast.makeText(TextMessageSender.this,textToToast,
							Toast.LENGTH_LONG).show();
				}

				else {

					SharedPreferences appSharedPrefs = PreferenceManager
							.getDefaultSharedPreferences(getApplicationContext());
					String id = appSharedPrefs.getString("uID", "");
					System.out.println("Iam IN!! " + id);
					new User(getApplicationContext(), titleText, Message, id,
							null, null, null, null).execute("msg");
					
					String textToToast = "Thank you for contacting us. We will get back to you shortly";
					if(lang.equals("AR"))
						textToToast = "شكرا ﻻتصالك بنا..رأيك مهم لنا";
					Toast.makeText(TextMessageSender.this,textToToast,
							Toast.LENGTH_LONG).show();
					finish();

				}

			}
		});
		close.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		super.onCreate(savedInstanceState);
	}

}
