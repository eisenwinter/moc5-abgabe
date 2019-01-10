package com.example.canteenchecker.canteenmanager.ui.ratings;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.canteenchecker.canteenmanager.core.CanteenModel;
import com.example.canteenchecker.canteenmanager.service.CanteenRestService;

import java.io.IOException;

import javax.inject.Inject;

public class RatingsViewModel extends ViewModel {
    private final CanteenRestService _api;

    @Inject
    public RatingsViewModel(CanteenRestService api){
        this._api = api;
    }

    private CanteenModel model;

    public LiveData<CanteenModel> load() throws IOException {
        return _api.get();
    }

    public CanteenModel getModel() {
        return model;
    }

    public LiveData<Boolean> delete(int ratingId) throws IOException {
        return _api.deleteRating(ratingId);
    }

    public void setModel(CanteenModel model) {
        this.model = model;
    }

}
