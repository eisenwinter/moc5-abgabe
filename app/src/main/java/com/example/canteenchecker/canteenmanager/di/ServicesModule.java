package com.example.canteenchecker.canteenmanager.di;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.infrastructure.CanteenRestServiceProxy;
import com.example.canteenchecker.canteenmanager.service.CanteenRestService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
public class ServicesModule {
    @Provides
    @Singleton
    public CanteenRestService provideRestService(Application application)  {
        return new CanteenRestServiceProxy(application.getSharedPreferences(application.getString(R.string.filekey), Context.MODE_PRIVATE));
    }
}