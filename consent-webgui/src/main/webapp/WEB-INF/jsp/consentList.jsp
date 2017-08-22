<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<jsp:include page="header.jsp" />

<c:if test="${notification != null}">
	<div class="alert alert-success" role="alert"><c:out value="${notification}"/></div>
</c:if>

<c:if test="${error != null}">
	<div class="alert alert-warning" role="alert"><c:out value="${error}"/></div>
</c:if>

<table class="table table-striped">
	<thead>
		<tr>
			<th>Navn</th>
			<th>Oprettet</th>
			<th>Sidst anvendt</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
	</tbody>	
</table>

<jsp:include page="footer.jsp" />