package dev.wenxu.sc2002.view;

import dev.wenxu.sc2002.controller.CampController;
import dev.wenxu.sc2002.controller.UserController;
import dev.wenxu.sc2002.entity.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * A screen for the user to choose their camps and log out.
 */
public class MainMenuView extends View {
    /**
     * The user that is currently logged in.
     */
    private final User user;

    /**
     * Create a new MainMenuView.
     * @param user The user that is currently logged in.
     */
    public MainMenuView(User user) {
        this.user = user;
    }

    /**
     * Create a new MainMenuView with an error.
     * @param user The user that is currently logged in.
     * @param error The error string to display to the user.
     */
    public MainMenuView(User user, String error) {
        this.user = user;
        this.error = error;
    }

    /**
     * Display a menu that allow the user to create and view camps.
     * @param sc The scanner that is scanning for inputs from stdin.
     * @return The LoginView to return back to the login screen.
     */
    @Override
    public View display(Scanner sc) {
        while (true) {
            clearScreen();
            System.out.printf("Welcome, %s!\n\n", user.getUserID());
            listCamps();
            System.out.println();

            List<Camp> camps = getVisibleCamps();
            if (user.isStaff()) {
                System.out.println("(C)reate a camp");
            }
            if (!camps.isEmpty()) {
                System.out.println("(V)iew camp");
            }
            System.out.println("(P)assword Change");
            System.out.println("(Q)uit");
            System.out.println();
            System.out.print("Choose your action: ");
            String command = sc.nextLine();

            try {
                if (user.isStaff() && command.equalsIgnoreCase("c")) {
                    System.out.print("Enter the name of the new camp: ");
                    String name = sc.nextLine();
                    CampInfo info = new CampInfo(name, user.getUserID());
                    info.setFaculty(user.getFaculty());
                    Camp newCamp = new Camp(info);
                    CampController.getInstance().addCamp(newCamp);
                } else if (!camps.isEmpty() && command.equalsIgnoreCase("v")) {
                    System.out.print("Enter the ID of the camp to view: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    if (id < 1 || id > camps.size()) {
                        error = "Invalid ID provided.";
                        continue;
                    }
                    return new CampView(camps.get(id - 1), user, this);
                } else if (command.equalsIgnoreCase("p")) {
                    return new PasswordChangeView(user, false);
                } else if (command.equalsIgnoreCase("q")) {
                    return new LoginView();
                } else {
                    error = "Unknown command.";
                }
            } catch (Exception ignored) {
                sc.nextLine();
            }
        }
    }

    /**
     * @return The list of camps visible to the current user.
     */
    private List<Camp> getVisibleCamps() {
        List<Camp> camps = CampController.getInstance().getCamps();
        return camps.stream().filter(camp -> UserController.isVisibleCamp(camp, user)).toList();
    }

    /**
     * Displays the list of camps visible to the current user.
     */
    private void listCamps() {
        List<Camp> visibleCamps = getVisibleCamps();
        if (visibleCamps.isEmpty()) {
            System.out.println("No camps available.");
            return;
        }
        System.out.println("Camps available:");
        for (int i = 0; i < visibleCamps.size(); i++) {
            Camp camp = visibleCamps.get(i);
            if (UserController.isManagedCamp(camp, user)) {
                System.out.print("* ");
            } else {
                System.out.print("  ");
            }
            System.out.printf("(%d) ", i+1);
            System.out.print(camp.getInfo().getName());
            System.out.printf(" (%d/%d)", camp.getAttendees().size(), camp.getInfo().getTotalSlots());
            int suggestionCount = camp.getSuggestions().size();
            if (suggestionCount > 0 && user.isStaff()) {
                if (suggestionCount == 1) {
                    System.out.print(" - 1 suggestion available");
                } else {
                    System.out.printf(" - %d suggestion available", suggestionCount);
                }
            }
            if (!user.isStaff()) {
                Optional<CampUser> campUser = camp.findUser(user.getUserID());
                if (campUser.isPresent() && campUser.get() instanceof CommitteeMember) {
                    System.out.print(" **COMMITTEE MEMBER**");
                } else if (campUser.isPresent()) {
                    System.out.print(" **ATTENDEE**");
                }
            }
            System.out.println();
        }
    }
}
