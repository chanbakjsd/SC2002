package dev.wenxu.sc2002.view;

import dev.wenxu.sc2002.entity.User;

import java.util.Scanner;

/**
 * The screen to change a user's password.
 */
public class PasswordChangeView extends View {
    /**
     * The user to change the password for.
     */
    private final User user;

    /**
     * True if the password change is forced (like the one during first login), false otherwise.
     */
    private final boolean forced;

    /**
     * Create a new PasswordChangeView.
     * @param user The user to change password for.
     * @param forced True if the user must change password before leaving this view, false otherwise.
     */
    public PasswordChangeView(User user, boolean forced) {
        this.user = user;
        this.forced = forced;
    }

    /**
     * Display the screen to change password.
     * @param sc The scanner that is scanning for inputs from stdin.
     * @return The view to return to.
     */
    @Override
    public View display(Scanner sc) {
        clearScreen();
        if (!forced) {
            // Validate password if it's initiated by the user.
            System.out.println("Enter your current password: ");
            String oldPassword = sc.nextLine();
            if (!user.validatePassword(oldPassword)) {
                return new MainMenuView(user, "Invalid password.");
            }
        }
        while (true) {
            if (forced) {
                System.out.print("Enter your new password: ");
            } else {
                System.out.print("Enter your new password (leave blank to quit): ");
            }
            String newPassword = sc.nextLine();
            if (!forced && newPassword.isEmpty()) {
                return new MainMenuView(user);
            }
            if (newPassword.equals("password")) {
                error = "'password' is not an acceptable password.";
                clearScreen();
                continue;
            }
            if (newPassword.length() < 8) {
                error = "Password must have 8 or more characters.";
                clearScreen();
                continue;
            }
            System.out.print("Enter your new password again: ");
            String confirmPassword = sc.nextLine();
            if (!newPassword.equals(confirmPassword)) {
                error = "New password does not match.";
                clearScreen();
                continue;
            }
            user.setPassword(newPassword);
            return new MainMenuView(user);
        }
    }
}
