package com.example.app_thoi_tiet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch, btnChangeAcitivity, btnFavorite;
    TextView txtName, txtCountry, txtTemp, txtStatus, txtMay, txtGio, txtNgay, txtHumidity;
    ImageView imgicon;
    String City="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        GetCurrent("Hanoi");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city=edtSearch.getText().toString();
                if (city.equals("")){
                    City="Hanoi";
                    GetCurrent("Hanoi");
                }
                else{
                    City=city;
                    GetCurrent(City);
                }
            }
        });
        btnChangeAcitivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city=edtSearch.getText().toString();
                Intent intent =new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("name",city);
                startActivity(intent);
            }
        });
    }
    public void GetCurrent(String data){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+data+"&appid=c6b536bfd602c8f7821c7193263a0c47&units=metric";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("dd",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtName.setText("Thành phố: "+name);

                            long l  = Long.valueOf(day);
                            Date date = new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss");
                            String Day = simpleDateFormat.format(date);
                            txtNgay.setText(Day);
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectweather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectweather.getString("main");
                            String icon = jsonObjectweather.getString("icon");

                            Picasso.with(MainActivity.this).load("https://openweathermap.org/img/wn/"+icon+".png").into(imgicon);
                            txtStatus.setText(status);
                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietdo = jsonObjectMain.getString("temp");
                            String doam = jsonObjectMain.getString("humidity");
                            Double a = Double.valueOf(nhietdo);
                            String NhietDo = String.valueOf(a.intValue());

                            txtTemp.setText(NhietDo+"°C");
                            txtHumidity.setText(doam+"%");

                            JSONObject jsonObjectwind = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectwind.getString("speed");
                            txtGio.setText(gio+"m/s");

                            JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectCloud.getString("all");
                            txtMay.setText(may+"%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            txtCountry.setText("Quốc Gia: " + country);
                        } catch (JSONException e) {
                            e.printStackTrace();
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
    private void AnhXa() {
        edtSearch =(EditText) findViewById(R.id.edsearch);
        btnSearch = (Button) findViewById(R.id.buttonSearch);
        btnChangeAcitivity = (Button) findViewById(R.id.buttonChangeActivity);
        txtName = (TextView) findViewById(R.id.textviewName);
        txtCountry = (TextView) findViewById(R.id.textviewCountry);
        txtTemp = (TextView) findViewById(R.id.textviewTemp);
        txtStatus = (TextView) findViewById(R.id.textviewStatus);
        txtMay = (TextView) findViewById(R.id.textviewMay);
        txtGio = (TextView) findViewById(R.id.textviewGio);
        txtNgay = (TextView) findViewById(R.id.textviewDay);
        txtHumidity = (TextView) findViewById(R.id.textviewHumidity);
        imgicon = (ImageView) findViewById(R.id.imageIcon);

    }
}