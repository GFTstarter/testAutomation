<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<tiles:importAttribute name="cssList"/>
<tiles:importAttribute name="jsList"/>
<head>
<!-- Tags for solve compatibility problems with IE versions -->
<%@ page isELIgnored="false"%>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<c:forEach var="cssValue" items="${cssList}">
	<link type="text/css" rel="stylesheet" href="<c:url value="${cssValue}"/>" />
</c:forEach>

<c:forEach var="jsValue" items="${jsList}">
	<script src="<c:url value="${jsValue}"/>"></script>
</c:forEach>
	
</head>

<div class="container">
	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="navbar-header">
			<label class="navbar-brand">Blotter - Test case automation</label>
		</div>
		<div class="navbar-collapse collapse">
			<div id="navbarlabel" class="navbar navbar-right">
				<img id="navbarimg"
					src="<c:url value="/resources/images/GFT_Logo_RGB_200pixel.png"/>" />
			</div>
			<form action="<c:url value='j_spring_security_logout'/>"
				method="POST" id="btnLogout" class="navbar-form navbar-right">
				<p class="navbar-text" style="color: white;">${user}</p>
				<c:if test="${user != null}">
					<a class="btn btn-primary"
						href="<c:url value="/j_spring_security_logout" />">Logout</a>
				</c:if>
			</form>
		</div>
	</div>
</div>
