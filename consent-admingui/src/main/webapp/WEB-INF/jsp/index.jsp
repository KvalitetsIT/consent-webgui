<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<jsp:include page="header.jsp" />

<c:if test="${notification != null}">
	<div class="alert alert-success" role="alert"><c:out value="${notification}"/></div>
</c:if>

<table class="table table-striped">
	<thead>
		<tr>
			<th>Applikations-id</th>
			<th>Applikations-navn</th>
			<th>Tekst til Notifikation</th>
			<th>Version</th>			
			<th>Funktioner</th>					
			<th></th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${consentTemplates}" var="consentTemplate">
		<tr>
        	<td><c:out value="${consentTemplate.appId}"/></td>
        	<td><c:out value="${consentTemplate.friendlyName}"/></td>
        	<td><c:out value="${consentTemplate.notificationSubject}"/></td>
        	<td><c:out value="${consentTemplate.version}"/></td>
        	<td>
        		<a class="btn btn-default" target='_blank' href=showConsentTemplate?templateId=<c:out value="${consentTemplate.id}"/>>Vis PDF</a><br/>
        	</td>
        	<td>
        		<form:form modelAttribute="request" action="updateConsentTemplate" enctype="multipart/form-data" method="POST">
        			<form:input type="file" path="pdfFile" name="pdfFile"/>
        			<form:hidden path="appId" value="${consentTemplate.appId}" />
        			<button type="submit" class="btn btn-primary">Upload ny PDF</button>
        		</form:form>
        	</td>
      	</tr>
	</c:forEach>
	</tbody>	
</table>

<jsp:include page="footer.jsp" />