package com.example.shms1.Data;

public class CaloriesCalculator {


    int age;
    private double weight;
    private double height;
    private int activity_level;
    private double activity_level_value;
    private int gender_id;
    private double calories;

    public CaloriesCalculator(int age, double weight, double height, int activity_level, int gender_id) {
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.activity_level = activity_level;
        this.gender_id = gender_id;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public int getActivity_level() {
        return activity_level;
    }

    public int getGender_id() {
        return gender_id;
    }

    private void setActivityLevelValue(int activity_level) {
        switch (activity_level) {
            case 0:
                activity_level_value = 1;
                break;
            case 1:
                activity_level_value = 1.0;
                break;
            case 2:
                activity_level_value = 1.2;
                break;
            case 3:
                activity_level_value = 1.375;
                break;
            case 4:
                activity_level_value = 1.55;
                break;
            case 5:
                activity_level_value = 1.725;
            case 6:
                activity_level_value = 1.9;

        }


    }

    public double calculateCalories() {
        setActivityLevelValue(getActivity_level());

        if (getGender_id() == 0) {
            calories = (66.4730 + (13.7516 * getWeight()) + (5.0033 * getHeight()) - (6.7550 * getAge())) * activity_level_value;
        }
        if (getGender_id() == 1) {
            calories = (655.4730 + (9.5634 * getWeight()) + (1.8496 * getHeight()) - (4.6756 * getAge())) * activity_level_value;

        }


        return calories;

    }


}
