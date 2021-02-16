package com.koreait.board3.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.koreait.board3.common.SecurityUtils;
import com.koreait.board3.common.Utils;
import com.koreait.board3.db.SQLInterUpdate;
import com.koreait.board3.db.UserDAO;
import com.koreait.board3.model.UserModel;

public class UserService {
	public static int join(HttpServletRequest request) {
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		String nm = request.getParameter("nm");
		int gender = Utils.getIntParam(request, "gender");
		String ph = request.getParameter("ph");
		
		
		String salt = SecurityUtils.getSalt();
		String encryptPw = SecurityUtils.getSecurePassword(user_pw, salt);
		
		
		System.out.println("encryptPw : "+ encryptPw);
		
		UserModel p = new UserModel();
		p.setUser_id(user_id);
		p.setUser_pw(encryptPw);
		p.setSalt(salt);
		p.setNm(nm);
		p.setGender(gender);
		p.setPh(ph);
		
		String sql = " INSERT INTO t_user "
				+ " (user_id, user_pw, salt, nm, gender, ph) "
				+ " VALUES "
				+ " (?, ?, ?, ?, ?, ?) ";
				
		return UserDAO.executeUpdate(sql, new SQLInterUpdate() {
			
			@Override
			public void proc(PreparedStatement ps) throws SQLException {
				ps.setNString(1, p.getUser_id());
				ps.setNString(2, p.getUser_pw());
				ps.setNString(3, p.getSalt());
				ps.setNString(4, p.getNm());
				ps.setInt(5, p.getGender());
				ps.setNString(6, p.getPh());
				System.out.println(p.getNm());
			}
		});
		
	}
	
	// 0: 에러발생, 1: 로그인 성공, 2: 아이디 없음, 3: 비밀번호 틀림
	public static int login(HttpServletRequest request) {
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		
		UserModel p = new UserModel();
		p.setUser_id(user_id);
		p.setUser_pw(user_pw);
		
		UserModel m = UserDAO.selUser(p);
		if(m == null) {
			return 2;
		}
		
		String encryptPw = SecurityUtils.getSecurePassword(user_pw, m.getSalt());
		if(encryptPw.equals(m.getUser_pw())) { // 로그인 성공
			HttpSession hs = request.getSession();
			hs.setAttribute("loginUser", m);
			m.setSalt(null);
			m.setUser_pw(null);
			return 1;
		}
		return 3;
	}
	
}

class MySqlInter implements SQLInterUpdate {

	@Override
	public void proc(PreparedStatement ps) throws SQLException {
		
	}
	
}