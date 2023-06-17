package io.github.eastmonster.dbsim.mapper;

import io.github.eastmonster.dbsim.entity.Instructor;
import io.github.eastmonster.dbsim.entity.Takes;
import io.github.eastmonster.dbsim.entity.Teaches;
import io.github.eastmonster.dbsim.util.dto.ChangeableProfileDTO;
import io.github.eastmonster.dbsim.util.dto.GradeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface InstructorMapper {
    @Select("SELECT inst_id, inst_name, dept_name FROM instructor" +
            " WHERE inst_id = #{instId}")
    Instructor getInstructorById(Integer instId);

    @Select("SELECT course_id, sec_id, title, selected_stu, building, room_number, credits, time_slot_id, semester, year FROM teaches " +
            "NATURAL JOIN course " +
            "NATURAL JOIN section " +
            "WHERE inst_id = #{instId}")
    List<Teaches> getTeachesById(Integer instId);

    @Select("SELECT stu_id, stu_name, major_name, grade from takes NATURAL JOIN student" +
            " WHERE course_id = #{courseId}" +
            "   AND sec_id = #{secId}" +
            "   AND semester = #{semester}" +
            "   AND year = #{year}")
    List<Takes> getSectionGrades(Integer courseId, Integer secId, String semester, Integer year);

    @Update("UPDATE takes SET grade = #{gradeDTO.grade} " +
            " WHERE stu_id = #{stuId}" +
            "   AND course_id = #{courseId}" +
            "   AND sec_id = #{gradeDTO.secId}" +
            "   AND year = #{gradeDTO.year}" +
            "   AND semester = #{gradeDTO.semester}")
    void updateGrade(Integer courseId, Integer stuId, GradeDTO gradeDTO);

    @Update("UPDATE instructor SET inst_passwd=#{newProfile.newPassword} " +
            " WHERE inst_id = #{instId}")
    void updatePassword(Integer instId, ChangeableProfileDTO newProfile);

    @Select("SELECT COUNT(*) FROM instructor " +
            " WHERE inst_id = #{instId} " +
            "   AND inst_passwd = #{password}")
    Integer verifyInstructor(Integer instId, String password);
}
