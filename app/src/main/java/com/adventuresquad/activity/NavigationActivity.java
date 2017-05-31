package com.adventuresquad.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.adventuresquad.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationActivity extends AppCompatActivity {

    @BindView(R.id.navigation_message)
    public TextView mTextMessage;

    @BindView(R.id.navigation_bar)
    public BottomNavigationView mNavigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_create:
                    showToastMessage("Navigation: Create");
                    mTextMessage.setText(R.string.title_create);
                    return true;
                case R.id.navigation_squads:
                    showToastMessage("Navigation: Squads");
                    mTextMessage.setText(R.string.title_squad);
                    return true;
                case R.id.navigation_home:
                    showToastMessage("Navigation: Home");
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_myTrips:
                    showToastMessage("Navigation: MyTrips");
                    mTextMessage.setText(R.string.title_my_trips);
                    return true;
                case R.id.navigation_profile:
                    showToastMessage("Navigation: Profile ");
                    mTextMessage.setText(R.string.title_profile);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        ButterKnife.bind(this);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * Shows a given string resource as a toast
     */
    public void showToastMessage(String string) {
        Toast.makeText(this, string,
                Toast.LENGTH_SHORT).show();
    }

}
