package io.github.eastmonster.dbsim.entity;


public class Student{
    private Integer stuId;

    private String stuPasswd;

    private String stuName;

    private Integer totalCred;

    private String majorName;

    // 以下属性没有在表中出现

    private String deptName;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + stuId +
                ", password='" + stuPasswd + '\'' +
                ", name='" + stuName + '\'' +
                ", totalCredit=" + totalCred +
                ", majorId=" + majorName +
                '}';
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStuPasswd() {
        return stuPasswd;
    }

    public void setStuPasswd(String stuPasswd) {
        this.stuPasswd = stuPasswd;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Integer getTotalCred() {
        return totalCred;
    }

    public void setTotalCred(Integer totalCred) {
        this.totalCred = totalCred;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
