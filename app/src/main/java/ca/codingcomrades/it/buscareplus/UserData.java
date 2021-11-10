package ca.codingcomrades.it.buscareplus;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class UserData {
    FirebaseDatabase database;
    FirebaseFirestore fStore;
    DatabaseReference myRef;
    Map<String, Object> arr;

//    public String retriveUserData(){
//        //final Map<String, Object>[] userInfo = new Map[]{new HashMap<>()};
//
//        fStore =FirebaseFirestore.getInstance();
//        DocumentReference df = fStore.collection("Users").document("mSZ8gSOQN2MoE8I1ibiU0F2UF1A3");
//
//        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    if(document.exists()){
//                        value[0] = (String) document.getData().get("Country");
//                        Log.d("TAG", "onComplete: " + value[0]);
//
//                    }else{
//                        Log.d("TAG", "onComplete: Error" );
//                    }
//                }else{
//                    Log.d("TAG", "onComplete: " + task.getException());
//                }
//            }
//        });
//
//        Log.d("TAG", "retriveUserData: "+value[0]);
//        return value[0];
//    }

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

}
