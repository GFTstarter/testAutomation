<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@ page isELIgnored="false"%>
<meta charset="UTF-8" />
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js"/>"></script>
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>" />
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" />
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>

<title>Blotter - Test Cases</title>

<script>
	function validate() {
		var value = form.file.value;
		ext = value.split(".").pop();
		if (!value) {
			alert("Please select a file.");
		} else if (ext !== "xml") {
			alert("Please select a .xml file.");
		}

		return (ext === "xml");
	}
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
					<a class="btn btn-primary" href="<c:url value="/j_spring_security_logout" />">Logout</a>
				</form>

			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<form:form id="form" method="POST" action="uploadFile"
				enctype="multipart/form-data"
				onsubmit="javascript:return validate(this);">
				<div class="form-group">
					<input type="hidden" name="id_release" value="${id_release}" />
					<input type="hidden" name="project" value="${project}" />
					<input type="hidden" name="tag" value="${tag}" /> <label>Import XML</label> 
					<input type="file" name="file">
					<p class="help-block">Only XML files are allowed.</p>
					<br>
					<div>
						<a href="refreshTicket?project=${project}&id_release=${id_release}&tag=${tag}"
							class="btn btn-primary">Back</a> <input class="btn btn-primary"
							type="submit" value="Upload" id="upload" />
					</div>
				</div>
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