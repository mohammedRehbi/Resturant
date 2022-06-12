package com.example.Resturant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MealViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String urladdress=MainActivity.IP+"meals.php";
    String[] roomNumber;
    String[] price;
    String[] imagepath;
    ListView listView;
    BufferedInputStream is;
    String line=null;
    String result=null;
    public static String currentRoomNumber, username;
    public static  ImageView imageView;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_view);

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.nav_viewFood);
        bottomNavigationView.setSelectedItemId(R.id.navigation_meals);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_meals:
                    return true;

                case R.id.navigation_reports:
                    startActivity(new Intent(getApplicationContext() , ReportShowActivity.class));
                    finish();
                    overridePendingTransition(0,0);
                    return true;

                case R.id.navigation_profits:
                    startActivity(new Intent(getApplicationContext() , prfitsActivity.class));
                    finish();
                    overridePendingTransition(0,0);
                    return true;

                    }
            return false;
        });


        Bundle b = getIntent().getExtras();
        if(b != null)
            username = b.getString("username");

        listView = (ListView) findViewById(R.id.lview2);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        CustomListView customListView=new CustomListView(this,roomNumber,price,imagepath);
        listView.setAdapter(customListView);
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
            roomNumber=new String[ja.length()];
            price=new String[ja.length()];
            imagepath=new String[ja.length()];

            for(int i=0;i<=ja.length();i++){
                jo=ja.getJSONObject(i);
                roomNumber[i]=jo.getString("number");
                price[i]=jo.getString("price_per_night");
                imagepath[i]=jo.getString("photo");
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
