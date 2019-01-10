package com.example.canteenchecker.canteenmanager.ui.basedata;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.core.CanteenModel;
import com.example.canteenchecker.canteenmanager.ui.MainActivity;
import com.example.canteenchecker.canteenmanager.ui.MainActivityViewModel;
import com.example.canteenchecker.canteenmanager.ui.login.LoginActivity;
import com.example.canteenchecker.canteenmanager.ui.mealoftheday.MealOfTheDayActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import dagger.android.AndroidInjection;

public class BaseDataActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.WaitingTimeSeekBar)
    SeekBar waitingTimeSeekBar;

    @BindView(R.id.waitingTimeTextView)
    TextView waitingTimeTextView;

    @BindView(R.id.phoneEditText)
    EditText phoneEditText;

    @BindView(R.id.websiteEditText)
    EditText websiteEditText;

    @BindView(R.id.addressEditText)
    EditText addressEditText;

    @BindView(R.id.nameEditText)
    EditText nameEditText;

    @OnCheckedChanged(R.id.toggleMapSwitch) void onMapSwitch(CompoundButton button, boolean checked) {
        if(checked){
            mapFragment.getView().setVisibility(View.VISIBLE);
            mapFragment.getView().getLayoutParams().height = 340;
        }else {
            mapFragment.getView().getLayoutParams().height = 0;
           mapFragment.getView().setVisibility(View.INVISIBLE);

        }
    }


    private ProgressDialog progressDialog;
    private BaseDataViewModel _viewModel;

    private Marker currentPosition;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basedata);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);
        _viewModel = ViewModelProviders.of(this, viewModelFactory).get(BaseDataViewModel.class);
        progressDialog = ProgressDialog.show(this, getString(R.string.info_service), getString(R.string.info_retriving_data));
        progressDialog.hide();
        //binding with butterknife failed ...

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.mapGoogleMap);
        mapFragment.getView().setVisibility(View.INVISIBLE);
        mapFragment.getView().getLayoutParams().height = 0;
        mapFragment.getMapAsync(new OnMapReadyCallback(){

            @Override
            public void onMapReady(final GoogleMap googleMap) {
                UiSettings uiSettings = googleMap.getUiSettings();
                uiSettings.setAllGesturesEnabled(true);
                uiSettings.setZoomControlsEnabled(true);

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        currentPosition = googleMap.addMarker(markerOptions);
                        geocoode();
                    }
                });
            }
        });

        SeekBar.OnSeekBarChangeListener seekbarListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waitingTimeTextView.setText(String.format("%d min.", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        waitingTimeSeekBar.setOnSeekBarChangeListener(seekbarListener);
        initialize();

    }

    private void updateFromViewModel(){
        this.nameEditText.setText(this._viewModel.getModel().getName());
        this.addressEditText.setText(this._viewModel.getModel().getAddress());
        this.websiteEditText.setText(this._viewModel.getModel().getWebsite());
        this.phoneEditText.setText(this._viewModel.getModel().getPhone());
        this.waitingTimeSeekBar.setProgress(this._viewModel.getModel().getAverageWaitingTime());
    }

    private void updateToViewModel(){
        this._viewModel.getModel().setName((this.nameEditText.getText().toString()));
        this._viewModel.getModel().setAddress(this.addressEditText.getText().toString());
        this._viewModel.getModel().setWebsite(this.websiteEditText.getText().toString());
        this._viewModel.getModel().setPhone(this.phoneEditText.getText().toString());
        this._viewModel.getModel().setAverageWaitingTime(this.waitingTimeSeekBar.getProgress());
    }

    private void initialize(){
        progressDialog.show();
        try {
            final BaseDataActivity that = this;
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
            Toast.makeText(BaseDataActivity.this,R.string.error_network, Toast.LENGTH_SHORT).show();
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
            updateToViewModel();
            if(_viewModel.validate()){
                try {
                    progressDialog.show();

                    _viewModel.save().observe(this,new Observer<Boolean>(){
                        @Override
                        public void onChanged(@Nullable Boolean value) {
                            progressDialog.dismiss();
                            if(value != null && value){
                                Toast.makeText(BaseDataActivity.this, "Saved!", Toast.LENGTH_SHORT).show();

                            }else{

                                Toast.makeText(BaseDataActivity.this,R.string.error_connection,  Toast.LENGTH_SHORT).show();
                                                           }
                        }
                    });
                } catch (IOException e) {
                    progressDialog.dismiss();
                    Toast.makeText(this, R.string.error_connection, Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, R.string.toast_empty_canteen_name, Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void geocoode() {
        final BaseDataActivity that = this;
        new AsyncTask<LatLng, Void, String>() {
            @Override
            protected String doInBackground(LatLng... latLngs) {

                List<Address> addresses;
                Geocoder geocoder = new Geocoder(that);
                String address = "";
                try {
                    addresses = geocoder.getFromLocation(latLngs[0].latitude, latLngs[0].longitude, 1);

                    if (addresses != null) {
                        address = addresses.get(0).getAddressLine(0);
                    }
                } catch (Exception e) {

                    return null;
                }
                return address;
            }

            @Override
            protected void onPostExecute(final String address) {
                if (address != null) {
                    that._viewModel.getModel().setAddress(address);
                    that.addressEditText.setText(address);
                }else{
                    Toast.makeText(that, getString(R.string.error_no_address_found), Toast.LENGTH_SHORT);
                }

            }
        }.execute(currentPosition.getPosition());
    }


}
