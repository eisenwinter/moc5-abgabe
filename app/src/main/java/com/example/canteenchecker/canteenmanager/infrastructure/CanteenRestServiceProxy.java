package com.example.canteenchecker.canteenmanager.infrastructure;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;

import com.example.canteenchecker.canteenmanager.core.CanteenModel;
import com.example.canteenchecker.canteenmanager.core.RatingModel;
import com.example.canteenchecker.canteenmanager.service.CanteenRestService;

import java.io.IOException;
import java.util.Collection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class CanteenRestServiceProxy implements CanteenRestService {
    private static final String SERVICE_BASE_URL = "https://canteenchecker.azurewebsites.net/";
    private static final String TAG = CanteenRestServiceProxy.class.toString();
    private final SharedPreferences _preferences;
    public CanteenRestServiceProxy(SharedPreferences preferences){
        this._preferences = preferences;
        this._authenticationToken = preferences.getString("auth",null);
    }

    private String _authenticationToken;
    private String getToken(){
        return String.format("Bearer %s", _authenticationToken);
    }

    @Override
    public LiveData<CanteenModel> get() throws IOException {
        final MutableLiveData<CanteenModel> data = new MutableLiveData<CanteenModel>();
        this.proxy.get(getToken()).enqueue(new Callback<CanteenModel>() {
            @Override
            public void onResponse(Call<CanteenModel> call, Response<CanteenModel> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CanteenModel> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    @Override
    public LiveData<Boolean> update(CanteenModel canteen) throws IOException {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();

        this.proxy.update(getToken(), canteen).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                data.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                data.setValue(false);
            }
        });
        return data;
    }

    @Override
    public LiveData<Boolean> deleteRating(int ratingId) throws IOException {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        this.proxy.removeRating(getToken(),ratingId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                data.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                data.setValue(false);
            }
        });
        return data;
    }

    @Override
    public LiveData<Boolean> login(String username, String password) throws IOException {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        final CanteenRestServiceProxy that = this;
        proxy.login(new ProxyLogin(username, password)).enqueue(new Callback<String>(){
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if (response.isSuccessful())
                {
                    that._authenticationToken = response.body();
                    SharedPreferences.Editor editor = _preferences.edit();
                    editor.putString("auth",that._authenticationToken);
                    editor.apply();
                    data.setValue(true);
                }else{
                    data.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                data.setValue(false);
            }
        });
        return  data;
    }
    @Override
    public void logout() {
        this._authenticationToken = null;
        SharedPreferences.Editor editor = _preferences.edit();
        editor.remove("auth");
        editor.apply();
    }

    @Override
    public boolean isAuthenticated() {
        return _authenticationToken != null && !_authenticationToken.isEmpty();
    }

    private interface Proxy {

        @POST("/Admin/Login")
        Call<String> login(@Body ProxyLogin login);

        @GET("/Admin/Canteen")
        Call<CanteenModel> get(@Header("Authorization") String authenticationToken);

        @PUT("/Admin/Canteen")
        Call<Void> update(@Header("Authorization") String authenticationToken, @Body CanteenModel canteen);

        @DELETE("/Admin/Canteen/Rating/{ratingId}")
        Call<ResponseBody> removeRating(@Header("Authorization") String authenticationToken , @Path("ratingId") int ratingId);

    }

    private final Proxy proxy = new Retrofit.Builder()
            .baseUrl(SERVICE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Proxy.class);

    private static class ProxyLogin {

        final String username;
        final String password;

        ProxyLogin(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }




}
