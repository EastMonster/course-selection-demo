package io.github.eastmonster.dbsim.entity;

public class Teaches {
    /**
     * inst_id, 教师教职工号
     */
    private Integer instId;

    /**
     * course_id, 课程号
     */
    private String courseId;

    /**
     * sec_id, 课序号
     */
    private Integer secId;

    /**
     * semester, 学期
     */
    private String semester;

    /**
     * year, 学年
     */
    private Integer year;

    /**
     * grade, 课程成绩
     */
    private Integer grade;

    // ===== 以下属性不属于 teaches 表 =====

    /**
     * building, 课程所在建筑
     */
    private String building;

    /**
     * room_number, 教室编号
     */
    private Integer roomNumber;

    /**
     * credits, 课程学分
     */
    private Integer credits;

    /**
     * selected_stu, 课程已选人数
     */
    private Integer selectedStu;

    /**
     * title, 课程名
     */
    private String title;

    /**
     * time_slot_id, 时间段编号
     */
    private Integer timeSlotId;

    @Override
    public String toString() {
        return "Teaches{" +
                "instId=" + instId +
                ", courseId='" + courseId + '\'' +
                ", secId=" + secId +
                ", semester='" + semester + '\'' +
                ", year=" + year +
                ", grade=" + grade +
                '}';
    }

    public Integer getInstId() {
        return instId;
    }

    public void setInstId(Integer instId) {
        this.instId = instId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
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

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getSelectedStu() {
        return selectedStu;
    }

    public void setSelectedStu(Integer selectedStu) {
        this.selectedStu = selectedStu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Integer timeSlotId) {
        this.timeSlotId = timeSlotId;
    }
}
