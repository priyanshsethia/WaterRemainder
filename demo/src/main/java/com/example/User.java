package com.example;

public class User {

    private String name;
    private char gender;
    private double waterIntake;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public double getWaterIntake() {
        return waterIntake;
    }

    public void setWaterIntake(double waterIntake) {
        this.waterIntake = waterIntake;
    }

    public String[] getString() {
        return new String[] {name, "" + gender, Double.toString(waterIntake)};
    }

    /**
     * @return
     */
    public double getRemainingIntake(){
        if(getGender() == 'M')
            return 3.7 - getWaterIntake()/1000;
        else
            return 2.7 - getWaterIntake()/1000;  
    }
}
