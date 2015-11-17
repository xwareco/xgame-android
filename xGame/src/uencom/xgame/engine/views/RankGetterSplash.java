package uencom.xgame.engine.views;

import uencom.xgame.engine.web.User;
import uencom.xgame.xgame.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class RankGetterSplash extends Activity {

	SharedPreferences appPrefs;
	String gameID, gameName, folder;
	String userID;
	int score;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.ranksplash);
		gameID = getIntent().getStringExtra("gameid");
		System.out.println(gameID);
		appPrefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		userID = appPrefs.getString("uID", "");
		score = getIntent().getIntExtra("Score", 0);
		Toast.makeText(this, "Score = " + score, Toast.LENGTH_LONG).show();
		folder = getIntent().getStringExtra("Folder");
		gameName = getIntent().getStringExtra("gamename");
		String s = String.valueOf(score);
		new User(RankGetterSplash.this, gameID, s, userID, folder, gameName,
				null, null).execute("score");
		super.onCreate(savedInstanceState);

	}

	@Override
	protected void onPause() {
		finish();
		overridePendingTransition(R.anim.transition10, R.anim.transition9);
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.transition8, R.anim.transition7);
		super.onBackPressed();
	}

}
