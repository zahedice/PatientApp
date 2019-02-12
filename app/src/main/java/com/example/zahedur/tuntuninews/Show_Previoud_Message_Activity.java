package com.example.zahedur.tuntuninews;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Show_Previoud_Message_Activity extends AppCompatActivity {



    TextView mTextView;

    Button submit;
    public SessionManager session;

    RequestQueue requestQueue;

    String  url = "https://zahedice14.000webhostapp.com/api/gettingmessage.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__previoud__message_);

        mTextView = (TextView)findViewById(R.id.show_message);


        mTextView.setText("");
        mTextView.setMovementMethod(new ScrollingMovementMethod());

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle(LoginActivity.username); // set the top title

         //Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Do something with response
                //mTextView.setText(response.toString());

                // Process the JSON
                try{
                    // Loop through the array elements
                    for(int i=0;i<response.length();i++){
                        // Get current json object
                        JSONObject student = response.getJSONObject(i);

                        // Get the current student (json object) data
                        String firstName = student.getString("name");
                        String lastName = student.getString("message");
                        String age = student.getString("date");

                        // Display the formatted json data in text view
                        mTextView.append(firstName +" " + age +"\nAge : " + lastName);
                        mTextView.append("\n\n");
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        VolleyLog.d("Volley Log",error);
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonArrayRequest);
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
        Intent intent = new Intent( Show_Previoud_Message_Activity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
