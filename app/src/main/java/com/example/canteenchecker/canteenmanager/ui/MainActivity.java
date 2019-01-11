package com.example.canteenchecker.canteenmanager.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.core.CanteenModel;
import com.example.canteenchecker.canteenmanager.ui.basedata.BaseDataActivity;
import com.example.canteenchecker.canteenmanager.ui.login.LoginActivity;
import com.example.canteenchecker.canteenmanager.ui.mealoftheday.MealOfTheDayActivity;
import com.example.canteenchecker.canteenmanager.ui.ratings.RatingsActivity;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity {

    private static final int LOGIN_FOR_CANTEEN_MANAGER = 23;
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ProgressDialog progressDialog;
    private MainActivityViewModel _viewModel;

    @BindView(R.id.canteenNameTextView)
    TextView canteenName;

    @BindView(R.id.ratingsTextView)
    TextView ratingHeadlineInfo;

    @BindView(R.id.mealOfDayTextView)
    TextView mealOfTheDay;

    @BindView(R.id.canteenInfoTextView)
    TextView canteenInfo;

    @BindView(R.id.ratingsAverageTextView)
    TextView ratingsAverage;


    @OnClick(R.id.mealOfTheDayButton) void startMealOfTheDay() {
        Intent intent = new Intent(this,  MealOfTheDayActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.baseDataButton) void startBaseData() {
        Intent intent = new Intent(this,  BaseDataActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ratingButton) void startRating() {
        Intent intent = new Intent(this,  RatingsActivity.class);
        startActivity(intent);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            initialize();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);
        _viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);
        progressDialog = ProgressDialog.show(this,getString(R.string.info_service),getString(R.string.info_retriving_data));
        progressDialog.hide();

        this.initialize();
    }
    private void updateFromViewModel(){
        this.canteenName.setText(this._viewModel.getCanteenName());
        this.ratingHeadlineInfo.setText(this._viewModel.getRatingCountInfo());
        this.mealOfTheDay.setText(this._viewModel.getMealOfTheDay());
        this.canteenInfo.setText(this._viewModel.getCanteenInfo());
        this.ratingsAverage.setText(this._viewModel.getRatingAverageInfo());
    }
    private void initialize(){
        if(_viewModel.isLoginRequired()){
            startActivityForResult(LoginActivity.createIntent(this), LOGIN_FOR_CANTEEN_MANAGER);
        }else {
            progressDialog.show();
            try {
                final MainActivity that = this;
                _viewModel.load().observe(this, new Observer<CanteenModel>() {
                    @Override
                    public void onChanged(@Nullable CanteenModel canteenModel) {
                        if(canteenModel != null){
                            progressDialog.dismiss();
                            that._viewModel.setModel(canteenModel);
                            that.updateFromViewModel();

                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, R.string.error_canteen_deleted, Toast.LENGTH_SHORT).show();
                            startActivityForResult(LoginActivity.createIntent(that), LOGIN_FOR_CANTEEN_MANAGER);
                        }
                    }
                });
            } catch (IOException e) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this,R.string.error_network, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            _viewModel.logout();
            initialize();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_FOR_CANTEEN_MANAGER && resultCode == Activity.RESULT_OK) {
            this.initialize();
        }
    }
}
