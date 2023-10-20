package dev.wenxu.sc2002.controller;

import dev.wenxu.sc2002.entity.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserController {
    private static final UserController instance = new UserController();

    private static final String[] SUFFIXES = {
        // Email suffixes.
        "@E.NTU.EDU.SG", "@NTU.EDU.SG",
        // Domain suffixes.
        "@STUDENT.MAIN.NTU.EDU.SG", "@STAFF.MAIN.NTU.EDU.SG",
    };

    /**
     * The list of the users that can login to the system.
     */
    private final List<User> users;

    private UserController() {
        users = new ArrayList<>();
    }

    /**
     * Returns the singular instance of UserController.
     * @return The instance of UserController.
     */
    public static UserController getInstance() {
        return instance;
    }

    /**
     * Adds the users in the CSV file to the list of users.
     * @param csvFile A buffered reader containing a CSV file with the user details.
     * @param isStaff True if the file contains information of staff, false if the file contains information of students.
     */
    public void addUsers(BufferedReader csvFile, boolean isStaff) {
        while (true) {
            try {
                String line = csvFile.readLine();
                if (line == null) {
                    return;
                }
                String[] values = line.split(",");
                if (values.length != 3) {
                    System.out.printf("Failed to parse line: %s\n", line);
                    continue;
                }
                String name = values[0].trim();
                String userID = normalizeUserID(values[1].trim());
                String faculty = values[2].trim();
                users.add(new User(userID, name, faculty, isStaff));
            } catch (IOException e) {
                return;
            }
        }
    }

    /**
     * @return Number of users initialized in the system.
     */
    public int getUserCount() {
        return users.size();
    }

    /**
     * Get the user by the user ID.
     * @param userID The user ID to fetch.
     * @return The user with the specified user ID, if found.
     */
    public Optional<User> getUser(String userID) {
        String normalizedUserID = normalizeUserID(userID);
        return users.stream().filter(u -> u.getUserID().equals(normalizedUserID)).findFirst();
    }

    /**
     * Normalizes the user ID by trying to retrieve the network ID without any suffixes and making them uppercase.
     * @param userID The user ID to be normalized.
     * @return The user ID after normalization.
     */
    private static String normalizeUserID(String userID) {
        userID = userID.trim();
        // Make everything uppercase as per NTU tradition.
        userID = userID.toUpperCase();
        // Remove common suffixes.
        for (String s: SUFFIXES) {
            if (userID.endsWith(s)) {
                userID = userID.substring(0, userID.length() - s.length());
            }
        }
        userID = userID.trim();
        return userID;
    }

    /**
     * Checks if the camp is managed by the provided user.
     * @param camp The camp to check.
     * @param user The user to check.
     * @return True if the camp is managed by the provided user, false otherwise.
     */
    public static boolean isManagedCamp(Camp camp, User user) {
        CampInfo info = camp.getInfo();
        String userID = user.getUserID();
        if (user.isStaff())
            return info.staffInCharge.equals(userID);
        Optional<CampUser> attendee = camp.getAttendees().stream().
                filter(u -> u.getUserID().equals(userID)).
                findFirst();
        return attendee.isPresent() && attendee.get() instanceof CommitteeMember;
    }

    /**
     * Checks if the camp is visible to the provided user.
     * @param camp The camp to check.
     * @param user The user to check.
     * @return True if the camp is visible to the provided user, false otherwise.
     */
    public static boolean isVisibleCamp(Camp camp, User user) {
        // Staff can view all camps.
        if (user.isStaff()) return true;
        // Committee members can always view their own camps.
        if (isManagedCamp(camp, user)) return true;
        // Student cannot view camps that are not visible.
        if (!camp.isVisible()) return false;
        // Camps that are open to all should be visible.
        if (camp.getInfo().isOpenToAll()) return true;
        // Otherwise, check if the user is from the right faculty.
        return user.getFaculty().equals(camp.getInfo().getFaculty());
    }
}
