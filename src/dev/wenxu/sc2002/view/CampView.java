package dev.wenxu.sc2002.view;

import dev.wenxu.sc2002.controller.UserController;
import dev.wenxu.sc2002.entity.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CampView extends View {
    /**
     * The camp that is being viewed/edited.
     */
    protected final Camp camp;
    /**
     * The user that is viewing/editing the camp.
     */
    protected final User user;
    /**
     * The view to return to after CampView has been exited.
     */
    protected final View originalView;

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

            this.showExtraInfo();
            this.listOptions();
            String command = sc.nextLine();

            try {
                View newView = handleCommand(sc, command);
                if (newView != null) {
                    return newView;
                }
            } catch (IllegalArgumentException e) {
                error = e.getMessage();
            }
        }
    }

    protected void showExtraInfo() {
        if (!user.isStaff()) {
            return;
        }
        if (camp.isVisible()) {
            System.out.println("This camp is VISIBLE to students.");
        } else {
            System.out.println("This camp is currently HIDDEN from students.");
        }
        System.out.println();
        List<Suggestion> suggestions = camp.getSuggestions();
        if (!suggestions.isEmpty()) {
            System.out.println("Suggestions available:");
            for (int i = 0; i < suggestions.size(); i++) {
                System.out.printf("(%d) Suggestion by %s\n", i+1, suggestions.get(i).getSuggesterID());
            }
            System.out.println();
        }
    }

    protected void listOptions() {
        if (canEdit()) {
            if (currentSuggestion().isPresent()) {
                System.out.println("(E)dit Suggestion");
                System.out.println("(D)elete Suggestion");
            } else {
                System.out.println("(E)dit Camp");
            }
        }
        if (user.isStaff()) System.out.println("Change (V)isibility");
        if (canRegister()) {
            System.out.println("Register as (A)ttendee");
            System.out.println("Register as Committee (M)ember");
        }
        if (isAttendee()) {
            System.out.println("(L)eave Camp");
        }
        System.out.println("(Q)uit");
    }

    protected View handleCommand(Scanner sc, String command) {
        if (command.equalsIgnoreCase("q")) {
            return originalView;
        }
        if (command.equalsIgnoreCase("e") && canEdit()) {
            return new CampEditView(camp, user, originalView);
        }
        if (command.equalsIgnoreCase("d") && currentSuggestion().isPresent()) {
            camp.deleteSuggestion(currentSuggestion().get());
            return null;
        }
        if (command.equalsIgnoreCase("v") && user.isStaff()) {
            camp.setVisible(!camp.isVisible());
            return null;
        }
        if (command.equalsIgnoreCase("a") && canRegister()) {
            camp.addUser(new CampUser(user.getUserID()));
            return null;
        }
        if (command.equalsIgnoreCase("m") && canRegister()) {
            camp.addUser(new CommitteeMember(user.getUserID()));
            return null;
        }
        if (command.equalsIgnoreCase("l") && isAttendee()) {
            camp.withdraw(user.getUserID());
            return null;
        }

        if (!user.isStaff()) {
            return null;
        }

        // Handle viewing suggestions.
        try {
            int id = Integer.parseInt(command);
            id--;
            if (id < 0 || id > camp.getSuggestions().size()) {
                error = "Invalid option. Please try again.";
                return null;
            }
            return new CampSuggestionView(camp, user, originalView, camp.getSuggestions().get(id));
        } catch (NumberFormatException e) {
            error = "Invalid option. Please try again.";
        }
        return null;
    }

    private boolean canEdit() {
        return user.isStaff() || UserController.isManagedCamp(camp, user);
    }

    private boolean canRegister() {
        Optional<CampUser> campUser = camp.findUser(user.getUserID());
        return !user.isStaff() && campUser.isEmpty();
    }

    private boolean isAttendee() {
        Optional<CampUser> campUser = camp.findUser(user.getUserID());
        return campUser.isPresent() && !(campUser.get() instanceof CommitteeMember);
    }

    private Optional<Suggestion> currentSuggestion() {
        return camp.getSuggestions().stream().
                filter(suggestion -> suggestion.getSuggesterID().equals(user.getUserID())).
                findFirst();
    }

    protected CampInfo getCampInfo() {
        return camp.getInfo();
    }

    /**
     * Lists all the properties of the camp.
     */
    private void listProperties() {
        CampInfo info = getCampInfo();
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

    protected void showProperty(int id, String key, String value) {
        String displayValue = ((value == null) || value.isEmpty()) ? "Unknown" : value;
        System.out.printf("%25s %s\n", key+":", displayValue);
    }
}
