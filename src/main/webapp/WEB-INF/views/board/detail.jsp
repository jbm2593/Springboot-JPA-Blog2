<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

		<button type="button" class="btn btn-outline-primary" onclick= "location.href = '/' ">돌아가기</button>
		<c:if test="${board.user.id == principal.user.id}">
			<a href="/board/${board.id}/updateForm"  class="btn btn-outline-warning">수정</a>
			<button id="btn-delete" class="btn btn-outline-danger">삭제</button>
		</c:if>
		<br/><br/>
		<div>
			글 번호 : <span id="id"><i>${board.id}	</i></span>
			작성자 : <span><i>${board.user.username}	</i></span>
		</div>
		<br/>
		<div >
			<h3>${board.title}</h3>
		</div>
		<hr />
		<div>
			<div>${board.content}</div>
		</div>
		<hr/>
</div>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>



