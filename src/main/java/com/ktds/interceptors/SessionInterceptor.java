package com.ktds.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ktds.member.constants.Member;

public class SessionInterceptor extends HandlerInterceptorAdapter{
	
	private final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class); // 암기   현재 클래스와 .클래스 적어줌
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller)
			throws Exception {
		
		String contextPath = request.getContextPath();  // 프젝명을 일일이 붙일 필요 없이 만들어주는거 
		
		if( request.getSession().getAttribute(Member.USER) == null	) {
			// System.out.println(request.getRequestURI() + "안돼 돌아가");
			logger.info(request.getRequestURI()+"안돼, 돌아가");
			response.sendRedirect(contextPath + "/login");
			return false;   // 여기서 false를 해주는건 인터셉터 중간체크
		} 
		
		return true;
	}
	
}
