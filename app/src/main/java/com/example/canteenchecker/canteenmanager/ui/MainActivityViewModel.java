package com.example.canteenchecker.canteenmanager.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.canteenchecker.canteenmanager.core.CanteenModel;
import com.example.canteenchecker.canteenmanager.service.CanteenRestService;

import java.io.IOException;

import javax.inject.Inject;

public class MainActivityViewModel extends ViewModel {
    private final CanteenRestService _api;
    private CanteenModel model;



    @Inject
    public MainActivityViewModel(CanteenRestService api){
        this._api = api;
    }


    public boolean isLoginRequired() {
        return !_api.isAuthenticated();
    }

    public LiveData<CanteenModel> load() throws IOException {
        return _api.get();
    }

    public CanteenModel getModel() {
        return model;
    }

    public void logout(){
        _api.logout();
    }

    public void setModel(CanteenModel model) {
        this.model = model;
    }

    public String getCanteenName(){
        if(this.model != null){
            return this.model.getName();
        }
        return "";
    }

    public String getCanteenInfo(){
        if(this.model != null){
            StringBuilder sb = new StringBuilder();
            if(this.model.getAddress() == null){
                sb.append("-");
            }else{
                sb.append(this.model.getAddress());
            }
            sb.append("\r\n");
            if(this.model.getPhone() == null){
                sb.append("-");
            }else{
                sb.append(this.model.getPhone());
            }
            sb.append(", ");
            if(this.model.getWebsite() == null){
                sb.append("-");
            }else{
                sb.append(this.model.getWebsite());
            }
            sb.append("\r\n");
            sb.append("Average waiting time: ");
            sb.append(this.model.getAverageWaitingTime());
            sb.append(" minutes.");
            return sb.toString();
        }
        return "";
    }

    public String getMealOfTheDay(){
        if(this.model != null){
            return this.model.getMeal();
        }
        return "";
    }

    public String getRatingCountInfo(){
        if(this.model != null){
            if(this.model.getRatings() == null) return "no ratings yet";
            return this.model.getRatings().size() + " ratings";
        }
        return "";
    }

    public String getRatingAverageInfo(){
        if(this.model != null){
            if(this.model.getRatings() == null) return "with no average rating";
            return "with an average of " + this.model.getAverageRating();
        }
        return "";
    }


}
