package com.example.Resturant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView mainImageView;
    private ImageView secondImageView;
    private TextView mainText, secondText, skip;
    private Button startButton;
    private int index=1;
    public static final String  IP= "http://192.168.1.115/loginreg/";
    private String mainText2="Food Ordering";
    private String mainText3="Enjoy Your Meal";
    private String secondText2="Your food is getting ready right after you verify the order. Just few minutes and you can enjoy your meal";
    private String secondText3="Our delivery employers can reach you even faster than you think!. Please feel free to rate our services. We appreciate it";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String PROFITS_PREFS = "profits_prefs";
    public static int profits=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainImageView= (ImageView) findViewById(R.id.startMainImageView);
        secondImageView= (ImageView) findViewById(R.id.startIndexImageView);
        mainText= (TextView) findViewById(R.id.startMainTextView);
        secondText= (TextView) findViewById(R.id.startSecondTextView);
        skip= (TextView) findViewById(R.id.startSkipTextView);
        startButton= (Button) findViewById(R.id.startButton);

       loadSharedPref();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (index==1){
                   mainImageView.setImageDrawable(getDrawable(R.drawable.foodordering));
                   secondImageView.setImageDrawable(getDrawable(R.drawable.index2));
                   mainText.setText(mainText2);
                   secondText.setText(secondText2);
                   index++;
               }
               else if (index ==2){
                   mainImageView.setImageDrawable(getDrawable(R.drawable.deliver));
                   secondImageView.setImageDrawable(getDrawable(R.drawable.index3));
                   mainText.setText(mainText3);
                   secondText.setText(secondText3);
                   startButton.setText("Start");
                   index++;
               }
               else if (index ==3){
                   Intent intent=new Intent(MainActivity.this , SignupActivity.class);
                   startActivity(intent);
                   finish();
               }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent=new Intent(MainActivity.this , LoginActivity.class);
                    startActivity(intent);
                    finish();

            }
        });

    }

    public void loadSharedPref(){
        SharedPreferences sharedPreferences= getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        profits=sharedPreferences.getInt(PROFITS_PREFS,0);

    }
}