package vn.crazyx.flinkgo.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.crazyx.flinkgo.config.ResponseFail;

public class FlinkgoAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        // TODO Auto-generated method stub
        authException.printStackTrace();
        
        ResponseFail res = new ResponseFail(HttpStatus.UNAUTHORIZED.value(), authException);
        response.setStatus(res.getStatus());
        response.setContentType("application/json;charset=UTF-8");
        
        response.getWriter().write(new ObjectMapper().writeValueAsString(res));
    }

}
