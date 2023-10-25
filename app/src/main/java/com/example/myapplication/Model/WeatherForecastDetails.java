package com.example.myapplication.Model;

import java.io.Serializable;

public class WeatherForecastDetails implements Serializable {

    private String time;
    private String temp;
    private String speed;
    private String rainPercenatge;

    private String statusImage;

    public WeatherForecastDetails(String time, String temp, String speed, String rainPercenatge, String statusImage) {
        this.time = time;
        this.temp = temp;
        this.speed = speed;
        this.rainPercenatge = rainPercenatge;
        this.statusImage = statusImage;
    }

    public String getTime() {
        return time;
    }

    public String getTemp() {
        return temp;
    }

    public String getSpeed() {
        return speed;
    }

    public String getRainPercenatge() {
        return rainPercenatge;
    }

    public String getStatusImage() {
        return statusImage;
    }

    @Override
    public String toString() {
        return "WeatherForecastDetails{" +
                "time='" + time + '\'' +
                ", temp='" + temp + '\'' +
                ", speed='" + speed + '\'' +
                ", rainPercenatge='" + rainPercenatge + '\'' +
                ", statusImage='" + statusImage + '\'' +
                '}';
    }
}
