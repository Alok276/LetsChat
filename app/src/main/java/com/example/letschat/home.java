package com.example.letschat;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_ui);
        Button button1;
 button1=findViewById(R.id.button1);
 button1.setOnClickListener(v-> {
     FirebaseAuth.getInstance().signOut();
     finish();
 });

    }
}
