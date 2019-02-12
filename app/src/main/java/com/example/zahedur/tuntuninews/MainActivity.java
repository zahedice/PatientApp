package com.example.zahedur.tuntuninews;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.zahedur.tuntuninews.dataUploading.StartMenu;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //ListView lv;
    Button b, sendMessage, show_message;
    public SessionManager session;
    MessageReceivedFromDoctor messageReceivedFromDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //lv = (ListView)findViewById(R.id.mylist);
        b = (Button) findViewById(R.id.addingnewnews);
        sendMessage = (Button) findViewById(R.id.messageToDoctors);
        show_message = (Button) findViewById(R.id.previous_message);
        //Intent i=getIntent();
       // String email=i.getStringExtra("email");
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle(LoginActivity.username); // set the top title



        //fetchingData();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StartMenu.class));
            }
        });


        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DoctorsMessage.class));
            }
        });

        show_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), Show_Previoud_Message_Activity.class));

            }
        });


        FirebaseMessaging.getInstance().subscribeToTopic("test");
        if (getIntent().getExtras() != null) {

            String message = getIntent().getExtras().getString("message");

            if (message != null) {
                //message = "No new message";

                // startActivity(new Intent(getApplicationContext(),AddingNews.class));
                // start the SecondActivity
                Intent intent = new Intent(this, MessageReceivedFromDoctor.class);
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(intent);
            }


        }
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();

        }
    }
/*
     void fetchingData(){


         String myURL = "https://zahedice14.000webhostapp.com/api/gettingnews.php";
         JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(myURL, new Response.Listener<JSONArray>() {
             @Override
             public void onResponse(JSONArray response) {

                  final String[]   news_title = new String[response.length()];
                 final String[]     news_details = new String[response.length()];
                 final String[]    news_time = new String[response.length()];


                 for(int i=0; i<response.length(); i++) {
                     try {


                         JSONObject jsonObject = (JSONObject) response.get(i);
                         news_title[i] = jsonObject.getString("title");
                         news_details[i] = jsonObject.getString("Temperature");
                         news_time[i] = jsonObject.getString("HeartRate");


                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }

                 lv.setAdapter(new ArrayAdapter(getApplicationContext(),R.layout.mylistview,R.id.textviewforlist,news_title));


              lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                  @Override
                  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                      Intent intent = new Intent(MainActivity.this, Details.class);
                      intent.putExtra("MyTITLE",news_title[i]);
                      intent.putExtra("MyDETAILS",news_details[i]);
                      intent.putExtra("MyTIME",news_time[i]);
                      startActivity(intent);
                  }
              });


             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 VolleyLog.d("Volley Log",error);
             }
         });

         com.example.zahedur.tuntuninews.AppController.getInstance().addToRequestQueue(jsonArrayRequest);
         Toast.makeText(getApplicationContext(),"Data Loaded Succesfully",Toast.LENGTH_LONG).show();
     }
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manubar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logoutUser();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
        /**
         * Logging out the user. Will set isLoggedIn flag to false in shared
         * preferences Clears the user data from sqlite users table
         * */

    private void logoutUser() {
        session.setLogin(false);

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}



