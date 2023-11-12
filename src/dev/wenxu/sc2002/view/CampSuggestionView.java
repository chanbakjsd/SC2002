package dev.wenxu.sc2002.view;

import dev.wenxu.sc2002.entity.*;

import java.util.Scanner;

public class CampSuggestionView extends CampView {
    private final Suggestion suggestion;

    public CampSuggestionView(Camp camp, User user, View originalView, Suggestion suggestion) {
        super(camp, user, originalView);
        this.suggestion = suggestion;
    }

    @Override
    protected void showExtraInfo() {
        System.out.printf("Suggestion by %s.\n", suggestion.getSuggesterID());
        System.out.println();
    }

    @Override
    protected void listOptions() {
        System.out.println("(A)ccept Suggestion");
        System.out.println("(R)eject Suggestion");
        System.out.println("(Q)uit Suggestion Preview");
    }

    @Override
    protected View handleCommand(Scanner sc, String command) {
        if (command.equalsIgnoreCase("a")) {
            camp.deleteSuggestion(suggestion);
            suggestion.applyTo(camp.getInfo());
            camp.findUser(suggestion.getSuggesterID())
                    .map(user -> (CommitteeMember)user)
                    .ifPresent(CommitteeMember::incrementPoint);
            return new CampView(camp, user, originalView);
        }
        if (command.equalsIgnoreCase("r")) {
            camp.deleteSuggestion(suggestion);
            return new CampView(camp, user, originalView);
        }
        if (command.equalsIgnoreCase("q")) {
            return new CampView(camp, user, originalView);
        }
        return null;
    }

    @Override
    protected CampInfo getCampInfo() {
        return suggestion;
    }
}
