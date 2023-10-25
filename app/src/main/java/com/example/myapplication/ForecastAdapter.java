package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.WeatherForecastDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private Context context;
    private ArrayList<WeatherForecastDetails>list;

    public ForecastAdapter(Context context, ArrayList<WeatherForecastDetails> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_viewholder, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        holder.temp.setText(list.get(position).getTemp()+"°C");
        holder.speed.setText(list.get(position).getSpeed()+"kph");
        holder.percentage.setText(list.get(position).getRainPercenatge()+"%");
        holder.time.setText(list.get(position).getTime());
//        if (holder.logo != null) {
//            Picasso.get().load(list.get(position).getStatusImage()).into(holder.logo);
//        }
       Picasso.get().load(list.get(position).getStatusImage()).error(R.drawable.cloud_logo).into(holder.logo);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ForecastViewHolder  extends RecyclerView.ViewHolder{

        private TextView temp,speed,percentage,time;
        private ImageView logo;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            temp=itemView.findViewById(R.id.settempforecast);
            speed=itemView.findViewById(R.id.setspeedforecast);
            percentage=itemView.findViewById(R.id.setpercenatgeforecast);
            time=itemView.findViewById(R.id.settimeforecast);
            logo=itemView.findViewById(R.id.forecastsetlogo);
        }
    }
}
