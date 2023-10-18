package com.pham_dang_khai.dataloger_client_microprocessers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    TextView tv, tvg, tvm, tvx, tvy, tvz, tvgx, tvgy, tvgz, tvmx, tvmy, tvmz;

    private Accelerometer accelerometer;
    private Gyroscope gyroscope;
    private Magnetic magnetic;
    String accx, accy,accz, gyrx, gyry, gyrz, magx, magy, magz;

    Button receiveButton, connectButton,disconnectButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        tvg = findViewById(R.id.tvg);
        tvm = findViewById(R.id.tvm);
        tvx = findViewById(R.id.tvx);
        tvy = findViewById(R.id.tvy);
        tvz = findViewById(R.id.tvz);
        tvgx = findViewById(R.id.tvgx);
        tvgy = findViewById(R.id.tvgy);
        tvgz = findViewById(R.id.tvgz);
        tvmx = findViewById(R.id.tvmx);
        tvmy = findViewById(R.id.tvmy);
        tvmz = findViewById(R.id.tvmz);
        receiveButton = findViewById(R.id.receiveButton);
        connectButton = findViewById(R.id.connectButton);
        disconnectButton = findViewById(R.id.disconnectButton);

        accelerometer = new Accelerometer(this);
        accelerometer.setListener((tx, ty, tz) -> {
            accx = varToString(tx);
            accy = varToString(ty);
            accz = varToString(tz);
            tvx.setText(accx);
            tvy.setText(accy);
            tvz.setText(accz);
        });
        gyroscope = new Gyroscope(this);
        gyroscope.setListener((gx, gy, gz) -> {
            gyrx = varToString(gx);
            gyry = varToString(gy);
            gyrz = varToString(gz);
            tvgx.setText(gyrx);
            tvgy.setText(gyry);
            tvgz.setText(gyrz);
        });
        gyroscope.register();
        magnetic = new Magnetic(this);
        magnetic.setListener((mx, my, mz) -> {
            magx = varToString(mx);
            magy = varToString(my);
            magz = varToString(mz);
            tvmx.setText(magx);
            tvmy.setText(magy);
            tvmz.setText(magz);
        });
        magnetic.register();

        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String editText=editText.getText() + ""; //Co the them neu muon gui tin nhan tu Client qua Sever


                String[] empid=new String[1];
                empid[0]="empid";
                String[] data=new String[1];
                //data[0]=empnumber;
                FetchData fetchData=new FetchData("https://krisambali.com/android/index.php", "POST", empid, data);
                if (fetchData.startPut()) {
                    if (fetchData.onComplete()) {
                        String result = fetchData.getResult();
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            Iterator<String> keys=jsonObject.keys();
                            String enumber= keys.next();
                            String name = jsonObject.optString(enumber);

                            tv.setText(enumber);
                            tvx.setText(name);
                            tvy.setText(name);
                            tvz.setText(name);
                            enumber= keys.next();
                            name = jsonObject.optString(enumber);

                            tvg.setText(enumber);
                            tvgx.setText(name);
                            tvgy.setText(name);
                            tvgz.setText(name);
                            enumber= keys.next();
                            name = jsonObject.optString(enumber);

                            tvm.setText(enumber);
                            tvmx.setText(name);
                            tvmy.setText(name);
                            tvmz.setText(name);
                            enumber= keys.next();
                            name = jsonObject.optString(enumber);

                        } catch (JSONException e) {
                            Log.e("Buffer Error", "Error converting result " + e.toString());
                        }
                    }

                }

            }
        });
    }

    private String varToString(float var){
        String strVar;
        Locale.setDefault(Locale.US);
        strVar = String.format("%.3f",var);
        return strVar;
    }
    @Override
    protected void onResume(){
        accelerometer.register();
        gyroscope.register();
        magnetic.register();
        super.onResume();
    }
    @Override
    protected void onPause(){
        accelerometer.unregister();
        gyroscope.unregister();
        magnetic.unregister();
        super.onPause();
    }
    @Override
    protected void onDestroy(){
        accelerometer.unregister();
        gyroscope.unregister();
        magnetic.unregister();
        super.onDestroy();
    }
    @Override
    protected void onRestart(){
        accelerometer.unregister();
        gyroscope.unregister();
        magnetic.unregister();
        super.onRestart();
    }
}