package com.ar.pablo.domain.model;

public class Car {

    private Integer carId;
    private String title;
    private Double lat;
    private Double lon;
    private String licencePlate;
    private Integer fuelLevel;
    private Integer vehicleStateId;
    private Integer vehicleTypeId;
    private String pricingTime;
    private String pricingParking;
    private Integer reservationState;
    private Boolean isClean;
    private Boolean isDamaged;
    private String distance;
    private String address;
    private String zipCode;
    private String city;
    private Integer locationId;

    private String hardwareId;
    private Boolean isActivatedByHardware;
    private String damageDescription;
    private String vehicleTypeImageUrl;

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public Integer getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(Integer fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public Integer getVehicleStateId() {
        return vehicleStateId;
    }

    public void setVehicleStateId(Integer vehicleStateId) {
        this.vehicleStateId = vehicleStateId;
    }

    public Integer getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Integer vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public String getPricingTime() {
        return pricingTime;
    }

    public void setPricingTime(String pricingTime) {
        this.pricingTime = pricingTime;
    }

    public String getPricingParking() {
        return pricingParking;
    }

    public void setPricingParking(String pricingParking) {
        this.pricingParking = pricingParking;
    }

    public Integer getReservationState() {
        return reservationState;
    }

    public void setReservationState(Integer reservationState) {
        this.reservationState = reservationState;
    }

    public Boolean getIsClean() {
        return isClean;
    }

    public void setIsClean(Boolean isClean) {
        this.isClean = isClean;
    }

    public Boolean getIsDamaged() {
        return isDamaged;
    }

    public void setIsDamaged(Boolean isDamaged) {
        this.isDamaged = isDamaged;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public Boolean getActivatedByHardware() {
        return isActivatedByHardware;
    }

    public void setActivatedByHardware(Boolean activatedByHardware) {
        isActivatedByHardware = activatedByHardware;
    }

    public String getDamageDescription() {
        return damageDescription;
    }

    public void setDamageDescription(String damageDescription) {
        this.damageDescription = damageDescription;
    }

    public String getVehicleTypeImageUrl() {
        return vehicleTypeImageUrl;
    }

    public void setVehicleTypeImageUrl(String vehicleTypeImageUrl) {
        this.vehicleTypeImageUrl = vehicleTypeImageUrl;
    }
}

