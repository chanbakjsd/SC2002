package dev.wenxu.sc2002;

import dev.wenxu.sc2002.controller.UserController;
import dev.wenxu.sc2002.view.LoginView;
import dev.wenxu.sc2002.view.View;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CAMMain {
    public static void main(String[] args) {
        initializeUsers();
        Scanner sc = new Scanner(System.in);
        View view = new LoginView();
        while (view != null) {
            view = view.display(sc);
        }
    }

    /**
     * Initialize the users' database.
     */
    private static void initializeUsers() {
        try (FileReader fr = new FileReader("staff.csv")) {
            UserController.getInstance().addUsers(new BufferedReader(fr), true);
        } catch (IOException e) {
            System.out.println("Failed to initialize user database: Error reading staff.csv:");
            System.out.println(e.getMessage());
            System.exit(1);
        }
        try (FileReader fr = new FileReader("student.csv")) {
            UserController.getInstance().addUsers(new BufferedReader(fr), false);
        } catch (IOException e) {
            System.out.println("Failed to initialize user database: Error reading student.csv:");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
