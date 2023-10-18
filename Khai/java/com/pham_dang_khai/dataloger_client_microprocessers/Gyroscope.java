package com.pham_dang_khai.dataloger_client_microprocessers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Gyroscope {
    public interface Listener {
        void onRotation(float gx, float gy, float gz);
    }

    private Gyroscope.Listener listener;

    public void setListener(Gyroscope.Listener l) {
        listener = l;
    }

    private final SensorManager sensorManager;
    private final Sensor sensor;
    private final SensorEventListener sensorEventListener;

    Gyroscope(Context context) {
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (listener != null) {
                    listener.onRotation(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    public void register() {
        sensorManager.registerListener(sensorEventListener, sensor, 100);
    }

    public void unregister() {
        sensorManager.unregisterListener(sensorEventListener);
    }
}
