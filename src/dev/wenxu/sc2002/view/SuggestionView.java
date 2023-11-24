package dev.wenxu.sc2002.view;

import dev.wenxu.sc2002.entity.*;

import java.util.Scanner;

/**
 * A display to show the suggestion of a camp committee member.
 */
public class SuggestionView extends CampView {
    /**
     * The suggestion that is being approved.
     */
    private final Suggestion suggestion;

    /**
     * Create a new SuggestionView.
     * @param camp The camp that the suggestion should apply to
     * @param user The user that is currently authenticated
     * @param originalView The view that should be returned to
     * @param suggestion The suggestion to view
     */
    public SuggestionView(Camp camp, User user, View originalView, Suggestion suggestion) {
        super(camp, user, originalView);
        this.suggestion = suggestion;
    }

    /**
     * Shows extra info regarding the suggestion.
     */
    @Override
    protected void showExtraInfo() {
        System.out.printf("Suggestion by %s.\n", suggestion.getSuggesterID());
        System.out.println();
    }

    /**
     * List possible options for the suggestion.
     */
    @Override
    protected void listOptions() {
        System.out.println("(A)ccept Suggestion");
        System.out.println("(R)eject Suggestion");
        System.out.println("(Q)uit Suggestion Preview");
    }

    /**
     * Handles a command by the user.
     * @param sc The scanner to read input from
     * @param command The command that is provided by the user
     * @return The view to return to, null if the view should stay the same
     */
    @Override
    protected View handleCommand(Scanner sc, String command) {
        if (command.equalsIgnoreCase("a")) {
            camp.deleteSuggestion(suggestion);
            suggestion.applyTo(camp.getInfo());
            camp.findUser(suggestion.getSuggesterID())
                    .map(user -> (CommitteeMember)user)
                    .ifPresent(CommitteeMember::incrementPoint);
            return originalView;
        }
        if (command.equalsIgnoreCase("r")) {
            camp.deleteSuggestion(suggestion);
            return originalView;
        }
        if (command.equalsIgnoreCase("q")) {
            return originalView;
        }
        return null;
    }

    /**
     * @return The suggestion to show as the camp info
     */
    @Override
    protected CampInfo getCampInfo() {
        return suggestion;
    }
}
