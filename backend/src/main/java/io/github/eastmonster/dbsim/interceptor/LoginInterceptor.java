package io.github.eastmonster.dbsim.interceptor;

import io.github.eastmonster.dbsim.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger log2 = Logger.getLogger("LoginInterceptor");
    // 拦截登录以外的其他请求，进行 Token 验证
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestToken = request.getHeader("Authorization");
        if (requestToken != null && !requestToken.isEmpty()) {
            Claims claims = JwtUtil.getClaimsByToken(requestToken);
            if (claims != null) {
                log2.log(Level.INFO, "Token 验证成功");
            } else {
                log2.log(Level.INFO, "Token 验证失败");
            }
            return claims != null;
        }
        log2.log(Level.INFO, "Token 验证失败");
        return false;
    }
}
