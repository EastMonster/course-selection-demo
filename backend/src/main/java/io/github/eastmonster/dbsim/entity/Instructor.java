package io.github.eastmonster.dbsim.entity;

public class Instructor {
    private Integer instId;

    private String instPasswd;

    private String instName;

    private String deptName;

    @Override
    public String toString() {
        return "Instructor{" +
                "id=" + instId +
                ", password='" + instPasswd + '\'' +
                ", name='" + instName + '\'' +
                ", departmentName='" + deptName + '\'' +
                '}';
    }

    public Integer getInstId() {
        return instId;
    }

    public void setInstId(Integer instId) {
        this.instId = instId;
    }

    public String getInstPasswd() {
        return instPasswd;
    }

    public void setInstPasswd(String instPasswd) {
        this.instPasswd = instPasswd;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
