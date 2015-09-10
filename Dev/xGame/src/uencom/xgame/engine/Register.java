package uencom.xgame.engine;

import uencom.xgame.engine.web.User;
import uencom.xgame.xgame.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class Register extends SherlockActivity {
	
	LinearLayout reg;
	ImageView regapi;
	EditText name , pass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.register_view);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    reg = (LinearLayout)findViewById(R.id.reglay);
	    name = (EditText)findViewById(R.id.editText1);
	    pass = (EditText)findViewById(R.id.editText2);
	    regapi = (ImageView)findViewById(R.id.imageView1);
	    regapi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new User(getApplicationContext(), name.getText().toString(), pass.getText().toString()).execute("register");
				
			}
		});
	    Animation a = AnimationUtils.loadAnimation(this, R.anim.transition4);
	    reg.startAnimation(a);
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
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent I = new Intent(this , MainView.class);
			startActivity(I);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
