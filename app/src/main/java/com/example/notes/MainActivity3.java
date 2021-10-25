package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity3 extends AppCompatActivity {
    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // 1. get EditText view
        EditText editText = (EditText) findViewById(R.id.editNote);

        // 2. get intent
        Intent intent = getIntent();

        // 3. get value of integer noteid from intent
        String noteid1 = intent.getStringExtra("noteid");

        // 4. Initialise class variable "noteid" with the value from intent
        noteid = Integer.parseInt(noteid1);

        if (noteid != -1){
            Note note = MainActivity2.notes.get(noteid);
            String noteContent = note.getContent();
            editText.setText(noteContent);
        }
    }


    public void saveFunction(View view){

        // 1. Get editText view and the content that user entered.
        EditText editText = (EditText) findViewById(R.id.editNote);
        String user_content = editText.getText().toString();

        // 2. Initialize SQLite db instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("note_list", Context.MODE_PRIVATE,null);

        // 3. Intialize DBHelper class
        DBHelper helper = new DBHelper(sqLiteDatabase);

        // 4. Set username in the following variable by fetching it from SharedPreferences.
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // 5. Save info to database
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1){ // add note
            title = "NOTE_" + (MainActivity2.notes.size()+1);
            helper.saveNotes(username, title, user_content, date);
        } else{ // update note
            title = "NOTE_" + (noteid + 1);
            helper.updateNote(title, date, user_content, username);
        }

        // 6. go to second activity using intents
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("message",username);
        startActivity(intent);
    }
}