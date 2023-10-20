package dev.wenxu.sc2002.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public String staffInCharge;

    /**
     * @param name The name of the camp.
     */
    public CampInfo(String name, String staffInCharge) {
        this.name = name;
        this.staffInCharge = staffInCharge;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getRegistrationDeadline() {
        return registrationDeadline;
    }
    public void setRegistrationDeadline(LocalDateTime registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public boolean isOpenToAll() {
        return openToAll;
    }
    public void setOpenToAll(boolean openToAll) {
        this.openToAll = openToAll;
    }

    public String getFaculty() {
        return faculty;
    }
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalSlots() {
        return totalSlots;
    }
    public void setTotalSlots(int totalSlots) {
        if (totalSlots < 0) {
            throw new IllegalArgumentException("Total slots must be non-negative.");
        }
        this.totalSlots = totalSlots;
    }

    public int getCampCommitteeSlots() {
        return campCommitteeSlots;
    }
    public void setCampCommitteeSlots(int campCommitteeSlots) {
        if (campCommitteeSlots < 0) {
            throw new IllegalArgumentException("Camp committee slots must be non-negative.");
        }
        if (campCommitteeSlots > 10) {
            throw new IllegalArgumentException("A camp may only have 10 committees.");
        }
        this.campCommitteeSlots = campCommitteeSlots;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getStaffInCharge() {
        return staffInCharge;
    }
    public void setStaffInCharge(String staffInCharge) {
        this.staffInCharge = staffInCharge;
    }
}
