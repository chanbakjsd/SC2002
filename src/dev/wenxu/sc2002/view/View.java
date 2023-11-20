package dev.wenxu.sc2002.view;

import java.util.Scanner;

public abstract class View {
    protected String error;
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
