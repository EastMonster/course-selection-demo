package io.github.eastmonster.dbsim.util;

public class GpaConverter {
    public static double getGpa(int grade) {
        if (grade >= 90) {
            return 4.0;
        } else if (grade >= 85) {
            return 3.7;
        } else if (grade >= 80) {
            return 3.3;
        } else if (grade >= 76) {
            return 3.0;
        } else if (grade >= 73) {
            return 2.7;
        } else if (grade >= 70) {
            return 2.3;
        } else if (grade >= 66) {
            return 2.0;
        } else if (grade >= 63) {
            return 1.7;
        } else if (grade >= 61) {
            return 1.3;
        } else if (grade == 60) {
            return 1.0;
        } else {
            return 0.0;
        }
    }
}
