<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/main/user_navs.jsp"></jsp:include>
<script src="../../../resources/js/jquery-3.6.1.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>레시피 리스트</title>
<link rel="stylesheet" href="../resources/rcss/listView.css">
<script type="module" src="../resources/multibox/multi-checkbox.js"></script>
<style type="text/css">
.wrap {
border: 1px solid #ccc;
margin-top: 0;
padding-left: 1%;
background-color: #fff;
}
div{
	/* border: 1px solid #ccc; */
}
.searchzone > div>div{
	padding-top: 1%;
	padding-bottom: 1%;
	
}
#active {
	padding: 1%;
	color: #fff;
	background-color: rgb(209, 24, 79);
	border-radius: 15%;
	
}

.searchzone a{
	padding: 1%;
	color: #000;
	
}
.iaddspec{
	padding: 1%;
	color: #000;	
}

.iaddspec:hover{
	color: rgb(209, 24, 79);	
}

.searchzone > div>div>p>a:hover{
	color: rgb(209, 24, 79);
	text-decoration: none;
}
.cg{
	border-right: 1px solid #ccc;
	margin-right: 3px;
	text-align: center;
	display: flex;
	align-items: center;
	justify-content : center;
}

.sel{
	
	vertical-align: middle;
}
.ing{
	margin: 0;
	padding-left: 2px;
	padding-right: 2px;
}
.ing > select{
	margin: 0;
	width: 100%;

}
.iline {
	border-bottom: 1px solid #ccc;
}


