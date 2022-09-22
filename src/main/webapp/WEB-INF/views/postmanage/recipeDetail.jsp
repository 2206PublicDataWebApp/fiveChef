<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">

</style>
</head>
<body>
<%-- 	<h1 align="center">${recipe.recipeNo }번게시글상세보기</h1> --%>
<!-- 	<br> -->
<!-- 	<br> -->
	<table align="center" width="1200" border="1">
		<tr>
			<td>제목</td>
			<td>${recipe.recipeTitle }</td>
			<td rowspan="10" width=300><img alt="본문이미지"
				src="/resources/ruploadFiles/${recipe.thumbnailRename }" width=300
				height=300></td>
		</tr>
		<tr>
			<td>작성자</td>
			<td>${recipe.userId }</td>
			
		</tr>
		
		<tr>
			<td>작성날짜</td>
			<td>${recipe.rCreateDate }</td>
			
		</tr>
		<tr>
			<td>조회수</td>
			<td colspan="">${recipe.recipeCount }</td>
		</tr>
		<tr>
			<td>좋아요수</td>
			<td colspan="">${recipe.recipeLikeCount }</td>
			
		</tr>
		<tr>
			<td>카테고리</td>
			<td colspan="">${recipe.typeCategory }</td>
		</tr>
		<tr>
			<td>방법</td>
			<td colspan="">${recipe.wayCategory }</td>
		</tr>
		<tr>
			<td>난이도</td>
			<td colspan="">${recipe.recipeLevel }</td>
		</tr>
		<tr>
			<td>조리시간</td>
			<td colspan="">${recipe.recipeTime }</td>
		</tr>
		<tr>
			<td>인원</td>
			<td colspan="">${recipe.recipePerson }</td>
		</tr>
		<tr height="300">
			<td>요리소개</td>
			<td colspan="2">${recipe.recipeIntro }</td>
			
		</tr>
		<tr>
			<td>${bundle }재료</td>
			<td colspan="2"><c:forEach items="${iList }" var="ingradient" varStatus="i">
			<p>
			<input type="text" value="${ingradient.largeCatName }" readonly="readonly">
			<input type="text" value="${ingradient.smallCatName }" readonly="readonly">
			<input type="text" value="${ingradient.ingAmount }" readonly="readonly">
			</p>
			</c:forEach></td>
		</tr>
		<tr>
			<td colspan="3">요리순서</td>
			
		</tr>
		<c:forEach items="${oList }" var="order" varStatus="i">
		<tr>
			<td>STEP ${i.count }</td>
			<td >${order.recipeContents }</td>
			<td ><img alt="본문이미지"
				src="/resources/ruploadFiles/${order.orderPhotoRename }" width=300
				height=300></td>
		</tr>
		</c:forEach>
		<tr height="300">
			<td>완성사진</td>
			<td colspan="2">
			<c:forEach items="${cList }" var="comPhoto" varStatus="i">
			<img alt="본문이미지"
				src="/resources/ruploadFiles/${comPhoto.comPhotoRename }" width=200
				height=200>
			</c:forEach>	
				</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<%-- ${sessionScope.loginUser.memberId } --%> <c:if
					test="${board.boardWriter eq sessionScope.loginUser.memberName }"></c:if>
				<a
				href="/board/modifyView.kh?boardNo=${board.boardNo }&page=${page}">수정페이지로</a>
				<a href="#" onclick="boardRemove(${page})">삭제하기</a> <a
				href="/recipe/${urlVal }.kh?page=${page }&category=${listValue }">리스트</a>
			</td>
		</tr>
	</table>
</body>
</html>