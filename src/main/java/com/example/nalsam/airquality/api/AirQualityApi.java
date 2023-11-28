package com.example.nalsam.airquality.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AirQualityApi {

    @GET("getCtprvnRltmMesureDnsty")
    Call<AirQualityData.GetAirQualityResponse> getAirQuality(@Query("serviceKey") String serviceKey,
                                                             @Query("returnType") String returnType,
                                                             @Query("numOfRows") int numOfRows,
                                                             @Query("pageNo") int pageNo,
                                                             @Query("ver") String ver,
                                                             @Query("sidoName") String sidoCode);
}
