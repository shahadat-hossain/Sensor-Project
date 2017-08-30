package com.shahadat.sensorproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    Button start;
    Button stop;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         intent=new Intent(this,ProfileManager.class);

        start=(Button)findViewById(R.id.buttonStart);
        start.setOnClickListener(this);

        stop=(Button)findViewById(R.id.buttonStop);
        stop.setOnClickListener(this);

//        stop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                stopService(new Intent(getBaseContext(), ProfileManager.class));
//                start.setVisibility(View.VISIBLE);
//                stop.setVisibility(View.GONE);
//            }
//        });


    }
    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    public void onClick(View v) {

        if(v==start){

            startService(intent);
            Toast.makeText(this, "Service Started from main",Toast.LENGTH_LONG).show();
            start.setVisibility(View.GONE);
            stop.setVisibility(View.VISIBLE);

        }
        else if(v==stop){
        //   Intent intent=new Intent(this, ProfileManager.class);
            stopService(intent);
            Toast.makeText(this, "Service Stopped from main",Toast.LENGTH_LONG).show();
            start.setVisibility(View.VISIBLE);
            stop.setVisibility(View.GONE);

        }
    }
}
