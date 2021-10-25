package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    public void loginFunction(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);

        EditText myTextField = (EditText) findViewById(R.id.textUsername);
        String str = myTextField.getText().toString();

        sharedPreferences.edit().putString("username", str).apply();
        goToActivity2(str);
    }

    public void goToActivity2(String s){
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("message",s); // helps us pass data to the second activity,
        // pass the string that we want to output to the new activity
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString(usernameKey, "").equals("")){
            String str = sharedPreferences.getString(usernameKey, "");
            goToActivity2(str);
        }
        else {
            setContentView(R.layout.activity_main);
        }
    }
}