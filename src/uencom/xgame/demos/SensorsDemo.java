package uencom.xgame.demos;

import uencom.xgame.sensors.Accelerometer;
import uencom.xgame.sensors.GPS;
import uencom.xgame.sensors.GyroScope;
import uencom.xgame.xgame.R;
import android.app.Activity;
import android.hardware.SensorEvent;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

public class SensorsDemo extends Activity {

	TextView GPS_TV, ACC_TV, GYRO_TV;
	GPS gps;
	Accelerometer Acc;
	GyroScope gyro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.sensorsdemo);
		GPS_TV = (TextView) findViewById(R.id.textView1);
		ACC_TV = (TextView) findViewById(R.id.textView2);
		GYRO_TV = (TextView) findViewById(R.id.textView3);
		initGPS();
		initAcc();
		initGyro();
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onPause() {
		gps.onGPSPause();
		Acc.onAccelerometerPause();
		gyro.onGyroPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		gps.onGPSResume();
		Acc.onAccelerometerResume();
		gyro.onGyroResume();
		super.onResume();
	}

	private void initGPS() {
		gps = new GPS(this) {

			@Override
			public void onBestLocationFound(Location bestCurrentLocation2) {
				if (gps.getAddress() != null) {
					GPS_TV.setText("Your current address is: "
							+ gps.getAddress());
				}
				super.onBestLocationFound(bestCurrentLocation2);
			}
		};

	}

	private void initAcc() {
		Acc = new Accelerometer(this) {
			@Override
			public void onAccelerometerChanged(SensorEvent sensorEvent) {

				float x = sensorEvent.values[0];
				float y = sensorEvent.values[1];
				float z = sensorEvent.values[2];

				ACC_TV.setText("Accelerometer Data=> X position: " + x
						+ " Y position: " + y + " Z position: " + z);
				super.onAccelerometerChanged(sensorEvent);
			}
		};

	}

	private void initGyro() {
		gyro = new GyroScope(this) {
			@Override
			public void onGyroChanged(SensorEvent sensorEvent) {
				float x = sensorEvent.values[0];
				float y = sensorEvent.values[1];
				float z = sensorEvent.values[2];

				GYRO_TV.setText("GyroScope Data=> X position: " + x
						+ " Y position: " + y + " Z position: " + z);

				super.onGyroChanged(sensorEvent);
			}
		};

	}
}
