<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<script defer src="/res/js/join.js"></script>
</head>
<body>
	<div id="centerContainer">
	
		<div>
			<form id="frm" action="/join" method="post">
				<div><input type="text" name="user_id" placeholder="id" required></div>
				<div><input type="password" name="user_pw" placeholder="password" required></div>
				<div><input type="password" name="user_pw_chk" placeholder="password check" required></div>
				<div><input type="text" name="nm" placeholder="Your Name" required></div>
				<div>
					Gender : 
					<label>Woman<input type="radio" name="gender" value="0" checked></label>
					<label>Man<input type="radio" name="gender" value="1"></label>
				</div>
				<div>
					Phone : <input type="text" name="ph" placeholder="Your Phone Number">
				</div>
				<div><input type="submit" value="LOGIN"></div>
			</form>
			<a href="/login">Go to Login</a>
		</div>
	</div>
	
</body>
</html>


