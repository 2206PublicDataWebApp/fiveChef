<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../main/admin_navs.jsp"></jsp:include>
<meta charset="UTF-8">
<title>공지사항 상세 조회</title>
<script src="/resources/js/jquery-3.6.1.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="container">
<div class="table-responsive">
	<table align="center" width="500" border="1" class="table table-bordered">
		<tr>
			<td align="center" width="150">제목</td>
			<td>${community.communityTitle }</td>
		</tr>
		<tr>
			<td align="center" width="150">게시판</td>
			<td>
			<c:if test="${community.cBoardCode eq 'free'}">
				자유게시판
			</c:if>
			<c:if test="${community.cBoardCode eq 'sale'}">
				할인정보게시판
			</c:if>
			</td>
		</tr>
		<tr>
			<td align="center" width="150">작성자</td>
			<td>${community.communityWriter }</td>
		</tr>
		<tr>
			<td align="center" width="150">작성일</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd" value="${community.cEnrollDate }"/></td>
		</tr>
		<tr>
			<td align="center" width="150">조회수</td>
			<td>${community.boardCount }</td>
		</tr>
		<tr height="300">
			<td align="center" width="150">내용</td>
			<td>${community.communityContents }
			</td>
		</tr>
		<tr>
			<td align="center" width="150">첨부파일</td>
			<td>
		 		<img alt="본문이미지" src="/resources/cuploadFiles/${community.communityFileRename }" width="300" height="300">
			</td>
		</tr>
		<c:if test="${loginAdmin.adminName eq '관리자'}"> <!-- 관리자만 수정, 삭제 가능 -->
		<tr>
			<td colspan="2" align="center">
				<button onclick="location.href='/postmanage/modifyView.kh?noticeNo=${notice.noticeNo }&page=${page}';" class="btn btn-info">수정</button>
				<button onclick="noticeRemove(${page});" class="btn btn-danger">삭제</button>
		</tr>
		</c:if>
	</table>
	<script>
		function noticeRemove(page) {
			event.preventDefault(); // 하이퍼링크 이동 방지
			if(confirm("해당 게시글을 삭제하시겠습니까?")) {
				location.href="/postmanage/remove.kh?page="+page;
			}
		}
	</script>
	</div>
	</div>
</body>
</html>