<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="/WEB-INF/views/templates/header.jsp"/>


<div class="container-fluid">

	<div class="row mt-4">
		<div class="col-md-10 offset-md-1">
			<h1>${board.no }번 글 수정</h1>
			<p class="text-secondary">
			글은 자신의 인격입니다.
			</p>
		</div>
	</div>
	
	<form method="post" class="board-form">
		<input type="hidden" name="no" value="${board.no }">
	
		<div class="row mt-4">
			<div class="col-md-10 offset-md-1">
				<div class="form-floating mt-4">
					<input class="form-control" type="text" name="writer" placeholder="작성자" value="${board.writer }" required/>
					<label class="form-label text-secondary">작성자</label>  
				</div>
			</div>
		</div>
		
		<div class="row mt-4">
			<div class="col-md-10 offset-md-1">
				<div class="form-floating">
					<input class="form-control" type="text" name="title" placeholder="제목" value="${board.title }" required/>
					<label class="form-label text-secondary">제목</label>  
				</div>
			</div>
		</div>
		<div class="row mt-4">
			<div class="col-md-10 offset-md-1">
				<textarea class="form-control" name="content" placeholder="내용 작성" rows="15" required>${board.content }</textarea>
			</div>
		</div>
		
		<!-- 비밀번호 수정을 위한 탭 제공 -->
		<div class="row mt-4">
			<div class="col-md-10 offset-md-1">
				<div class="form-floating">
					<input class="form-control" type="password" name="password" placeholder="비밀번호" required>
					<label class="form-label text-secondary">비밀번호</label>
				</div>
			</div>
		</div>
		
		<div class="row mt-4">
			<div class="col-md-10 offset-md-1 text-end">
				<a href="./" class="btn btn-secondary">목록</a>
				<button type="submit" class="btn btn-primary">수정</button>
			</div>
		</div>
		
	
	</form>
</div>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"/>