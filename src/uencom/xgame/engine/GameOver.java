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

public class GameOver extends SherlockActivity {
	ImageView login;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.game_over_view);
		login = (ImageView)findViewById(R.id.imageView4);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Login,Register activity open
				Intent I = new Intent(getApplicationContext() , LoginOrRegister.class);
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
