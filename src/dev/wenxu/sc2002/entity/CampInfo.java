package dev.wenxu.sc2002.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The information stored for a camp.
 */
public class CampInfo {
    /**
     * The name of the camp.
     */
    private String name;
    /**
     * The starting date of the camp.
     */
    private LocalDate startDate;
    /**
     * The ending date of the camp.
     */
    private LocalDate endDate;
    /**
     * The time at which students may no longer register.
     */
    private LocalDateTime registrationDeadline;

    /**
     * True if the camp is open to all, false if the camp is only open to the specific faculty.
     */
    private boolean openToAll;
    /**
     * The faculty that the camp is open to if openToAll is false.
     */
    private String faculty;

    /**
     * The location of the camp.
     */
    private String location;

    /**
     * The number of slots available for students to register.
     */
    private int totalSlots;
    /**
     * The number of slots for the committee.
     */
    private int campCommitteeSlots;

    /**
     * Brief description of the camp that will be displayed to the user.
     */
    private String description;
    /**
     * The user ID of the staff that created this camp entry.
     */
    private String staffInCharge;

    /**
     * @param name The name of the camp.
     * @param staffInCharge The user ID of the staff in charge.
     */
    public CampInfo(String name, String staffInCharge) {
        this.name = name;
        this.staffInCharge = staffInCharge;
    }

    /**
     * @return The name of the camp.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name of the camp to the provided name.
     * @param name The new name of the camp.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The date that the camp starts.
     */
    public LocalDate getStartDate() {
        return startDate;
    }
    /**
     * Updates the start date of the camp to the provided start date.
     * @param startDate The new start date of the camp.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @return The date that the camp ends.
     */
    public LocalDate getEndDate() {
        return endDate;
    }
    /**
     * Updates the end date of the camp to the provided end date.
     * @param endDate The new start date of the camp.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * @return The registration deadline of the camp.
     */
    public LocalDateTime getRegistrationDeadline() {
        return registrationDeadline;
    }
    /**
     * Updates the registration deadline of the camp to the provided registration deadline.
     * @param registrationDeadline The new registration deadline of the camp.
     */
    public void setRegistrationDeadline(LocalDateTime registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    /**
     * @return True if the camp is open to all, false otherwise.
     */
    public boolean isOpenToAll() {
        return openToAll;
    }
    /**
     * Updates if the camp is open to all.
     * @param openToAll True if the camp should be open to all, false otherwise.
     */
    public void setOpenToAll(boolean openToAll) {
        this.openToAll = openToAll;
    }

    /**
     * @return The faculty that the camp is open to.
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * Updates the faculty that the camp is open to.
     * @param faculty The new faculty that the camp is open to.
     */
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    /**
     * @return The location of the camp is held at.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Updates the location of the camp is held at.
     * @param location The new location of the camp is held at.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return The total number of slots available for registration.
     */
    public int getTotalSlots() {
        return totalSlots;
    }

    /**
     * Updates the total number of slots available for registration.
     * @param totalSlots The new number of slots available for registration.
     */
    public void setTotalSlots(int totalSlots) {
        if (totalSlots < 0) {
            throw new IllegalArgumentException("Total slots must be non-negative.");
        }
        this.totalSlots = totalSlots;
    }

    /**
     * @return The total number of slots available for camp committee.
     */
    public int getCampCommitteeSlots() {
        return campCommitteeSlots;
    }

    /**
     * Updates the total number of slots available for camp committee.
     * @param campCommitteeSlots The new number of slots available for camp committee.
     */
    public void setCampCommitteeSlots(int campCommitteeSlots) {
        if (campCommitteeSlots < 0) {
            throw new IllegalArgumentException("Camp committee slots must be non-negative.");
        }
        if (campCommitteeSlots > 10) {
            throw new IllegalArgumentException("A camp may only have 10 committees.");
        }
        this.campCommitteeSlots = campCommitteeSlots;
    }

    /**
     * @return The description of the camp.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Updates the new description of the camp.
     * @param description The new description of the camp.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The user ID of the staff in charge.
     */
    public String getStaffInCharge() {
        return staffInCharge;
    }

    /**
     * Updates the staff in charge to the provided staff.
     * @param staffInCharge The user ID of the staff in charge.
     */
    public void setStaffInCharge(String staffInCharge) {
        this.staffInCharge = staffInCharge;
    }
}
