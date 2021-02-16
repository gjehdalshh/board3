package com.koreait.board3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.koreait.board3.common.Utils;
import com.koreait.board3.model.BoardCmtSEL;
import com.koreait.board3.model.BoardPARAM;

public class BoardCmtDAO extends CommonDAO{
	
	public static List<BoardCmtSEL> selCmtList(BoardPARAM p) {
		List<BoardCmtSEL> list = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BoardCmtSEL cmt = null;
		
		
		String sql = " SELECT A.i_cmt, A.ctnt, A.r_dt, A.i_user, B.nm, "
				+ " DATE_FORMAT(A.r_dt, '%y-%m-%d %H:%i') "
				+ " FROM t_board_cmt A, t_user B"
				+ " WHERE A.i_user = B.i_user and i_board = ?"
				+ " order by i_cmt desc ";
				 
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, p.getI_board());
			rs = ps.executeQuery();
			
			while(rs.next()) {
				cmt = new BoardCmtSEL();
				list.add(cmt);
				cmt.setCtnt(rs.getNString("ctnt"));
				cmt.setUser_nm(rs.getString("nm"));
				cmt.setR_dt(rs.getString("r_dt"));
				cmt.setI_cmt(rs.getInt("i_cmt"));
				cmt.setI_user(rs.getInt("i_user"));
				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		return list;
		
	}


}





