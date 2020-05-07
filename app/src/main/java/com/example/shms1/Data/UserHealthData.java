package com.example.shms1.Data;

public class UserHealthData {

    private int total_steps;
    private double total_distance;
    private int calories;


    public UserHealthData(int t_steps, double t_distance, int calories) {
        this.total_steps = t_steps;
        this.total_distance = t_distance;
        this.calories = calories;
    }

    public int getTotal_steps() {
        return total_steps;
    }

    public double getTotal_distance() {
        return total_distance;
    }

    public int getCalories() {
        return calories;
    }
}
