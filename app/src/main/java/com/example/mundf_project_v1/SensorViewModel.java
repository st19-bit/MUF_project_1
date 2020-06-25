package com.example.mundf_project_v1;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SensorViewModel extends ViewModel {

    private final static class AccelerationLiveData extends LiveData<AccelerationInformation>{
        // <generischer Datentyp> -> eigene Klasse
        private final AccelerationInformation accelerationInformation = new AccelerationInformation();
        private SensorManager sensorManager;
        private Sensor accelerationSensor;
        private Sensor gravitySensor;
        private float[] gravity;

        // Listener für Sensor:
        private SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        float[] values = removeGravity(gravity, event.values);
                        accelerationInformation.setXYZ(values[0], values[1], values[2]);
                        accelerationInformation.setSensor(event.sensor);
                        setValue(accelerationInformation);
                        break;
                    case Sensor.TYPE_GRAVITY:
                        gravity = event.values;
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        AccelerationLiveData(Context context){
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            // sensorManager kann leer sein:
            if (sensorManager != null){
                // Sensoren instanzieren:
                accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
            }else{
                // sollte eigentlich nicht hier rein gehen
                throw new RuntimeException("Hoppla...");
            }
        }

        @Override
        protected void onActive() {
            super.onActive();
            // Listener registrieren: (wenn LiveCycle on Resume)
            sensorManager.registerListener(listener, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(listener, accelerationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        protected void onInactive() {
            super.onInactive();
            // Listener unregistrieren: (wenn LiveCyle on Pause)
            sensorManager.unregisterListener(listener);
        }

        private float[] removeGravity(float[] gravity, float[] values) {

            if (gravity == null) {
                return values;
            }
            final float alpha = 0.8f;
            float g[] = new float[3];
            g[0] = alpha * gravity[0] + (1 - alpha) * values[0];
            g[1] = alpha * gravity[2] + (1 - alpha) * values[1];
            g[2] = alpha * gravity[2] + (1 - alpha) * values[2];
            // liefert Acceleration-Werte ohne Gravitationsbeschleunigung
            return new float[]{
                    values[0] - g[0],
                    values[1] - g[1],
                    values[2] - g[2]
            };
        };
    }


}
