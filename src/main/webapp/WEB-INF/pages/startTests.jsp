<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ page isELIgnored="false"%>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">


<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>" />
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" />
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/dataTables.bootstrap.css"/>" />
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/datepicker3.css"/>" />
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.dataTables.min.js"/>"></script>
<script src="<c:url value="/resources/js/dataTables.bootstrap.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap-datepicker.js"/>"></script>
<script src="<c:url value="/resources/js/startTests.js"/>"></script>

<style>
	.current-task {
	  background-color: #B4D5F5 !important;
	}
</style>

<title>Blotter - Test cases</title>
</head>
<body>

	<!-- Add new test case table -->
			<div class="container">
				<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
					<div class="navbar-header">
						<label class="navbar-brand">Start Tests</label>
					</div>
					<div class="navbar-collapse collapse">
						<div id="navbarlabel" class="navbar navbar-right">
							<img id="navbarimg"
							src="<c:url value="/resources/css/images/GFT_Logo_RGB_200pixel.png"/>" />
						</div>
					</div>
				</div>
			</div>
		<!-- Content -->
	<div id="startTest">
		<c:if test="${param.state != null}">
			<div class="alert alert-success" role="alert">Test registred 
					as "passed".</div>
		</c:if>
		<div class="container">
					<table id="addTestCases" class="table table-bordered table-hover table-striped">
						<thead>
							<tr>
								<th>Step</th>
								<th>Status</th>
								<th>Play</th>
							</tr>				
						</thead>
						<tbody>
						<c:forEach var="testcase" items="${testCasesList}">
							<c:if test="${current_task == testcase.task_id}">
								<tr id="task-${testcase.task_id}" class="current-task">
							</c:if>
							<c:if test="${current_task != testcase.task_id}">
								<tr id="task-${testcase.task_id}">
							</c:if>
								<td>${testcase.task_id}</td>
								<td>${testcase.status}</td>
								<td><a title="Click to start"
								href="startTestsSelected?id_testcase=${testcase.testcase_id}&id_ticket=${testcase.id_ticket}&status=${testcase.status}&pre_requisite=${testcase.pre_requisite}&testcase_description=${testcase.testcase_description}&results=${testcase.results}&id_task=${testcase.task_id}">
								<span class="glyphicon glyphicon-play"></span>
								</a></td>
							</tr>
							
						</c:forEach>
						</tbody>
					</table>
		</div>
		<form:form id="form1" method="POST" commandName="testCase" action="startTestsP">
			<div class="container">
				<label>Pré-requisite</label>
				<div class="form-group">
					<textarea readOnly="true" class="form-control" rows="4" cols="25">${pre_requisite}</textarea>
				</div>
				<label>Description</label>
				<div class="form-group">
					<textarea readOnly="true" class="form-control" rows="4" cols="30">${testcase_description}</textarea>
				</div>
				<label>Results</label>
				<div class="form-group">
					<textarea readOnly="true" class="form-control" rows="4" cols="30">${results}</textarea>
				</div>
				<label>Comments</label>
				<div class="form-group">
					<form:textarea path="comments" rows="4" cols="30"
									cssClass="form-control" value="" />
				</div>

				<input type="hidden" path="testcase_id" name="id_testcase" value="${id_testcase}" />
				<input type="hidden" path="status" name="status" value="${status}" />
				<input type="hidden" path="id_ticket" name="id_ticket" value="${id_ticket}" />
				<!--<a href="refreshTicket?project=${project}&tag=${tag}&jira=${jira}&id_release=${id_release}"
				   class="btn btn-primary">Back</a> -->
				
				<button type="submit" class="btn btn-primary" 
						name="action" value="passed">Passed</button>
				<button type="submit" class="btn btn-danger" id="buttonfloat"
						name="action" value="failed">Failed</button> 
						
	
			</div>
		</form:form>
	</div>
</body>

</html>