.page_bar {
	position:relative;
	float:left;
	margin: 0;
	/* border: 1px solid #ccc; */
}
#bar-bar {
	visibility: hidden;
	
	padding-bottom: 1%;

}
.r{
	position: relative;
	margin: 10%;
	
}
.r .btn{
	justify-content: right;
	text-align: right;
	display: block;
	float: left;
}
.btn-primary{
color:#fff;
background-color:rgb(209, 24, 79);
border-color:rgb(209, 24, 79)
}
.btn-primary:hover{
color:#fff;
background-color:rgb(216, 50, 100);
border-color:rgb(216, 50, 100)
}
.btn-primary1{
color:rgb(o,o,o);
background-color:rgb(209, 24, 79);
border-color:rgb(209, 24, 79)
}
#titlediv {
	border-bottom: 1px solid #aaa;
}
.btn-group{
	padding-left: 50px;
}
#seacolor{
	color: rgb(209, 24, 79);
}
</style>
</head>
<body class="bg-light vsc-initialized">
	<div class="wrap row">
		<div class="searchzone row" >
			<div class="row">
				<div class="cg col-md-1">
					<p class="sel fs-6"><b>종류별</b></p>
				</div>
				<div class="col-md-10">
					<p class="sel fs-6">
						<!-- 전체보기는 리셋으로 -->
						<a href="/recipe/recipeList.kh">전체</a>
						<c:forEach items="${wList }" var="wRecipe" varStatus="i">
							<c:if test="${wRecipe.typeCode eq 'TYPE'}">
								<c:choose>
									<c:when test="${wRecipe.whatNo eq whatRecipe}">
										<a href="/recipe/recipeList.kh?whatRecipe=${wRecipe.whatNo}" id="active" >${wRecipe.typeName}</a>
									</c:when>
									<c:otherwise>
										<a href="/recipe/recipeList.kh?whatRecipe=${wRecipe.whatNo}">${wRecipe.typeName}</a>
									</c:otherwise>

								</c:choose>
							</c:if>
						</c:forEach>
					</p>
				</div>
			</div>
			<div class="row">
				<div class="cg col-md-1">
					<p class="sel fs-6"><b>방법별</b></p>
				</div>
				<div class="col-md-10">
					<p class="sel fs-6">
						<a href="/recipe/recipeList.kh">전체</a>
						<c:forEach items="${wList }" var="wRecipe" varStatus="i">
							<c:if test="${wRecipe.typeCode eq 'WAY'}">
								<c:choose>
									<c:when test="${wRecipe.whatNo eq whatRecipe}">
										<a href="/recipe/recipeList.kh?whatRecipe=${wRecipe.whatNo}" id="active" >${wRecipe.typeName}</a>
									</c:when>
									<c:otherwise>
										<a href="/recipe/recipeList.kh?whatRecipe=${wRecipe.whatNo}">${wRecipe.typeName}</a>
									</c:otherwise>

								</c:choose>
							</c:if>
						</c:forEach>
					</p>
				</div>
			</div>
			<div class="iline row">
				<div class="cg col-md-1">
					<p class="sel fs-6"><b>재료별</b></p>
				</div>
				<c:forEach items="${lList }" var="largeCat" varStatus="i">
					<c:if test="${largeCat.largeCatId != 'A9' && largeCat.largeCatId != 'A10' && largeCat.largeCatId != 'A11' && largeCat.largeCatId != 'A13'}">
						
						<div class="ing col-md-1">
							<!-- <p class="sel fs-9"> -->
								<select class="select form-select-sm" name="categorySelect" id="categorySelect" multiple size="3" onchange="saveIng(this)">
									<option disabled>${largeCat.largeCatName}</option>	
									<hr>
									<c:forEach items="${sList }" var="smallCat" varStatus="k">
										<c:if test="${largeCat.largeCatId eq smallCat.largeCatId}">
											<option value="${smallCat.smallCatId}">${smallCat.smallCatName}</option>	
											
										</c:if>

									</c:forEach>
								</select>
							<!-- </p> -->
						</div>
								</c:if>
						</c:forEach>

			</div>
			<div class="iline row">
				<div class="cg col-md-1">
					<p class="sel fs-6"><b>선택재료</b></p>
				</div>
				<div class="iaddzone col-md-7">
					<p class="sel fs-6"  id="iaddzone">
					</p>
				</div>
				<div class="bb col-md-3">
					<div class="btn-group" role="group" aria-label="Basic mixed styles example">
						<form action="/recipe/searchIng.kh" method="post">
						<button type="button" class="btn btn-outline-secondary" onclick="location.href='/recipe/recipeList.kh'">초기화</button>
						<input type="hidden" value="" id="postingid" name="postingid">
						<button type="submit" class="btn btn btn-outline-secondary" onclick="ingRecipeSearch()">검색</button>
					</form>
					  </div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-12">
					<c:if test="${sessionScope.postingid != null}">
						<p class="sel fs-5"  id="iaddzone">
						<c:forEach items="${sList }" var="smallCat" varStatus="i">
							<c:forEach items="${searching }" var="searching" varStatus="k">
								<c:if test="${searching eq smallCat.smallCatId}">
									<span><b id="seacolor">${smallCat.smallCatName}</b></span>	
								</c:if>
							</c:forEach>
						</c:forEach>
						에 대한 검색결과입니다!
					</p>
					</c:if>
				</div>
			</div>


		</div>
			<div class="row" id="titlediv">
		<div class="col-md-8">
		<h2> 총 <span style="border: #ccc">${totalCount }</span>개의 레시피가 있습니다.</h2>
		</div>
		<div class="r col-md-4" id="gogo">
			
				<form action="/recipe/recipeList.kh" method="get">
					<a class="btn btn-outline-secondary" href="/recipe/recipeList.kh?whatRecipe=${whatRecipe}&category=all" name="category" value="all">최신순</a>
					<a class="btn btn-outline-secondary" href="/recipe/recipeList.kh?whatRecipe=${whatRecipe}&category=countView" name="category" value="countView">조회수순</a>
					<a class="btn btn-outline-secondary" href="/recipe/recipeList.kh?whatRecipe=${whatRecipe}&category=likeView" name="category" value="likeView">좋아요순 </a>
				</form>
			
		</div>
	</div>	
		<div class="fixed_img_col" align="center">
			<ul>
				<c:forEach items="${rList }" var="recipe" varStatus="i">
				<!-- ${sessionScope.loginUser.userId } 불러는 오는데 500에러 뜸 -->
					<li><a onclick="" href="/recipe/recipeDetailView.kh?recipeNo=${recipe.recipeNo }&page=${currentPage }&category=${listValue }"><span class="thumb"><img
								onerror="this.src='../resources/images/logo.png'"
								src="/resources/ruploadFiles/${recipe.thumbnailRename }" alt="">
								<em>Click</em></span><strong>${recipe.recipeTitle }</strong></a>
								<p>${recipe.userId }</p>
								<p>좋아요: ${recipe.recipeLikeCount } 조회수: ${recipe.recipeCount }</p>
								<c:forEach items="${wList }" var="wList" varStatus="i">
									<c:if test="${recipe.recipeTime eq wList.whatNo}">
									<p>🕓: ${wList.typeName}</p></c:if>
								</c:forEach>
							</li>
				</c:forEach>
				
			</ul>
		</div>
		<hr id="bar-bar">
		<div class="row">
		<div class="page_bar col-md-12" align="center">
				<c:if test="${currentPage > 1 }">
						<a class="btn btn-primary" href="/recipe/${urlVal }.kh?page=${currentPage -1 }&category=${listValue }&whatRecipe=${whatRecipe}"><</a>
				</c:if>
				<c:if test="${totalCount <= 1 }">
					<a class="btn btn-primary1" href="/recipe/${urlVal }.kh?page=${p }&category=${listValue }&whatRecipe=${whatRecipe}"><b>1</b></a>
				</c:if>
				<c:forEach var="p" begin="${startNavi }" end="${endNavi}">
					<c:if test="${currentPage eq p }">
						<a class="btn btn-primary1" href="/recipe/${urlVal }.kh?page=${p }&category=${listValue }&whatRecipe=${whatRecipe}"><b>${p }</b></a>
					</c:if>
					<c:if test="${currentPage ne p and currentPage ne null}">
						<a class="btn btn-primary" href="/recipe/${urlVal }.kh?page=${p }&category=${listValue }&whatRecipe=${whatRecipe}">${p }</a>
						</c:if>
				</c:forEach>
				<c:if test="${currentPage < maxPage }">
						<a class="btn btn-primary" href="/recipe/${urlVal }.kh?page=${currentPage +1 }&category=${listValue }&whatRecipe=${whatRecipe}">></a>
				</c:if>
		</div>
		</div>
		<br><br><br>
	</div>
