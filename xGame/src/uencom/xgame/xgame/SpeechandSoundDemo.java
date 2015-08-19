package uencom.xgame.xgame;

/*import java.util.ArrayList;

import uencom.Xgame.sound.headPhone;
import uencom.Xgame.speech.speechRecognition;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class SpeechandSoundDemo extends Activity implements OnClickListener,
		android.content.DialogInterface.OnClickListener {

	TextView TV, TV2, TV3;
	speechRecognition engine = speechRecognition.getInstance();
	headPhone headPhone = new headPhone(this);
	int REQUST_CODE = 999;
	String lang = "ar-EG";
	AlertDialog dialog;
	AlertDialog.Builder buil;
	String[] languages = { "Arabic", "English" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.speechandsounddemo);
		TV = (TextView) findViewById(R.id.textView1);
		TV.setOnClickListener(this);
		TV2 = (TextView) findViewById(R.id.textView2);
		TV2.setOnClickListener(this);
		TV3 = (TextView) findViewById(R.id.textView3);
		TV3.setOnClickListener(this);
		buil = new AlertDialog.Builder(this);
		buil.setTitle("Select your language");
		buil.setItems(languages, this);

		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.textView1:
			// headPhone.play(R.raw.helicopter2, 2);
			if (headPhone.detectHeadPhones() == false)
				Toast.makeText(this, " √ﬂœ „‰  Ê’Ì· ”„«⁄«  «·√–‰",
						Toast.LENGTH_LONG).show();
			else {
				dialog = buil.create();
				dialog.show();
				
			}
			break;

		case R.id.textView2:
			headPhone.play(R.raw.helicopter, 3);
			break;

		case R.id.textView3:
			headPhone.play(R.raw.helicopter2, 2);
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// check speech recognition result
		if (requestCode == REQUST_CODE && resultCode == RESULT_OK) {
			// store the returned word list as an ArrayList
			ArrayList<String> suggestedWords = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

			ProcessWords(suggestedWords);
		} else
			Toast.makeText(this, "·„ Ì „ «· ⁄—› ⁄·Ï √Ì ﬂ·„« ",
					Toast.LENGTH_LONG).show();
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void ProcessWords(ArrayList<String> suggestedWords) {

		for (String word : suggestedWords) {

			if (word.equals("Ì„Ì‰") || word.equals("right")) {
				Toast.makeText(getApplicationContext(), word, Toast.LENGTH_LONG)
						.show();
				headPhone.setLeftLevel(0f);
				headPhone.setRightLevel(1f);
				headPhone.play(R.raw.gameover, 0);
			} else if (word.equals("Ì”«—") || word.equals("left")) {
				Toast.makeText(getApplicationContext(), word, Toast.LENGTH_LONG)
						.show();
				headPhone.setRightLevel(0f);
				headPhone.setLeftLevel(1f);
				headPhone.play(R.raw.gameover, 0);
			} else if (word.equals("«Œ »«—") || word.equals("test")) {
				Toast.makeText(getApplicationContext(), word, Toast.LENGTH_LONG)
						.show();
				headPhone.setRightLevel(1f);
				headPhone.setLeftLevel(1f);
				headPhone.play(R.raw.gameover, 0);
			}

		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {

		case 0:
			lang = "ar-EG";
			TV.setText("«÷€ÿ ·»œ¡ «·√Ê«„—.·«Œ »«— ”„«⁄… «·√–‰ «·Ì„‰Ï ﬁ· (Ì„Ì‰) Ê·«Œ »«— ”„«⁄… «·√–‰ «·Ì”—Ï ﬁ· (Ì”«—) Ê·«Œ »«— ﬂ· « «·”„«⁄ Ì‰ ﬁ· («Œ »«—).");
			TV2.setText("«÷€ÿ Â‰« ·«Œ »«— «— ›«⁄ Ê«‰Œ›«÷ „” ÊÏ «·’Ê  „‰ «·Ì„Ì‰ ··Ì”«—");
			TV3.setText("«÷€ÿ Â‰« ·«Œ »«— «— ›«⁄ Ê«‰Œ›«÷ „” ÊÏ «·’Ê  „‰ «·Ì”«— ··Ì„Ì‰");
			break;

		case 1:
			lang = "en-US";
			TV.setText("«Press here to start the speech commands. To test the right headset say (right) , To test the left headset say (left), To test both headsets together say (test).");
			TV2.setText("Press here to test sound leveling from right to left");
			TV3.setText("Press here to test sound leveling from left to right");

			break;
		}
		
		engine.initRecognition(10, "Speak now", lang);
		startActivityForResult(engine.getListenIntent(), REQUST_CODE);

	}

	
}*/
