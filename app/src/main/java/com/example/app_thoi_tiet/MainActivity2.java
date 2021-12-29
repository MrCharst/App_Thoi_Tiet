package com.example.app_thoi_tiet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    String namepho="";
    TextView txtTenTP;
    ImageView imgback;
    ListView lv;
    CustomAdapter customAdapter;
    ArrayList<ThoiTiet> mangthoitiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Anhxa();
        Intent intent  = getIntent();
        String City = intent.getStringExtra("name");
        Log.d("ada","ten thanh pho:"+City);
        if (City.equals("")){
            namepho="Hanoi";
            Get7days(namepho);
        }
        else{
            namepho=City;
            Get7days(namepho);
        }
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void Anhxa() {
        txtTenTP = (TextView) findViewById(R.id.textviewTenthanhpho);
        imgback = (ImageView) findViewById(R.id.imageviewback);
        lv = (ListView) findViewById(R.id.listview1);
        mangthoitiet = new ArrayList<ThoiTiet>();
        customAdapter = new CustomAdapter(MainActivity2.this, mangthoitiet);
        lv.setAdapter(customAdapter);
    }
    public void Get7days(String data){
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&cnt=7&appid=c6b536bfd602c8f7821c7193263a0c47";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //Log.d("ok",response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");
                            txtTenTP.setText(name);

                            JSONArray jsonArraylist = jsonObject.getJSONArray("list");
                            for(int i = 0 ; i < jsonArraylist.length(); i++){
                                JSONObject jsonObjectlist = jsonArraylist.getJSONObject(i);
                                String dt_txt = jsonObjectlist.getString("dt");
                                long l  = Long.valueOf(dt_txt);
                                Date date = new Date(l*1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm");
                                String Day = simpleDateFormat.format(date);

                                //Log.d("ok",Day);
                                JSONObject jsonArraylistmain = jsonObjectlist.getJSONObject("main");
                                String temp_min = jsonArraylistmain.getString("temp");
                                String temp_max = jsonArraylistmain.getString("temp_max");
                                String NhietDoMax = String.valueOf(Double.valueOf(temp_max).intValue());
                                String NhietDoMin = String.valueOf(Double.valueOf(temp_min).intValue());
                                Log.d("d",NhietDoMax+NhietDoMin);
                                JSONArray jsonArraylistwheather = jsonObjectlist.getJSONArray("weather");
                                JSONObject jsonObjectdescription = jsonArraylistwheather.getJSONObject(0);
                                String description = jsonObjectdescription.getString("description");
                                String icon = jsonObjectdescription.getString("icon");
                                mangthoitiet.add(new ThoiTiet(Day, description, icon, NhietDoMax, NhietDoMin));
                            }
                            customAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            //e.printStackTrace();
                            Log.d("Loi", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


        requestQueue.add(stringRequest);
    }


}