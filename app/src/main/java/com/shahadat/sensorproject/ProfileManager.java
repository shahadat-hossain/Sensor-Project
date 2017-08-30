package com.shahadat.sensorproject;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.widget.Toast;

import static android.media.AudioManager.MODE_RINGTONE;
import static android.media.AudioManager.RINGER_MODE_VIBRATE;
import static android.media.AudioManager.VIBRATE_SETTING_OFF;
import static android.media.AudioManager.VIBRATE_TYPE_RINGER;


/**
 * Created by shaha on 8/8/2017.
 */

public class ProfileManager extends IntentService implements SensorEventListener {



    private double lightValue;




    private double valueX;
    private double valueY;
    private double valueZ;
     boolean proxFlag=true;

    Vibrator v;

    AudioManager audioManager;
    SensorManager sensorManager;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ProfileManager() {
        super("pm");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onCreate(){

        lightValue = 0.0;
         v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

       Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Sensor proximity=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


        if( lightSensor != null ){

            Toast.makeText(this, "Light Sensor", Toast.LENGTH_LONG).show();
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

        }
        if( accelerometer != null ){

            Toast.makeText(this, "Accelometer Sensor", Toast.LENGTH_LONG).show();
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        }
        if(proximity!=null){
            sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        Thread thread=new Thread(new ThreadClass(startId));
        thread.start();


        // setVibrate();
        return START_STICKY;
    }


    class ThreadClass implements Runnable{
        int serviceId;

        ThreadClass(int sId){
            this.serviceId=sId;

        }

        @Override
        public void run() {
            try{
                Thread.sleep(150);

            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }



    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_LIGHT){

            Toast.makeText(this, "" + event.values[0], Toast.LENGTH_SHORT);

            lightValue = event.values[0];


            if(lightValue < 8.0){
                setSilent();

               // offVibrate();
            }
            else if(lightValue >= 10.0){
                setRinging();
            }
        }

        if( event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            valueX = event.values[0];
            valueY = event.values[1];
            valueZ = event.values[2];

//            if( (valueX <= 2.0 && valueX >= -0.5 ) && (valueY >= 0.5 && valueY <= 2.5)  && valueZ >= 9.1 )

            if( valueZ>=2&& valueZ>10 ){
                setSilent();
            }
            else if( valueZ <= -2 ){
                setRinging();

            }else{

            }
        }
        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){

            if(event.values[0]==0){
                proxFlag=false;
            }
            else{
                proxFlag=true;
            }
        }
        if(proxFlag==false){
            setSilent();

        }
        else{
            setRinging();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void setVibrate(){

        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        SystemClock.sleep(30);
    }
    public void setNormal(){
        audioManager.setRingerMode(MODE_RINGTONE);
        SystemClock.sleep(30);
    }


    public void setRinging(){
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        SystemClock.sleep(30);
    }


    public void setSilent(){
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        SystemClock.sleep(30);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
//        sensorManager.unregisterListener(this);
    }
//    public void offVibrate(){

//        if(audioManager.getRingerMode()== RINGER_MODE_VIBRATE){
//
//        }
//        audioManager.setRingerMode(AudioManager.VIBRATE_SETTING_OFF);
//        SystemClock.sleep(30);
//    }
}
