package dev.wenxu.sc2002.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A suggestion that allows suggested changes to be temporarily stored while modifying the information of a camp..
 */
public class Suggestion extends CampInfo {
    /**
     * The user ID of the suggester.
     */
    private final String suggesterID;
    /**
     * The original camp information that the suggestion was editing.
     */
    private final CampInfo original;

    /**
     * True if open to all was updated, false otherwise.
     */
    private boolean openToAllUpdated;
    /**
     * True if total slots was updated, false otherwise.
     */
    private boolean totalSlotsUpdated;
    /**
     * True if camp committee slots was updated, false otherwise.
     */
    private boolean campCommitteeSlotsUpdated;

    /**
     * Creates a new Suggestion.
     * @param suggesterUserID The user ID of the suggester that is editing the camp info.
     * @param original The original camp information to be edited.
     */
    public Suggestion(String suggesterUserID, CampInfo original) {
        super(null, null);
        this.suggesterID = suggesterUserID;
        this.original = original;
    }

    /**
     * @return The user ID of the suggester
     */
    public String getSuggesterID() {
        return this.suggesterID;
    }

    /**
     * Applies the suggestion to the specified camp info.
     * @param info The information to apply the suggestion to.
     */
    public void applyTo(CampInfo info) {
        if (super.getName() != null) info.setName(super.getName());
        if (super.getStartDate() != null) info.setStartDate(super.getStartDate());
        if (super.getEndDate() != null) info.setEndDate(super.getEndDate());
        if (super.getRegistrationDeadline() != null) info.setRegistrationDeadline(super.getRegistrationDeadline());
        if (super.getFaculty() != null) info.setFaculty(super.getFaculty());
        if (super.getLocation() != null) info.setLocation(super.getLocation());
        if (this.openToAllUpdated) info.setOpenToAll(super.isOpenToAll());
        if (this.totalSlotsUpdated) info.setTotalSlots(super.getTotalSlots());
        if (this.campCommitteeSlotsUpdated) info.setCampCommitteeSlots(super.getCampCommitteeSlots());
        if (super.getDescription() != null) info.setDescription(super.getDescription());
        if (super.getStaffInCharge() != null) info.setStaffInCharge(super.getStaffInCharge());
    }

    /**
     * @return The name of the camp.
     */
    @Override
    public String getName() {
        if (super.getName() != null) return super.getName();
        return original.getName();
    }

    /**
     * @return The date that the camp starts.
     */
    @Override
    public LocalDate getStartDate() {
        if (super.getStartDate() != null) return super.getStartDate();
        return original.getStartDate();
    }

    /**
     * @return The date that the camp ends.
     */
    @Override
    public LocalDate getEndDate() {
        if (super.getEndDate() != null) return super.getEndDate();
        return original.getEndDate();
    }

    /**
     * @return The registration deadline of the camp.
     */
    @Override
    public LocalDateTime getRegistrationDeadline() {
        if (super.getRegistrationDeadline() != null) return super.getRegistrationDeadline();
        return original.getRegistrationDeadline();
    }

    /**
     * @return The faculty that the camp is open to.
     */
    @Override
    public String getFaculty() {
        if (super.getFaculty() != null) return super.getFaculty();
        return original.getFaculty();
    }

    /**
     * @return The location of the camp is held at.
     */
    @Override
    public String getLocation() {
        if (super.getLocation() != null) return super.getLocation();
        return original.getLocation();
    }

    /**
     * @return True if the camp is open to all, false otherwise.
     */
    @Override
    public boolean isOpenToAll() {
        if (this.openToAllUpdated) return super.isOpenToAll();
        return original.isOpenToAll();
    }

    /**
     * @return The total number of slots available for registration.
     */
    @Override
    public int getTotalSlots() {
        if (this.totalSlotsUpdated) return super.getTotalSlots();
        return original.getTotalSlots();
    }

    /**
     * @return The total number of slots available for camp committee.
     */
    @Override
    public int getCampCommitteeSlots() {
        if (this.campCommitteeSlotsUpdated) return super.getCampCommitteeSlots();
        return original.getCampCommitteeSlots();
    }

    /**
     * @return The description of the camp.
     */
    @Override
    public String getDescription() {
        if (super.getDescription() != null) return super.getDescription();
        return original.getDescription();
    }

    /**
     * @return The user ID of the staff in charge.
     */
    @Override
    public String getStaffInCharge() {
        if (super.getStaffInCharge() != null) return super.getStaffInCharge();
        return original.getStaffInCharge();
    }

    /**
     * Updates if the camp is open to all.
     * @param openToAll True if the camp should be open to all, false otherwise.
     */
    @Override
    public void setOpenToAll(boolean openToAll) {
        this.openToAllUpdated = true;
        super.setOpenToAll(openToAll);
    }

    /**
     * Updates the total number of slots available for registration.
     * @param totalSlots The new number of slots available for registration.
     */
    @Override
    public void setTotalSlots(int totalSlots) {
        this.totalSlotsUpdated = true;
        super.setTotalSlots(totalSlots);
    }

    /**
     * Updates the total number of slots available for camp committee.
     * @param campCommitteeSlots The new number of slots available for camp committee.
     */
    @Override
    public void setCampCommitteeSlots(int campCommitteeSlots) {
        this.campCommitteeSlotsUpdated = true;
        super.setCampCommitteeSlots(campCommitteeSlots);
    }
}