package io.github.eastmonster.dbsim.service;

import io.github.eastmonster.dbsim.entity.Takes;
import io.github.eastmonster.dbsim.mapper.StudentMapper;
import io.github.eastmonster.dbsim.util.GpaConverter;
import io.github.eastmonster.dbsim.util.Misc;
import io.github.eastmonster.dbsim.util.Result;
import io.github.eastmonster.dbsim.util.dto.SelectionDTO;
import io.github.eastmonster.dbsim.util.dto.StudentStatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    private StudentMapper studentMapper;

    @Transactional
    public Result selectCourse(Integer stuId, SelectionDTO selection) {
        if (!Misc.getInstance().isIsSelectionEnabled()) {
            return Result.error().setMessage("当前不能选课，请刷新页面");
        }
        try {
            studentMapper.addTake(stuId, selection.getCourseId(), selection.getSecId(), Misc.getInstance().getYear(), Misc.getInstance().getSemester());
            studentMapper.incrementSelectedStudent(selection.getCourseId(), selection.getSecId(), Misc.getInstance().getYear(), Misc.getInstance().getSemester());
            return Result.ok().setMessage("选课成功");
        } catch (Exception e) {
            return Result.error().setMessage("选课失败");
        }
    }

    @Transactional
    public Result dropCourse(Integer stuId, Integer courseId, Integer secId) {
        if (!Misc.getInstance().isIsSelectionEnabled()) {
            return Result.error().setMessage("当前不能退课，请刷新页面");
        }
        try {
            studentMapper.deleteTake(stuId, courseId, secId, Misc.getInstance().getYear(), Misc.getInstance().getSemester());
            studentMapper.decrementSelectedStudent(courseId, secId, Misc.getInstance().getYear(), Misc.getInstance().getSemester());
            return Result.ok().setMessage("退课成功");
        } catch (Exception e) {
            return Result.error().setMessage("退课失败");
        }
    }

    public StudentStatDTO getStudentStat(Integer stuId) {
        String gpa, wag;

        List<Takes> takes = studentMapper.getAllSelectedValidCourses(stuId);
        int totalCredit = studentMapper.getTotalCredit(stuId);
        int _totalCredit = 0; // 这里要把不及格科目的学分用来计算
        double totalWeightedGpa = 0;
        double totalWeightedScore = 0;
        int failedCourse = 0;
        for (var take : takes) {
            if (take.getGrade() < 60) failedCourse++;
            _totalCredit += take.getCredits();
            totalWeightedGpa += GpaConverter.getGpa(take.getGrade()) * take.getCredits();
            totalWeightedScore += take.getGrade() * take.getCredits();
        }

        if (totalCredit == 0) {
            return new StudentStatDTO("0", "0", 0, 0, failedCourse);
        }

        DecimalFormat df1 = new DecimalFormat(".00");
        DecimalFormat df2 = new DecimalFormat(".0");
        gpa = df1.format(totalWeightedGpa / _totalCredit);
        wag = df2.format(totalWeightedScore / _totalCredit);

        return new StudentStatDTO(gpa, wag, totalCredit, takes.size() - failedCourse, failedCourse);
    }
}
