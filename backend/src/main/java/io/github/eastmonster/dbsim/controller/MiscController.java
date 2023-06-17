package io.github.eastmonster.dbsim.controller;

import io.github.eastmonster.dbsim.util.Misc;
import io.github.eastmonster.dbsim.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "其他接口")
@RestController
@RequestMapping("/misc")
@CrossOrigin
public class MiscController {

    @GetMapping("")
    @ApiOperation("获得学期和选课开关信息")
    public Misc getMisc() {
        return Misc.getInstance();
    }

    @PutMapping("")
    @ApiOperation("切换选课开关")
    public Result changeSelectionStatus() {
        Misc.getInstance().setIsSelectionEnabled(!Misc.getInstance().isIsSelectionEnabled());
        return Result.ok().setMessage("选课状态切换成功").addData("isSelectionEnabled", Misc.getInstance().isIsSelectionEnabled());
    }
}
