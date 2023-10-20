package dev.wenxu.sc2002.entity;

/**
 * An instance of a user that is participating in the camp.
 */
public class CampUser {
    /**
     * The ID of the user that is participating in the camp.
     */
    private final String userID;

    /**
     * @param userID The ID of the user to create.
     */
    public CampUser(String userID) {
        this.userID = userID;
    }

    /**
     * @return The ID of the user.
     */
    public String getUserID() {
        return userID;
    }
}
