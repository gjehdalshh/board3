package com.koreait.board3.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;
import com.koreait.board3.db.UserDAO;


@WebServlet("/login")
public class LoginSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!SecurityUtils.isLogout(request)) {
			response.sendRedirect("/main");
			return;
		}
		
		Utils.forward("로그인", "user/login", request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int result = UserService.login(request);
		if(result == 1) { // 로그인 성공
			response.sendRedirect("/main");
			return;
		}
		switch(result) {
		case 2:
			request.setAttribute("msg", "아이디를 확인해 주세요.");
			break;
		case 3:
			request.setAttribute("msg", "비밀번호를 확인해 주세요.");
			break;
		}
		String user_id = request.getParameter("user_id");
		request.setAttribute("user_id", user_id);
		doGet(request, response);
		System.out.println("result : "+result);
	}
}
