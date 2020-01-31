package com.ar.pablo.data.net.retrofit;

import com.ar.pablo.data.dto.CarReservationDto;
import com.ar.pablo.data.entity.model.CarReservationEntity;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CarReservationAPI {
    @POST("wunderfleet-recruiting-mobile-dev-quick-rental")
    Observable<CarReservationEntity> setCarReservation(
            @Header("Authorization") String header,
            @Body CarReservationDto dto);
}
