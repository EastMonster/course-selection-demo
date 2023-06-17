package io.github.eastmonster.dbsim.util.dto;

public class StudentStatDTO {
    private String gpa; // 截断后传输

    private String weightedAvgGrade;

    private Integer totalCredit;

    private Integer studiedCourseCount;

    private Integer failedCourseCount;

    public StudentStatDTO(String gpa, String weightedAvgGrade, Integer totalCredit, Integer studiedCourseCount, Integer failedCourseCount) {
        this.gpa = gpa;
        this.weightedAvgGrade = weightedAvgGrade;
        this.totalCredit = totalCredit;
        this.studiedCourseCount = studiedCourseCount;
        this.failedCourseCount = failedCourseCount;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getWeightedAvgGrade() {
        return weightedAvgGrade;
    }

    public void setWeightedAvgGrade(String weightedAvgGrade) {
        this.weightedAvgGrade = weightedAvgGrade;
    }

    public Integer getStudiedCourseCount() {
        return studiedCourseCount;
    }

    public void setStudiedCourseCount(Integer studiedCourseCount) {
        this.studiedCourseCount = studiedCourseCount;
    }

    public Integer getFailedCourseCount() {
        return failedCourseCount;
    }

    public void setFailedCourseCount(Integer failedCourseCount) {
        this.failedCourseCount = failedCourseCount;
    }

    public Integer getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(Integer totalCredit) {
        this.totalCredit = totalCredit;
    }
}
