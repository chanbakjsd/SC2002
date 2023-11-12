package dev.wenxu.sc2002.view;

import dev.wenxu.sc2002.controller.UserController;
import dev.wenxu.sc2002.entity.Camp;
import dev.wenxu.sc2002.entity.CampInfo;
import dev.wenxu.sc2002.entity.CommitteeMember;
import dev.wenxu.sc2002.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

public class CampView extends View {
    private static final DateTimeFormatter DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
            if (user.isStaff()) {
                if (camp.isVisible()) {
                    System.out.println("This camp is VISIBLE to students.");
                } else {
                    System.out.println("This camp is currently HIDDEN from students.");
                }
                System.out.println();
            }
            boolean canEdit = user.isStaff() || UserController.isManagedCamp(camp, user);
            if (user.isStaff()) System.out.println("(C)hange Visibility");
            if (canEdit) System.out.println("Toggle (E)dit Mode");
            System.out.println("(Q)uit");
            String command = sc.nextLine();
            if (command.equalsIgnoreCase("c") && user.isStaff()) {
                camp.setVisible(!camp.isVisible());
            } else if (command.equalsIgnoreCase("e") && canEdit) {
                editMode = !editMode;
            } else if (command.equalsIgnoreCase("q")) {
                return originalView;
            } else if (editMode) {
                editItem(sc, command);
            } else {
                error = "Invalid option. Please try again.";
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

    private void editItem(Scanner sc, String optionStr) {
        CampInfo info = camp.getInfo();
        try {
            int option = Integer.parseInt(optionStr);
            switch (option) {
                default:
                    error = "Invalid option. Please try again.";
                    break;
                case 1:
                    System.out.print("Please enter the new name for the camp: ");
                    info.setName(sc.nextLine());
                    break;
                case 2:
                    System.out.print("Please enter the start date (YYYY-MM-DD): ");
                    LocalDate startDate = LocalDate.parse(sc.nextLine());
                    System.out.print("Please enter the end date (YYYY-MM-DD): ");
                    LocalDate endDate = LocalDate.parse(sc.nextLine());
                    if (startDate.isAfter(endDate)) {
                        error = "End date cannot be before start date.";
                        return;
                    }
                    info.setStartDate(startDate);
                    info.setEndDate(endDate);
                    break;
                case 3:
                    System.out.print("Please enter the registration deadline (YYYY-MM-DD hh:mm:ss): ");
                    LocalDateTime deadline = LocalDateTime.parse(sc.nextLine(), DATE_TIME_PATTERN);
                    info.setRegistrationDeadline(deadline);
                    break;
                case 4:
                    System.out.print("Please enter the faculty the camp should be open to (or 'NTU' if open-to-all): ");
                    String faculty = sc.nextLine();
                    if (faculty.equalsIgnoreCase("NTU")) {
                        info.setOpenToAll(true);
                    } else {
                        info.setOpenToAll(false);
                        info.setFaculty(faculty);
                    }
                    break;
                case 5:
                    System.out.print("Please enter the location of the camp: ");
                    info.setLocation(sc.nextLine());
                    break;
                case 6:
                    System.out.print("Please enter the number of slots in the camp: ");
                    info.setTotalSlots(sc.nextInt());
                    break;
                case 7:
                    System.out.print("Please enter the number of committee members in the camp: ");
                    info.setCampCommitteeSlots(sc.nextInt());
                    break;
                case 8:
                    System.out.print("Please enter the name of the staff that will be in charge: ");
                    String staffID = sc.nextLine();
                    Optional<User> staff = UserController.getInstance().getUser(staffID);
                    boolean isValidStaff = staff.map(User::isStaff).orElse(false);
                    if (isValidStaff) {
                        info.setStaffInCharge(staff.get().getUserID());
                    } else {
                        error = "Invalid staff ID provided.";
                    }
                    break;
                case 9:
                    System.out.println("Please enter the description of the camp:");
                    info.setDescription(sc.nextLine());
                    break;
            }
        } catch (NumberFormatException e) {
            error = "Invalid option. Please try again.";
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        } catch (DateTimeParseException e) {
            error = "Invalid format entered.";
        }
    }

    private void showProperty(int id, String key, String value) {
        if (editMode) System.out.printf("(%d) ", id);
        if (value == null) value = "Unknown";
        System.out.printf("%25s %s\n", key+":", value);
    }
}
