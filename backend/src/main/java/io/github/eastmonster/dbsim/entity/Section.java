package io.github.eastmonster.dbsim.entity;

public class Section {
    private Integer courseId;

    private Integer secId;

    private String semester;

    private Integer year;

    private Integer selectedStu;

    private Integer maxStu;

    private String building;

    private Integer roomNumber;

    private Integer timeSlotId;

    // 以下属性没有在表中出现
    private String deptName;

    private String title;

    private String instName;

    private Integer credits;

    @Override
    public String toString() {
        return "Section{" +
                "courseId=" + courseId +
                ", sectionId=" + secId +
                ", semester='" + semester + '\'' +
                ", year=" + year +
                ", selectedStudent=" + selectedStu +
                ", maxStudent=" + maxStu +
                ", building='" + building + '\'' +
                ", room_number=" + roomNumber +
                ", timeSlotId=" + timeSlotId +
                '}';
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getSecId() {
        return secId;
    }

    public void setSecId(Integer secId) {
        this.secId = secId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSelectedStu() {
        return selectedStu;
    }

    public void setSelectedStu(Integer selectedStu) {
        this.selectedStu = selectedStu;
    }

    public Integer getMaxStu() {
        return maxStu;
    }

    public void setMaxStu(Integer maxStu) {
        this.maxStu = maxStu;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Integer timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }
}
