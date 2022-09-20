<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
</head>
<body>
	<h1 align="center">
		<a href="home.kh">냉장고 Chef</a>
	</h1>
	<br><br><br><br>
	<div class="login-area">
		<form action="/user/login.kh" method="post">
			 <table align="center">
			 	<tr>
			 		<td><input type="text" name="user-id" placeholder="아이디"></td>
			 	</tr>
			 	<tr>
			 		<td><input type="password" name="user-pwd" placeholder="비밀번호"></td>
			 	</tr>
			 	<tr>
			 		<td>
			 		<button type="submit" class="btn_login" id="login">로그인</button>
			 		<button type="button" class="btn_join" id="join" onclick="joinUser()">회원가입</button>
			 		</td>
			 	</tr>
			 </table>
		</form>
	</div>
	<script>
		function joinUser() {
			location.href = "/user/userJoinView.kh";
		}
		
	</script>
	
</body>
</html>