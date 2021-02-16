package com.koreait.board3.board;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.koreait.board3.board.cmt.BoardCmtService;
import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;
import com.koreait.board3.db.BoardCmtDAO;
import com.koreait.board3.db.BoardDAO;
import com.koreait.board3.db.SQLInterUpdate;
import com.koreait.board3.model.BoardCmtSEL;
import com.koreait.board3.model.BoardModel;
import com.koreait.board3.model.BoardPARAM;
import com.koreait.board3.model.BoardSEL;

public class BoardService {
	public static String regMod(HttpServletRequest request) {
		
		int i_board = Utils.getIntParam(request, "i_board");
		int typ = Utils.getIntParam(request, "typ");
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		int i_user = SecurityUtils.getLoginUserPk(request);
		
		if(i_board == 0) { // 등록
			String sql = " INSERT INTO t_board "
					+ " (typ, seq, title, ctnt, i_user) "
					+ " SELECT ?, ifnull(max(seq), 0) + 1, ?, ?, ? "
					+ " FROM t_board "
					+ " WHERE typ = ? ";
			
			BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
				
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setInt(1, typ);
					ps.setString(2, title);
					ps.setString(3, ctnt);
					ps.setInt(4, i_user);
					ps.setInt(5, typ);
				}
			});
			return "list?typ="+typ;
			
		} else { // 수정
			String sql = " UPDATE t_board SET title = ?"
					+ " , ctnt = ? "
					+ " WHERE i_board = ? "
					+ " AND i_user = ? ";
			
				BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
				
				@Override
				public void proc(PreparedStatement ps) throws SQLException {
					ps.setString(1, title);
					ps.setString(2, ctnt);
					ps.setInt(3, i_board);
					ps.setInt(4, SecurityUtils.getLoginUserPk(request));
				}
			});
			return "detail?i_board="+i_board;
		}
	}
	
	
	public static void selBoardList(HttpServletRequest request) {
		int typ = Utils.getIntParam(request, "typ");
		BoardPARAM p = new BoardPARAM();
		p.setTyp(typ);
		
		request.setAttribute("list", BoardDAO.selBoardList(p));
		
	}
	
	public static BoardSEL detail(HttpServletRequest request) {
		int i_board = Utils.getIntParam(request, "i_board");
		if(i_board == 0) {
			return null;
		}
		
		BoardPARAM p = new BoardPARAM();
		p.setI_board(i_board);
		p.setI_user(SecurityUtils.getLoginUserPk(request));
		
		request.setAttribute("cmtList", BoardCmtService.selBoardCmtList(p));
		
		return BoardDAO.selBoard(p);
	}
	
public static int delBoard(HttpServletRequest request) {
		
		int i_board = Utils.getIntParam(request, "i_board");
		int i_user = SecurityUtils.getLoginUserPk(request);
	
		BoardPARAM p = new BoardPARAM();
		String sql = "DELETE FROM t_board WHERE i_board=? AND i_user = ? "; 
		// pk값을 가진 유저만 삭제가능
		return BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
		
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, i_board);
				ps.setInt(2, i_user);
			}
		});
	}
	
	public static BoardSEL selBoard(BoardPARAM p) {
	
		return BoardDAO.selBoard(p);
	
	}
	
	public static String ajaxFavorite(HttpServletRequest request) {
		int result = 0;
		int state = Utils.getIntParam(request, "state");
		int i_board = Utils.getIntParam(request, "i_board");
		int i_user = SecurityUtils.getLoginUserPk(request);
		
		String sql = null;
		
		switch(state) {
		case 0: // 좋아요 해제
			sql = " DELETE FROM t_board_favorite "
					+ " WHERE i_board = ? "
					+ " AND i_user = ? ";
			break;
		case 1: // 좋아요 처리
			sql = " INSERT INTO t_board_favorite "
					+ "(i_board, i_user)"
					+ " VALUES "
					+ " (?, ?) ";
			break;
		}
		
		result = BoardDAO.executeUpdate(sql, new SQLInterUpdate() {
			
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setInt(1, i_board);
				ps.setInt(2, i_user);
			}
		} );
		
		return String.format("{\"result\":%d}", result);
	}
}







