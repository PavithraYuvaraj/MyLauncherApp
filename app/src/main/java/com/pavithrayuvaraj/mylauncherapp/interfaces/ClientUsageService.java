package com.pavithrayuvaraj.mylauncherapp.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ClientUsageService {

    @GET("6323a7995c146d63ca9d124f")
    Call<ResponseBody> getRecord();

}
