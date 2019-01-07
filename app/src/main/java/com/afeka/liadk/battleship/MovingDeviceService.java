package com.afeka.liadk.battleship;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MovingDeviceService extends Service implements SensorEventListener {

    private SensorManager mSensorManager;
    private final IBinder mBinder = new MovingDeviceBinder();
    private Sensor mSensor;
    private boolean mFirst, isMoved, called;
    private double mX, mY, mZ;
    final private double MAX_DELTA = 0.3;
    private long mStartMove, afterCall;
    final private long MAX_TIME_SEC = 3, MAX_TIME_AFTER_CALL = 2;
    private static final float NS2S = 1.0f / 1000000000.0f;

    interface onDeviceMovedListener {
        void onDeviceMoved();

        void onDeviceStillNotBack();

        void onDeviceBack();
    }

    private onDeviceMovedListener mListener;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (mFirst) {
            mX = sensorEvent.values[0];
            mY = sensorEvent.values[1];
            mZ = sensorEvent.values[2];
            called = mFirst = false;
        } else {
            if (Math.abs(mX - sensorEvent.values[0]) > MAX_DELTA || Math.abs(mY - sensorEvent.values[1]) > MAX_DELTA || Math.abs(mZ - sensorEvent.values[2]) > MAX_DELTA) {
                if (isMoved) {
                    if ((sensorEvent.timestamp - mStartMove) * NS2S > MAX_TIME_SEC)
                        if (!called) {
                            if (mListener != null) {
                                mListener.onDeviceMoved();
                                called = true;
                                afterCall = sensorEvent.timestamp;
                            }
                        } else {
                            if ((sensorEvent.timestamp - afterCall) * NS2S > MAX_TIME_AFTER_CALL) {
                                mListener.onDeviceStillNotBack();
                                afterCall = sensorEvent.timestamp;
                            }
                        }
                } else {
                    mStartMove = sensorEvent.timestamp;
                    isMoved = true;
                }
            } else {
                if (isMoved)
                    if (mListener != null)
                        mListener.onDeviceBack();
                called = isMoved = false;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public class MovingDeviceBinder extends Binder {
        MovingDeviceService getService() {
            return MovingDeviceService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        mFirst = true;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(getApplicationContext(), R.string.dont_have_sensor, Toast.LENGTH_LONG).show();
            return null;
        }
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        boolean temp = super.onUnbind(intent);
        mSensorManager.unregisterListener(this);
        return temp;
    }

    public void registerListener(onDeviceMovedListener listener) {
        this.mListener = listener;
    }

    public void unRegisterListener() {
        this.mListener = null;
    }
}
