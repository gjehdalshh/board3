package com.koreait.board3.board.cmt;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;
import com.koreait.board3.db.BoardCmtDAO;
import com.koreait.board3.db.BoardDAO;
import com.koreait.board3.db.SQLInterUpdate;
import com.koreait.board3.model.BoardCmtSEL;
import com.koreait.board3.model.BoardPARAM;

public class BoardCmtService {
	public static String reg(HttpServletRequest request) {
		int i_board = Utils.getIntParam(request, "i_board");
		System.out.println("asd"+i_board);
		int i_user = SecurityUtils.getLoginUserPk(request);
		String ctnt = request.getParameter("ctnt");
		
		String sql = " INSERT INTO t_board_cmt "
				+ " (i_board, i_user, ctnt) "
				+ " VALUES "
				+ " (?, ?, ?) ";
		
		int result = BoardCmtDAO.executeUpdate(sql, new SQLInterUpdate() {
			
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, i_board);
				ps.setInt(2, i_user);
				ps.setNString(3, ctnt);
			}
		});
		
		String url = "../detail?i_board="+i_board;
		
		if(result != 1) {
			url += "&err=1";
		}
		
		return url;
	}
	
	public static List<BoardCmtSEL> selBoardCmtList(BoardPARAM p) {
		return BoardCmtDAO.selCmtList(p);
	}

	public static String del(HttpServletRequest request) {
		
		int i_board = Utils.getIntParam(request, "i_board");
		int i_cmt = Utils.getIntParam(request, "i_cmt");
		
		String sql = "DELETE FROM t_board_cmt WHERE i_cmt = ?"
				+ " and i_user = ?";
		
		
		 BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
			
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, i_cmt);
				ps.setInt(2, SecurityUtils.getLoginUserPk(request));
			}
		});
		 return "/board/detail?i_board="+i_board;
	}

	public static String mod(HttpServletRequest request) {
		int i_board = Utils.getIntParam(request, "i_board");
		int i_cmt = Utils.getIntParam(request, "i_cmt");
		int i_user = SecurityUtils.getLoginUserPk(request);
		String ctnt = request.getParameter("ctnt");
		
		String sql = " UPDATE t_board_cmt "
				+ " SET ctnt = ?"
				+ " WHERE i_cmt = ? AND i_user = ? ";
		
		int result = BoardCmtDAO.executeUpdate(sql, new SQLInterUpdate() {
			
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setString(1, ctnt);
				ps.setInt(2, i_cmt);
				ps.setInt(3, i_user);
			}
		});
		
		String url = "../detail?i_board="+i_board;
		
		if(result != 1) {
			url += "&err=2";
		}
		
		return url;
	}
}







