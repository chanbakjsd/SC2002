package dev.wenxu.sc2002.view;

import dev.wenxu.sc2002.controller.UserController;
import dev.wenxu.sc2002.entity.User;

import java.util.Scanner;

/**
 * A screen for the users to login.
 */
public class LoginView extends View {
    /**
     * Show the login screen of the user.
     * @param sc The scanner that is scanning for inputs from stdin.
     * @return The view to switch to, typically the MainMenuView.
     */
    @Override
    public View display(Scanner sc) {
        UserController uc = UserController.getInstance();
        while (true) {
            try {
                clearScreen();
                System.out.println("Good day! Please login to the Camp Application and Management System.");
                System.out.println();
                System.out.print("User ID: ");
                String userID = sc.nextLine();
                User user = uc.getUser(userID).orElseThrow(() -> new IllegalArgumentException("Invalid user ID."));
                System.out.print("Password: ");
                String password = sc.nextLine();
                if(!user.validatePassword(password)) {
                    throw new IllegalArgumentException("Invalid password. Please try again.");
                }
                if (password.equals("password")) {
                    return new PasswordChangeView(user, true);
                }
                return new MainMenuView(user);
            } catch (Exception e) {
                error = e.getMessage();
            }
        }
    }
}
