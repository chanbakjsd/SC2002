package dev.wenxu.sc2002.entity;

/**
 * A user that can login to the CAMS system.
 */
public class User {
    /**
     * The network user ID of the user, which is the part before @ in email address.
     */
    private final String userID;
    /**
     * The name of the user.
     */
    private final String name;
    /**
     * The password of the user. While storing it in plaintext is not ideal, we are keeping things simple here.
     */
    private String password;
    /**
     * The name of the faculty that the user belongs to.
     */
    private final String faculty;
    /**
     * True if the user is a staff, false otherwise.
     */
    private final boolean isStaff;

    /**
     * Creates a new User with the default password of "password".
     *
     * @param userID  The network ID of the user.
     * @param name The display name of the user.
     * @param faculty The faculty that the user belongs to.
     * @param isStaff True if the user is a staff, false otherwise.
     */
    public User(String userID, String name, String faculty, boolean isStaff) {
        this.userID = userID;
        this.name = name;
        this.faculty = faculty;
        this.password = "password";
        this.isStaff = isStaff;
    }

    /**
     * @return The ID of the user
     */
    public String getUserID() {
        return userID;
    }
    /**
     * @return The display name of the user
     */
    public String getName() {
        return name;
    }
    /**
     * @return The name of the faculty that the user belongs to.
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * Validate that the password provided is correct for the user.
     * @param password The password to validate.
     * @return True if the password is valid, false if the password is invalid.
     */
    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Updates the password of the user to the new user.
     * @param password The new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return True if the user is a staff, false if the user is a student.
     */
    public boolean isStaff() {
        return isStaff;
    }
}
