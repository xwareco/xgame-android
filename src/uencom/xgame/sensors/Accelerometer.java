package uencom.xgame.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public abstract class Accelerometer implements SensorEventListener {

	// Variables
	private Sensor accelerometerSensor;
	private SensorManager manager;

	public Accelerometer(Context ctx) {
		// initialize everything
		manager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
		accelerometerSensor = manager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		manager.registerListener(this, accelerometerSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onAccuracyChanged(Sensor sensorEvent, int arg1) {

	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		Sensor mySensor = sensorEvent.sensor;
		// if the sensor used is the Accelerometer call the user code
		if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			onAccelerometerChanged(sensorEvent);
		}

	}

	public void onAccelerometerChanged(SensorEvent sensorEvent) {

	}

	public void onAccelerometerPause() {
		// unregister the listener to save power (must be called on activity on
		// pause)
		manager.unregisterListener(this);
	}

	public void onAccelerometerResume() {
		// register the listener again (must be called on activity on resume)
		manager.registerListener(this, accelerometerSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

}
