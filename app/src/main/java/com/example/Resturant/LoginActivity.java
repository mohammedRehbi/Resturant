package com.example.Resturant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class LoginActivity extends AppCompatActivity {

    private ImageView dontHaveAccount;
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dontHaveAccount= (ImageView) findViewById(R.id.imageViewDontHave);
        editTextUsername= (EditText) findViewById(R.id.loginUser);
        editTextPassword= (EditText) findViewById(R.id.loginPassword);
        buttonLogin= (Button) findViewById(R.id.buttonLogin);
        spinner=findViewById(R.id.spinner3);
        dontHaveAccount.setOnClickListener(view -> {
            Intent intent=new Intent(LoginActivity .this , SignupActivity.class);
            startActivity(intent);
            finish();

        });

        buttonLogin.setOnClickListener(view -> {
            String username= String.valueOf(editTextUsername.getText());
            String password= String.valueOf(editTextPassword.getText());

            if(username.isEmpty()  || password.isEmpty() ){
                Toast.makeText(getApplicationContext(), "Please make sure all fields are not empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (spinner.getSelectedItem().equals("Customer")){
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[2];
                        field[0] = "username";
                        field[1] = "password";
                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = username;
                        data[1] = password;
                        PutData putData = new PutData(MainActivity.IP+"login.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("Login Success")){
                                    Bundle b = new Bundle();
                                    b.putString("username", username); //Your id
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(LoginActivity.this , HomeActivity.class);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                        //End Write and Read data with URL
                    }
                });

            }
            else if (spinner.getSelectedItem().equals("Manager")){
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[2];
                        field[0] = "username";
                        field[1] = "password";
                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = username;
                        data[1] = password;
                        PutData putData = new PutData(MainActivity.IP+"login_admin.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("Login Success")){
                                    Bundle b = new Bundle();
                                    b.putString("username", username); //Your id
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MealViewActivity.class);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                        //End Write and Read data with URL
                    }
                });


            }

            else if (spinner.getSelectedItem().equals("Chef")){
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[2];
                        field[0] = "username";
                        field[1] = "password";
                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = username;
                        data[1] = password;
                        PutData putData = new PutData(MainActivity.IP+"login_chef.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("Login Success")){
                                    Bundle b = new Bundle();
                                    b.putString("username", username); //Your id
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, ChefActivity.class);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                        //End Write and Read data with URL
                    }
                });


            }

            else if (spinner.getSelectedItem().equals("Delivery Employer")){
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[2];
                        field[0] = "username";
                        field[1] = "password";
                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = username;
                        data[1] = password;
                        PutData putData = new PutData(MainActivity.IP+"login_delivery.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if(result.equals("Login Success")){
                                    Bundle b = new Bundle();
                                    b.putString("username", username); //Your id
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, deliveryActivity.class);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                        //End Write and Read data with URL
                    }
                });


            }

        });
    }
}