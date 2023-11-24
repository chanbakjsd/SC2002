package dev.wenxu.sc2002.entity;

/**
 * A committee member that is helping out in a camp.
 */
public class CommitteeMember extends CampUser {
    /**
     * The number of points the committee member has. 1 point is given every time the committee member answers an enquiry.
     */
    private int points;

    /**
     * @param userID The ID of the user to create.
     */
    public CommitteeMember(String userID) {
        super(userID);
    }

    /**
     * @return The number of points the committee member has.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Adds 1 point to the committee member.
     */
    public void incrementPoint() {
        this.points++;
    }
}
