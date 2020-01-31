package com.ar.pablo.wundermobilitytest.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import timber.log.Timber;

public class CarMapFragment extends DaggerFragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int AMOUNT_OF_CLICK_TO_GO_TO_DETAIL = 2;

    private CarMapViewModel carMapViewModel;
    private GoogleMap map;
    private MapView mapView;
    private List<Marker> markers = new ArrayList<>();
    private int clickCount = 0;

    @Inject
    CarMapViewModelFactory carMapViewModelFactory;

    public static CarMapFragment newInstance() {
        return new CarMapFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.carMapViewModel =
                new ViewModelProvider(this, this.carMapViewModelFactory)
                        .get(CarMapViewModel.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentCarMapBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_car_map,
                container, false);
        setUpMapView(savedInstanceState, binding);
        return binding.getRoot();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMarkerClickListener(this);
        enableMyLocation();
        carMapViewModel.loadCarInfo();
        carMapViewModel.getCarLiveData().observe(this, this::handleCarInfo);
        carMapViewModel.getErrorLiveData().observe(this, throwable ->
                Toast.makeText(getContext(),
                        "An error has occur trying to fetch vehicles information",
                        Toast.LENGTH_SHORT).show());
        setUpMapStyle();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0
                    && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        clickCount++;
        removeMarkers(marker);
        marker.showInfoWindow();

        if (clickCount == AMOUNT_OF_CLICK_TO_GO_TO_DETAIL) {
            openDetailView(CarDetailFragment
                    .newInstance(Objects.requireNonNull(marker.getTag()).toString()));
        }
        return true;
    }

    private void removeMarkers(Marker marker) {
        for (Marker mark : markers) {
            if (!mark.getId().equals(marker.getId())) {
                mark.remove();
            }
        }
    }

    private void handleCarInfo(List<CarUi> cars) {
        if (cars != null) {
            addMarkers(cars);
            setBoundingArea();
        }
    }

    private void setUpMapView(@Nullable Bundle savedInstanceState, FragmentCarMapBinding binding) {
        mapView = binding.carMapFragment;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
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

    private void addMarkers(List<CarUi> cars) {
        for (CarUi vehicle : cars) {
            Marker marker = makeMarker(vehicle);
            marker.setTag(vehicle.getCarId());
            markers.add(marker);
        }
    }

    private Marker makeMarker(CarUi vehicle) {
        return map.addMarker(new MarkerOptions()
                .position(new LatLng(vehicle.getLat(), vehicle.getLon()))
                .title(vehicle.getTitle()));
    }

    private void setBoundingArea() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 0;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.animateCamera(cameraUpdate);
    }

    private void setUpMapStyle() {
        try {
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            Objects.requireNonNull(getActivity()), R.raw.map_style));

            if (!success) {
                Timber.e("Style parsing filed");
            }
        } catch (Resources.NotFoundException e) {
            Timber.e(e, "Can't find style. Error: ");
        }
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
}
