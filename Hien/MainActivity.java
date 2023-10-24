package com.hien.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView aX, aY, aZ, gX, gY, gZ, mX, mY, mZ;
    TextView textView;
    Button bt_connect, bt_receive, bt_reset;
    private ConnectTask connectTask;
    private boolean isConnected = false;
    public EditText ed_ip;


    @SuppressLint("MissingInflatedId")
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

        bt_connect = findViewById(R.id.bt_connect);
        bt_receive = findViewById(R.id.bt_receive);
        bt_reset = findViewById(R.id.bt_reset);
        textView = findViewById(R.id.textView3);
        ed_ip = findViewById(R.id.ed_ip);

        bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnected) {
                    cancelConnection();
                } else {
                    startConnection();
                }
            }
        });

        bt_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Receive_Task().execute();
            }
        });

        bt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aX.setText("0");
                aY.setText("0");
                aZ.setText("0");
                gX.setText("0");
                gY.setText("0");
                gZ.setText("0");
                mX.setText("0");
                mY.setText("0");
                mZ.setText("0");
            }
        });
    }// End on Create

    private void startConnection() {
        bt_connect.setEnabled(false);
        connectTask = new ConnectTask();
        connectTask.execute();
    }

    private void cancelConnection() {
        if (connectTask != null) {
            connectTask.cancel(true);
        }
    }

    private class ConnectTask extends AsyncTask<Void, Void, Boolean> {

        private static final String CONNECT_URL = "http://192.168.137.94/connect";

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL(CONNECT_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                return responseCode == HttpURLConnection.HTTP_OK;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean isConnected) {
            if (isConnected) {
                textView.setText("Connected");
                Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
            } else {
                textView.setText("Connection failed");
                Toast.makeText(MainActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }

            bt_connect.setEnabled(true);
            MainActivity.this.isConnected = isConnected;
        }

        @Override
        protected void onCancelled() {
            bt_connect.setEnabled(true);
        }
    }

    public class Receive_Task extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL("http://192.168.137.94/data"); // Thay đổi URL thành địa chỉ IP của ESP8266
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    reader.close();
                } else {
                    result.append("HTTP Error: ").append(responseCode);
                }

                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                result.append("Error: ").append(e.getMessage());
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            // Xử lý kết quả trả về từ ESP8266 ở đây
//            textView.setText(result);
            try{
                JSONObject jsonObject = new JSONObject(result);
                String accX = jsonObject.getString("accx");
                String accY = jsonObject.getString("accy");
                String accZ = jsonObject.getString("accz");
                String gyrX = jsonObject.getString("gyrx");
                String gyrY = jsonObject.getString("gyry");
                String gyrZ = jsonObject.getString("gyrz");
                String magX = jsonObject.getString("magx");
                String magY = jsonObject.getString("magy");
                String magZ = jsonObject.getString("magz");

                aX.setText(accX);
                aY.setText(accY);
                aZ.setText(accZ);
                gX.setText(gyrX);
                gY.setText(gyrY);
                gZ.setText(gyrZ);
                mX.setText(magX);
                mY.setText(magY);
                mZ.setText(magZ);

                textView.setText("Received data!");

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}