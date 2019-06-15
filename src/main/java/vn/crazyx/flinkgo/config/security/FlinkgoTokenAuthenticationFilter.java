package vn.crazyx.flinkgo.config.security;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class FlinkgoTokenAuthenticationFilter extends GenericFilterBean {
    private static final Logger log = Logger.getLogger(FlinkgoTokenAuthenticationFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        
        String authorizeToken = req.getHeader("Authorization");

        
        try {
            if (authorizeToken == null) {
                throw new Exception();
            }
            
            String tokenType = authorizeToken.substring(0, authorizeToken.indexOf(' '));
            String token = authorizeToken.substring(authorizeToken.indexOf(' ') + 1);
            
            log.info(token);
            if (tokenType.equals("Bearer")) {
                               
                FlinkgoAuthenticationToken auth = 
                        new FlinkgoAuthenticationToken(null, token);

                
                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        } catch (Exception e) {

        } finally {
            chain.doFilter(request, response);
        }
    }

}
