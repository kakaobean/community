package com.ktds.interceptors;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
   int count = 0;
   
   private Map<String, Integer> failIdMap;
   
   public LoginInterceptor() {
	   failIdMap = new HashMap<String, Integer>();
   }
   
   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
         throws Exception {

     if ( request.getMethod().equalsIgnoreCase("post")) {
    	 String userId = request.getParameter("email");
    	 String password = request.getParameter("password");
    	 
    	 if( userId.equals("admin")) {
    		 if( !failIdMap.containsKey(userId)) {
    			 failIdMap.put(userId, 0);
    		 }
    		 if(!password.equals("1234")) {
    			 int failCount = failIdMap.get(userId);
    		 	 failIdMap.put(userId, ++failCount);
    		 }
    	 }
    	 
//    	 if(failIdMap.get(userId) > 3) {
//    		 RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/error/500.jsp/");
//    		 rd.forward(request, response);
//    		 return false;
//    	 }
     }
	   return true;

   }

}