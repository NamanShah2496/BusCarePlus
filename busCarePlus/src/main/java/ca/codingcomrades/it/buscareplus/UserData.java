// Naman Shah , n01392496 , Section RNA
// Aryan Sood , n01393003, Section RNA
// Vishesh Bansal, n01395119, Section RNA
// Jaskirat Singh , N01403975 , Section RNB


package ca.codingcomrades.it.buscareplus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class UserData extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseFirestore fStore;
    DatabaseReference myRef;
    Map<String, Object> arr;



    public boolean isInternetAvailable(Context context,View view) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                } else{

                    return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
                }
            }
        }
        return false;
    }





    public boolean isInternetAvailable(View view) {
        Snackbar snackbar = Snackbar.make(view, "Not Connected to Internet!", Snackbar.LENGTH_INDEFINITE);

        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            snackbar.dismiss();
            return !ipAddr.equals("");

        } catch (Exception e) {
            snackbar.show();
            return false;
        }
    }


    public void updateUserData(){
        DocumentReference df = fStore.collection("Users").document("mSZ8gSOQN2MoE8I1ibiU0F2UF1A3");
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("FirstName","Test");
        userInfo.put("LastName","User");
        userInfo.put("Phone", "94459945648");
        userInfo.put("Age", "23");
        userInfo.put("Address", "123 Jane St");
        userInfo.put("City", "Toronto");
        userInfo.put("Province", "Ontario");
        userInfo.put("Country", "Canada");
        userInfo.put("isUser","1");
       // toastPrint(userInfo.get("Country").toString());
        df.set(userInfo);


        Log.d("TAG",df.get().toString() );
    }
    public class checkInternet implements Runnable{

        Context context;
        View view;
        checkInternet(Context context,View view){
            this.context = context;
            this.view = view;
        }
        @Override
        public void run() {
            //usr.isInternetAvailable(context,view);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            run();
        }
    }
}
