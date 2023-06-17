package io.github.eastmonster.dbsim.mapper;

import io.github.eastmonster.dbsim.entity.Section;
import io.github.eastmonster.dbsim.entity.Student;
import io.github.eastmonster.dbsim.entity.Takes;
import io.github.eastmonster.dbsim.util.dto.ChangeableProfileDTO;
import io.github.eastmonster.dbsim.util.dto.ScheduleDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {

    @Select("SELECT stu_id, stu_name, total_cred, dept_name, major_name FROM student NATURAL JOIN major " +
            "WHERE stu_id = #{id}")
    Student getStudentById(Integer id);

    @Select("SELECT * FROM " +
            "takes NATURAL JOIN section " +
            "      NATURAL JOIN course " +
            " WHERE stu_id = #{stuId} " +
            "   AND semester = #{semester} " +
            "   AND year = #{year} ") // 不用动脑子的写法，但是好丑😔
    List<ScheduleDTO> getSchedule(Integer stuId, String semester, Integer year);

    @Select("SELECT course_id, title, grade, credits, semester, year FROM takes " +
            "NATURAL JOIN course" +
            " WHERE stu_id = #{stuId}")
    List<Takes> getGrade(Integer stuId);

    @Select("SELECT * FROM takes NATURAL JOIN course " +
            " WHERE stu_id = #{stuId}" +
            "   AND grade > 0")
    List<Takes> getAllSelectedValidCourses(Integer stuId);

    @Select("SELECT COUNT(*) FROM student " +
            " WHERE stu_id = #{stuId} " +
            "   AND stu_passwd = #{password}")
    Integer verifyStudent(Integer stuId, String password);

    @Update("UPDATE student SET stu_passwd=#{newProfile.newPassword} " +
            " WHERE stu_id = #{stuId}")
    void updatePassword(Integer stuId, ChangeableProfileDTO newProfile);

    @Select("SELECT total_cred from student " +
            " WHERE stu_id = #{stuId}")
    Integer getTotalCredit(Integer stuId);

    @Select("SELECT course_id, sec_id, title, grade from takes NATURAL JOIN course " +
            "WHERE stu_id = #{stuId}" +
            "  AND year = #{year}" +
            "  AND semester = #{semester}" +
            "  AND grade = 0")
    List<Takes> getTakes(Integer stuId, Integer year, String semester);

    @Delete("DELETE FROM takes " +
            "WHERE stu_id = #{stuId} " +
            "  AND year = #{year} " +
            "  AND semester = #{semester} " +
            "  AND course_id = #{courseId}" +
            "  AND sec_id = #{secId}")
    void deleteTake(Integer stuId, Integer courseId, Integer secId, Integer year, String semester);

    @Update("UPDATE section SET selected_stu = selected_stu - 1 " +
            " WHERE year = #{year} " +
            " AND semester = #{semester} " +
            " AND course_id = #{courseId} " +
            " AND sec_id = #{secId}")
    void decrementSelectedStudent(Integer courseId, Integer secId, Integer year, String semester);

    /* 获取所有 section 里没有选过的课，且时间不能重叠 */
    @Select("""
    SELECT *
    FROM section AS s
        NATURAL JOIN teaches
        NATURAL JOIN course AS c
        NATURAL JOIN instructor
    WHERE year = #{year} AND semester = #{semester}
        AND selected_stu < max_stu
        AND NOT EXISTS (
            SELECT *
            FROM takes AS tt
                NATURAL JOIN section AS ss
                NATURAL JOIN course AS cc
            WHERE tt.stu_id = #{stuId}
                AND (s.course_id = tt.course_id
                     OR (ss.time_slot_id <= s.time_slot_id and ss.time_slot_id + c.credits - 1 >= s.time_slot_id)
                     OR (s.time_slot_id <= ss.time_slot_id and s.time_slot_id + c.credits - 1 >= ss.time_slot_id)
                    )
        )""")
    List<Section> getCourseOptions(Integer stuId, Integer year, String semester);

    @Insert("INSERT INTO takes (stu_id, course_id, sec_id, semester, year, grade) " +
            "VALUE (#{stuId}, #{courseId}, #{secId}, #{semester}, #{year}, 0)")
    void addTake(Integer stuId, Integer courseId, Integer secId, Integer year, String semester);

    @Update("UPDATE section SET selected_stu = selected_stu + 1 " +
            " WHERE year = #{year} " +
            " AND semester = #{semester} " +
            " AND course_id = #{courseId} " +
            " AND sec_id = #{secId}")
    void incrementSelectedStudent(Integer courseId, Integer secId, Integer year, String semester);
}
