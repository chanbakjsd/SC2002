package dev.wenxu.sc2002.controller;

import dev.wenxu.sc2002.entity.Camp;
import dev.wenxu.sc2002.entity.CommitteeMember;
import dev.wenxu.sc2002.entity.User;

import java.util.ArrayList;
import java.util.List;

public class CampController {
    private static final CampController instance = new CampController();

    /**
     * The list of camps that are being managed by the system.
     */
    private final List<Camp> camps;

    private CampController() {
        camps = new ArrayList<>();
    }

    /**
     * @return The instance of CampController.
     */
    public static CampController getInstance() {
        return instance;
    }


    /**
     * @return The list of camps being managed by CampController.
     */
    public List<Camp> getCamps() {
        return camps;
    }

    /**
     * Add the provided camp to the list of camps.
     * @param camp The camp to add.
     */
    public void addCamp(Camp camp) {
        camps.add(camp);
    }

    /**
     * Remove the camp if it is found.
     * @param camp The camp to delete.
     */
    public void deleteCamp(Camp camp) {
        camps.remove(camp);
    }

    /**
     * Returns whether the user is an existing committee member.
     * @param userID The ID of the user to check.
     * @return True if the user is an existing committee member, false otherwise.
     */
    public boolean isCommitteeMember(String userID) {
        return camps.stream().map(camp -> camp.findUser(userID)).
                anyMatch(u ->
                        u.isPresent() &&
                        (u.get() instanceof CommitteeMember)
                );
    }
}