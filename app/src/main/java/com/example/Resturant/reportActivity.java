package com.example.Resturant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class reportActivity extends AppCompatActivity {
    private RadioButton like, dislike;
    private EditText commentText;
    private Button submit;
    String rate= "Like";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        like= (RadioButton) findViewById(R.id.radioLike);
        dislike= (RadioButton) findViewById(R.id.radioDislike);
        commentText= (EditText) findViewById(R.id.edit_textComment);
        submit= (Button) findViewById(R.id.buttonSubmit);

        like.setOnClickListener(v -> {
            dislike.setChecked(false);
            rate="Like";
        });

        dislike.setOnClickListener(v -> {
            like.setChecked(false);
            rate="Dislike";
        });


        submit.setOnClickListener(v -> {
            String username= HomeActivity.username;
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[3];
                    field[0] = "username";
                    field[1] = "rate";
                    field[2] = "comment";
                    //Creating array for data
                    String[] data = new String[3];
                    data[0] = username;
                    data[1]  = rate;
                    data[2] = commentText.getText().toString();
                    PutData putData = new PutData(MainActivity.IP+"reporting.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if(result.equals("Reporting Success")){
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(reportActivity.this , HomeActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                    //End Write and Read data with URL
                }
            });
        });

    }
}