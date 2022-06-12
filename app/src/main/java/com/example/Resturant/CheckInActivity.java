package com.example.Resturant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class CheckInActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    private TextView nights,total,mainTV;
    private SeekBar seekBar;
    private EditText addressText;
    private int totalInt;
    private int price=0;
    private RadioButton Delivery, noDelivery;
    private Button buttonCount;
    public static String roomNo = "", address= "";
    public static int delivery = 0, totalPaid= 0, amount= 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        Bundle b = getIntent().getExtras();
        String priceString = "";
        if(b != null)
        priceString = b.getString("price2");
        roomNo = b.getString("key2");
        price= Integer.parseInt(priceString);


        nights= (TextView) findViewById(R.id.textViewNights);
        total= (TextView) findViewById(R.id.textViewTotal);
        seekBar= (SeekBar) findViewById(R.id.seekBar);
        buttonCount=(Button) findViewById(R.id.buttonCont);
        addressText= (EditText) findViewById(R.id.textAddress);

        mainTV= (TextView) findViewById(R.id.textViewMain);
        mainTV.setText(roomNo);

        Delivery= (RadioButton) findViewById(R.id.radioButtonWithRoomService);
        noDelivery= (RadioButton) findViewById(R.id.radioButtonWithoutRoomService);


        noDelivery.setChecked(true);
        addressText.setVisibility(View.GONE);

        Delivery.setOnClickListener(v -> {
            noDelivery.setChecked(false);
            totalInt= (int) (totalInt*1.25);
            total.setText(""+totalInt);
            delivery=1;
            addressText.setVisibility(View.VISIBLE);
        });

        noDelivery.setOnClickListener(v -> {
            Delivery.setChecked(false);
           try {totalInt= (Integer.parseInt(nights.getText().toString()))*price;} catch (Exception e){e.printStackTrace();}
            total.setText(""+totalInt);
            delivery=0;
            addressText.setText("");
            addressText.setVisibility(View.GONE);
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                nights.setText(""+progress);
                totalInt= price*progress;
                total.setText(""+totalInt);
                amount= progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonCount.setOnClickListener(v -> {
            totalPaid=totalInt;
            address=addressText.getText().toString();
            Intent intent=new Intent(CheckInActivity.this , PaymentActivity.class);
            startActivity(intent);
            finish();
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text= adapterView.getItemAtPosition(i).toString();
        switch(text){
         //here we could decide what to do with the spinner choice
            default: { break;}
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}