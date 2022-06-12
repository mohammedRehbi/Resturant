package com.example.Resturant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String urladdress=MainActivity.IP+"meals.php";
    String[] roomNumber;
    String[] price;
    String[] imagepath;
    ListView listView;
    BufferedInputStream is;
    String line=null;
    String result=null;
    private ImageView currentReserv;
    public static String currentRoomNumber, username, orderStatus;

    EditText search;

  public static  ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        saveSharedPref();

        Bundle b = getIntent().getExtras();
        if(b != null)
        username = b.getString("username");

        listView = (ListView) findViewById(R.id.lview);
        search = (EditText) findViewById(R.id.edSearch);
        currentReserv= (ImageView) findViewById(R.id.currentResv);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "username";
                //Creating array for data
                String[] data = new String[1];
                data[0] = username;
                System.out.println(username);
                PutData putData = new PutData(MainActivity.IP+"res.php", "POST", field, data);
                PutData putData2 = new PutData(MainActivity.IP+"res2.php", "POST", field, data);
                if (putData.startPut() && putData2.startPut()) {
                    if (putData.onComplete() && putData2.onComplete()) {
                        String result = putData.getResult();
                        String result2 = putData2.getResult();
                        HomeActivity.currentRoomNumber=result;
                        HomeActivity.orderStatus=result2;

                    }
                }
                //End Write and Read data with URL
            }
        });

        currentReserv.setOnClickListener(v -> {
            Intent intent=new Intent(HomeActivity.this , CurrentOrderActivity.class);
            startActivity(intent);
            // finish();
        });

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();
        CustomListView customListView=new CustomListView(this,roomNumber,price,imagepath);
        listView.setAdapter(customListView);

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



                if (imageView.getDrawable()==null){
                    return;
                }
                else{


                Intent intent = new Intent(HomeActivity.this, MealActivity.class);
                Bundle b = new Bundle();


                imageView.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
               // Toast.makeText(getApplicationContext(), drawable.toString(), Toast.LENGTH_SHORT).show();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                b.putString("key", textView.getText().toString().trim()); //Your id
                b.putString("price", textViewPrice.getText().toString().trim()); //Your id
                intent.putExtra("image",byteArray);
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);

            }
            }

        });

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                try{
                    String roomNo= search.getText().toString();
                    int index = 10;
                    switch (roomNo){
                        case "Cheese Burger": index=0; break;
                        case "Double Burger": index=1; break;
                        case "Big Burger": index=2; break;
                        case "Cranshy Burger": index=3; break;
                        case "Chicken Burger": index=4; break;
                        default:
                            break;
                    }

                listView.performItemClick(listView.getAdapter().getView(index, null, null), index, listView.getItemIdAtPosition(3));
                } catch (Exception e) {
                     Toast.makeText(getApplicationContext(), "Can't find meal", Toast.LENGTH_SHORT).show();
                }
                return true;
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

    public void saveSharedPref(){
        SharedPreferences sharedPreferences= getSharedPreferences(MainActivity.SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt(MainActivity.PROFITS_PREFS,MainActivity.profits);
        editor.apply();

    }
}
