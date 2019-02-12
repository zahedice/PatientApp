package com.example.zahedur.tuntuninews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageReceivedFromDoctor extends AppCompatActivity{

    TextView msv;
    Button button;

    public SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_received_from_doctor);

        msv = (TextView) findViewById(R.id.message_view);
         button = (Button)findViewById(R.id.buton);
        // get the text from MainActivity
        Intent intent = getIntent();
        String text = intent.getStringExtra(Intent.EXTRA_TEXT);


        try {
            fetchingData(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent1 = new Intent(MessageReceivedFromDoctor.this,MainActivity.class);
               // startActivity(intent1);

                startActivity(new Intent(getApplicationContext(),DoctorsMessage.class));

            }
        });

    }


    String result=" ";

    void fetchingData(String message) throws JSONException {

        String name, mes, date;

        String in;
        in = message;

        // JSONObject reader = new JSONObject(message);


        JSONArray contacts = new JSONArray(message);

        //JSONObject jsonObject =(JSONObject).contacts.get(1);
        //JSONObject jsonObject = (JSONObject) response.get(i);
        JSONObject jsonObject = contacts.getJSONObject(0);

       // name = jsonObject.getString("n");
        mes = jsonObject.getString("message");
        date = jsonObject.getString("upload_date");

        result = result + " (" + date + ") \n" + mes;


        msv.setText(result);
    }

}
