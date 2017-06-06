package com.adventuresquad.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.adventuresquad.R;

public class SquadsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squads);

        //Initialise navbar
        new NavbarHelper(this, R.id.navigation_squads);
    }
}
