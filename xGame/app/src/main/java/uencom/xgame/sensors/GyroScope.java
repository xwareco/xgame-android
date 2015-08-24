package uencom.xgame.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public abstract class GyroScope implements SensorEventListener {

	// Variables
	private Sensor gyroSensor;
	private SensorManager manager;

	public GyroScope(Context ctx) {
		manager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
		gyroSensor = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		manager.registerListener(this, gyroSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onAccuracyChanged(Sensor sensorEvent, int arg1) {

	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		Sensor mySensor = sensorEvent.sensor;

		// if the sensor is gyroscope call the user code
		if (mySensor.getType() == Sensor.TYPE_GYROSCOPE) {
			onGyroChanged(sensorEvent);
		}

	}

	public void onGyroChanged(SensorEvent sensorEvent) {

	}

	public void onGyroPause() {
		manager.unregisterListener(this);
	}

	public void onGyroResume() {
		manager.registerListener(this, gyroSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

}
