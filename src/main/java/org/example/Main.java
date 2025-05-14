package org.example;

import org.example.testingDemo.MakeMyTripFieldsTest;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Running test file");
        // Call the test method
        try {
            MakeMyTripFieldsTest.main();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}