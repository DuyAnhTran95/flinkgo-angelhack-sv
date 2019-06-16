package vn.crazyx.flinkgo.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class CORSFilter extends GenericFilterBean {

private final String allowedOrigin = "*";
	
	public CORSFilter() {
		super();
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		
		if (StringUtils.isEmpty(response.getHeader("Access-Control-Allow-Origin"))) {
			response.setHeader("Access-Control-Allow-Origin", allowedOrigin);
		}
		
		response.setHeader("Access-Control-Allow-Methods",
				"POST, PUT, GET, OPTIONS, DELETE");
		
		response.setHeader("Access-Control-Max-Age", "3600");
		
		response.setHeader("Access-Control-Allow-Headers", "cache-control, x-requested-with, Content-Type, Authorization, CLIENT_ID");

		if (!"OPTIONS".equals(request.getMethod())) {
			chain.doFilter(req, res);
		}
	}
}
