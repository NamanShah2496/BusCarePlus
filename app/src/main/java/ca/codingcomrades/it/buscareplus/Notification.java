package ca.codingcomrades.it.buscareplus;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class Notification extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent,int flags, int startId){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        addNotification();
        return START_STICKY;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

    }
    public void addNotification() {
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("BgNotification", "Notificattion", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        String id="id";
        NotificationCompat.Builder review  = new NotificationCompat.Builder(Notification.this,"BgNotification")
                .setSmallIcon(R.drawable.star_review)
                .setContentTitle("Test Background")
                .setContentText(getString(R.string.notification_main))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat managerCompat= NotificationManagerCompat.from(Notification.this);
        managerCompat.notify(1,review.build());
    }

    private class DoBackgroundTask extends AsyncTask<Integer,Integer,Integer>{

        @Override
        protected Integer doInBackground(Integer... integers) {
            return null;
        }
    }

}
