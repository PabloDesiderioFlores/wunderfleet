package com.ar.pablo.wundermobilitytest.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.ar.pablo.domain.model.Car;
import com.ar.pablo.wundermobilitytest.R;
import com.ar.pablo.wundermobilitytest.databinding.FragmentCarDetailBinding;
import com.ar.pablo.wundermobilitytest.ui.viewmodel.CarDetailViewModel;
import com.ar.pablo.wundermobilitytest.ui.viewmodel.viewmodelfactory.CarDetailViewModelFactory;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class CarDetailFragment extends DaggerFragment {

    private static final String CAR_ID = "com.ar.wundermobilitytest.carid";
    private FragmentCarDetailBinding binding;
    private String carId;
    private CarDetailViewModel carDetailViewModel;
    @Inject
    CarDetailViewModelFactory carDetailViewModelFactory;

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

        binding.btnRent.setOnClickListener(this::onClick);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        carDetailViewModel.getCarInfoById(carId);

        carDetailViewModel.getCarLiveData().observe(this, this::handleCarInfo);

        carDetailViewModel.getCarLiveDataError().observe(this, throwable ->
                handleError("An has occur while trying to get vehicle detail information" + throwable));
    }

    private void onClick(View v) {
        showLoadingSpinner();

        carDetailViewModel.setCarReservation(carId);

        carDetailViewModel.getCarReservationLiveData().observe(this, carReservation ->
                handleCarReservationInfo());

        carDetailViewModel.getCarReservationLiveDataError().observe(this, throwable ->
                handleError("An error has occur trying to reserve this car" + throwable));
    }

    private void handleCarInfo(Car car) {
        if (car != null) {
            binding.setCar(car);
            showDetailInfo();
        }
    }

    private void handleCarReservationInfo() {
        showDetailInfo();
        Toast.makeText(getContext(), "Car has been reserved", Toast.LENGTH_SHORT).show();
        backToCarMapFragment();
    }

    private void handleError(String error) {
        showDetailInfo();
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    private void showDetailInfo() {
        binding.loadingSpinner.setVisibility(View.GONE);
        binding.containerInfo.setVisibility(View.VISIBLE);
    }

    private void showLoadingSpinner() {
        binding.loadingSpinner.setVisibility(View.VISIBLE);
        binding.containerInfo.setVisibility(View.GONE);
    }

    private void backToCarMapFragment() {
        FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        fm.popBackStack();
    }
}
