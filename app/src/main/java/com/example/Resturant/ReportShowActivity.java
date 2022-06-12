package com.example.Resturant;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReportShowActivity extends AppCompatActivity {
    private static  final String BASE_URL = MainActivity.IP+"reports.php";
    String[] username;
    String[] email;
    private List<Report> items = new ArrayList<>();
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_show);

        recycler=findViewById(R.id.user_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        loadItems();
        //  print();


        bottomNavigationView=(BottomNavigationView)findViewById(R.id.nav_viewFood);
        bottomNavigationView.setSelectedItemId(R.id.navigation_reports);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_reports:
                    return true;

                case R.id.navigation_meals:
                    startActivity(new Intent(getApplicationContext() , MealViewActivity.class));
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





    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void loadItems() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);
                                String name = object.getString("username");
                                String rate = object.getString("rate");
                                String comment= object.getString("comment");
                                // Toast.makeText(UserShowActivity.this, name,Toast.LENGTH_LONG).show();
                                Report report = new Report(name, rate,comment);
                                items.add(report);
                            }
                            //text.setText(items.toString());
                        }catch (Exception e){

                        }

                        CaptionedReportAdapter adapter = new CaptionedReportAdapter(ReportShowActivity.this,
                                items);
                        recycler.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(ReportShowActivity.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(ReportShowActivity.this).add(stringRequest);

    }
    private void print(){
        //   text.setText("22"+"-->"+items.get(1).toString());

    }
}