package com.example.canteenchecker.canteenmanager.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.canteenchecker.canteenmanager.ui.MainActivityViewModel;
import com.example.canteenchecker.canteenmanager.ui.basedata.BaseDataActivity;
import com.example.canteenchecker.canteenmanager.ui.basedata.BaseDataViewModel;
import com.example.canteenchecker.canteenmanager.ui.login.LoginActivityViewModel;
import com.example.canteenchecker.canteenmanager.ui.mealoftheday.MealOfTheDayViewModel;
import com.example.canteenchecker.canteenmanager.ui.ratings.RatingsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginActivityViewModel.class)
    public abstract ViewModel bindLoginActivityViewModel(LoginActivityViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    public abstract ViewModel bindMainActivityViewModel(MainActivityViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(BaseDataViewModel.class)
    public abstract ViewModel bindBaseDataViewModel(BaseDataViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(MealOfTheDayViewModel.class)
    public abstract ViewModel bindMealOfTheDayViewModel(MealOfTheDayViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(RatingsViewModel.class)
    public abstract ViewModel bindRatingsViewModel(RatingsViewModel model);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
