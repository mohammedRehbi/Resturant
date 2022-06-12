package com.example.Resturant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChefActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String urladdress=MainActivity.IP+"chefOrders.php";
    String[] mealName;
    String[] amount;
    String[] usernameOrder;
    String[] delivery;
    ListView listView;
    BufferedInputStream is;
    String line=null;
    String result=null;
    String addressurl="";
    public static String currentMealName, username;
    public static  ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);

        Bundle b = getIntent().getExtras();
        if(b != null)
            username = b.getString("username");

        listView = (ListView) findViewById(R.id.lview2);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
       try{CustomListViewChef customListViewChef=new CustomListViewChef(this,mealName,amount);  listView.setAdapter(customListViewChef); } catch (Exception e){e.printStackTrace();}

       if(delivery!=null){
        if(delivery[0].equals("0")){
            addressurl="chefdone.php";
        }
        else addressurl = "chefDoneDelivery.php";
       }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {

                TextView textView = (TextView) view.findViewById(R.id.textViewRoomNumber);
                TextView textViewPrice = (TextView) view.findViewById(R.id.textViewPrice);
                imageView = (ImageView) findViewById(R.id.imageViewRoomPhoto);
                listView.setEnabled(false);
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.setEnabled(true);
                    }
                }, 500);


                    Intent intent = new Intent(ChefActivity.this, ChefActivity.class);
                    Bundle b = new Bundle();

                    String user= usernameOrder[0];
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[1];
                    field[0] = "username";
                    //Creating array for data
                    String[] data = new String[1];
                    data[0] = user;
                    PutData putData = new PutData(MainActivity.IP+addressurl, "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if (result.equals("Order Chef Finished")) {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            }
                        }}

                }


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
            mealName=new String[ja.length()];
            amount=new String[ja.length()];
            usernameOrder=new String[ja.length()];
            delivery=new String[ja.length()];


            for(int i=0;i<=ja.length();i++){
                jo=ja.getJSONObject(i);
                mealName[i]=jo.getString("meal");
                amount[i]=jo.getString("amount");
                usernameOrder[i]=jo.getString("username");
                delivery[i]=jo.getString("delivery");

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

}
