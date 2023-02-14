<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<h1>${board.no }번 글 수정하기</h1>

<form method="post">
	<input type="hidden" name="no" value="${board.no}"/>
	<input type="text" name="title" value="${board.title }" required/><br><br>
	<input type="text" anme="writer" value="${board.writer }" required/><br><br>
	<textarea name="content" rows="10" cols="60" required>
		${board.content }
	</textarea>
	<button type="submit">수정</button>
</form>	
