package com.example.zahedur.tuntuninews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.ErrorListener;

public class AddingNews extends AppCompatActivity   {

    EditText _title, _date, _temp,_hrate;
    Button  _submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_news);


        Intent intent = getIntent();


        _title = (EditText)findViewById(R.id.news_title);
        _date = (EditText)findViewById(R.id.news_date);
        _temp = (EditText)findViewById(R.id.news_temp);
        _hrate = (EditText)findViewById(R.id.news_hrate);
        _submit = (Button)findViewById(R.id.submit_news);


        _title.setText("zahed888");
        _hrate.setText(intent.getStringExtra("hrate"));
        _temp.setText(intent.getStringExtra("temp"));



        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        _date.setText(mydate);


        _submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://zahedice14.000webhostapp.com/api/newspostfromapp.php";
                StringRequest sq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    protected Map<String, String> getParams(){

                        Map<String,String> parr = new HashMap<String, String>();
                        parr.put("mytitle",_title.getText().toString());
                        parr.put("mydate",_date.getText().toString());
                       // parr.put("mytemp",_temp.getText().toString());
                        parr.put("mytemp",_temp.getText().toString());
                        parr.put("myheartrate",_hrate.getText().toString());

                        return  parr;

                    }


                };

                AppController.getInstance().addToRequestQueue(sq);
                Toast.makeText(getApplicationContext(),"Patient Information is added to website successfully",Toast.LENGTH_LONG).show();


                Intent intent = new Intent(AddingNews.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



}