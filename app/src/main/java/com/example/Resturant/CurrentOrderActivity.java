package com.example.Resturant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class CurrentOrderActivity extends AppCompatActivity {

    private TextView room, status, statusText;
    public static Button checkout;
    String status3="Your order is ready for you. Please pick it up and confirm receiving it after that. Enjoy your meal";
    String status2="Your order is on its way to your location right now. Enjoy your meal!";
    String status4="Your order is finished! Please feel free to write down about your experience with us";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);

        room= (TextView) findViewById(R.id.ResRoomNumber);
        status= (TextView) findViewById(R.id.order_status);
        statusText= (TextView) findViewById(R.id.textViewStatusString);
        room.setText(HomeActivity.currentRoomNumber);
        status.setText(HomeActivity.orderStatus);
        checkout= (Button) findViewById(R.id.buttonCheckOut);
        if (room.getText().toString().isEmpty()){
            checkout.setEnabled(false);
        }

        checkout.setEnabled(false);
        String statusString= status.getText().toString();
        switch(statusString){
            case "In its way": {statusText.setText(status2); break;}
            case "Ready for you": {statusText.setText(status3); checkout.setEnabled(true);  break;}
            case "Finished": {statusText.setText(status4); checkout.setBackgroundResource(R.drawable.btnreport); checkout.setEnabled(true); break;}
            default: statusText.setText("Your order is being cared of by the chef. Just a few minutes"); break;
        }

        if(status.getText().toString().equals("Ready for you")){
            checkout.setEnabled(true);
        }

        checkout.setOnClickListener(v -> {

            if (statusString.equals("Finished")){
                Intent intent=new Intent(CurrentOrderActivity.this , reportActivity.class);
                startActivity(intent);
                finish();
                return;
            }

            String number= room.getText().toString();

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[2];
                    field[0] = "number";
                    field[1] = "username";
                    //Creating array for data
                    String[] data = new String[2];
                    data[0] = number;
                    data[1] = HomeActivity.username;
                    PutData putData = new PutData(MainActivity.IP+"checkout.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if(result.equals("Order Finished")){
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(CurrentOrderActivity.this , HomeActivity.class);
                                startActivity(intent);
                                finish();
                                HomeActivity.clearReservation();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                    //End Write and Read data with URL
                }
            });



        });
    }
}