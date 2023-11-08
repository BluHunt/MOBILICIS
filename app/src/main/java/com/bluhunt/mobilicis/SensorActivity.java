package com.bluhunt.mobilicis;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mGyroscope;
    private Sensor mBarometer;
    private Sensor mAccelerometer;
    private Sensor mRotationVector;
    private Sensor mProximity;
    private Sensor mAmbientLight;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DeviceInfo deviceInfo = new DeviceInfo(SensorActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        findViewById(R.id.back).setOnClickListener(view -> onBackPressed());

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mBarometer = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mRotationVector = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mAmbientLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        TextView Gyroscope = findViewById(R.id.Gyroscope);
        Gyroscope.setText("Gyroscope : "+ mGyroscope);
        mDatabase.child(deviceInfo.getModel()).child("Live Sensor")
                .child("Gyroscope").setValue(mGyroscope);

        TextView Barometer = findViewById(R.id.Barometer);
        Barometer.setText("Barometer : "+ mBarometer);
        mDatabase.child(deviceInfo.getModel()).child("Live Sensor")
                .child("Barometer").setValue(mBarometer);

        TextView Accelerometer = findViewById(R.id.Accelerometer);
        Accelerometer.setText("Accelerometer : "+ mAccelerometer);
        mDatabase.child(deviceInfo.getModel()).child("Live Sensor")
                .child("Accelerometer").setValue(mAccelerometer);

        TextView RotationVector = findViewById(R.id.RotationVector);
        RotationVector.setText("RotationVector : "+ mRotationVector);
        mDatabase.child(deviceInfo.getModel()).child("Live Sensor")
                .child("RotationVector").setValue(mRotationVector);

        TextView Proximity = findViewById(R.id.Proximity);
        Proximity.setText("Proximity : "+ mProximity);
        mDatabase.child(deviceInfo.getModel()).child("Live Sensor")
                .child("Proximity").setValue(mProximity);

        TextView AmbientLight = findViewById(R.id.AmbientLight);
        AmbientLight.setText("AmbientLight : "+ mAmbientLight);
        mDatabase.child(deviceInfo.getModel()).child("Live Sensor")
                .child("AmbientLight").setValue(mAmbientLight);
        Flag.setSensorImageCheck(Boolean.TRUE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mBarometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mRotationVector, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mAmbientLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float[] sensorValues = event.values;
        switch (sensorType) {
            case Sensor.TYPE_GYROSCOPE:
                // Handle gyroscope sensor values
                break;
            case Sensor.TYPE_PRESSURE:
                // Handle barometer sensor values
                break;
            case Sensor.TYPE_ACCELEROMETER:
                // Handle accelerometer sensor values
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                // Handle rotation vector sensor values
                break;
            case Sensor.TYPE_PROXIMITY:
                // Handle proximity sensor values
                break;
            case Sensor.TYPE_LIGHT:
                // Handle ambient light sensor values
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}