package uencom.xgame.demos;

import uencom.xgame.sound.TTS;
import uencom.xgame.xgame.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TTSDemo extends Activity implements OnClickListener,
		android.content.DialogInterface.OnClickListener {

	EditText input;
	Button startTTS;
	float duration;
	String lang;
	AlertDialog dialog;
	AlertDialog.Builder buil;
	String[] languages = { "Arabic", "English" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.ttsdemo);
		input = (EditText) findViewById(R.id.editText1);
		startTTS = (Button) findViewById(R.id.button1);
		startTTS.setOnClickListener(this);
		buil = new AlertDialog.Builder(this);
		buil.setTitle("Select your language");
		buil.setItems(languages, this);
		lang = "ar";
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.button1:
			if (!input.getText().toString().equals("")) {
				dialog = buil.create();
				dialog.show();

			}
			break;

		default:
			break;
		}

	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		switch (which) {

		case 0:
			lang = "ar";
			break;

		case 1:
			lang = "en";
			break;
		}

		
		new TTS(getApplicationContext(), lang).execute(input.getText()
				.toString());
		

	}

}
