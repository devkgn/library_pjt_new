package com.goodee.library.member.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class MemberLoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		if(session != null) {
			Object object = session.getAttribute("loginedMember");
			if(object != null)
				return true;
		}
		response.sendRedirect(request.getContextPath()+"/login");
		return false;		
	}	

}