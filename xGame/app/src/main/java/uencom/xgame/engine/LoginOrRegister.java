package uencom.xgame.engine;

import uencom.xgame.xgame.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class LoginOrRegister extends SherlockActivity {
	ImageView login , register;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.login_or_register_view);
		login = (ImageView)findViewById(R.id.imageView1);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		register = (ImageView)findViewById(R.id.imageView2);
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent I = new Intent(getApplicationContext() , Login.class);
				startActivity(I);
			}
		});
		register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent I = new Intent(getApplicationContext() , Register.class);
				startActivity(I);
			}
		});
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
