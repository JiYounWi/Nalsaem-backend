package com.example.nalsam.weather.api;

import retrofit2.Call;
import retrofit2.http.*;

public interface WeatherForecastApi {
    @GET("getVilageFcst")
    Call<WeatherForecastData.GetWeatherForecastResponse> getWeatherForecast(@Query("serviceKey")String serviceKey,
                                                                         @Query("dataType") String dataType,
                                                                         @Query("pageNo")int pageNo,  //61
                                                                         @Query("numOfRows")int numOfRows,
                                                                         @Query("base_date") String date,
                                                                         @Query("base_time") String time,
                                                                         @Query("nx")String nx,  //61
                                                                         @Query("ny")String ny); //125
}
