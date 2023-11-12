package dev.wenxu.sc2002.view;

import dev.wenxu.sc2002.controller.UserController;
import dev.wenxu.sc2002.entity.Camp;
import dev.wenxu.sc2002.entity.CampUser;
import dev.wenxu.sc2002.entity.CommitteeMember;
import dev.wenxu.sc2002.entity.User;

import java.util.List;
import java.util.Scanner;

public class MemberView extends View {
    /**
     * The camp that is being checked.
     */
    private final Camp camp;
    /**
     * The view to return to after MemberView exits.
     */
    private final View originalView;

    public MemberView(Camp camp, View originalView) {
        this.camp = camp;
        this.originalView = originalView;
    }

    @Override
    public View display(Scanner sc) {
        while (true) {
            clearScreen();
            List<CampUser> committees = camp.getAttendees().stream().
                    filter(attendee -> attendee instanceof CommitteeMember).
                    toList();
            List<CampUser> attendees = camp.getAttendees().stream().
                    filter(attendee -> !(attendee instanceof CommitteeMember)).
                    toList();
            if (committees.isEmpty() && attendees.isEmpty()) {
                System.out.println("No users are currently registered.");
                System.out.println();
            }
            if (!committees.isEmpty()) {
                System.out.println("Committee Members:");
                for (int i = 0; i < committees.size(); i++) {
                    String userID = committees.get(i).getUserID();
                    String username = UserController.getInstance().getUser(userID).
                            map(User::getName).
                            orElse("Unknown User");
                    System.out.printf("%d. %s - %s\n", i+1, userID, username);
                }
                System.out.println();
            }
            if (!attendees.isEmpty()) {
                System.out.println("Attendees:");
                for (int i = 0; i < attendees.size(); i++) {
                    String userID = attendees.get(i).getUserID();
                    String username = UserController.getInstance().getUser(userID).
                            map(User::getName).
                            orElse("Unknown User");
                    System.out.printf("%d. %s - %s\n", i+1, userID, username);
                }
                System.out.println();
            }

            System.out.println("(Q)uit");
            String command = sc.nextLine();
            if (command.equalsIgnoreCase("q")) {
                return originalView;
            } else {
                error = "Unknown option. Please try again.";
            }
        }
    }
}
