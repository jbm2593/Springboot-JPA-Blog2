<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="layout/header.jsp"%>

<div class="text-center banner">
	 <a href="/"> <img src="/images/kyowon.jpg" style="width: auto; height:100px" class="banner-img"></a>
</div>

<div class="container">

	<div class="search-div" style="float: right; width: 400px; margin-bottom:10px">
		<form class="search-form d-flex" action="" method="GET">
			<input class="form-control me-sm-2" type="text" name="searchKeyword" value="${searchKeyword}" placeholder="검색어를 입력하세요.">
			<button class="btn btn-primary my-2 my-sm-0"  style="margin-left: 10px; background-color:#FF8000" type="submit">Search</button>
		</form>
	</div>
	
	<table class="table table-hover text-center">
		<thead>
			<tr>
				<th>No</th>
				<th class="th-title">제목</th> 
				<th>작성자</th>
				<th>작성시간</th>
				<th>조회수</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="board" items="${boards.content}">
				<tr class="odd gradeX">
					<td><c:out value="${board.id}" /></td>
					<td><c:out value="${board.title}" /></td> 
					<td><c:out value="${board.user.username}" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.createDate}"/></td>
					<td><c:out value="${board.count}" /></td>
					<td><a href="/board/${board.id}" class="btn btn-success">상세보기</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<ul class="pagination justify-content-center">
		<c:choose>
			<c:when test="${boards.first}">
				<li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number-1}&searchKeyword=${searchKeyword}">Previous</a></li>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${boards.last}">
				<li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number+1}&searchKeyword=${searchKeyword}">Next</a></li>
			</c:otherwise>
		</c:choose>

	</ul>

</div>

<%@ include file="layout/footer.jsp"%>
