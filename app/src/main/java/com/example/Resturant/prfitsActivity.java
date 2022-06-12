package com.example.Resturant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class prfitsActivity extends AppCompatActivity {

    private TextView profits;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prfits);

        profits= (TextView) findViewById(R.id.textViewProfits);
        profits.setText("$"+String.valueOf(MainActivity.profits));

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.nav_viewFood);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profits);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_profits:
                    return true;

                case R.id.navigation_meals:
                    startActivity(new Intent(getApplicationContext() , MealViewActivity.class));
                    finish();
                    overridePendingTransition(0,0);
                    return true;

                case R.id.navigation_reports:
                    startActivity(new Intent(getApplicationContext() , ReportShowActivity.class));
                    finish();
                    overridePendingTransition(0,0);
                    return true;

            }
            return false;
        });

    }
}