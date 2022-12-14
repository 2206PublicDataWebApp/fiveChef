<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
	<script src="../../../resources/js/jquery-3.6.1.min.js"></script>
	
	<title>냉장고 칸 페이지</title>
</head>

<style>
#main-form {
	margin-left: 10%;
	margin-right: 10%;
}

textarea.form-controls {
	min-height: calc(1.5em + 2rem + 2px);
	border: #333;
	border: 1px solid #ced4da;
	border-radius: .25rem;
}

.form-controls:focus {
	color: #212529;
	background-color: #fff;
	border-color: #86b7fe;
	outline: 0;
	box-shadow: 0 0 0 .25rem rgba(13, 110, 253, .25)
}

#btn-2 {
	height: 40px;
	border: 0;
	color: white;
	background-color: rgb(209, 24, 79);
}

#btn-2 {
	color: #fff;
	background-color: rgb(209, 24, 79);
	border-color: rgb(209, 24, 79)
}

#btn-2:hover {
	color: #fff;
	background-color: rgb(216, 50, 100);
	border-color: rgb(216, 50, 100)
}

.btn-outline-primary {
	color: rgb(209, 24, 79);
	border-color: rgb(209, 24, 79)
}

.btn-outline-primary:hover {
	color: #fff;
	background-color: rgb(209, 24, 79);
	border-color: rgb(209, 24, 79)
}

#cutline {
	visibility: hidden;
}

#thumbnailzone {
	position: relative;
	width: auto;
	height: 200px;
	border: 1px solid #aaa;
	border-radius: .25rem;
}

#thumbnailzone img {
	position: absolute;
	width: 100%;
	height: 100%;
	object-fit: cover;
	/* border-radius: 5%; */
	top: 50%;
	left: 50%;
	margin-top: 0;
	transform: translate(-50%, -50%);
	!
	important
}

#thumbnailzone .form-label {
	width: 100%;
}

#thumbnailzone input {
	position: relative;
	width: 100%;
}

#ingHidden {
	visibility: hidden;
	height: 0px;
	width: 0px;
}

.vertical {
            border-left: 2px solid black;
            height: 280px;
            position:absolute;
            left: 28.6%;
        }
</style>

