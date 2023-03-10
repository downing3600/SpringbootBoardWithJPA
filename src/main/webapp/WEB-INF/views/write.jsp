<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/views/templates/header.jsp"></jsp:include>

<div class="container-fluid">
	<div class="row mt-4">
		<div class="col-md-10 offset-md-1">
		<h1>게시글 작성</h1>
		<p class="text-secondary">
			글은 자신의 인격 입니다.
		</p>
        </div>
    </div>

    <form method="post" class="board-form">

        <c:if test="${param.no != null}">
            <input type="hidden" name="no" value="${param.no}">

		</c:if>
		<!--no가 있을 경우 답글 이므로 전달을 위한 hidden feild생성 -->

		<div class="row mt-4">
			<div class="col-md-10 offset-md-1">
				<div class="form-floating mt-4">
					<input name="writer" class="form-control" placeholder="작성자" required />
					<label class="form-label text-secondary">작성자</label>
				</div>
			</div>
		</div>

		<div class="row mt-4">
			<div class="col-md-10 offset-md-1">
				<div class="form-floating">
					<input name="title" class="form-control" placeholder="제목" required />
					<label class="form-label text-secondary">제목</label>
				</div>
			</div>
		</div>

		<div class="row mt-4">
			<div class="col-md-10 offset-md-1">
					<textarea name="content" class="form-control" rows="15" placeholder="내용 작성" required></textarea>
			</div>
		</div>

		<!-- 비밀번호 입력 창 추가 -->
		<div class="row mt-4">
			<div class="col-md-10 offset-md-1">
				<div class="form-floating">
					<input type="password" class="form-control" name="password" placeholder="비밀번호" required />
					<label class="form-label text-secondary">비밀번호</label>

				</div>

			</div>
		</div>

		<div class="row mt-4">
			<div class="col-md-10 offset-md-1 text-end">
				<a href="./" class="btn btn-secondary">목록</a>
				<button type="submit" class="btn btn-primary">등록</button>
			</div>
		</div>

	</form>

</div>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"></jsp:include>