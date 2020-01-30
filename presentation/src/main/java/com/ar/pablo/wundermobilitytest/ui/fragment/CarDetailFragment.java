package com.ar.pablo.wundermobilitytest.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.ar.pablo.wundermobilitytest.R;

import dagger.android.support.DaggerFragment;

public class CarDetailFragment extends DaggerFragment {

    private static final String CAR_ID = "com.ar.wundermobilitytest.carid";
    private String carId;

    static CarDetailFragment newInstance(String carId) {
        Bundle args = new Bundle();
        args.putString(CAR_ID, carId);
        CarDetailFragment fragment = new CarDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (getArguments() != null && getArguments().containsKey(CAR_ID)) {
            carId = getArguments().getString(CAR_ID);
        }

        return DataBindingUtil.inflate(inflater,
                R.layout.fragment_car_detail,
                container, false).getRoot();
    }

}
