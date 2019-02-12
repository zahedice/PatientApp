package com.example.zahedur.tuntuninews;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zahedur on 2/26/18.
 */

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        final String token = FirebaseInstanceId.getInstance().getToken();

        //onTokenRefresh(token);

        Log.e("MYTAG", token);
        sendRegistrationToServer(token);



    }

    private void sendRegistrationToServer(final String token) {

        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);

        RequestQueue requestQueue;


        //requestQueue = Volley.newRequestQueue(getApplicationContext());

        final String URL_REGISTER_DEVICE = "https://zahedice14.000webhostapp.com/fcm/register.php";

                StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // progressDialog.dismiss();
                       // try {
                           // JSONObject obj = new JSONObject(response);
                            //Toast.makeText(FirebaseInstanceIDService.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                       // } catch (JSONException e) {
                           // e.printStackTrace();
                       // }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progressDialog.dismiss();
                       // Toast.makeText(FirebaseInstanceIDService.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<>();
               // params.put("email", email);
                params.put("token", token);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //private void registerToken(String token) {
   /*
    private void onTokenRefresh(String token) {


        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("Token", token)
                .build();

        Request request = new Request.Builder()
                .url("https://zahedice14.000webhostapp.com/fcm/register.php")
                .post(body)
                .build();


        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */


}
