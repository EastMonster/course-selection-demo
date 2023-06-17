package io.github.eastmonster.dbsim.controller;

import io.github.eastmonster.dbsim.service.LoginService;
import io.github.eastmonster.dbsim.util.JwtUtil;
import io.github.eastmonster.dbsim.util.Result;
import io.github.eastmonster.dbsim.util.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "登录接口")
@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("") // 一根斜杠浪费了我四个小时的时间
    @ApiOperation("用户登录")
    public Result login(@RequestBody UserDTO user) {
        return loginService.login(user.getId(), user.getPassword(), user.getIsStudent());
    }

    @GetMapping("/check")
    @ApiOperation("登录验证")
    public Claims checkToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return JwtUtil.getClaimsByToken(token);
    }
}
