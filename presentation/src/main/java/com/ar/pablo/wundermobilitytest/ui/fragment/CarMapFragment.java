package com.ar.pablo.wundermobilitytest.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.ar.pablo.wundermobilitytest.R;
import com.ar.pablo.wundermobilitytest.databinding.FragmentCarMapBinding;
import com.ar.pablo.wundermobilitytest.ui.model.CarUi;
import com.ar.pablo.wundermobilitytest.ui.viewmodel.CarMapViewModel;
import com.ar.pablo.wundermobilitytest.ui.viewmodel.viewmodelfactory.CarMapViewModelFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class CarMapFragment extends DaggerFragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    public static CarMapFragment newInstance() {
        return new CarMapFragment();
    }

    @Inject
    CarMapViewModelFactory carMapViewModelFactory;
    private CarMapViewModel carMapViewModel;
    private GoogleMap map;
    private MapView mapView;
    private List<Marker> markers = new ArrayList<>();
    private int clickCount = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.carMapViewModel =
                new ViewModelProvider(this, this.carMapViewModelFactory)
                        .get(CarMapViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentCarMapBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_car_map,
                container, false);
        mapView = binding.carMapFragment;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return binding.getRoot();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            requestPermissions(new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0
                    && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            }
        }
    }

    private void setMarkers(List<CarUi> cars) {
        for (CarUi vehicle : cars) {
            Marker marker =
                    map.addMarker(new MarkerOptions()
                            .position(new LatLng(vehicle.getLat(), vehicle.getLon()))
                            .title(vehicle.getTitle()));
            marker.setTag(vehicle.getCarId());
            markers.add(marker);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(this);
        enableMyLocation();
        carMapViewModel.getCarInfo().observe(this, cars -> {
            if (cars != null) {
                setMarkers(cars);
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        clickCount++;
        for (Marker mark : markers) {
            if (!mark.getId().equals(marker.getId())) {
                mark.remove();
            }
        }
        marker.showInfoWindow();

        if (clickCount == 2) {
            openDetailView(CarDetailFragment
                    .newInstance(Objects.requireNonNull(marker.getTag()).toString()));
        }
        return true;
    }

    private void openDetailView(Fragment fragment) {
        FragmentTransaction fragmentTransaction =
                Objects.requireNonNull(getActivity())
                        .getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
        clickCount = 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
