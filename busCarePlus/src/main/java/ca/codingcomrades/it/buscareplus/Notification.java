package ca.codingcomrades.it.buscareplus;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Notification extends Service {
    Intent intent1;
    PendingIntent pendingIntent;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent,int flags, int startId){

        Intent intent1 = new Intent(this, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent  = PendingIntent.getActivity(getApplicationContext(), 0, intent1, 0);

        try {
            new DoBackgroundTask().execute();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //int doom = onStartCommand(intent,flags,startId);
        return START_STICKY;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

    }


    private class DoBackgroundTask extends AsyncTask<Integer,Integer,Integer>{

        Handler handler = new Handler();
        DatabaseReference database =FirebaseDatabase.getInstance().getReference();
        boolean flag=false;
        int busNum=927;
        double speed,temperatureReading;
        int passengers,carbonReading;


        @Override
        protected Integer doInBackground(Integer... integers) {



          updateUI();
            return null;
        }
        public void updateUI() {
            if(flag){
                try {
                    Thread.sleep(5000);
                    flag =false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            handler.postDelayed(() -> database.child("Data/" + busNum).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    } else {
                        temperatureReading = Double.parseDouble(String.valueOf(task.getResult().child("Maintenance/Temperature").getValue()));
                        carbonReading = Integer.parseInt(String.valueOf(task.getResult().child("Maintenance/Co2").getValue()));
                        passengers = Integer.parseInt(String.valueOf(task.getResult().child("Safety/Passengers").getValue()));
                        speed = Double.parseDouble(String.valueOf(task.getResult().child("Safety/Speed").getValue()));
                        isDanger();

                    }
                    updateUI();
                }
            }), 1000);
        }
        public void isDanger(){

            Boolean danger = false;
            String msg = " ";
            if(speed>50){
                msg="Bus is Overspeeding";
                addNotification(msg);
                danger = true;
            }if(passengers>30) {
                danger = true;
                msg = "Bus is Overcrowded, passenger count: " + passengers;
                addNotification(msg);
            }
             if(temperatureReading>24) {
                danger = true;
                msg = "Bus is Overheated, temp:" + temperatureReading + " Celsius";
                addNotification(msg);
            }
            if(carbonReading>1000) {
                danger = true;
                msg = "Too much CO2 in the bus, readings: " + carbonReading + " ppm";
                addNotification(msg);
            }
            if(danger) {
                addNotification(msg);
            }
        }
        public void addNotification(String msg) {
            NotificationChannel channel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                channel = new NotificationChannel("BgNotification", "Notificattion", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }

            String id="id";

            NotificationCompat.Builder warningNotification  = new NotificationCompat.Builder(Notification.this,"BgNotification")
                    .setSmallIcon(R.drawable.alert_notification)
                    .setContentTitle("Warning!!")
                    .setContentText(msg)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManagerCompat managerCompat= NotificationManagerCompat.from(Notification.this);
            managerCompat.notify(1,warningNotification.build());
            flag=true;
        }
    }

}
