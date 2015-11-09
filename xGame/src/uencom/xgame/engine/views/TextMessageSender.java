package uencom.xgame.engine.views;

import java.util.Locale;

import uencom.xgame.engine.web.User;
import uencom.xgame.xgame.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TextMessageSender extends Activity {

	EditText title, msg;
	TextView titleTV, msgTV;
	Button send;
	ImageView close;
	Typeface arabic, english;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.textmessenger);
		title = (EditText) findViewById(R.id.editText1);
		msg = (EditText) findViewById(R.id.editText2);
		titleTV = (TextView) findViewById(R.id.textView1);
		msgTV = (TextView) findViewById(R.id.textView2);
		close = (ImageView) findViewById(R.id.imageView1);
		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(getAssets(),
				"fonts/DJB Stinky Marker.ttf");
		Locale current = getResources().getConfiguration().locale;
		if (current.getDisplayLanguage().equals("Arabic")) {
			titleTV.setTypeface(arabic);
			msgTV.setTypeface(arabic);
		} else if (current.getDisplayLanguage().equals("English")) {
			titleTV.setTypeface(english);
			msgTV.setTypeface(english);
		}
		send = (Button) findViewById(R.id.button1);
		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String titleText = title.getText().toString();
				String Message = msg.getText().toString();

				if (titleText.equals("") || Message.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please fill in all the fields", Toast.LENGTH_LONG)
							.show();
				}

				else {
					
					SharedPreferences appSharedPrefs = PreferenceManager
							.getDefaultSharedPreferences(getApplicationContext());
					String id = appSharedPrefs.getString("uID", "");
					System.out.println("Iam IN!! " + id);
					new User(getApplicationContext(), titleText, Message, id,
							null).execute("msg");
					
					Toast.makeText(getApplicationContext(),
							"Your feedback has been successfully sent,Thank you for helping us enhancing xGame", Toast.LENGTH_LONG)
							.show();
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
