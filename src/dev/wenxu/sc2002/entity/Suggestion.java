package dev.wenxu.sc2002.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Suggestion extends CampInfo {
    private final String suggesterID;
    private final CampInfo original;

    private boolean openToAllUpdated;
    private boolean totalSlotsUpdated;
    private boolean campCommitteeSlotsUpdated;

    public Suggestion(String suggesterUserID, CampInfo original) {
        super(null, null);
        this.suggesterID = suggesterUserID;
        this.original = original;
    }

    public String getSuggesterID() {
        return this.suggesterID;
    }

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

    @Override
    public String getName() {
        if (super.getName() != null) return super.getName();
        return original.getName();
    }

    @Override
    public LocalDate getStartDate() {
        if (super.getStartDate() != null) return super.getStartDate();
        return original.getStartDate();
    }

    @Override
    public LocalDate getEndDate() {
        if (super.getEndDate() != null) return super.getEndDate();
        return original.getEndDate();
    }

    @Override
    public LocalDateTime getRegistrationDeadline() {
        if (super.getRegistrationDeadline() != null) return super.getRegistrationDeadline();
        return original.getRegistrationDeadline();
    }

    @Override
    public String getFaculty() {
        if (super.getFaculty() != null) return super.getFaculty();
        return original.getFaculty();
    }

    @Override
    public String getLocation() {
        if (super.getLocation() != null) return super.getLocation();
        return original.getLocation();
    }

    @Override
    public boolean isOpenToAll() {
        if (this.openToAllUpdated) return super.isOpenToAll();
        return original.isOpenToAll();
    }

    @Override
    public int getTotalSlots() {
        if (this.totalSlotsUpdated) return super.getTotalSlots();
        return original.getTotalSlots();
    }

    @Override
    public int getCampCommitteeSlots() {
        if (this.campCommitteeSlotsUpdated) return super.getCampCommitteeSlots();
        return original.getCampCommitteeSlots();
    }

    @Override
    public String getDescription() {
        if (super.getDescription() != null) return super.getDescription();
        return original.getDescription();
    }

    @Override
    public String getStaffInCharge() {
        if (super.getStaffInCharge() != null) return super.getStaffInCharge();
        return original.getStaffInCharge();
    }

    @Override
    public void setOpenToAll(boolean openToAll) {
        this.openToAllUpdated = true;
        super.setOpenToAll(openToAll);
    }

    @Override
    public void setTotalSlots(int totalSlots) {
        this.totalSlotsUpdated = true;
        super.setTotalSlots(totalSlots);
    }

    @Override
    public void setCampCommitteeSlots(int campCommitteeSlots) {
        this.campCommitteeSlotsUpdated = true;
        super.setCampCommitteeSlots(campCommitteeSlots);
    }
}