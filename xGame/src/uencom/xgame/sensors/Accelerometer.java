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
	private long last_update;
	private float xMin, yMin, xMax, yMax, lastX, lastY;

	public Accelerometer(Context ctx, float x, float y, float z) {
		// initialize everything
		manager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
		accelerometerSensor = manager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		manager.registerListener(this, accelerometerSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		last_update = 0;
		xMin = 0;
		yMin = -75f;
		xMax = x;
		yMax = y;
		lastX = 100;
		lastY = 100;
	}

	@Override
	public void onAccuracyChanged(Sensor sensorEvent, int arg1) {

	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		Sensor mySensor = sensorEvent.sensor;
		// if the sensor used is the Accelerometer call the user code
		if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			// onAccelerometerChanged(sensorEvent);
			float x = sensorEvent.values[0];
			float y = sensorEvent.values[1] * 10;
			// System.out.println(y);
			// float z = sensorEvent.values[2];
			long curTime = System.currentTimeMillis();

			if ((curTime - last_update) > 800) {
				if (x <= xMin)
					onXHugeLeft();
				else if (x >= xMax)
					onXHugeRight();
				else if (x > lastX && x < xMax)
					onXGoodRight();
				else if (x < lastX && x > xMin)
					onXGoodLeft();

				if (y <= yMin) {
					onYHugeLeft();

				} else if (y >= yMax) {
					onYHugeRight();
				} else if ((y - lastY) >= 5 && y < yMax) {
					onYGoodRight();
				} else if ((lastY - y) >= 5 && y > yMin) {
					onYGoodLeft();
				}

				lastX = x;
				lastY = y;
				last_update = curTime;
			}
		}

	}

	public abstract void onZGoodLeft();

	public abstract void onZGoodRight();

	public abstract void onZHugeRight();

	public abstract void onZHugeLeft();

	public abstract void onYGoodLeft();

	public abstract void onYGoodRight();

	public abstract void onYHugeRight();

	public abstract void onYHugeLeft();

	public abstract void onXGoodLeft();

	public abstract void onXGoodRight();

	public abstract void onXHugeRight();

	public abstract void onXHugeLeft();

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
