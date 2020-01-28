package com.ar.pablo.wundermobilitytest.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.ar.pablo.wundermobilitytest.R;
import com.ar.pablo.wundermobilitytest.ui.viewmodel.CarMapViewModel;
import com.ar.pablo.wundermobilitytest.ui.viewmodel.viewmodelfactory.CarMapViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import timber.log.Timber;

public class CarMapFragment extends DaggerFragment {

    @Inject
    CarMapViewModelFactory carMapViewModelFactory;
    CarMapViewModel carMapViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_car_map,
                container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.carMapViewModel =
                new ViewModelProvider(this, this.carMapViewModelFactory)
                        .get(CarMapViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        carMapViewModel.getCarInfo().observe(this, car -> {
            if (car != null) {
                Timber.d("Message%s", car);
            }
        });
    }
}
