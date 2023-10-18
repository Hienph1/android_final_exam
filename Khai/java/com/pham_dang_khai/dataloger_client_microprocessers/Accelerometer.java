package com.pham_dang_khai.dataloger_client_microprocessers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Accelerometer {
    public interface Listener{
        void onTranslation(float tx, float ty, float tz);
    }
    private Listener listener;
    public void setListener(Listener l){
        listener = l;
    }
    private final SensorManager sensorManager;
    private final Sensor sensor;
    private final SensorEventListener sensorEventListener;
    Accelerometer(Context context){
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (listener != null){
                    listener.onTranslation(event.values[0],event.values[1],event.values[2]);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
    public void register() {sensorManager.registerListener(sensorEventListener,sensor,100);}
    public void unregister() {sensorManager.unregisterListener(sensorEventListener);}
}
