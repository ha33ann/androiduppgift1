package com.example.android1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //skapar en sensor manager som hanterar sensorn
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //skapar en sensor listener som lyssnar på sensorn
        SensorEventListener sel = new SensorEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            //metod som körs när sensorn ändrar värde och uppdaterar texten samt ändrar rotation på bilden
            public void onSensorChanged(SensorEvent event) {
                TextView xangle = findViewById(R.id.xangle);
                TextView yangle = findViewById(R.id.yangle);
                TextView zangle = findViewById(R.id.zangle);
                xangle.setText("X: " + event.values[0]);
                yangle.setText("Y: " + event.values[1]);
                zangle.setText("Z: " + event.values[2]);
                View andr = findViewById(R.id.andr);
                andr.setRotation(event.values[0]);
                andr.setRotationY(event.values[1]);
                andr.setRotationX(event.values[2]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        //registrerar sensorn och lyssnaren för sensorn
        sensorManager.registerListener(sel, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        //avregistrerar sensorn och lyssnaren på den övre knapptrycken
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View andr = findViewById(R.id.andr);
                andr.setRotation(0);
                andr.setRotationY(0);
                andr.setRotationX(0);
                sensorManager.unregisterListener(sel);
            }
        });
       //registrerar tillbaka sensorn och lyssnaren på den undre knapptrycken
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorManager.registerListener(sel, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        });
    }
}