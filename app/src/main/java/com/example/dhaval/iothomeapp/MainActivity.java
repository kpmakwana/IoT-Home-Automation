package com.example.dhaval.iothomeapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected static final int RESULT_SPEECH = 1;

    private ImageButton btnSpeak;
    private TextView txtText;
    private Button send;
    //private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtText = (TextView) findViewById(R.id.txtText);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        send = (Button) findViewById(R.id.button);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-IN");

                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    txtText.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Opps! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }

    public void send_text(View v){

        String key = "buzzer";
        String key1 = "bulb";
        String key2 = "fan";
        String value = txtText.getText().toString();
        int x=-1,bul=-1,f=-1;
        if(value.equalsIgnoreCase("alert on")||value.equalsIgnoreCase("turn on alert")||value.equalsIgnoreCase("on alert")||value.equalsIgnoreCase("alert on")||value.equalsIgnoreCase("turn on alert")) {
            x = 1;
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            database.child(key).setValue
                    (x);
        }
        else if(value.equalsIgnoreCase("alert off")||value.equalsIgnoreCase("turn off alert")||value.equalsIgnoreCase("off alert")||value.equalsIgnoreCase("alert of")||value.equalsIgnoreCase("turn of alert")) {
            x = 0;
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            database.child(key).setValue
                    (x);
        }
        else if(value.equalsIgnoreCase("light on")||value.equalsIgnoreCase("turn on light")||value.equalsIgnoreCase("turn light on")||value.equalsIgnoreCase("lights please")||value.equalsIgnoreCase("lights on")){
            bul = 1;
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            database.child(key1).setValue
                    (bul);
        }
        else if(value.equalsIgnoreCase("light off")||value.equalsIgnoreCase("turn off light")||value.equalsIgnoreCase("turn off lights")||value.equalsIgnoreCase("lights off")||value.equalsIgnoreCase("turn light of")){
            bul = 0;
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            database.child(key1).setValue
                    (bul);
        }
        else if(value.equalsIgnoreCase("turn on fan")||value.equalsIgnoreCase("fan on")||value.equalsIgnoreCase("turn fan on")||value.equalsIgnoreCase("fan on")||value.equalsIgnoreCase("on fan")){
            f = 1;
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            database.child(key2).setValue
                    (f);
        }
        else if(value.equalsIgnoreCase("turn fan off")||value.equalsIgnoreCase("fan off")||value.equalsIgnoreCase("turn off fan")||value.equalsIgnoreCase("fan of")||value.equalsIgnoreCase("of fan")||value.equalsIgnoreCase("off fan")){
            f = 0;
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            database.child(key2).setValue
                    (f);
        }
        else if(value.equalsIgnoreCase("all off")||value.equalsIgnoreCase("all of")||value.equalsIgnoreCase("shutdown") || value.equalsIgnoreCase("system shutdown"))
            {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                database.child(key1).setValue
                        (0);

                database.child(key2).setValue
                        (0);
            }
            //final DatabaseReference myRef = database.getReference("users");
        //DatabaseReference childRef = myRef.push();
        //childRef.setValue(txtText.getText().toString());


        //DatabaseReference myRef = database.getReference("move");
        //myRef.setValue(txtText.getText().toString());
        Toast.makeText(getApplicationContext(),"Sent",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    txtText.setText(text.get(0));
                    Toast.makeText(this, txtText.getText(), Toast.LENGTH_SHORT).show();

                }
                break;
            }

        }
    }
}
