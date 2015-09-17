package uencom.xgame.engine;

import java.util.Locale;

import uencom.xgame.engine.web.User;
import uencom.xgame.xgame.R;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class Register extends SherlockActivity {
	
	LinearLayout reg;
	ImageView regapi;
	EditText name , pass;
	TextView title, nameTV , passTV;
	Typeface arabic, english;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.register_view);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		arabic = Typeface.createFromAsset(getAssets(),
				"fonts/Kharabeesh Font.ttf");
		english = Typeface.createFromAsset(getAssets(),
				"fonts/DJB Stinky Marker.ttf");
		Locale current = getResources().getConfiguration().locale;
		
	    reg = (LinearLayout)findViewById(R.id.reglay);
	    name = (EditText)findViewById(R.id.editText1);
	    pass = (EditText)findViewById(R.id.editText2);
	    title = (TextView)findViewById(R.id.textView1);
	    nameTV = (TextView)findViewById(R.id.textView2);
	    passTV = (TextView)findViewById(R.id.textView3);
	    regapi = (ImageView)findViewById(R.id.imageView1);
	    regapi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new User(getApplicationContext(), name.getText().toString(), pass.getText().toString()).execute("register");
				
			}
		});
	    Animation a = AnimationUtils.loadAnimation(this, R.anim.transition4);
	    reg.startAnimation(a);
	    if (current.getDisplayLanguage().equals("Arabic")) {
			title.setTypeface(arabic);
			nameTV.setTypeface(arabic);
			passTV.setTypeface(arabic);
		} else if (current.getDisplayLanguage().equals("English")) {
			title.setTypeface(english);
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
		if (item.getItemId() == android.R.id.home) {

			if (mDrawerLayout.isDrawerOpen(rellay)) {
				mDrawerLayout.closeDrawer(rellay);
			} else {
				mDrawerLayout.openDrawer(rellay);
			}
		}*/

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
}
