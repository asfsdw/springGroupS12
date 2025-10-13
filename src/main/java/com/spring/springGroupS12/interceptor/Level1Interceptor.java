package com.spring.springGroupS12.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Level1Interceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		int level = session.getAttribute("sLevel") == null ? 99 : (int)session.getAttribute("sLevel");
		if(level > 1) {
			if(level > 4) request.getRequestDispatcher("/Message/loginNo").forward(request, response);
			else request.getRequestDispatcher("/Message/levelNo").forward(request, response);
			return false;
		}
		else return true;
	}
}
