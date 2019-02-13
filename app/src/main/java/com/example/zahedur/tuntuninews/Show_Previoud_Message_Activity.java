package com.example.zahedur.tuntuninews;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Show_Previoud_Message_Activity extends AppCompatActivity {

    public SessionManager session;

    RequestQueue requestQueue;
    ListView mTextView;

    public static final String message = "name";
    public static final String date = "date";
    public static final String JSON_ARRAY = "result";
    // private JSONArray result;
    private JSONArray result1;
    //Spinner spinner1,spinner2;
    //private ArrayList<String> arrayList;
    private ArrayList<String> arrayList1;


    String  url = "https://zahedice14.000webhostapp.com/PatientDataStore/MessageRetrievalFromDatabase.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__previoud__message_);

        mTextView = (ListView)findViewById(R.id.messageList);
        arrayList1 = new ArrayList<String>();
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle(LoginActivity.username); // set the top title


        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject j = null;
                try {
                    j = new JSONObject(response);
                    result1= j.getJSONArray(JSON_ARRAY);
                    doctorname(result1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", LoginActivity.username);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();

        }
    }

            String temp1,temp2;
private void doctorname(JSONArray j) {
    arrayList1.clear();
    for (int i = 0; i < j.length(); i++) {
        try {
            JSONObject json = j.getJSONObject(i);
            temp1 = json.getString(message);
            temp2 = json.getString(date);
            arrayList1.add("Date: " + temp2 + "\n" + "Message: " + temp1);
            //arrayList1.add(json.getString(message));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mTextView.setAdapter(new ArrayAdapter(getApplicationContext(), R.layout.messagetextview, R.id.textviewforlist1, arrayList1));
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