<!-- class="btn btn-primary" -->
<footer>
<jsp:include page="/WEB-INF/views/main/footer.jsp"></jsp:include>
</footer>
<script>
	//jstl 재료목록 js 배열화
	var count = 0;
	var smallidlist= new Array();
	const smallCat = new Array();
	<c:forEach items="${sList }" var="smallCat" varStatus="i">
		smallCat.push({
			smallCatId : "${smallCat.smallCatId}",
			smallCatName : "${smallCat.smallCatName}"
		});
	</c:forEach>
	
	//재료목록 선택시 선택재료 추가
	function saveIng(ingCode){
		console.log(smallCat[0].smallCatId == $(ingCode).val());
		// console.log(document.getElementById('categorySelect').value())
		// if()
		// document.getElementsByClassName('.iaddzone').innerText = $(ingCode).val();
		// array.forEach(element => {
		
		// });
		console.log(count)
		if(count >= 10){
			alert("10개까지 넣을 수 있습니다.")
		}
		var form = document.getElementById("iaddzone");
		var s = $(ingCode).val().toString()
		if(count <10){
			smallCat.forEach(function(item,index) {
				if(item.smallCatId == $(ingCode).val()){
					form.innerHTML += '<span class="iaddspec"><b>➕'+item.smallCatName+'</b></span>'
					smallidlist.push(item.smallCatId)
				}
			});
			console.log(smallidlist)
			count++;
		}
		$("#postingid").val(smallidlist);
	}		
	// function ingRecipeSearch(){
	// 	if(smallidlist == ""){
	// 		alert("값이없습니다");
	// 		location.href="/recipe/recipeList.kh"
	// 	}
	// 	if(smallidlist != ""){
	// 		action="/recipe/searchIng.kh"
	// 	}
	// 	console.log(smallidlist == "")
	// }
	
</script>
</body>
</html>