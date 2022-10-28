package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new SportsManagementApp();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");
        }
    }
}
