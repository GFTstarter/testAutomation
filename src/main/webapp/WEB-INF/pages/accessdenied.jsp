<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<%@ page isELIgnored="false"%>
		<meta charset="UTF-8">
		<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>" />
		<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" />
		<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
		<script src="<c:url value="/resources/js/jquery-1.11.1.min.js"/>"></script>
		<title>Blotter - Test Cases</title>
	</head>
	
	<body>
		<div class="container">
			<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
				<div class="navbar-header">
					<label class="navbar-brand">Blotter - Test case automation</label>
				</div>
				<div class="navbar-collapse collapse">
					<div id="navbarlabel" class="navbar navbar-right">
						<img id="navbarimg"
							src="<c:url value="/resources/css/images/GFT_Logo_RGB_200pixel.png"/>" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<h3 class="text-muted text-center">Access denied</h3>
	
				<c:if test="${not empty error}">
					<div style="color: red">Your access was denied. Caused :
						${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
				</c:if>
	
				<a href="login" class="btn btn-primary">Go back to login page</a>
			</div>
		</div>
		<div class="footer">
			<div class="col-md-6 col-md-offset-3">
				<p>© GFT Group</p>
			</div>
		</div>
	</body>
</html>