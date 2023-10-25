package com.example.myapplication;

import static com.android.volley.Request.Method.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Model.WeatherForecastDetails;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY="https://api.weatherapi.com/v1/forecast.json?key=5e454612d15c42e0af9141437232410";


    private TextView setLocationTxt;
    private TextView setTempTxt;

    private TextInputEditText searchEdt;
    private ImageView searchLogo;
    private ImageView currentWeatherLogo;

    private RecyclerView recyclerView;

    private ArrayList<WeatherForecastDetails>list=new ArrayList<>();
    ForecastAdapter forecastAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLocationTxt=findViewById(R.id.setloction_txt);
        setTempTxt=findViewById(R.id.temp_txt);
        searchEdt=findViewById(R.id.search_edittxt);
        searchLogo=findViewById(R.id.search_logo);
        currentWeatherLogo=findViewById(R.id.currentSatusWeather_img);
        recyclerView=findViewById(R.id.forecastRecycleview);



        searchLogo.setOnClickListener(e->{
            if(!(searchEdt.getText()+"").equals("") )
                getData(searchEdt.getText()+"".trim());
            else
                Toast.makeText(this,"Please enter location",Toast.LENGTH_SHORT).show();

        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        forecastAdapter = new ForecastAdapter(this, list);

        recyclerView.setAdapter(forecastAdapter);




    }



    private  void getData(String location){
        list.clear();
        String url=API_KEY+"&q="+location+"&days=2&aqi=no&alerts=no";

        //Instantiate the RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        //Json Oject request

        JsonObjectRequest request = new JsonObjectRequest(GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject responseJSONObject = response.getJSONObject("location");
                    setLocationTxt.setText("⟟"+responseJSONObject.getString("name"));
                    setTempTxt.setText(response.getJSONObject("current").getDouble("temp_c")+"°C");

                    // set weather current status logo
                    String iconUrl=response.getJSONObject("current").getJSONObject("condition").getString("icon");

                    String fullIconUrl="https:"+iconUrl;
                    //Log.i("VIKAS",fullIconUrl);

                     //load image
                    Picasso.get().load(fullIconUrl).into(currentWeatherLogo);





                    // now load forecast details  .all details add into arraylist;
                    JSONObject forecast = response.getJSONObject("forecast");
                    JSONArray forecastJSONArray = forecast.getJSONArray("forecastday");

                    // maxWindSpeed
                    String maxWindSpeed=forecastJSONArray.getJSONObject(0).getJSONObject("day")
                            .getString("maxwind_kph");

                    JSONArray hour = forecastJSONArray.getJSONObject(0).getJSONArray("hour");

                    for(int i=0;i<hour.length();i++){

                        String tempC = hour.getJSONObject(i).getString("temp_c");

                        // to check pm or am
                        String isDay = hour.getJSONObject(i).getString("is_day");

                        //time
                        String time =hour.getJSONObject(i).getString("time");

                        String timeFormat=simpleTimeformate(time);


                        String windSpeed = hour.getJSONObject(i).getString("wind_kph");

                        //percentage
                        String rainPercenatge = hour.getJSONObject(i).getString("chance_of_rain");

                     //   Log.i("SMILE", rainPercenatge+"");

                        //icon
                        String icon = hour.getJSONObject(i).getJSONObject("condition").getString("icon");
                        String fulliconAdress="https:"+icon;

                        // now all details add into array....
                        list.add(new WeatherForecastDetails(timeFormat,tempC,windSpeed,rainPercenatge,fulliconAdress));



                    }


                    // run background
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            forecastAdapter.notifyDataSetChanged();
                        }
                    });




                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // handing error
              //  Toast.makeText(MainActivity.this,"PLease Enter Proper Location",Toast.LENGTH_LONG).show();

            }
        });

        requestQueue.add(request);



    }
    private  String simpleTimeformate(String time){
        String format;
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat outputFormate = new SimpleDateFormat("yyyy-MM-dd hh:mma");

        try {
            Date date = input.parse(time);
            format = outputFormate.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return  format;


    }
}