package com.hien.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Accelerometer accelerometer;
    private Gyroscope gyroscope;
    private Magnetic magnetic;
    TextView aX, aY, aZ, gX, gY, gZ, mX, mY, mZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aX = findViewById(R.id.tv_aX);
        aY = findViewById(R.id.tv_aY);
        aZ = findViewById(R.id.tv_aZ);
        gX = findViewById(R.id.tv_gX);
        gY = findViewById(R.id.tv_gY);
        gZ = findViewById(R.id.tv_gZ);
        mX = findViewById(R.id.tv_mX);
        mY = findViewById(R.id.tv_mY);
        mZ = findViewById(R.id.tv_mZ);
        accelerometer = new Accelerometer(this);
        gyroscope = new Gyroscope(this);
        magnetic = new Magnetic(this);

        accelerometer.setListener((tx, ty, tz) -> {
            String acx = varToString(tx);
            String acy = varToString(ty);
            String acz = varToString(tz);
            aX.setText(acx);
            aY.setText(acy);
            aZ.setText(acz);
        });

        gyroscope.setListener((tgx, tgy, tgz) -> {
            String gx = varToString(tgx);
            String gy = varToString(tgy);
            String gz = varToString(tgz);
            gX.setText(gx);
            gY.setText(gy);
            gZ.setText(gz);
        });

        magnetic.setListener((mx, my, mz) -> {
            String Mx = varToString(mx);
            String My = varToString(my);
            String Mz = varToString(mz);
            mX.setText(Mx);
            mY.setText(My);
            mZ.setText(Mz);
        });
    }

    protected void onResume(){
        accelerometer.register();
        gyroscope.register();
        magnetic.register();
        super.onResume();
    }

    protected void onPause(){
        accelerometer.unregister();
        gyroscope.unregister();
        magnetic.unregister();
        super.onPause();
    }

    protected void onDestroy(){
        accelerometer.unregister();
        gyroscope.unregister();
        magnetic.unregister();
        super.onDestroy();
    }

    protected void onRestart(){
        accelerometer.unregister();
        gyroscope.unregister();
        magnetic.unregister();
        super.onRestart();
    }

    private String varToString(float var){
        String strVar;
        Locale.setDefault(Locale.US);
        strVar = String.format("%.3f", var);
        return strVar;
    }
}
