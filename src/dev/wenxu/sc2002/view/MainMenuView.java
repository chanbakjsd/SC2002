package dev.wenxu.sc2002.view;

import dev.wenxu.sc2002.controller.CampController;
import dev.wenxu.sc2002.controller.UserController;
import dev.wenxu.sc2002.entity.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainMenuView extends View {
    /**
     * The user that is currently logged in.
     */
    private final User user;

    /**
     * @param user The user that is currently logged in.
     */
    public MainMenuView(User user) {
        this.user = user;
    }

    /**
     * @param user The user that is currently logged in.
     * @param error The error string to display to the user.
     */
    public MainMenuView(User user, String error) {
        this.user = user;
        this.error = error;
    }

    /**
     * @param sc The scanner that is scanning for inputs from stdin.
     * @return The LoginView to return back to the login screen.
     */
    @Override
    public View display(Scanner sc) {
        while (true) {
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
                } else {
                    CampView view = new CampView(camps.get(id-1), user, this);
                    view.display(sc);
                }
            } else if (command.equalsIgnoreCase("p")) {
                return new PasswordChangeView(user, false);
            } else if (command.equalsIgnoreCase("q")) {
                return new LoginView();
            } else {
                error = "Unknown command.";
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
            System.out.printf(" (%d/%d)\n", camp.getAttendees().size(), camp.getInfo().getTotalSlots());
        }
    }

}
