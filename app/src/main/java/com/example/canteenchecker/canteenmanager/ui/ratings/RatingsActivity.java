package com.example.canteenchecker.canteenmanager.ui.ratings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canteenchecker.canteenmanager.R;
import com.example.canteenchecker.canteenmanager.core.CanteenModel;
import com.example.canteenchecker.canteenmanager.core.RatingModel;
import com.example.canteenchecker.canteenmanager.infrastructure.MyFirebaseMessagingService;
import com.example.canteenchecker.canteenmanager.ui.MainActivity;
import com.example.canteenchecker.canteenmanager.ui.basedata.BaseDataViewModel;
import com.example.canteenchecker.canteenmanager.ui.login.LoginActivity;
import com.example.canteenchecker.canteenmanager.ui.mealoftheday.MealOfTheDayActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class RatingsActivity extends AppCompatActivity {
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ProgressDialog progressDialog;
    private RatingsViewModel _viewModel;
    private final RatingAdapter ratingAdapter = new RatingAdapter();

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "New Rating Received!", Toast.LENGTH_SHORT).show();
            initialize();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);
        _viewModel = ViewModelProviders.of(this, viewModelFactory).get(RatingsViewModel.class);
        progressDialog = ProgressDialog.show(this, getString(R.string.info_service), getString(R.string.info_retriving_data));
        progressDialog.hide();

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, MyFirebaseMessagingService.updatedRatingsMessage());
    }

    private void initialize(){
        progressDialog.show();
        try {
            final RatingsActivity that = this;
            _viewModel.load().observe(this, new Observer<CanteenModel>() {
                @Override
                public void onChanged(@Nullable CanteenModel canteenModel) {
                    if(canteenModel != null){
                        progressDialog.dismiss();
                        that._viewModel.setModel(canteenModel);
                        ratingAdapter.setRatings(canteenModel.getRatings());

                    }
                }
            });
        } catch (IOException e) {
            progressDialog.dismiss();
            Toast.makeText(RatingsActivity.this,R.string.error_network, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }


    private void createConfirmedDelete(DialogInterface.OnClickListener clickListener){
        final View dialogDelete = LayoutInflater.from(this).inflate(R.layout.modal_delete, null);
        new AlertDialog.Builder(this).setTitle(R.string.title_are_you_sure)
                .setView(dialogDelete)
                .setPositiveButton("Delete", clickListener)
                .create()
                .show();
    }

    class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {

        private final List<RatingModel> ratings = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rating, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return ratings.size();
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final RatingModel current = ratings.get(position);
            final RatingAdapter that = this;

            holder.remarkTextView.setText(current.getRemark());
            holder.userNameTextView.setText(current.getUsername());
            holder.ratingRatingBar.setRating(current.getRatingPoints());
            Date date = new Date(current.getTimestamp());
            holder.createdTextView.setText(new SimpleDateFormat("dd-MM-yyyy, HH:mm").format((date)));
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createConfirmedDelete(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            try {
                                progressDialog.show();
                                _viewModel.delete(current.getRatingId()).observe(RatingsActivity.this, new Observer<Boolean>() {
                                    @Override
                                    public void onChanged(@Nullable Boolean aBoolean) {
                                        if(aBoolean != null && aBoolean){
                                            that.ratings.remove(current);
                                            notifyDataSetChanged();
                                            Toast.makeText(RatingsActivity.this, R.string.toast_deleted, Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(RatingsActivity.this,R.string.toast_couldntdelete, Toast.LENGTH_SHORT).show();
                                        }
                                        progressDialog.dismiss();
                                    }
                                });
                            } catch (IOException e) {
                                progressDialog.dismiss();
                                Toast.makeText(RatingsActivity.this,R.string.toast_couldntdelete, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            });

        }

        public void setRatings(Collection<RatingModel> ratings) {
            ratings.clear();
            if (ratings != null) {
                ratings.addAll(ratings);
            }
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView userNameTextView = itemView.findViewById(R.id.createdByTextView);
            private final TextView createdTextView = itemView.findViewById(R.id.createdOnTextView);
            private final TextView remarkTextView = itemView.findViewById(R.id.itemRemarkTextView);
            private final RatingBar ratingRatingBar = itemView.findViewById(R.id.ratingRatingBar);
            private final Button deleteButton = itemView.findViewById(R.id.deleteButton);

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

    }



}
