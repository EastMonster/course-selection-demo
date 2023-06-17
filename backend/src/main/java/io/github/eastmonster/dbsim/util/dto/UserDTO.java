package io.github.eastmonster.dbsim.util.dto;

public class UserDTO {
    private Integer id;

    private String password;

    private Boolean isStudent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean student) {
        isStudent = student;
    }
}
