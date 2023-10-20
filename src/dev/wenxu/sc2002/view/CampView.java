package dev.wenxu.sc2002.view;

import dev.wenxu.sc2002.controller.UserController;
import dev.wenxu.sc2002.entity.Camp;
import dev.wenxu.sc2002.entity.CampInfo;
import dev.wenxu.sc2002.entity.CommitteeMember;
import dev.wenxu.sc2002.entity.User;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CampView extends View {
    /**
     * True if the camp is in editor view, false otherwise.
     */
    private boolean editMode;
    /**
     * The camp that is being viewed/edited.
     */
    private final Camp camp;
    /**
     * The user that is viewing/editing the camp.
     */
    private final User user;
    /**
     * The view to return to after CampView has been exited.
     */
    private final View originalView;

    public CampView(Camp camp, User user, View originalView) {
        this.camp = camp;
        this.user = user;
        this.originalView = originalView;
    }

    public View display(Scanner sc) {
        while (true) {
            clearScreen();
            listProperties();
            System.out.println();
            if (user.isStaff() || UserController.isManagedCamp(camp, user)) {
                System.out.println("(E)dit");
            }
            System.out.println("(Q)uit");
            String command = sc.nextLine();
            if (command.equalsIgnoreCase("e")) {
                editMode = !editMode;
            } else if (command.equalsIgnoreCase("q")) {
                return originalView;
            }
        }
    }

    /**
     * Lists all the properties of the camp.
     */
    private void listProperties() {
        CampInfo info = camp.getInfo();
        showProperty(1, "Name", info.getName());
        if (info.getStartDate() == null || info.getEndDate() == null) {
            showProperty(2, "Date", "Unknown");
        } else {
            String start = info.getStartDate().format(DateTimeFormatter.ISO_DATE);
            String end = info.getEndDate().format(DateTimeFormatter.ISO_DATE);
            showProperty(2, "Date", start + " to " + end);
        }
        if (info.getRegistrationDeadline() == null) {
            showProperty(3, "Registration Deadline", "Unavailable");
        } else {
            showProperty(3, "Registration Deadline", info.getRegistrationDeadline().format(DateTimeFormatter.ISO_DATE_TIME));
        }
        if (info.isOpenToAll()) {
            showProperty(4, "Open to", "NTU");
        } else {
            showProperty(4, "Open to", "Specific Faculty (" + info.getFaculty() + ")");
        }
        showProperty(5, "Location", info.getLocation());
        String slotFilled = String.valueOf(camp.getAttendees().size());
        String totalSlot = String.valueOf(info.getTotalSlots());
        showProperty(6, "Slots", slotFilled + "/" + totalSlot);
        String committeeFilled = String.valueOf(camp.getAttendees().stream().filter(u -> u instanceof CommitteeMember).count());
        String committeeSlot = String.valueOf(info.getCampCommitteeSlots());
        showProperty(7, "Committees", committeeFilled + "/" + committeeSlot);
        showProperty(8, "Staff in Charge", info.getStaffInCharge());
        showProperty(9, "Description", info.getDescription());
        System.out.println();
    }

    private void showProperty(int id, String key, String value) {
        if (editMode) System.out.printf("(%d) ", id);
        if (value == null) value = "Unknown";
        System.out.printf("%25s %s\n", key+":", value);
    }
}
