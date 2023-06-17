package io.github.eastmonster.dbsim.controller;

import io.github.eastmonster.dbsim.entity.Section;
import io.github.eastmonster.dbsim.entity.Student;
import io.github.eastmonster.dbsim.entity.Takes;
import io.github.eastmonster.dbsim.mapper.StudentMapper;
import io.github.eastmonster.dbsim.service.CourseService;
import io.github.eastmonster.dbsim.service.ProfileService;
import io.github.eastmonster.dbsim.util.Misc;
import io.github.eastmonster.dbsim.util.Result;
import io.github.eastmonster.dbsim.util.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "学生接口")
@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/{stuId}")
    @ApiOperation("获取学生信息")
    public Student getStudent(@PathVariable Integer stuId) {
        return studentMapper.getStudentById(stuId);
    }

    @PatchMapping("/{stuId}")
    @ApiOperation("修改学生信息") // 其实只能改密码
    public Result modifyInstructor(@PathVariable Integer stuId,
                                   @RequestBody ChangeableProfileDTO student) {
        return profileService.changePassword(stuId, student);
    }

    @GetMapping("/{stuId}/schedule")
    @ApiOperation("获取学生课表信息")
    public List<ScheduleDTO> getSchedules(@PathVariable Integer stuId,
                                          @RequestParam String semester,
                                          @RequestParam Integer year) {
        return studentMapper.getSchedule(stuId, semester, year);
    }

    @GetMapping("/{stuId}/takes/options")
    @ApiOperation("获取学生可选课程")
    public List<Section> getOptions(@PathVariable Integer stuId) {
        return studentMapper.getCourseOptions(stuId, Misc.getInstance().getYear(), Misc.getInstance().getSemester());
    }

    @PostMapping("/{stuId}/takes")
    @ApiOperation("学生选课")
    public Result selectCourse(@PathVariable Integer stuId,
                               @RequestBody SelectionDTO selection) {
        return courseService.selectCourse(stuId, selection);
    }

    @DeleteMapping("/{stuId}/takes")
    @ApiOperation("学生退课")
    public Result dropCourse(@PathVariable Integer stuId,
                             @RequestParam Integer courseId,
                             @RequestParam Integer secId) {
        return courseService.dropCourse(stuId, courseId, secId);
    }

    @GetMapping("/{stuId}/takes")
    @ApiOperation("获取学生本学期已选课")
    public List<Takes> getTakes(@PathVariable Integer stuId) {
        return studentMapper.getTakes(stuId, Misc.getInstance().getYear(), Misc.getInstance().getSemester());
    }

    @GetMapping("/{stuId}/grades")
    @ApiOperation("获取学生成绩信息")
    public List<Takes> getGrades(@PathVariable Integer stuId) {
        return studentMapper.getGrade(stuId);
    }


    @GetMapping("/{stuId}/stat")
    @ApiOperation("获取学生概要信息")
    public StudentStatDTO getStudentStat(@PathVariable Integer stuId) {
        return courseService.getStudentStat(stuId);
    }
}
