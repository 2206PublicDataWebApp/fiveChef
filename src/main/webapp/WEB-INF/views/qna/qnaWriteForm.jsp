<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
</head>
<body>
	<h1 align="center">게시글 등록 페이지</h1>
	<br><br>
	<form action="/qna/qnaRegist.kh" method="post" enctype="multipart/form-data">
		<table align="center" border="1">
			<tr>
				<td>제목</td>
				<td><input type="text" name="questionTitle"></td>
			</tr>
			<tr>
				<td>작성자</td>
				<td><input type="text" name="questionWriter" value="${loginUser.userId }" readonly></td>
			</tr>
			
			<tr>
				<td>내용</td>
				<td><textarea cols="50" rows="20" name="questionContents"></textarea></td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td><input type="file" name="uploadFile"></td>
			</tr>
			<tr>
				<td colspan="2">
				<input type="submit" value="등록">
				<input type="reset" value="취소">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>