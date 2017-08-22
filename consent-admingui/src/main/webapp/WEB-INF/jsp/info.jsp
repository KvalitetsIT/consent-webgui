<%@taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp" />

<c:if test="${notification != null}">
	<div class="alert alert-success" role="alert"><c:out value="${notification}"/></div>
</c:if>
<c:if test="${link != null}">
	<a href=<c:out value="${link}"/> class="btn btn-default">Forts&aelig;t</a>
</c:if>
</div>
</body>
</html>