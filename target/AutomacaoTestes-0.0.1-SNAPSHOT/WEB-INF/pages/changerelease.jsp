<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ page isELIgnored="false"%>
<meta charset="UTF-8" />
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js"/>"></script>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.min.css"/>" />
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/style.css"/>" />
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<title>Blotter - Test Cases</title>

<script>
</script>

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
				<form action="<c:url value='j_spring_security_logout'/>"
					method="POST" id="btnLogout" class="navbar-form navbar-right">
					<p class="navbar-text" style="color: white;">${user}</p>
					<a class="btn btn-primary"
						href="<c:url value="/j_spring_security_logout" />">Logout</a>
				</form>

			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<input type="hidden" name="id_release" value="${id_release}" /> <input
				type="hidden" name="project" value="${project}" /> <input
				type="hidden" name="tag" value="${tag}" />
			<form:form role="form" method="POST" action="changeRelease"
				commandName="ReleaseTicket">
				<div class="form-group">
					<label>Choose the tickets: </label>
					<form:select path="ticketList" cssClass="form-control">
						<form:options items="${ticketList}" itemLabel="jira"
							itemValue="id_ticket" />
					</form:select>
				</div>
				<div class="form-group">
					<label>Choose the release to transfer to: </label>
					<form:select path="releaseList" cssClass="form-control"
						multiple="false">
						<form:options items="${releaseList}" itemLabel="projecttag"
							itemValue="id_release" />
					</form:select>
				</div>
				<br>
				<input type="hidden" name="id_release" value="${id_release}" />
				<input type="hidden" name="project" value="${project}" />
				<input type="hidden" name="tag" value="${tag}" />
				<a
					href="ticketsList?project=${project}&tag=${tag}&id_release=${id_release}"
					class="btn btn-primary">Back</a>
				<button type="submit" class="btn btn-primary" id="upload">Save
					changes</button>
			</form:form>
		</div>
	</div>
	<div class="footer">
		<div class="col-md-6 col-md-offset-3">
			<p>© GFT Group</p>
		</div>
	</div>
</body>
</html>