<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  	
	<jsp:include page="/WEB-INF/views/templates/header.jsp"></jsp:include>
	
	<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/lightpick@1.6.2/css/lightpick.css">
  	<!-- container -->
  	<div class="container-fluid">
  		<!-- 제목 -->
  		<div class="row mt-4">
  			<div class="col-md-10 offset-md-1">
  			<h1>자유 게시판</h1>
  			<p>
  				타인에 대한 비방 또는 모욕은 사전 예고 없이 삭제됩니다.
  			</p>
  			</div>
  		</div>
  		
  		<!-- 확인용 -->
		<div class="row mt-4">
			<div class="col-md-10 offset-md-1">
				${boardVO }<br>
				${boardVO.search}<br>
				${pagination}
			</div>
			
		</div>		
  		<!-- 복합 검색을 위한 아코디언 검색창 -->
  		<div class="row mt-4">
  			<div class="col-md-10 offset-md-1">
  				<div class="card border-dark">
  					<div class="card-header">
  						<a class="btn" data-bs-toggle="collapse" href="#collapse-body">검색 도구 상자</a>
  					</div>
  					
  					<!-- 검색인지 목록인지에 따라 옵션 펼침 여부를 다르게 처리 -->
  					<c:choose>
  						<c:when test="${boardVO.search}">
  						<div class="collapse show" id="collapse-body">
  						</c:when>
  						
  					 	<c:otherwise>
						<div class="collapse hide" id="collapse-body">
  						</c:otherwise>
  						
  						
  					</c:choose>
  				
	  					<div class="card-body">
	  					
	  						<!-- 검색 form -->
	  						<form action="./" method="post" class="search-form">
	  							
	  							<!-- 번호 검색 -->
	  							<div class="row"><div class="col">
	  								<div class="form-floating">
	  									<input name="no" class="form-control" type="text" placeholder="번호" value=${boardVO.no }>
	  									<label class="form-label text-secondary">번호</label>
	  								</div>
	  							
	  							</div></div>
	  							
	  							<!-- 작성자 검색 -->
	  							<div class="row mt-4"><div class="col">
	  								<div class="form-floating">
		  								<input name="writer" class="form-control" type="text" placeholder="작성자" value=${boardVO.writer }>
	  									<label class="form-label text-secondary">작성자</label>
	  								</div>
	  							
	  							</div></div>
	  							
	  							<!-- 제목 검색 -->
	  							<div class="row mt-4"><div class="col">
	  								<div class="form-floating">
		  								<input name="title" class="form-control" type="text" placeholder="제목" value="${boardVO.title }">
	  									<label class="form-label text-secondary">제목에 포함된 글자</label>
  									</div>
	  							
	  							</div></div>
	  							
	  							<!-- 내용 검색 -->
	  							<div class="row mt-4"><div class="col">
		  							<div class="form-floating">
		  								<input name="content" class="form-control" type="text" placeholder="내용" value="${boardVO.content }">
	  									<label class="form-label text-secondary">내용에 포함된 글자</label>
  									</div>
	  							
	  							</div></div>
	  							
	  							<div class="row mt-4">
	  								<!-- 시작일 검색 -->
	  								<div class="col">
		  								<div class="form-floating">
			  								<input name="begin" class="form-control" type="text" placeholder="시작일" value="${boardVO.begin }">
		  									<label class="form-label text-secondary">검색 시작일</label>
	  									</div>
		  							
	  							</div>
	  							
	  							<!-- 종료일 검색 -->
		  							<div class="col">
		  								<div class="form-floating">
			  								<input name="end" class="form-control" type="text" placeholder="종료일" value=${boardVO.end }>
		  									<label class="form-label text-secondary">검색 종료일</label>
	  									</div>
		  							
		  							</div>
	  							
	  							</div>
	  							
	  							<div class="row mt-4">
	  								<div class="col text-end">
	  									<input type="reset" class="btn btn-warning btn-lg" value="초기화">
	  									<a href="./" class="btn btn-secondary btn-lg">목록</a>
	  									<button type="submit" class="btn btn-success btn-lg">검색</button>
	  								</div>
	  							</div>
	  								
	  						
	  						</form>
	  					</div>
	  				</div>
  				</div>
  			</div>
  		</div>
  		
  		<!-- 게시글 개수 및 글쓰기 버튼 -->
  		<div class="row mt4">
  			<div class="col-md-10 offset-md-1">
  				<div class="row">
  					<div class="col text-start">
  						(${pagination.current} / ${pagination.total})
  					</div>
  					<div class="col text-end">
  						<a href="write" class="btn btn-primary">글쓰기</a>
  					</div>
  				</div>
  			</div>
  		</div>
  		
	  	<div class="row mt-4">
	  		<div class="col-md-10 offset-md-1">
	  			<table class="table table-hover">
	  				<thead class="bg-dark text-light">
	  					<tr>
	  						<th>번호</th>
	  						<th width="40%">제목</th>
	  						<th>작성자</th>
	  						<th>작성일</th>
	  						<th>조회수</th>

	  						<th>no</th>
	  						<th>grp</th>
	  						<th>seq</th>
	  						<th>dep</th>
	  					</tr>
	  				</thead>
	  				
	  				<tbody class="text-center">
						<c:forEach var="board" items="${data.content}">
						<tr>
							<td>${board.no }</td>
