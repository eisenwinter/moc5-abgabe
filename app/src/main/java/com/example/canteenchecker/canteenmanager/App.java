package com.example.canteenchecker.canteenmanager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


import com.example.canteenchecker.canteenmanager.di.DaggerAppComponent;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import javax.inject.Inject;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {

    private static final String FIREBASE_MESSAGING_TOPIC = "canteens";

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder().application(this).build().inject(this);

        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().subscribeToTopic(FIREBASE_MESSAGING_TOPIC);

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
