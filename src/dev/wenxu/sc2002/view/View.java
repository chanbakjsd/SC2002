package dev.wenxu.sc2002.view;

import java.util.Scanner;

public abstract class View {
    protected String error;

    /**
     * clearScreen clears supported terminals and displays the error message.
     */
    protected void clearScreen() {
        // Clears supported terminals.
        System.out.println("\u001b[2J\u001b[H");
        if (error != null) {
            System.out.println("ERROR: " + error);
            System.out.println();
        }
        error = null;
    }

    /**
     * Display the content of the view.
     * @param sc The scanner that is scanning for inputs from stdin.
     * @return The next view to display.
     */
    public abstract View display(Scanner sc);
}
