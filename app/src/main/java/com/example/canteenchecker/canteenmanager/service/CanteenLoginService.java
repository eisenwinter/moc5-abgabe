package com.example.canteenchecker.canteenmanager.service;

import android.arch.lifecycle.LiveData;

import java.io.IOException;

public interface CanteenLoginService  extends CanteenAuthService  {
    LiveData<Boolean> login(String username, String password) throws IOException;
    void logout();
}
