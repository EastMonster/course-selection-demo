package io.github.eastmonster.dbsim.util.dto;

// 教师更改成绩的时候提交的表单格式
public class GradeDTO {
    private Integer secId;

    private String semester;

    private Integer year;

    private Integer grade;


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
}
