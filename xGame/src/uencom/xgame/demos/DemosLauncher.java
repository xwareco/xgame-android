package uencom.xgame.demos;


import uencom.xgame.engine.xGameParser;
import uencom.xgame.xgame.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DemosLauncher extends Activity implements OnClickListener {

	Button speechDemo, gesturesDemo, sensorsDemo, TTSDemo, XMLDemo;
	String lang = "EN";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.demoslancher);
		speechDemo = (Button) findViewById(R.id.button1);
		speechDemo.setOnClickListener(this);
		gesturesDemo = (Button) findViewById(R.id.button2);
		gesturesDemo.setOnClickListener(this);
		sensorsDemo = (Button) findViewById(R.id.button3);
		sensorsDemo.setOnClickListener(this);
		TTSDemo = (Button) findViewById(R.id.button4);
		TTSDemo.setOnClickListener(this);
		XMLDemo = (Button) findViewById(R.id.button5);
		XMLDemo.setOnClickListener(this);

		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.button1:
			//Intent I = new Intent(this, SpeechandSoundDemo.class);
			//startActivity(I);
			break;

		case R.id.button2:
			Intent I2 = new Intent(this, GesturesDemo.class);
			startActivity(I2);
			break;

		case R.id.button3:
			Intent I3 = new Intent(this, SensorsDemo.class);
			startActivity(I3);
			break;

		case R.id.button4:
			Intent I4 = new Intent(this, TTSDemo.class);
			startActivity(I4);
			break;

		case R.id.button5:
			Intent I5 = new Intent(this, xGameParser.class);
			startActivity(I5);
			break;
		}

	}

}
