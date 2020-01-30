package com.ar.pablo.wundermobilitytest.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.ar.pablo.domain.model.Car;
import com.ar.pablo.wundermobilitytest.R;
import com.ar.pablo.wundermobilitytest.databinding.FragmentCarDetailBinding;
import com.ar.pablo.wundermobilitytest.ui.viewmodel.CarDetailViewModel;
import com.ar.pablo.wundermobilitytest.ui.viewmodel.viewmodelfactory.CarDetailViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class CarDetailFragment extends DaggerFragment {

    private static final String CAR_ID = "com.ar.wundermobilitytest.carid";
    private String carId;
    private CarDetailViewModel carDetailViewModel;
    @Inject
    CarDetailViewModelFactory carDetailViewModelFactory;
    private FragmentCarDetailBinding binding;

    static CarDetailFragment newInstance(String carId) {
        Bundle args = new Bundle();
        args.putString(CAR_ID, carId);
        CarDetailFragment fragment = new CarDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.carDetailViewModel =
                new ViewModelProvider(this, this.carDetailViewModelFactory)
                        .get(CarDetailViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (getArguments() != null && getArguments().containsKey(CAR_ID)) {
            carId = getArguments().getString(CAR_ID);
        }

        this.binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_car_detail,
                container, false);

        binding.btnRent.setOnClickListener(v -> {

        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        carDetailViewModel.getCarInfoById(carId).observe(this, car -> {
            if (car != null) {
                binding.setCar(car);
            }
        });
    }
}
