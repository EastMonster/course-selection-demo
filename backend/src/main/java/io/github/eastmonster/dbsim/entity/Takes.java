package io.github.eastmonster.dbsim.entity;

public class Takes {
    /**
     * stu_id 学生学号
     */
    private Integer stuId;

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

    // ===== 以下属性不属于 takes 表 =====

    /**
     * credits, 课程学分
     */
    private Integer credits;

    /**
     * selected_stu, 课程已选人数
     */
    private Integer selectedStu;

    /**
     * max_stu, 课程最大课容量
     */
    private Integer maxStu;

    /**
     * major_name, 专业名
     */
    private String majorName;

    /**
     * stu_name, 学生姓名
     */
    private String stuName;

    /**
     * title, 课程名
     */
    private String title;

    @Override
    public String toString() {
        return "Takes{" +
                "stuId=" + stuId +
                ", courseId='" + courseId + '\'' +
                ", secId=" + secId +
                ", semester='" + semester + '\'' +
                ", year=" + year +
                ", grade=" + grade +
                '}';
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
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

    public Integer getMaxStu() {
        return maxStu;
    }

    public void setMaxStu(Integer maxStu) {
        this.maxStu = maxStu;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
