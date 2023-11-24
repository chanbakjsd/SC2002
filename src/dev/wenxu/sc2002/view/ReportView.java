package dev.wenxu.sc2002.view;

import dev.wenxu.sc2002.entity.Camp;
import dev.wenxu.sc2002.entity.CampInfo;
import dev.wenxu.sc2002.entity.CampUser;
import dev.wenxu.sc2002.entity.CommitteeMember;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * The screen to allow users to generate camp reports.
 */
public class ReportView extends View {
    /**
     * The pattern used to generate file names from date and time.
     */
    private static final DateTimeFormatter FILE_NAME_PATTERN = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * The camp to generate report for.
     */
    private final Camp camp;
    /**
     * The view to return to when the user quits.
     */
    private final View originalView;
    /**
     * True if the player is allowed to generate performance report, false otherwise.
     */
    private final boolean allowPerformance;
    /**
     * True if the report generated will include camp committee, false otherwise.
     */
    private boolean campCommittee;
    /**
     * True if the report generated will include attendees, false otherwise.
     */
    private boolean attendee;

    /**
     * Create a new ReportView.
     * @param camp The camp to generate report for.
     * @param originalView The view to return to.
     * @param allowPerformance True if performance reports were allowed, false otherwise.
     */
    public ReportView(Camp camp, View originalView, boolean allowPerformance) {
        this.camp = camp;
        this.originalView = originalView;
        this.allowPerformance = allowPerformance;
    }

    /**
     * Display the screen to allow users to generate reports.
     * @param sc The scanner that is scanning for inputs from stdin.
     * @return The view to return to.
     */
    @Override
    public View display(Scanner sc) {
        while (true) {
            clearScreen();
            boolean canGenerate = campCommittee || attendee;
            boolean canGeneratePerf = campCommittee && !attendee && allowPerformance;
            System.out.print(campCommittee ? "* " : "  ");
            System.out.println("Include (C)amp Committee");
            System.out.print(attendee ? "* " : "  ");
            System.out.println("Include (A)ttendee");
            System.out.println();
            if (canGeneratePerf) {
                System.out.println("(G)enerate Performance Report");
            } else if (canGenerate) {
                System.out.println("(G)enerate Report");
            } else {
                System.out.println("Choose one type of member to generate report.");
            }
            System.out.println("(Q)uit");
            String command = sc.nextLine();
            if (command.equalsIgnoreCase("c")) {
                campCommittee = !campCommittee;
            } else if (command.equalsIgnoreCase("a")) {
                attendee = !attendee;
            } else if (command.equalsIgnoreCase("g") && canGenerate) {
                generateReport();
            } else if (command.equalsIgnoreCase("q")) {
                return originalView;
            } else {
                error = "Invalid command. Please try again.";
            }
        }
    }

    /**
     * Write a file with the description of the camp and another file containing the list of attendees.
     */
    public void generateReport() {
        LocalDateTime nowTime = LocalDateTime.now();
        String txtFileName = nowTime.format(FILE_NAME_PATTERN) + " " + camp.getInfo().getName() + ".txt";
        String csvFileName = nowTime.format(FILE_NAME_PATTERN) + " " + camp.getInfo().getName() + ".csv";
        try (FileWriter writer = new FileWriter(txtFileName)) {
            CampInfo info = camp.getInfo();
            writer.write(info.getName() + "\n\n");
            if (info.getStartDate() == null || info.getEndDate() == null) {
                writer.write("Date: Unknown\n");
            } else {
                String start = info.getStartDate().format(DateTimeFormatter.ISO_DATE);
                String end = info.getEndDate().format(DateTimeFormatter.ISO_DATE);
                writer.write("Date: " + start + " to " + end + "\n");
            }
            if (info.getRegistrationDeadline() == null) {
                writer.write("Registration Deadline: Unavailable\n");
            } else {
                writer.write("Registration Deadline: " + info.getRegistrationDeadline().format(DateTimeFormatter.ISO_DATE_TIME) + "\n");
            }
            if (info.isOpenToAll()) {
                writer.write("Open to: NTU\n");
            } else {
                writer.write("Open to: Specific Faculty (" + info.getFaculty() + ")\n");
            }
            if (info.getLocation() == null) {
                writer.write("Location: Unknown\n");
            } else {
                writer.write("Location: " + info.getLocation() + "\n");
            }
            String slotFilled = String.valueOf(camp.getAttendees().size());
            String totalSlot = String.valueOf(info.getTotalSlots());
            writer.write("Slots: " + slotFilled + "/" + totalSlot + "\n");
            String committeeFilled = String.valueOf(camp.getAttendees().stream().filter(u -> u instanceof CommitteeMember).count());
            String committeeSlot = String.valueOf(info.getCampCommitteeSlots());
            writer.write("Committees" + committeeFilled + "/" + committeeSlot + "\n");
            if (info.getStaffInCharge() == null) {
                writer.write("Staff In Charge: Unknown\n");
            } else {
                writer.write("Staff in Charge: " + info.getStaffInCharge() + "\n");
            }
            if (info.getDescription() == null) {
                writer.write("Description: <not provided>\n");
            } else {
                writer.write("Description: " + info.getDescription() + "\n");
            }
            writer.write("\nGenerated by CAM at " + nowTime.format(DateTimeFormatter.ISO_DATE_TIME));
        } catch (IOException e) {
            error = "Failed to write file: " + e.getMessage();
            return;
        }
        try (FileWriter writer = new FileWriter(csvFileName)) {
            boolean includeScore = campCommittee && !attendee && allowPerformance;
            if (includeScore) {
                writer.write("UserID,Type,Score\n");
            } else {
                writer.write("UserID,Type\n");
            }
            for (CampUser campUser : camp.getAttendees()) {
                if (!campCommittee && campUser instanceof CommitteeMember) {
                    continue;
                }
                if (!attendee && !(campUser instanceof CommitteeMember)) {
                    continue;
                }
                writer.write(campUser.getUserID() + ",");
                writer.write((campUser instanceof CommitteeMember) ? "Committee" : "Attendee");
                if (campUser instanceof CommitteeMember && includeScore) {
                    int score = ((CommitteeMember)campUser).getPoints();
                    writer.write("," + score);
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            error = "Failed to write file: " + e.getMessage();
            return;
        }
        this.info = "The report has been generated: " + csvFileName;
    }
}
