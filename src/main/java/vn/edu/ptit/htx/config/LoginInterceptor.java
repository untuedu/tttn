package vn.edu.ptit.htx.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        boolean publicPath = uri.equals("/login") || uri.startsWith("/css/") || uri.startsWith("/js/")
                || uri.startsWith("/webjars/") || uri.startsWith("/h2-console");
        if (publicPath || request.getSession().getAttribute("user") != null) {
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }
}
