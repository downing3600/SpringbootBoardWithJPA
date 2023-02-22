<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/views/templates/header.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row mt-4">
        <div class="col-md-10 offset-md-1"></div>
        <h1 class="text-danger text-center">삭제 오류</h1>
    </div>

    <div class="row">
        <div class="col-md-10 offset-md-1">

            <p class="text-danger text-center">
                답글이 달린 글은 삭제할 수 없습니다.
            </p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-10 offset-md-1 text-center">
            <a class="btn btn-link btn-lg" href="./">목록으로</a>

        </div>
    </div>
</div>


<jsp:include page="/WEB-INF/views/templates/footer.jsp"></jsp:include>