package com.ar.pablo.data.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CarReservationDto {

    @SerializedName("carId")
    @Expose
    private String carId;

    public CarReservationDto() {
        //empty constructor
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}
