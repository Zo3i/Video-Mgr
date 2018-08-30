package com.jo.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor{
	
	private List<String> unCheckUrls;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		String requestUrl = request.getRequestURI();
		//检查此路由是否被拦截
		if (unCheckUrls.contains(requestUrl)) {
			return true;
		}
		if (null == request.getSession().getAttribute("sessionUser")) {
			response.sendRedirect(request.getContextPath() + "/users/login.action");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	public List<String> getUnCheckUrls() {
		return unCheckUrls;
	}

	public void setUnCheckUrls(List<String> unCheckUrls) {
		this.unCheckUrls = unCheckUrls;
	}
}
