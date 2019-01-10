package com.example.canteenchecker.canteenmanager.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.canteenchecker.canteenmanager.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity  {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private LoginActivityViewModel _viewModel;

    private ProgressDialog progressDialog;

    @BindView(R.id.user)
    EditText userEditText;

    @BindView(R.id.password)
    EditText passwordEditText;

    @NonNull
    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        progressDialog = ProgressDialog.show(this,getString(R.string.info_service),getString(R.string.info_retriving_data));
        progressDialog.hide();

        AndroidInjection.inject(this);
        _viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginActivityViewModel.class);
    }

    @OnClick(R.id.sign_in_button)
    void onLoginClicked() {
        progressDialog.show();
        try {
            if(processInput()){
                _viewModel.loginAttempt().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        progressDialog.dismiss();
                        if(aBoolean) {
                            setResult(Activity.RESULT_OK);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,R.string.error_invalid_credentials, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        } catch (IOException e) {
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this,R.string.error_network, Toast.LENGTH_SHORT).show();

        }
    }

    boolean processInput(){
        if(userEditText.getText().toString().trim().isEmpty()){
            Toast.makeText(LoginActivity.this,R.string.error_invalid_username, Toast.LENGTH_SHORT).show();
            return false;
        }
        _viewModel.setUsername(userEditText.getText().toString());
        if(passwordEditText.getText().toString().trim().isEmpty()){
            Toast.makeText(LoginActivity.this,getString(R.string.error_no_password), Toast.LENGTH_SHORT).show();
            return false;
        }
        _viewModel.setPassword(passwordEditText.getText().toString());
        return true;
    }
}

