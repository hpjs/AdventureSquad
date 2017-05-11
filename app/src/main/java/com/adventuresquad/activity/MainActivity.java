package com.adventuresquad.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.adventuresquad.R;
import com.adventuresquad.model.Adventure;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Adventure> mAdventureList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private AdventureAdapter mTrainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
