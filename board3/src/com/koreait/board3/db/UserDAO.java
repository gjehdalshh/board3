package com.koreait.board3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.model.UserModel;

public class UserDAO extends CommonDAO {
	
	public static UserModel selUser(UserModel p) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " SELECT i_user, nm, user_pw, salt "
				+ " FROM t_user "
				+ " WHERE user_id = ? ";
		
		try {
			con = DbUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setNString(1, p.getUser_id());
			rs = ps.executeQuery();
			
			if(rs.next()) {
				UserModel m = new UserModel();
				m.setI_user(rs.getInt("i_user"));
				m.setNm(rs.getString("nm"));
				m.setUser_pw(rs.getString("user_pw"));
				m.setSalt(rs.getString("salt"));
				return m;
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			DbUtils.close(con, ps, rs);
		}
		
		return null;
	}
}
