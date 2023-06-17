package io.github.eastmonster.dbsim.util;

// 这个类用于存储杂项信息
// 做成单例，方便访问
public class Misc {

    private Integer year = 2023;

    private String semester = "秋期";

    private boolean isSelectionEnabled = true;

    private Misc() {}

    private static final Misc singleton = new Misc();

    public static Misc getInstance() {
        return singleton;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public boolean isIsSelectionEnabled() {
        return isSelectionEnabled;
    }

    public void setIsSelectionEnabled(boolean isSelectionEnabled) {
        this.isSelectionEnabled = isSelectionEnabled;
    }
}
