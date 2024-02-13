package goorm.woowa.webide.member.security.filter;

import com.google.gson.Gson;
import goorm.woowa.webide.member.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

// 모든 요청에 동작하는 필터
@Slf4j
public class JWTCheckFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String path = request.getRequestURI();

        if (path.startsWith("/api/oauth/member")) {
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String accessToken = request.getHeader("Authorization").substring(7);

            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("jwt check error={}", e.getMessage());

            Gson errorResponse = new Gson();
            String msg = errorResponse.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.println(msg);
            writer.close();
        }

    }
}