package dev.wenxu.sc2002.view;

import dev.wenxu.sc2002.controller.UserController;
import dev.wenxu.sc2002.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

/**
 * The screen that allows users to edit a camp.
 */
public class CampEditView extends CampView {
    /**
     * The pattern for the user to input the registration time with.
     */
    private static final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * The suggestion that is being edited.
     */
    private final Suggestion suggestion;

    /**
     * Creates a new camp edit view.
     * @param camp The camp to edit
     * @param user The user that is currently authenticated
     * @param originalView The view to return to after exit
     */
    public CampEditView(Camp camp, User user, View originalView) {
        super(camp, user, originalView);
        this.suggestion = camp.findSuggestion(user.getUserID()).orElse(new Suggestion(user.getUserID(), camp.getInfo()));
    }

    /**
     * Displays the list of options for editing camp.
     */
    @Override
    protected void listOptions() {
        if (user.isStaff()) {
            System.out.println("(S)ave Changes");
        } else {
            System.out.println("(S)ave Suggestion");
            System.out.println("(D)elete Suggestion");
        }
        System.out.println("(Q)uit without Saving");
    }

    /**
     * Handles the command of the user.
     * @param sc The scanner to read input from
     * @param command The command that is provided by the user
     * @return The view to change to, if applicable. Null otherwise.
     */
    @Override
    protected View handleCommand(Scanner sc, String command) {
        if (command.equalsIgnoreCase("s")) {
            if (user.isStaff()) {
                suggestion.applyTo(camp.getInfo());
            } else {
                camp.upsertSuggestion(suggestion);
                Optional<CampUser> campUser = camp.findUser(user.getUserID());
                if (campUser.isEmpty()) {
                    error = "Internal server error - Cannot find committee member.";
                } else {
                    CommitteeMember member = (CommitteeMember) campUser.get();
                    member.incrementPoint();
                }
            }
            return originalView;
        }
        if (command.equalsIgnoreCase("d")) {
            camp.deleteSuggestion(suggestion);
            return originalView;
        }
        if (command.equalsIgnoreCase("q"))
            return originalView;
        try {
            int option = Integer.parseInt(command);
            switch (option) {
                default:
                    error = "Invalid option. Please try again.";
                    break;
                case 1:
                    System.out.print("Please enter the new name for the camp: ");
                    suggestion.setName(sc.nextLine());
                    break;
                case 2:
                    System.out.print("Please enter the start date (YYYY-MM-DD): ");
                    LocalDate startDate = LocalDate.parse(sc.nextLine());
                    System.out.print("Please enter the end date (YYYY-MM-DD): ");
                    LocalDate endDate = LocalDate.parse(sc.nextLine());
                    if (startDate.isAfter(endDate)) {
                        error = "End date cannot be before start date.";
                        return null;
                    }
                    suggestion.setStartDate(startDate);
                    suggestion.setEndDate(endDate);
                    break;
                case 3:
                    System.out.print("Please enter the registration deadline (YYYY-MM-DD hh:mm:ss): ");
                    LocalDateTime deadline = LocalDateTime.parse(sc.nextLine(), DATE_TIME_PATTERN);
                    suggestion.setRegistrationDeadline(deadline);
                    break;
                case 4:
                    System.out.print("Please enter the faculty the camp should be open to (or 'NTU' if open-to-all): ");
                    String faculty = sc.nextLine();
                    if (faculty.equalsIgnoreCase("NTU")) {
                        suggestion.setOpenToAll(true);
                        suggestion.setFaculty(null);
                    } else {
                        suggestion.setOpenToAll(false);
                        suggestion.setFaculty(faculty);
                    }
                    break;
                case 5:
                    System.out.print("Please enter the location of the camp: ");
                    suggestion.setLocation(sc.nextLine());
                    break;
                case 6:
                    System.out.print("Please enter the number of slots in the camp: ");
                    suggestion.setTotalSlots(sc.nextInt());
                    break;
                case 7:
                    System.out.print("Please enter the number of committee members in the camp: ");
                    suggestion.setCampCommitteeSlots(sc.nextInt());
                    break;
                case 8:
                    System.out.print("Please enter the name of the staff that will be in charge: ");
                    String staffID = sc.nextLine();
                    Optional<User> staff = UserController.getInstance().getUser(staffID);
                    boolean isValidStaff = staff.map(User::isStaff).orElse(false);
                    if (isValidStaff) {
                        suggestion.setStaffInCharge(staff.get().getUserID());
                    } else {
                        error = "Invalid staff ID provided.";
                    }
                    break;
                case 9:
                    System.out.println("Please enter the description of the camp:");
                    suggestion.setDescription(sc.nextLine());
                    break;
            }
        } catch (NumberFormatException e) {
            error = "Invalid option. Please try again.";
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        } catch (InputMismatchException e) {
            error = "Invalid value entered.";
        } catch (DateTimeParseException e) {
            error = "Invalid format entered.";
        }
        return null;
    }

    /**
     * Displays the property of the camp.
     * @param id The ID of the property
     * @param key The key of the property
     * @param value The value of the property
     */
    @Override
    protected void showProperty(int id, String key, String value) {
        System.out.printf("(%d) ", id);
        super.showProperty(id, key, value);
    }

    /**
     * @return The info of the camp to display
     */
    @Override
    protected CampInfo getCampInfo() {
        return suggestion;
    }
}
