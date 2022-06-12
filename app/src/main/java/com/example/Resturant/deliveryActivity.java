package com.example.Resturant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class deliveryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String urladdress=MainActivity.IP+"deliveryOrders.php";
    String[] addressString;
    String[] usernameOrder;
    ListView listView;
    BufferedInputStream is;
    String line=null;
    String result=null;
    public static String currentRoomNumber, username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        Bundle b = getIntent().getExtras();
        if(b != null)
            username = b.getString("username");

        listView = (ListView) findViewById(R.id.lview2);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        try{CustomListViewDelivery customListViewDelivery=new CustomListViewDelivery(this, addressString, usernameOrder);  listView.setAdapter(customListViewDelivery); } catch (Exception e){e.printStackTrace();}


        listView.setOnItemClickListener((adapter, view, position, arg) -> {

            listView.setEnabled(false);
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(() -> listView.setEnabled(true), 500);


            Intent intent = new Intent(deliveryActivity.this, deliveryActivity.class);

            String user= usernameOrder[0];
            //Starting Write and Read data with URL
            //Creating array for parameters
            String[] field = new String[1];
            field[0] = "username";
            //Creating array for data
            String[] data = new String[1];
            data[0] = user;
            PutData putData = new PutData(MainActivity.IP+"deliveryDone.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    if (result.equals("Order Delivery Finished")) {
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                    }
                }}

        });



    }
    private void collectData()
    {
//Connection
        try{

            URL url=new URL(urladdress);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            is=new BufferedInputStream(con.getInputStream());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        //content
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();
            while ((line=br.readLine())!=null){
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();

        }

//JSON
        try{
            JSONArray ja=new JSONArray(result);
            JSONObject jo=null;
            addressString=new String[ja.length()];
            usernameOrder=new String[ja.length()];


            for(int i=0;i<=ja.length();i++){
                jo=ja.getJSONObject(i);
                addressString[i]=jo.getString("address");
                usernameOrder[i]=jo.getString("username");

            }
        }
        catch (Exception ex)
        {

            ex.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    public static void clearReservation(){
        currentRoomNumber="";

    }
}