<body>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
	<div class="container">
	<header>
		<hr style="border-width:1px;">
		<div class="mt-3" align="center">
			<h1 align="center" >${fridgeName } 페이지</h1>
		</div>
	</header>
	
		<div class="card-body" style="background-color:rgb(255, 198, 198); border-radius: 10px; padding: 30px;">
		<div class="row">
			<div class="col-2" align="left">
				<div class="row">
					<div class="col">
						<button class="btn btn-secondary" onclick="location.href='/fridge/myFridge.kh'">&#10094; 이전 페이지</button>
						<div class="col">
							<form action="/recipe/searchIngMyFridge.kh" method="post">
								<c:forEach items="${stList }" var="storage" varStatus="j">
									<input type="hidden" id="seachingIng${j.index }" name="ingredBundle" value="${storage.ingredBundle }">
									<input type="hidden" name="fridgeNo" value="${fridgeNo}">
									<input type="hidden" name="fridgeName" value="${fridgeName}">
									<input type="hidden" name="stSelectNo" value="${j.index }">
									<input type="hidden" name="storageNo" value="${storage.storageNo }">
								</c:forEach>
									<input class="btn btn-info" type="submit" value="검색"> 
							</form>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col" align="right">
				<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#createStorage" id="btn-2" onclick="modalTransVal()">&#43; 칸 생성</button>
				<button class="btn btn-danger" onclick="deleteStorage(${fridgeNo }, '${fridgeName }');" id="btn-2">&#8722;  칸 삭제</button>
			</div>
		</div>
			<c:if test="${not empty stList }">
				<c:forEach items="${stList }" var="storage" varStatus="j">
					<input type="hidden" name="storageSelectNo" value="${storage.storageSelectNo }">
					<input type="hidden" name="storageNo" value="${storage.storageNo }">
					<hr style="border-width:2px;">
					<div class="row mb-2 mt-2">
						<div class="col-3" style="background-color:rgb(255, 230, 230); padding:20px; border-radius:5px;" >
							<br>
							<div class="row">
								<div class="col-2">
									<input class="chkBox" type="checkbox" name="storageBoxCheck" id="storageCheck${j.index }" value="${storage.storageNo }">
								</div>
								<div class="col">
									<h3>${storage.storageName }</h3>
								</div>
								<div class="col">
									<button class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#modifyStorage${j.index }">이름 수정</button>
								</div>
							</div>
							<br>
							<div class="row">
								<div class="col-3 text-centered">
									<h6><b>대분류</b></h6>
								</div>
								<div class="col">
									<select id="selLarge" style="width: 150px;" onchange="selectLargeBox(this.value, ${fridgeNo}, '${fridgeName }',${j.index }, ${storage.storageNo });">
										<c:forEach items="${lList }" var="largeCat"  varStatus="i">
											<option id="largeCatOpt" value="${largeCat.largeCatId }" <c:if test="${largeCat.largeCatId  eq storage.largeCatId }">selected</c:if>>${largeCat.largeCatName }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<br>
							<div class="row">
								<div class="col-3 text-centered">
									<h6><b>소분류</b></h6>
								</div>
								<div class="col">
									<select id="selSmall" style="width: 150px; height:100px;" multiple onchange="list_selected(this, ${j.index });" >
										<c:forEach items="${sList }" var="smallCat"  varStatus="i">
											<c:if test="${smallCat.largeCatId eq storage.largeCatId }">
												<option id="smallCatOpt" value="${smallCat.smallCatId }" >${smallCat.smallCatName }</option>
											</c:if>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="row" id="customInput" style="display:none;">
								<input type="text" placeholder="재료 검색">
							</div>
							<br>
							<div class="row justify-content-center">
								<div class="col-5">
									<form action="/fridge/registIngred.kh" method="post">
										<input type="hidden" id="ingredBundle${j.index }" name="ingredBundle" value="">
										<input type="hidden" name="fridgeNo" value="${fridgeNo}">
										<input type="hidden" name="fridgeName" value="${fridgeName}">
										<input type="hidden" name="stSelectNo" value="${j.index }">
										<input type="hidden" name="storageNo" value="${storage.storageNo }">
										<input class="btn btn-primary" type="submit" value="재료 저장"> 
									</form>
								</div>
								<div class="col-5">
									<form action="/fridge/deleteIngred.kh" method="post">
										<input type="hidden" name="fridgeNo" value="${fridgeNo}">
										<input type="hidden" name="fridgeName" value="${fridgeName}">
										<input type="hidden" name="stSelectNo" value="${j.index }">
										<input type="hidden" name="storageNo" value="${storage.storageNo }">
										<input class="btn btn-danger" type="submit" value="재료 삭제"> 
									</form>
								</div>
							</div>
						</div>
						<div class="col" style="padding:20px">
							<div class="row row-cols-6">
								<c:forEach items="${iList[j.index] }" var="ingred" varStatus="n">
									<c:if test="${not empty ingred}">
										<c:forEach items="${sList }" var="smallCat" varStatus="s">
	<!-- 										<input type="hidden" name="ingred"> -->
													<c:if test="${ingred eq smallCat.smallCatId }">
														<div class="col">
															<div class="row" id="values">
																<div class="col-1">
																	<input type="checkbox" id="ingredCheck${k }">
																</div>
																<div class="col">
																	<label for="ingredCheck${n }"> ${smallCat.smallCatName }</label>
																</div>
															</div>
														</div>
													</c:if>
										</c:forEach>
									</c:if>
									<c:if test="${empty ingred}">
										재료를 입력해주세요
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
					
					
					<!--Modify Storage Modal -->
					<div class="modal fade" id="modifyStorage${j.index }" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog modal-dialog-centered">
							<div class="modal-content">
							 	<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">이름 수정</h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
								</div>
								    <div class="modal-body">
									    <div class="modal-body p-5 pt-0">
											<form action="/fridge/modifyStorage.kh" name="modifyStorage" method="post">
												<br>
												<div class="form-floating mb-3">
													<input type="hidden" name="fridgeNo" value="${fridgeNo}">
													<input type="hidden" name="fridgeName" value="${fridgeName}">
													<input type="hidden" name="stList" value="${storage}">
													<input type="hidden" name="storageNo" value="${storage.storageNo}">
													<input type="text" class="form-control rounded-4" id="storageName" placeholder="칸 이름 입력" name="storageName" value="${storage.storageName }" required>
													<label for="floatingInput">칸 이름</label>
												</div>
												<button class="w-100 mb-2 btn btn-lg btn-primary" type="submit">수정 완료</button>
											</form>
										</div>
							    	</div>
							</div>
						</div>
					</div>
		
				</c:forEach>
			</c:if>
			<c:if test="${empty stList }">
				<hr>
				<br>
				<div>
					<div colspan="6" align="center"><h3><b>칸을 생성해 주세요.</b></h3></div>
				</div>
				<br>
				<hr>
			</c:if>
		</div>
	</div>
		
	<!--Create Storage Modal -->
	<div class="modal fade" id="createStorage" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
			 	<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">칸 생성</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				    <div class="modal-body">
					    <div class="modal-body p-5 pt-0">
							<form action="/fridge/createStorage.kh?fridgeNo=${fridgeNo}&fridgeName=${fridgeName}" method="post">
								<br>
								<div class="form-floating mb-3">
									<input type="hidden" name="fridgeNo" value="${fridgeNo}">
									<input type="hidden" name="largeCatId" value="${largeCatId }">
									<input type="hidden" name="stSelectNo" value="" id="stSelNo">
									<input type="text" class="form-control rounded-4" id="storageName" placeholder="칸 이름 입력" name="storageName" required>
									<label for="floatingInput">칸 이름 </label>
								</div>
								<button class="w-100 mb-2 btn btn-lg btn-primary" type="submit" >생성 완료</button>
							</form>
						</div>
			    	</div>
			</div>
		</div>
	</div>
		
		
		
		
	<script>
		function selectLargeBox(value, fNo, fName, jNo, sNo){
			var $form = $("<form>"); // <>꺽쇠를 적어야 태그 생성
			$form.attr("action", "/fridge/changeSmall.kh");
			$form.attr("method", "get");
			$form.append("<input type='hidden' value='"+value+"'name='largeCatId''>");
			$form.append("<input type='hidden' value='"+fNo+"' name='fridgeNo''>");
			$form.append("<input type='hidden' value='"+fName+"' name='fridgeName''>");
			$form.append("<input type='hidden' value='"+jNo+"' name='selectBoxNo'>");
			$form.append("<input type='hidden' value='"+sNo+"' name='storageNo''>");
			$form.appendTo("body");
			$form.submit();
		}
		
		
		function deleteStorage(fNo, fName){
			var checkedList = new Array();
			$(".chkBox:checked").each(function(index, item){
				checkedList.push(this.value);
			});
			
			if (checkedList == ''){
				alert("삭제할 칸을 선택해주시기 바랍니다.");
			} else {
				if(confirm("다시 복원 할 수 없습니다.\n정말 삭제하시겠습니까?")){
					$.each(checkedList, function(index, item){
						console.log(checkedList);
						var $form = $("<form>"); // <>꺽쇠를 적어야 태그 생성
		 				$form.attr("action", "/fridge/deleteStorage.kh");
		 				$form.attr("method", "post");
		 				$form.append("<input type='hidden' value='"+fNo+"' name='fridgeNo'>");
		 				$form.append("<input type='hidden' value='"+fName+"' name='fridgeName'>");
		 				$form.append("<input type='hidden' value='"+checkedList+"' name='stSelectNo'>");
		 				$form.appendTo("body");
						$form.submit();
					});
				}
			}
		}
		// 민석님
		function list_selected(e, jNo) {
			const selsList = [];
// 			const texts = [];
			
			// options에서 selected 된 element 찾기
			for(let i=0; i < e.options.length; i++) {
				const option = e.options[i];
			  	if(option.selected) {
				    selsList.push(option.value);
// 				    texts.push(option.text);
			  	}
			}
			
			$('#ingredBundle'+jNo+'').val(selsList);
			
		}
		
		function modalTransVal(st){
			$.each(st, function(index, item){
				$(".stSelNo").val(index)
				console.log(st[i]);
			});
			
		}
		
		
	</script>
</body>
</html>


