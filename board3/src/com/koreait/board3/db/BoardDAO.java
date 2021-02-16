package com.koreait.board3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.board3.common.Utils;
import com.koreait.board3.model.BoardPARAM;
import com.koreait.board3.model.BoardSEL;

public class BoardDAO extends CommonDAO{
	
	public static List<BoardSEL> selBoardList(BoardPARAM p) {
		List<BoardSEL> list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		String sql = " SELECT A.i_board, A.seq, A.typ, A.title, A.r_dt, A.hits, A.i_user, B.nm "
			+" FROM t_board A "
			+" INNER JOIN t_user B "
			+" ON A.i_user = B.i_user "
			+" WHERE A.typ = ? "
			+" ORDER BY A.seq DESC ";
	
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, p.getTyp());
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				BoardSEL boardsel = new BoardSEL();
				list.add(boardsel);
			
				boardsel.setI_board(rs.getInt("i_board"));
				boardsel.setSeq(rs.getInt("seq"));
				boardsel.setTitle(rs.getString("title"));
				boardsel.setR_dt(rs.getString("r_dt"));
				boardsel.setHits(rs.getInt("hits"));
				boardsel.setI_user(rs.getInt("i_user"));
				boardsel.setNm(rs.getString("nm"));
	
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
	
		return list;
	}
	
	public static BoardSEL selBoard(BoardPARAM p) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BoardSEL sel = null;
		
		String sql = " SELECT"
				+ " A.seq, A.typ, A.title, A.ctnt, A.r_dt, A.hits"
				+ " , A.i_user, B.nm"
				+ " , CASE WHEN C.i_board IS NULL THEN 0 ELSE 1 END AS is_favorite"
				+ " FROM t_board A"
				+ " INNER JOIN t_user B"
				+ " ON A.i_user = B.i_user"
				+ " LEFT JOIN t_board_favorite C"
				+ " ON A.i_board = C.i_board"
				+ " AND C.i_user = ?" 
				+ " WHERE A.i_board = ?";
		
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, p.getI_user());
			ps.setInt(2, p.getI_board());
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				sel = new BoardSEL();
				sel.setI_board(p.getI_board());
				sel.setSeq(rs.getInt("seq"));
				sel.setTyp(rs.getInt("typ"));
				sel.setTitle(rs.getString("title"));
				sel.setCtnt(rs.getString("ctnt"));
				sel.setR_dt(rs.getString("r_dt"));
				sel.setHits(rs.getInt("hits"));
				sel.setI_user(rs.getInt("i_user"));
				sel.setNm(rs.getString("nm"));
				sel.setIs_favorite(rs.getInt("is_favorite"));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		
		return sel;
	}
	
}
