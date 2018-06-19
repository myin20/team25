package com.myin.team25.interceptor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.myin.team25.domain.MemberVo;
import com.myin.team25.service.MemberService;


public class AuthInterceptor extends HandlerInterceptorAdapter {
		
	@Autowired
	MemberService ms;
	
	 @Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
			
		 HttpSession session = request.getSession();		
		 
		 //�α��� �������� ���
		 if(session.getAttribute("sMemberMidx") == null){
			
			//�̵��� �ּҸ� ��´�
			 saveDest(request);
			//���� pc�� ����� ��Ű ������ ������
			 Cookie loginCookie = WebUtils.getCookie(request,"loginCookie"); 
			 			 
			 //����� ��Ű�� �ڵ� �α��������� ������
			if (loginCookie  != null){ 
		
			//��Ű�� ����� Ű������ ���� Ű�� DB�� �ִ��� üũ�ؼ� ������ �� ȸ�������� ��´� 	
			 MemberVo mv =  ms.checkAutoLogin(loginCookie.getValue());
			 if (mv != null) {
				 
				 //�ڵ��α��� ����� �����ϸ� ���ǿ� ���
				 request.getSession().setAttribute("sMemberId", mv.getMemberId());
				 request.getSession().setAttribute("sMemberMidx", mv.getMemberMidx());
				 request.getSession().setAttribute("sMemberName", mv.getMemberName());	
				
				 Cookie loginCookie2 = new Cookie("loginCookie",loginCookie.getValue());
				 loginCookie2.setPath("/");
				 loginCookie2.setMaxAge(60*60*24*7);
				 response.addCookie(loginCookie2);	
				 
				 // DB ���̺��� ��¥ ����
				 Calendar cal = Calendar.getInstance();
				 cal.setTime(new Date());
				 cal.add(Calendar.DATE, 7);
				 DateFormat df1 = new SimpleDateFormat("yy-MM-dd");   
				 String sessionLimit = df1.format(cal.getTime());
				 
				 ms.keeplogin(mv.getMemberMidx(), loginCookie.getValue(), sessionLimit);				
			 
			 }else {
				 response.sendRedirect(request.getContextPath()+"/MemberLoginController");
				 return false;
			 }
			 
			}else {			
			 response.sendRedirect(request.getContextPath()+"/MemberLoginController");
			 return false;
			}
			 
		 }
		 
		 return true;		
	 	}
	
	 private void saveDest(HttpServletRequest req){
	
		 String uri = req.getRequestURI();		 
		 String query = req.getQueryString();
		
		 
		 if (query ==null || query.equals("null")){
			 query = "";
		 }else {
			 query = "?" + query;
		 }
		 
		 if (req.getMethod().equals("GET")){
			 req.getSession().setAttribute("dest", uri+query);
			
		//	 System.out.println("�̵��ؾ��� ������url:"+uri+query);
		 }		 
	 }	
}
