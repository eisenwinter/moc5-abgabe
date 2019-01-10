package com.example.canteenchecker.canteenmanager.di;

import com.example.canteenchecker.canteenmanager.ui.MainActivity;
import com.example.canteenchecker.canteenmanager.ui.basedata.BaseDataActivity;
import com.example.canteenchecker.canteenmanager.ui.login.LoginActivity;
import com.example.canteenchecker.canteenmanager.ui.mealoftheday.MealOfTheDayActivity;
import com.example.canteenchecker.canteenmanager.ui.ratings.RatingsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();
    @ContributesAndroidInjector
    abstract LoginActivity contributeLoginActivity();
    @ContributesAndroidInjector
    abstract BaseDataActivity contributeBaseDataActivity();
    @ContributesAndroidInjector
    abstract MealOfTheDayActivity contributeMealOfTheDayActivity();
    @ContributesAndroidInjector
    abstract RatingsActivity contributeRatingsActivity();
}