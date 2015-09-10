package uencom.xgame.engine;

import uencom.xgame.xgame.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends Activity {
	
	TextView label;
	ImageView logo , error , refresh;
	ProgressBar bar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.splash_activity);
		label = (TextView)findViewById(R.id.textView1);
		error = (ImageView)findViewById(R.id.imageView1);
		logo = (ImageView)findViewById(R.id.imageView2);
		refresh = (ImageView)findViewById(R.id.imageView3);
		refresh.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				bar.setVisibility(View.VISIBLE);
				logo.setVisibility(View.GONE);
				error.setVisibility(View.GONE);
				refresh.setVisibility(View.GONE);
				label.setGravity(Gravity.CENTER_HORIZONTAL);
				label.setText("Loading data");
				new Server(getApplicationContext() , label , logo , error , refresh ,bar).execute();
			}
		});
		bar = (ProgressBar)findViewById(R.id.progressBar1);
		final Animation a = AnimationUtils.loadAnimation(this, R.anim.transition3);
		
		a.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				
				logo.setVisibility(View.GONE);
				label.setVisibility(View.VISIBLE);
				bar.setVisibility(View.VISIBLE);
				new Server(getApplicationContext() , label , logo , error , refresh ,bar).execute();
			}
		});
		
		
		new Handler().postDelayed(new Runnable()
		{
		   @Override
		   public void run()
		   {
			   logo.startAnimation(a);
		   }
		}, 700);
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onPause() {
		finish();
		super.onPause();
	}

}
