package com.example.canteenchecker.canteenmanager.service;

import android.arch.lifecycle.LiveData;

import com.example.canteenchecker.canteenmanager.core.CanteenModel;

import java.io.IOException;

public interface CanteenCrudService extends CanteenAuthService {
    LiveData<CanteenModel> get() throws IOException;
    LiveData<Boolean> update(CanteenModel canteen) throws IOException;
    LiveData<Boolean> deleteRating(int ratingId) throws IOException;
}
