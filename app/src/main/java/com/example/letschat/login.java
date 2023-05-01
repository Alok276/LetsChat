package com.example.letschat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

public class login extends AppCompatActivity {
private Button button1;
private EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        button1=findViewById(R.id.button);
        text=findViewById(R.id.mobileText);
        FirebaseApp.initializeApp(/*context=*/ this);
        button1.setOnClickListener(v-> {
            String str=text.getText().toString();
            Intent intent1= new Intent(getApplicationContext(),authentication.class);
            Toast.makeText(login.this, str, Toast.LENGTH_SHORT).show();
            intent1.putExtra("message_key", str);
            startActivity(intent1);
            finish();
        });
    }
}
