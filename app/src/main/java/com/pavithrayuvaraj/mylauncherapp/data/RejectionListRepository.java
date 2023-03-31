package com.pavithrayuvaraj.mylauncherapp.data;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.pavithrayuvaraj.mylauncherapp.interfaces.ClientUsageService;
import com.pavithrayuvaraj.mylauncherapp.util.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * class to perform network operation
 */
public class RejectionListRepository {
    public static final String TAG = RejectionListRepository.class.getName();
    public static RejectionListRepository mRejectionListRepository;
    private final Application mApplication;

    private RejectionListRepository(Application application) {
        mApplication = application;
    }
    public static RejectionListRepository getInstance(Application application) {
        if(mRejectionListRepository == null) {
            mRejectionListRepository = new RejectionListRepository(application);
        }
        return mRejectionListRepository;
    }

    /**
     * Method to get the list of denied apps
     * @return  List of strings
     */
    public MutableLiveData<List<String>> getDenyList() {
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .cache(new Cache(mApplication.getCacheDir(), 10 * 1024 *1024))
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    if(NetworkUtils.isInternetConnected(mApplication)) {
                        request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                    } else {
                        request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                    }
                    return chain.proceed(request);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.jsonbin.io/v3/b/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        ClientUsageService clientUsageService = retrofit.create(ClientUsageService.class);

        MutableLiveData<List<String>> appInfoMutableLiveData = new MutableLiveData<>();

        Call<ResponseBody> responseBodyCall = clientUsageService.getRecord();
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: " + response.code());
                if(response.code() == 200) {
                    if(response.body() != null) {
                        appInfoMutableLiveData.setValue(processResponse(response.body()));
                    } else {
                        appInfoMutableLiveData.setValue(null);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                appInfoMutableLiveData.setValue(null);
            }
        });
        return appInfoMutableLiveData;
    }

    /*
    {
        "record":{"denylist":["com.android.chrome","com.google.android.apps.maps","com.android.dialer","com.google.android.gm"]},
        "metadata":{"id":"6323a7995c146d63ca9d124f","private":false,"createdAt":"2022-09-15T22:30:49.803Z","name":"Denylist"}}
     */

    /**
     * Method to process JSON response
     * @param message   JSON response
     * @return          list of strings containing denied apps info
     */
    private List<String> processResponse(ResponseBody message) {
        List<String> denyList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(message.string());
            JSONObject record = new JSONObject(jsonObject.getString("record"));
            JSONArray denyArray = new JSONArray(record.getString("denylist"));

            for(int i = 0; i < denyArray.length(); i++) {
                denyList.add(denyArray.getString(i));
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return denyList;
    }

}
