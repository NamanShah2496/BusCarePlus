package ca.codingcomrades.it.buscareplus;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;


public class ReviewActivity  extends AppCompatActivity {
FirebaseDatabase database;
Button submit;
RatingBar ratingBar;
TextView model;
EditText fullName,phone,email,comment;
    Float rating;
    String name,num,emailAddress,Comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        bindFields();
        submit.setOnClickListener(v -> sendReview());
        String str = android.os.Build.MODEL;
        model.setText(str);
    }
    public void sendReview(){
        if(validate()) {
           database = FirebaseDatabase.getInstance();
            Log.d("TAG", "sendReview: Working" );
//            DatabaseReference myRef = database.getReference("FeedBack/Speed");
//            myRef.setValue("12Km/h");
//        fStore = FirebaseFirestore.getInstance();
//        fAuth = FirebaseAuth.getInstance();
        }
    }
    public void bindFields(){
        fullName = findViewById(R.id.fullNameInput);
        phone = findViewById(R.id.phoneNumInput);
        email = findViewById(R.id.emailInput);
        ratingBar =findViewById(R.id.ratingBar);
        comment = findViewById(R.id.commentInput);
        model = findViewById(R.id.model_print);
        submit = findViewById(R.id.submitBtn);

    }
    public boolean validate(){
        getValues();
        if(name.isEmpty() || num.isEmpty() || emailAddress.isEmpty() || Comment.isEmpty() || rating==0){
            return false;
        }
        return true;
    }
    public void getValues(){
        name = fullName.getText().toString();
        num = phone.getText().toString();
        emailAddress = email.getText().toString();
        Comment = comment.getText().toString();
        rating = ratingBar.getRating();
        Log.d("TAG", "getValues: " +rating);

    }
}
