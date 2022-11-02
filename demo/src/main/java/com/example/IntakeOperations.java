package com.example;

public interface IntakeOperations {
    
    boolean checkFileExists();

    boolean createNewFile();
    
    User readFileData();
    
    boolean writeFileData(User userData);

    void drinkWater();
}
