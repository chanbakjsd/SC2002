package dev.wenxu.sc2002.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Camp {
    /**
     * True if the camp is visible to the potential attendees, false otherwise.
     */
    private boolean visible;
    /**
     * The information for the camp.
     */
    private final CampInfo info;
    /**
     * The user IDs of the attendees.
     */
    private final List<CampUser> attendees;
    /**
     * The user IDs of attendees that have withdrawn.
     */
    private final List<String> withdrawnAttendees;

    /**
     * @param info The information of the camp.
     */
    public Camp(CampInfo info) {
        this.info = info;
        this.attendees = new ArrayList<>();
        this.withdrawnAttendees = new ArrayList<>();
    }

    /**
     * @return The information of the camp.
     */
    public CampInfo getInfo() {
        return info;
    }

    /**
     * Adds a user to the attendees of the camp.
     * @param user The camp user to add to the camp.
     */
    public void addUser(CampUser user) {
        if (this.attendees.size() >= this.info.getTotalSlots()) {
            throw new IllegalArgumentException("The camp is full.");
        }
        long committeeMemberCount = this.attendees.stream().filter(u -> u instanceof CommitteeMember).count();
        if (user instanceof CommitteeMember && committeeMemberCount >= this.info.getCampCommitteeSlots()) {
            throw new IllegalArgumentException("There may not be more committee members.");
        }
        boolean duplicate = this.attendees.stream().anyMatch(attendee -> attendee.getUserID().equals(user.getUserID()));
        if (duplicate) {
            throw new IllegalArgumentException("User is already in the camp.");
        }
        if (this.withdrawnAttendees.contains(user.getUserID())) {
            throw new IllegalArgumentException("User has previously withdrawn from the camp.");
        }
        if (this.info.getRegistrationDeadline() == null || LocalDateTime.now().isAfter(this.info.getRegistrationDeadline())) {
            throw new IllegalArgumentException("The camp registration deadline is over.");
        }
        this.attendees.add(user);
    }

    /**
     * Withdraws the participation of the specified user.
     * @param userID The ID of the user who is withdrawing.
     */
    public void withdraw(String userID) {
        CampUser user = this.attendees.stream().
                filter(u -> u.getUserID().equals(userID)).
                findFirst().
                orElseThrow(() -> new IllegalArgumentException("The user has not registered for the camp."));
        if (user instanceof CommitteeMember) {
            throw new IllegalArgumentException("Committee members may not quit from the camp.");
        }
        this.attendees.remove(user);
        this.withdrawnAttendees.add(userID);
    }

    /**
     * @return The list of attendees.
     */
    public List<CampUser> getAttendees() {
        return this.attendees;
    }

    /**
     * @return The visibility of the camp. True if the camp is visible to potential attendees.
     */
    public boolean isVisible() {
        return visible;
    }
    /**
     * Update the visibility of the camp.
     * @param visible The camp will be viewable to students if this is set to true.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}