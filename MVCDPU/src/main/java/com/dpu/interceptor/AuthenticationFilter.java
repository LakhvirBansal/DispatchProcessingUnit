package com.dpu.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "authFilter", urlPatterns={"/showcategory","/showdivision","/showdriver","/showuser","/showshipper","/showservice","/showterminal","/showtrailer","/showtruck","/showvendor","/showvmc","/home","/showissue","/showpo"})
public class AuthenticationFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("in init");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		System.out.println("in filter");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		res.setHeader("Cache-Control", "no-cache");
        res.setHeader("Cache-Control", "no-store");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);
        // We will check whether user is logged in or not.....HttpSession
        if(req.getSession().getAttribute("un") != null){
            chain.doFilter(request, response);
        }else{
            res.sendRedirect("login");
        }
	}

	@Override
	public void destroy() {
		System.out.println("in destroy");
	}

	
}
