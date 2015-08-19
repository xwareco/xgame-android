package uencom.xgame.engine;

import uencom.xgame.xgame.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class GameOver extends SherlockActivity {
	ImageView login;
	Button home , playAgain;
	LinearLayout gamescore;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.game_over_view);
		login = (ImageView)findViewById(R.id.imageView3);
		home = (Button)findViewById(R.id.button2);
		playAgain = (Button)findViewById(R.id.button1);
		gamescore = (LinearLayout)findViewById(R.id.gslay);
		Animation a = AnimationUtils.loadAnimation(this, R.anim.transition4);
		gamescore.startAnimation(a);
		ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Login,Register activity open
				Intent I = new Intent(getApplicationContext() , Register.class);
				startActivity(I);
			}
		});
		home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent I = new Intent(getApplicationContext() , SplashActivity.class);
				startActivity(I);
				
			}
		});
		super.onCreate(savedInstanceState);
		playAgain.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
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
	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}
}
