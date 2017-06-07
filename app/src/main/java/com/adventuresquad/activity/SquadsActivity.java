package com.adventuresquad.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.adventuresquad.R;
import com.adventuresquad.api.SquadApi;
import com.adventuresquad.api.StorageApi;
import com.adventuresquad.api.UserApi;
import com.adventuresquad.interfaces.Presentable;
import com.adventuresquad.interfaces.PresentableListView;
import com.adventuresquad.model.Squad;
import com.adventuresquad.presenter.SquadsPresenter;

import java.util.List;

public class SquadsActivity extends AppCompatActivity implements PresentableListView<Squad>{

    RecyclerView mSquadsView;
    SquadsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squads);

        //Set up presenter with dependencies
        mPresenter = new SquadsPresenter(this, new SquadApi(), new UserApi(), new StorageApi());

        //Initialise navbar
        new NavbarHelper(this, R.id.navigation_squads);
    }


    @Override
    public void addListItem(Squad item) {

    }

    @Override
    public void updateList(List<Squad> itemList) {

    }

    @Override
    public void displayMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
