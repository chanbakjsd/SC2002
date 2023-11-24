package dev.wenxu.sc2002.view;

import java.util.Scanner;

/**
 * A display that can be showed to the user.
 */
public abstract class View {
    /**
     * The error to show the next time the screen was cleared.
     */
    protected String error;
    /**
     * The info to show the next time the screen was cleared.
     */
    protected String info;

    /**
     * clearScreen clears supported terminals and displays the error message.
     */
    protected void clearScreen() {
        // Clears supported terminals.
        System.out.println("\u001b[2J\u001b[H");
        if (error != null) {
            System.out.println("ERROR: " + error);
        }
        if (info != null) {
            System.out.println("INFO: " + info);
        }
        if (error != null || info != null) {
            System.out.println();
        }
        error = null;
        info = null;
    }

    /**
     * Display the content of the view.
     * @param sc The scanner that is scanning for inputs from stdin.
     * @return The next view to display.
     */
    public abstract View display(Scanner sc);
}
