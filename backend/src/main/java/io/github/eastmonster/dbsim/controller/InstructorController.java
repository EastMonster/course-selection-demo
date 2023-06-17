package io.github.eastmonster.dbsim.controller;

import io.github.eastmonster.dbsim.entity.Instructor;
import io.github.eastmonster.dbsim.entity.Takes;
import io.github.eastmonster.dbsim.entity.Teaches;
import io.github.eastmonster.dbsim.mapper.InstructorMapper;
import io.github.eastmonster.dbsim.service.ProfileService;
import io.github.eastmonster.dbsim.util.Result;
import io.github.eastmonster.dbsim.util.dto.ChangeableProfileDTO;
import io.github.eastmonster.dbsim.util.dto.GradeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "教师接口")
@RestController
@RequestMapping("/instructor")
@CrossOrigin
public class InstructorController {
    @Autowired
    private InstructorMapper instructorMapper;

    @Autowired
    private ProfileService profileService;

    @GetMapping("/{instId}")
    @ApiOperation("获取教师信息")
    public Instructor getInstructor(@PathVariable Integer instId) {
        return instructorMapper.getInstructorById(instId);
    }

    @PatchMapping("/{instId}")
    @ApiOperation("修改教师信息")
    public Result modifyInstructor(@PathVariable Integer instId,
                                   @RequestBody ChangeableProfileDTO instructor) {
        return profileService.changePassword(instId, instructor);
    }

    @GetMapping("/{instId}/teaches")
    @ApiOperation("查询教师教授的课程")
    public List<Teaches> getTeachesById(@PathVariable Integer instId) {
        return instructorMapper.getTeachesById(instId);
    }

    @GetMapping("/grade/{courseId}")
    @ApiOperation("获取指定课程的成绩信息")
    public List<Takes> getScore(@PathVariable Integer courseId,
                                @RequestParam Integer secId,
                                @RequestParam String semester,
                                @RequestParam Integer year) {
        return instructorMapper.getSectionGrades(courseId, secId, semester, year);
    }

    @PatchMapping("/grade/{courseId}/student/{stuId}")
    @ApiOperation("更新指定课程中指定学生的成绩信息")
    public Result updateGrade(@PathVariable Integer courseId,
                              @PathVariable Integer stuId,
                              @RequestBody GradeDTO gradeInfo) {
        try {
            instructorMapper.updateGrade(courseId, stuId, gradeInfo);
            return Result.ok().setMessage("成绩更新成功");
        } catch (Exception e) {
            return Result.error().setMessage("成绩更新失败");
        }
    }
}
