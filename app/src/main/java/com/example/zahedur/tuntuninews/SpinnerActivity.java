package com.example.zahedur.tuntuninews;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by zahedur on 2/23/18.
 */

public class SpinnerActivity extends AddingNews implements AdapterView.OnItemSelectedListener {


    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        Toast.makeText(this, "YOUR SELECTION IS : " + parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();


    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}