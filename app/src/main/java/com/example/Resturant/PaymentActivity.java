package com.example.Resturant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;


public class PaymentActivity extends AppCompatActivity {

    private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
    private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
    private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
    private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
    private static final char DIVIDER = '-';

    private static final int CARD_DATE_TOTAL_SYMBOLS = 5; // size of pattern MM/YY
    private static final int CARD_DATE_TOTAL_DIGITS = 4; // max numbers of digits in pattern: MM + YY
    private static final int CARD_DATE_DIVIDER_MODULO = 3; // means divider position is every 3rd symbol beginning with 1
    private static final int CARD_DATE_DIVIDER_POSITION = CARD_DATE_DIVIDER_MODULO - 1; // means divider position is every 2nd symbol beginning with 0
    private static final char CARD_DATE_DIVIDER = '/';

    private static final int CARD_CVC_TOTAL_SYMBOLS = 3;

    private Button pay;
    private EditText cardNumberEditText, cardDateEditText, cardCVCEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        cardNumberEditText =(EditText) findViewById(R.id.cardNumberEditText);
        cardNumberEditText.setTag("not valid");
        cardDateEditText = (EditText) findViewById(R.id.cardDateEditText);
        cardCVCEditText = (EditText) findViewById(R.id.cardCVCEditText);
        pay= (Button) findViewById(R.id.buttonPay);
        cardNumberEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // noop
            }

            @Override
            public void afterTextChanged(Editable s) {

                putIcon();
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }

            public void putIcon(){
                if (cardNumberEditText.getText().toString().length()==1){
                    if (cardNumberEditText.getText().toString().equals("4")){
                        cardNumberEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visa, 0);
                        cardNumberEditText.setTag("valid");
                    }

                    else   if (cardNumberEditText.getText().toString().equals("5")){
                        cardNumberEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.master, 0);
                        cardNumberEditText.setTag("valid");
                    }
                }

                else if(cardNumberEditText.getText().toString().length()==0){
                    cardNumberEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_card_number, 0);
                    cardNumberEditText.setTag("not vaild");
                }


            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // check that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });



        cardDateEditText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, CARD_DATE_TOTAL_SYMBOLS, CARD_DATE_DIVIDER_MODULO, CARD_DATE_DIVIDER)) {
                    s.replace(0, s.length(), concatString(getDigitArray(s, CARD_DATE_TOTAL_DIGITS), CARD_DATE_DIVIDER_POSITION, CARD_DATE_DIVIDER));
                }
            }
        });

        cardCVCEditText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > CARD_CVC_TOTAL_SYMBOLS) {
                    s.delete(CARD_CVC_TOTAL_SYMBOLS, s.length());
                }
            }


        });

        pay.setOnClickListener(v -> {

            if (cardNumberEditText.getText().toString().length()<TOTAL_SYMBOLS || !cardNumberEditText.getTag().equals("valid")){
                Toast.makeText(getApplicationContext(), "Please enter valid credit card number!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (cardDateEditText.getText().toString().length()<CARD_DATE_TOTAL_SYMBOLS){
                Toast.makeText(getApplicationContext(), "Please enter valid expire date!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (cardCVCEditText.getText().toString().length()<CARD_CVC_TOTAL_SYMBOLS){
                Toast.makeText(getApplicationContext(), "CVC must be 3 digits!", Toast.LENGTH_SHORT).show();
                return;
            }

            String number= CheckInActivity.roomNo;
            String username= HomeActivity.username;
            int delivery= CheckInActivity.delivery;
            int totalPaid= CheckInActivity.totalPaid;
            int amount = CheckInActivity.amount;
            String address= CheckInActivity.address;

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[6];
                    field[0] = "number";
                    field[1] = "username";
                    field[2] = "totalPaid";
                    field[3] = "delivery";
                    field[4] = "amount";
                    field[5] = "address";
                    //Creating array for data
                    String[] data = new String[6];
                    data[0] = number;
                    data[1] = username;
                    data[2]  = String.valueOf(totalPaid);
                    data[3] = String.valueOf(delivery);
                    data[4] = String.valueOf(amount);
                    data[5] = address;
                    PutData putData = new PutData(MainActivity.IP+"ordering.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if(result.equals("Ordering Success")){
                                Toast.makeText(getApplicationContext(), result+" "+username, Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(PaymentActivity.this , CheckInResultActivity.class);
                                startActivity(intent);
                                finish();
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




    private boolean isInputCorrect(Editable s, int size, int dividerPosition, char divider) {
        boolean isCorrect = s.length() <= size;
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && (i + 1) % dividerPosition == 0) {
                isCorrect &= divider == s.charAt(i);
            } else {
                isCorrect &= Character.isDigit(s.charAt(i));
            }
        }
        return isCorrect;
    }

    private String concatString(char[] digits, int dividerPosition, char divider) {
        final StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 0) {
                formatted.append(digits[i]);
                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                    formatted.append(divider);
                }
            }
        }

        return formatted.toString();
    }

    private char[] getDigitArray(final Editable s, final int size) {
        char[] digits = new char[size];
        int index = 0;
        for (int i = 0; i < s.length() && index < size; i++) {
            char current = s.charAt(i);
            if (Character.isDigit(current)) {
                digits[index] = current;
                index++;
            }
        }
        return digits;
    }
}

/*
 if (cardNumberEditText.getText().toString().length()<TOTAL_SYMBOLS || !cardNumberEditText.getTag().equals("valid")){
                    Toast.makeText(getApplicationContext(), "Please enter valid credit card number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cardDateEditText.getText().toString().length()<CARD_DATE_TOTAL_SYMBOLS){
                    Toast.makeText(getApplicationContext(), "Please enter valid expire date!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cardCVCEditText.getText().toString().length()<CARD_CVC_TOTAL_SYMBOLS){
                    Toast.makeText(getApplicationContext(), "CVC must be 3 digits!", Toast.LENGTH_SHORT).show();
                    return;
                }
 */