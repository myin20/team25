package com.myin.team25.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class LoginInterceptor extends HandlerInterceptorAdapter {
		
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,Object handler,ModelAndView modelAndView) throws Exception{
				
	 Object sMemberId = modelAndView.getModel().get("sMemberId");
	 Object sMemberMidx = modelAndView.getModel().get("sMemberMidx");
	 Object sMemberName = modelAndView.getModel().get("sMemberName");
	 
	 //modelAndView.getModelMap().get()�� ��ü ������
	 
	 if(sMemberMidx != null){
		 //��Ʈ�ѷ����� ���� �𵨰� ������ ���ǿ� ���
		 request.getSession().setAttribute("sMemberId", sMemberId);
		 request.getSession().setAttribute("sMemberMidx", sMemberMidx);
		 request.getSession().setAttribute("sMemberName", sMemberName);
		 
		// System.out.println(request.getParameter("useCookie"));
		
		 if (request.getParameter("useCookie").equals("on") ) {
		 
		//	 DateFormat df = new SimpleDateFormat("yyMMdd");   
		//	 String Cookiedate = df.format(new Date());	 
		//	 int sessionLimit = Integer.parseInt(Cookiedate);
		//	 System.out.println("sessionLimit:"+sessionLimit);
						 
			 Cookie loginCookie = new Cookie("loginCookie",request.getSession().getId());
			 loginCookie.setPath("/");
			 //7�ϵ��� Ŭ���̾�Ʈ ��Ű�����Ѵ�
			 loginCookie.setMaxAge(60*60*24*7);				 
			 response.addCookie(loginCookie);		
		 }
		}	
	}
	 @Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
			
		 HttpSession session = request.getSession();		 
		 
		 if(session.getAttribute("sMemberMidx") != null){
			 session.removeAttribute("sMemberId");
			 session.removeAttribute("sMemberMidx");
			 session.removeAttribute("sMemberName");
		 }
		 
		 return true;		
	 	}
	 }
