package com.example.canteenchecker.canteenmanager.ui.login;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.example.canteenchecker.canteenmanager.service.CanteenLoginService;
import com.example.canteenchecker.canteenmanager.service.CanteenRestService;

import java.io.IOException;

import javax.inject.Inject;
public class LoginActivityViewModel extends ViewModel {
    private String username;
    private String password;

    private final MutableLiveData<Boolean> loginResponse = new MutableLiveData<Boolean>();
    private final CanteenLoginService loginService;
    @Inject
    public LoginActivityViewModel(CanteenRestService loginService){
        this.loginService = loginService;
        if(this.loginService.isAuthenticated())  this.loginService.logout();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public LiveData<Boolean> loginAttempt() throws IOException {
        return loginService.login(this.username,this.password);
    }
}
