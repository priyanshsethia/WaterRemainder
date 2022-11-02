/**
 * @author  Priyansh Sethia
 * @version  1.0.1
 * @since    2022-11-02
 * See also {@link https://github.com/priyanshsethia/WaterRemainder.git}
*/

package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class App implements IntakeOperations {

    /**
     * "Data.csv" to store data.
     */
    private static final String FILE_NAME = "Data.csv";

    public static void main(String[] args) {
        App app = new App();
        app.init();
    }

    public void init() {

        System.out.println("-----------------------------");
        System.out.println("Welcome to WaterRemainder App");
        System.out.println("-----------------------------");

        if (!checkFileExists()) {
            createNewFile();
        } else {
            System.out.println(FILE_NAME + " file exists.");
        }

        if (!checkUserDataExists()) {

            User user = new User();
            Scanner input = new Scanner(System.in);

            System.out.println("\nEnter name : ");
            user.setName(input.next());

            System.out.println("\nEnter gender (M/F) : ");
            user.setGender(input.next().charAt(0));

            user.setWaterIntake(0);

            if (writeFileData(user)) {
                System.out.println("\nData insertion successfull.");
                drinkWater();
            }
        } else {
            drinkWater();
        }

        System.out.println("-----------------------------");
    }

    @Override
    public boolean checkFileExists() {
        return new File(FILE_NAME).exists();
    }

    public boolean checkUserDataExists() {
        return readFileData() != null;
    }

    @Override
    public boolean createNewFile() {
        try {
            if (new File(FILE_NAME).createNewFile()) {
                System.out.println("\n" + FILE_NAME + " created successfully.");
                return true;
            } else {
                System.out.println("\n" + FILE_NAME + " not created.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("\nFacing issue while creating file, please try again.");
            return false;
        }
    }

    @Override
    public User readFileData() {

        if (checkFileExists()) {
            try {
                FileReader fileReader = new FileReader(FILE_NAME);
                CSVReader csvReader = new CSVReader(fileReader);

                String[] data;
                User userData = new User();

                try {
                    while ((data = csvReader.readNext()) != null) {
                        userData.setName(data[0]);
                        userData.setGender(data[1].charAt(0));
                        userData.setWaterIntake(Double.parseDouble(data[2]));
                    }

                    if (userData.getName() == null){
                            return null;
                    }
                    return userData;
                } catch (IOException e) {
                    System.out.println("\nFailed to fetch data.");
                    return null;
                }

            } catch (FileNotFoundException e) {
                System.out.println("\nFailed to fetch data.");
                return null;
            }

        } else {
            System.out.println("\nFile not exists to read data.");
            return null;
        }
    }

    @Override
    public boolean writeFileData(User userData) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(FILE_NAME));
            /*
             * // adding header to csv
             * String[] header = { "Name", "Gender", "WaterIntake" };
             * writer.writeNext(header);
             */

            // adding data to csv
            String[] userDataSet = userData.getString();
            writer.writeNext(userDataSet);

            writer.close();
            return true;

        } catch (Exception e) {
            System.out.println("\nFailed to insert data.");
            return false;
        }
    }

    @Override
    public void drinkWater() {
        Scanner input = new Scanner(System.in);

        System.out.println("\nDo you want to record you water intake ? (Y/N)");
        String response = input.nextLine();

        if (response.equalsIgnoreCase("y")) {
            System.out.println("\nEnter water intake (ml) : ");
            double waterIntake = input.nextDouble();

            User userData = readFileData();

            User newUserData = new User();
            newUserData.setName(userData.getName());
            newUserData.setGender(userData.getGender());
            newUserData.setWaterIntake(waterIntake);

            if (userData != null) {
                writeFileData(newUserData);
                System.out.println("\nData update successfully.");
                System.out.println("\n" + readFileData().getRemainingIntake() + " litres of water is remaining to drink.");
            } else {
                System.out.println("\nFacing some issue, please try again.");
                drinkWater();
            }

        } else if (response.equalsIgnoreCase("n"))
            System.out.println("\n" + readFileData().getRemainingIntake() + " litres of water is remaining to drink.");
        else {
            System.out.println("\nInvalid input, please enter again.");
            drinkWater();
        }

    }

}
