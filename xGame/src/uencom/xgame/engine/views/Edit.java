package uencom.xgame.engine.views;

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

public class Edit extends Activity {
	
	TextView email, newEmail;
	ImageView edit;
	Button forgot, update;
	EditText newMail;
	Typeface arabic, english;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.edit);
		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(getAssets(),
				"fonts/DJB Stinky Marker.ttf");
		SharedPreferences appSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		email = (TextView)findViewById(R.id.textView1);
		newEmail = (TextView)findViewById(R.id.textView2);
		email.setTypeface(english);
		newEmail.setTypeface(english);
		email.setText(appSharedPrefs.getString("uName", ""));
		
		edit = (ImageView)findViewById(R.id.imageView1);
		forgot = (Button)findViewById(R.id.button2);
		update = (Button)findViewById(R.id.button1);
		
		newMail = (EditText)findViewById(R.id.editText1);
		
		edit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				newEmail.setVisibility(View.VISIBLE);
				newMail.setVisibility(View.VISIBLE);
				update.setVisibility(View.VISIBLE);
				
			}
		});
		super.onCreate(savedInstanceState);
	}

}
