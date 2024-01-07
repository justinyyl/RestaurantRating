package ui;

import javax.swing.*;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new RestaurantRating();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to run application: file not found");
        }
    }
}
