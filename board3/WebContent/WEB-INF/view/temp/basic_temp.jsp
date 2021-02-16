<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
<link rel="stylesheet" href="/res/css/common.css?ver=14">
<link rel="stylesheet" href="/res/css/board.css?ver=14">
<c:forEach items="${jsList}" var="item">
	<script defer src="/res/js/${item}.js?ver=12"></script>
</c:forEach>
<script defer src="/res/js/common.js"></script>  
</head>
<body>
	<div id="container">
		<header>
			<ul>	<!-- 위에서 아래로 -->
				<li>${loginUser.nm}님 환영합니다.</li>
				<li><a href="/logout">Logout</a></li>
				<li><a href="/board/list?typ=1">게임</a></li>
				<li><a href="/board/list?typ=2">스포츠</a></li>
				<li><a href="/board/list?typ=3">연예/방송</a></li>
				
			</ul>
		</header>
		<section>
			<jsp:include page="${page}"/>
		</section>
		<footer>
			
		</footer>
	</div>
</body>
</html>

