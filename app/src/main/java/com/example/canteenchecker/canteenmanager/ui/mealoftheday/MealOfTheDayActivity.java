package com.example.canteenchecker.canteenmanager.ui.mealoftheday;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.core.CanteenModel;
import com.example.canteenchecker.canteenmanager.ui.MainActivity;
import com.example.canteenchecker.canteenmanager.ui.basedata.BaseDataActivity;
import com.example.canteenchecker.canteenmanager.ui.basedata.BaseDataViewModel;
import com.example.canteenchecker.canteenmanager.ui.login.LoginActivity;
import com.example.canteenchecker.canteenmanager.ui.ratings.RatingsActivity;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class MealOfTheDayActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.mealPriceEditText)
    EditText mealPriceEditText;

    @BindView(R.id.mealNameEditText)
    EditText mealNameEditText;

    private ProgressDialog progressDialog;
    private MealOfTheDayViewModel _viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealoftheday);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);
        _viewModel = ViewModelProviders.of(this, viewModelFactory).get(MealOfTheDayViewModel.class);
        progressDialog = ProgressDialog.show(this, getString(R.string.info_service), getString(R.string.info_retriving_data));
        progressDialog.hide();
        initialize();
    }

    private void updateFromViewModel(){
        this.mealNameEditText.setText(this._viewModel.getModel().getMeal());
        this.mealPriceEditText.setText(this._viewModel.getModel().getMealPrice().toString());
    }

    private void initialize(){
        progressDialog.show();
        try {
            final MealOfTheDayActivity that = this;
            _viewModel.load().observe(this, new Observer<CanteenModel>() {
                @Override
                public void onChanged(@Nullable CanteenModel canteenModel) {
                    if(canteenModel != null){
                        progressDialog.dismiss();
                        that._viewModel.setModel(canteenModel);
                        that.updateFromViewModel();

                    }
                }
            });
        } catch (IOException e) {
            progressDialog.dismiss();
            Toast.makeText(MealOfTheDayActivity.this,R.string.error_network, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basedata, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {

                try {
                    progressDialog.show();
                    this._viewModel.getModel().setMeal(this.mealNameEditText.getText().toString());
                    this._viewModel.getModel().setMealPrice(Double.parseDouble(this.mealPriceEditText.getText().toString()));
                    _viewModel.save().observe(this,new Observer<Boolean>(){
                        @Override
                        public void onChanged(@Nullable Boolean value) {
                            progressDialog.dismiss();
                            if(value != null && value){
                                Toast.makeText(MealOfTheDayActivity.this, "Saved!", Toast.LENGTH_SHORT).show();

                            }else{

                                Toast.makeText(MealOfTheDayActivity.this,R.string.error_connection,  Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (IOException e) {
                    progressDialog.dismiss();
                    Toast.makeText(this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
