<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
	<h1 align="center">공지사항 목록</h1>
	<br><br>
	<table align="center" border="1">
	
	<tr>
			<td colspan="5" align="center">
				<form action="/notice/search.kh" method="get">
					<select name="searchCondition" class="btn btn-dark">
						<option value="all" <c:if test="${searchCondition eq 'all' }">selected</c:if>>전체</option>
						<option value="title" <c:if test="${searchCondition eq 'title' }">selected</c:if>>제목</option>
						<option value="contents" <c:if test="${searchCondition eq 'contents' }">selected</c:if>>내용</option>
					</select>
					<input type="text" name="searchValue" value="${searchValue}">
					<input type="submit" value="검색" class="btn btn-dark">
				</form>
			</td>
			<td>
				<button onclick="location.href='/notice/writeView.kh';" class="btn btn-info">공지사항 등록</button>
			</td>
		</tr>
		<tr align="center">
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>날짜</th>
			<th>조회수</th>
			<th>첨부파일</th>
		</tr>
	<c:if test="${!empty nList }">
	<c:forEach items="${nList }" var="notice" varStatus="i">
		<tr align="center">
			<td>${i.count }</td>
			<td><a href="/notice/detail.kh?noticeNo=${notice.noticeNo }&page=${currentPage }">${notice.noticeTitle }</a></td>
			<td>${notice.noticeWriter }</td>
			<td>${notice.nCreateDate }</td>
			<td>${notice.noticeCount }</td>
			<td>
				<c:if test="${!empty notice.noticeFilename }">
					O
				</c:if>
				<c:if test="${empty notice.noticeFilename }">
					X
				</c:if>
			</td>
		</tr>
		</c:forEach>
		<tr align="center" height="20">
			<td colspan="6">
				<c:if test="${currentPage != 1 }">
					<a href="/notice/${urlVal }.kh?page=${currentPage - 1 }&searchCondition=${searchCondition}&searchValue=${searchValue}" class="btn btn-primary"><</a>
				</c:if>
				<c:forEach var="p" begin="${startNavi }" end="${endNavi }">
					<c:if test="${currentPage eq p }">
						<b class="btn btn-primary">${p }</b>
					</c:if>
					<c:if test="${currentPage ne p }">
						<a href="/notice/${urlVal }.kh?page=${p }&searchCondition=${searchCondition}&searchValue=${searchValue}" class="btn btn-light">${p }</a>
					</c:if>
				</c:forEach>
				<c:if test="${maxPage > currentPage }">
					<a href="/notice/${urlVal }.kh?page=${currentPage + 1 }&searchCondition=${searchCondition}&searchValue=${searchValue}" class="btn btn-primary">></a>
				</c:if>
			</td>
		</tr>
		</c:if>
		<c:if test="${empty nList }">
			<tr>
				<td colspan="6" align="center"><b>데이터가 존재하지 않습니다.</b></td>
			</tr>
		</c:if>
		
	</table>
</body>
</html>