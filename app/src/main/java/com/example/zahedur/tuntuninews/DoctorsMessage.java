package com.example.zahedur.tuntuninews;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DoctorsMessage extends AppCompatActivity {


    EditText message;
    Button send;
    public SessionManager session;

    RequestQueue requestQueue;
    String  url = "http://zahedice14.000webhostapp.com/PatientDataStore/storeMessage.php";
    String username=LoginActivity.username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_message);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle(LoginActivity.username); // set the top title


        message =(EditText)findViewById(R.id.message);
       // date = (EditText)findViewById(R.id.date);
        send =(Button)findViewById(R.id.send_button);




       // String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        //date.setText(mydate);


        requestQueue = Volley.newRequestQueue(getApplicationContext());


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // String  url = "https://zahedice14.000webhostapp.com/api/DoctorMessage.php";

                StringRequest sq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {

                    protected Map<String ,String> getParams(){

                        Map<String,String> parr = new HashMap<String, String>();


                        parr.put("username",username);
                        parr.put("message","P: "+message.getText().toString());
                       // parr.put("date",date.getText().toString());


                        return parr;

                    }
                };

                requestQueue.add(sq);

                // AppController.getInstance().addToRequestQueue(sq);
                Toast.makeText(getApplicationContext(),"Message is send to the Doctor",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(DoctorsMessage.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();

        }

    }
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
        Intent intent = new Intent(DoctorsMessage.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