<!-- 							<td class="text-left"> -->
							<td class="text-start">
								<c:if test="${board.dep > 0}">


									<!--답글 차수(dep)만큼 띄어쓰기 추가 -->
									<c:forEach var="i" begin="1" end="${board.dep}">
										&nbsp;&nbsp;
									</c:forEach>
									<!--답글에 아이콘 추가-->
									<i class="fa-solid fa-reply fa-rotate-180"></i>
								</c:if>

								<a href="detail?no=${board.no }">
									${board.title }
								</a>
								<!-- 댓글 개수 추가 표시 -->
								<c:if test="${board.replyCount > 0 }">
								[${board.replyCount }]
								</c:if>
							</td>
							<td>${board.writer }</td>
							<td>${board.writeTime }</td>
							<td>${board.readcount }</td>

							<td>${board.no }</td>
							<td>${board.grp }</td>
							<td>${board.seq }</td>
							<td>${board.dep }</td>
						</tr>
						</c:forEach>
						
					</tbody>
	  			</table>
	  		</div>
	  	
	  	</div>
	  	
	  	<!-- 페이지 네비게이터 -->
	  	<div class="row mt-4">
	  		<div class="col-md-10 offset-md-1">
	  			<ul class="pagination justify-content-center">
	  			
	  			<!-- 처음이 있을 경우만 링크 설정 -->
	  			<c:choose>
		  			<c:when test="${pagination.hasFirstBlock()}">
		  				<li class="page-item"><a class="page-link" href="#" data-page="${pagination.getFirst() }">&laquo;</a></li>
		  			</c:when>
	  			
		  			<c:otherwise>
		  				<li class="page-item"><a class="page-link disabled" href="#" >&laquo;</a></li>
		  			</c:otherwise>
	  			</c:choose>
	  			
	  			
	  			<!-- 이전이 있을 경우만 링크 설정 -->
	  			
	  			<c:choose>
	  			
	  				<c:when test="${pagination.hasPreviousBlock() }">
	  					<li class="page-item"><a class="page-link" href="#" data-page="${pagination.getPrevious() }">&lt;</a></li>
	  				</c:when>
	  				
	  				<c:otherwise>
		  				<li class="page-item"><a class="page-link disabled" href="#">&lt;</a></li>
	  				</c:otherwise>
	  				
	  			</c:choose>
	  			
	  			
	  			<!-- begin부터 end까지 표시 (보여줄 땐 p, 이동할 땐 p-1 JPA 시작페이지 특성 때문인듯)-->
	  			
	  			<c:forEach var="p" begin="${pagination.begin}" end="${pagination.end }">
	  				<c:choose>
	  			
		  				<c:when test="${p == pagination.current }">
		  					<li class="page-item active"><a class="page-link disabled" href="#">${p}</a></li>
		  				</c:when>
		  				
		  				<c:otherwise>
		  					<li class="page-item"><a class="page-link" href="#" data-page="${p-1 }">${p}</a></li>
		  				</c:otherwise>
		  				
		  			</c:choose>
	  			
	  			</c:forEach>
	  			
	  			<!-- 다음이 있을 경우만 링크 설정 -->
	  			
	  			<c:choose>
	  			
	  				<c:when test="${pagination.hasNextBlock() }">
	  					<li class="page-item"><a class="page-link" href="#" data-page="${pagination.getNext() }">&gt;</a></li>
	  				</c:when>
	  				
	  				<c:otherwise>
		  				<li class="page-item"><a class="page-link disabled" href="#">&gt;</a></li>
	  				</c:otherwise>
	  				
	  			</c:choose>
	  			
	  			<!-- 마지막이 아닐 경우만 링크 설정 -->
	  			
	  			<c:choose>
	  			
	  				<c:when test="${pagination.hasLastBlock() }">
	  					<li class="page-item"><a class="page-link" href="#" data-page="${pagination.getLast() }">&raquo;</a></li>
	  				</c:when>
	  				
	  				<c:otherwise>
		  				<li class="page-item"><a class="page-link disabled" href="#">&raquo;</a></li>
	  				</c:otherwise>
	  				
	  			</c:choose>
	  			
	  			</ul>
	  		</div>
	  	</div>
	  	
  	</div>
  	
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js" ></script>
  	<script src="https://cdn.jsdelivr.net/npm/lightpick@1.6.2/lightpick.js"></script>
  	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  	<script>
  		$(function(){
  			//.page-link에 대한 링크 설정(data-page 속성을 가지고 있을 경우)
  			$(".page-link").click(function(e){
  				e.preventDefault();// 기본 클릭 이벤트 제거
  				
  				const page = $(this).data("page");
  				
  				console.log(page);
  				//if(!page) return; 해당 코드에서 첫 페이지와 1번 페이지는 0의 값을 갖는데 이는 0이 false 값을 주는지 계속 리턴 되기 때문에 아래 처럼 커스텀 하였다.
  				if(!$.isNumeric(page)) return;
  				
  				//.search-form에 추가하여 전송
  				$("<input>").attr("name", "page").attr("type", "hidden").val(page).appendTo(".search-form");
  				$(".search-form").submit();
  			});
  			
  			//datepicker 설정(시작~종료일)
			const picker = new Lightpick({
				field: document.querySelector("input[name=begin]"),// 시작일
				secondField: document.querySelector("input[name=end"),//종료일
				format:"YYYY-MM-DD",//입력 형식
				maxDate:moment(),//미래 선택 불가
				numberOfMonths:1//1달씩 표시되도록 설정
			})  			
  		});
  		
  		
  	</script>
  	
  	<jsp:include page="/WEB-INF/views/templates/footer.jsp"></jsp:include>
  	
  	<!-- footer -->

