package com.example.canteenchecker.canteenmanager.ui.mealoftheday;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.canteenchecker.canteenmanager.core.CanteenModel;
import com.example.canteenchecker.canteenmanager.service.CanteenRestService;

import java.io.IOException;

import javax.inject.Inject;

public class MealOfTheDayViewModel extends ViewModel {


    private final CanteenRestService _api;
    private CanteenModel model;

    @Inject
    public MealOfTheDayViewModel(CanteenRestService api) {
        this._api = api;
    }

    public LiveData<CanteenModel> load() throws IOException {
        return _api.get();
    }

    public CanteenModel getModel() {
        return model;
    }

    public LiveData<Boolean> save() throws IOException {
        return _api.update(this.model);
    }

    public void setModel(CanteenModel model) {
        this.model = model;
    }


}