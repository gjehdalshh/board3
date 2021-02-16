package com.koreait.board3.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;
import com.koreait.board3.db.BoardDAO;
import com.koreait.board3.model.BoardSEL;

@WebServlet("/board/list")
public class BoardListSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(SecurityUtils.isLogout(request)) {
			response.sendRedirect("/login");
			return;
		}
		
		int typ = Utils.getIntParam(request, "typ");
		request.setAttribute("typ", typ);
//		String[] jsList = {"board"};
//		request.setAttribute("jsList", jsList);
		request.setAttribute("jsList", new String[]{"board"});
		BoardService.selBoardList(request);
		Utils.forwardTemp("리스트", "temp/basic_temp", "board/bList", request, response);
		
		
	}

}
