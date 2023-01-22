package com.example.trainapplication;

public class train {
    private int id;
    private String car_type;
    private int luggage;
    private int seats;
    private int car_class;

    public train(int id, String car_type, int luggage, int seats, int car_class){
        this.id = id;
        this.car_type = car_type;
        this.luggage = luggage;
        this.seats = seats;
        this.car_class = car_class;
    }

    public int getId() {
        return id;
    }

    public String getCar_type() {
        return car_type;
    }

    public int getLuggage() {
        return luggage;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setLuggage(int luggage) {
        this.luggage = luggage;
    }

    public int getCar_class() {
        return car_class;
    }

    public void setCar_class(int car_class) {
        this.car_class = car_class;
    }
}
