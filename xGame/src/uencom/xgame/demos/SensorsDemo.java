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
import android.widget.Toast;

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
		Acc = new Accelerometer(this, 2000f, 2000f, 2000f) {
			@Override
			public void onXGoodLeft() {
				Toast.makeText(getApplicationContext(), "Good left turn", Toast.LENGTH_LONG).show();
				
			}
			@Override
			public void onXGoodRight() {
				Toast.makeText(getApplicationContext(), "Good right turn", Toast.LENGTH_LONG).show();
				
			}
			@Override
			public void onXHugeLeft() {
				Toast.makeText(getApplicationContext(), "Huge left turn", Toast.LENGTH_LONG).show();
				
			}
			@Override
			public void onXHugeRight() {
				Toast.makeText(getApplicationContext(), "Huge Right turn", Toast.LENGTH_LONG).show();
				
			}
			@Override
			public void onZGoodLeft() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onZGoodRight() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onZHugeRight() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onZHugeLeft() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onYGoodDown() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onYGoodUp() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onYHugeUp() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onYHugeDown() {
				// TODO Auto-generated method stub
				
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
