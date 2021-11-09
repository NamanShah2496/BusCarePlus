package ca.codingcomrades.it.buscareplus;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReviewActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        EditText nameEditText = findViewById(R.id.username_input);
        String fullName = nameEditText.getText().toString();
        TextView tv1 = findViewById(R.id.model_print);
        String str = android.os.Build.MODEL;
        tv1.setText(str);
    }

}

