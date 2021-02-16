package com.koreait.board3.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;
import com.koreait.board3.model.BoardPARAM;
import com.koreait.board3.model.BoardSEL;


@WebServlet("/board/regmod")
public class BoardRegmodSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(SecurityUtils.isLogout(request)) {
			response.sendRedirect("/login");
			return;
		}
		int i_board = Utils.getIntParam(request, "i_board");
		
		if(i_board > 0) {
			request.setAttribute("data", BoardService.detail(request));
		}
		/*
		int typ = Utils.getIntParam(request, "typ");
		request.setAttribute("typ", typ);
		*/
		request.setAttribute("jsList", new String[]{"board"});
		Utils.forwardTemp("등록/수정", "temp/basic_temp", "board/bRegmod", request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.sendRedirect(BoardService.regMod(request));
		
//		int i_board = Utils.getIntParam(request, "i_board");
//		
//		int result = BoardService.regMod(request);
//		
//		if(i_board > 0) { // 수정 > 디테일 페이지
//			response.sendRedirect("detail?i_board="+i_board);
//			
//		} else { // 등록 리스트 페이지
//			String typ = request.getParameter("typ");
//			response.sendRedirect("list?typ="+typ);
//		}
	}

}
