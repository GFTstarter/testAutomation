<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<%@ page isELIgnored="false"%>
<meta charset="UTF-8">

<script src="<c:url value="/resources/js/jquery-1.11.1.min.js"/>"></script>
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>" />
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" />
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/dataTables.bootstrap.css"/>" />
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/js/jquery.dataTables.min.js"/>"></script>
<script src="<c:url value="/resources/js/dataTables.bootstrap.js"/>"></script>
<script src="<c:url value="/resources/js/releaseTickets.js"/>"></script>

<title>Blotter - Test cases</title>
</head>
<body>
	<!-- Header -->
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

	<!-- Main container -->
	<div class="row">
		<div class="col-md-6">
			<ol class="breadcrumb">
				<li><a href="back">/ Releases</a></li>
				<li class="active">${project}</li>
			</ol>
		</div>

		<!-- Hidden parameters and buttons -->
		<div class="col-md-6" id="buttonsRight">
			<input type="hidden" name="id_release" value="${id_release}" /> 
			<input type="hidden" name="project" value="${project}" /> 
			<input type="hidden" name="tag" value="${tag}" /> 
				<a href="changeRelease?id_release=${id_release}&project=${project}&tag=${tag}"
					class="btn btn-primary">Transfer tickets to another release 
					<span class="glyphicon glyphicon-transfer"></span>
				</a> 
				<a href="uploadFile?id_release=${id_release}&project=${project}&tag=${tag}"
					class="btn btn-primary">Import XML 
					<span class="glyphicon glyphicon-import"></span>
				</a> 
				<a href="getZip?id_release=${id_release}&tag=${tag}&project=${project}"
					class="btn btn-primary">Export all 
					<span class="glyphicon glyphicon-compressed"></span>
				</a>
				
		</div>
		<div class="footer"></div>

		<!-- Messages -->
		<c:if test="${param.smsg != null}">
			<div class="alert alert-success" role="alert">Tickets have been
				transfered successfully.</div>
		</c:if>

		<!-- Ticket tables -->
		<table id="table_id" class="table table-hover table-striped">
			<thead>
				<tr>
					<th>JIRA</th>
					<th>Description</th>
					<th>Environment</th>
					<th>Developer</th>
					<th>Tester</th>
					<th>Status</th>
					<th></th>
					<th></th>
					<th></th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="ticket" items="${ticketList}">
					<tr>
						<td><c:choose>
								<c:when test="${ticket.testcase_status == 'Failed'}">
									<a href="http://dbatlas.db.com/jira02/browse/${ticket.jira}"
										target="_blank" class="failedLink">${ticket.jira}</a>
								</c:when>
								<c:otherwise>
									<a href="http://dbatlas.db.com/jira02/browse/${ticket.jira}"
										target="_blank">${ticket.jira}</a>
								</c:otherwise>
							</c:choose></td>
						<td>${ticket.description}</td>
						<td>${ticket.environment}</td>
						<td class="developer">${ticket.developer}</td>
						<td class="tester">${ticket.tester}</td>
						<td class="status">${ticket.status}</td>

						<td><a title="Click to edit the row data" href="#"
							class="edit" data-toggle="modal" data-id="${ticket.id_ticket}">
								<span class="glyphicon glyphicon-pencil"></span>
						</a></td>

						<td><a title="Click to details"
							href="testCases?id_ticket=${ticket.id_ticket}&jira=${ticket.jira}&tag=${tag}&description=${ticket.description}&developer=${ticket.developer}&tester=${ticket.tester}&environment=${ticket.environment}&run_time=${ticket.run_time}">
								<span class="glyphicon glyphicon-list"></span>
						</a></td>
						
						<td><a title="Click start tests" href="startTests?id_ticket=${ticket.id_ticket}&jira=${ticket.jira}&tag=${tag}&description=${ticket.description}&developer=${ticket.developer}&tester=${ticket.tester}&environment=${ticket.environment}&run_time=${ticket.run_time}"  
							   onclick="window.open('startTests?id_ticket=${ticket.id_ticket}&jira=${ticket.jira}&tag=${tag}&description=${ticket.description}&developer=${ticket.developer}&tester=${ticket.tester}&environment=${ticket.environment}&run_time=${ticket.run_time}', 'newwindow', 'width=450, height=650'); return false;"
							   data-id="${ticket.id_ticket}"> <span
							   class="glyphicon glyphicon-play"></span>
						</a></td>

						<td><c:choose>
								<c:when test="${ticket.testcase_status == 'Failed'}">
									<span class="glyphicon glyphicon-export"></span>
								</c:when>
								<c:otherwise>
									<a title="Click to Excel"
										href="getExcel/${ticket.jira}_Test_Plan?id_ticket=${ticket.id_ticket}&jira=${ticket.jira}&tag=${tag}&environment=${ticket.environment}&developer=${ticket.developer}&tester=${ticket.tester}&description=${ticket.description}&run_time=${ticket.run_time}"><span
										class="glyphicon glyphicon-export"></span></a>
								</c:otherwise>
							</c:choose></td>

						<%-- 						<td><c:choose> --%>
						<%-- 							<c:when test="${ticket.testcase_status == 'Failed'}"> --%>
						<!-- 								<a title="Click to delete" -->
						<%-- 									href="sendMail?developer=${ticket.developer}&jira=${ticket.jira}"><span --%>
						<!-- 									class="glyphicon glyphicon-envelope"></span></a> -->
						<%-- 							</c:when> --%>
						<%-- 							<c:otherwise> --%>
						<!-- 								<span class="glyphicon glyphicon-envelope"></span> -->
						<%-- 							</c:otherwise> --%>
						<%-- 						</c:choose></td> --%>

						<td><a title="Click to delete" href="#" class="delete"
							data-toggle="modal" data-id="${ticket.id_ticket}"> <span
								class="glyphicon glyphicon-remove"></span>
						</a></td>
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br> <a href="back" class="btn btn-primary">Back</a>
	</div>

	<!-- Modal to edit the ticket -->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Edit</h4>
				</div>
				<form:form method="POST" commandName="ticket" action="updateTicket"
					role="form">
					<div class="modal-body">
						<div class="form-group">
							<label for="ticketDeveloper">Developer</label>
							<form:input path="developer" type="email" name="ticketDeveloper"
								id="ticketDeveloper" value="" cssClass="form-control" />
							<form:errors path="developer" cssClass="error" />
						</div>
						<div class="form-group">
							<label for="ticketTester">Tester</label>
							<form:input path="tester" type="email" name="ticketTester" 
							id="ticketTester" value="" cssClass="form-control" />
							<form:errors path="tester" cssClass="error" />
						</div>
						<div class="form-group">
							<label>Status</label>
							<form:select path="status" id="ticketStatus"
								cssClass="form-control">
								<form:option value="Open" label="Open" />
								<form:option value="In UAT" label="In UAT" />
								<form:option value="Ready for Development" label="Ready for Development" />
								<form:option value="In Testing" label="In Testing" />
								<form:option value="Ready for Testing" label="Ready for Testing" />
								<form:option value="On Hold" label="On Hold" />
								<form:option value="Failed" label="Failed" />
							</form:select>
						</div>
						<form:input type="hidden" cssClass="form-control" path="id_release" name="id_release" id="id_release" value="${id_release}" /> 
						<input type="hidden" name="project" id="project" value="${project}" /> 
						<input type="hidden" name="tag" id="tag" value="${tag}" /> 
						<form:input type="hidden" cssClass="form-control" path="id_ticket" name="id_ticket" id="id_ticket" value="${id_ticket}" />
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Save changes</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<!-- Modal to delete the ticket -->
	<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Information!</h4>
				</div>
				<div class="modal-body">Do you want to remove this record?</div>
				<form:form method="POST" commandName="ticket" action="deleteTicket"
					role="form">
					<div class="modal-footer">
						<input type="hidden" name="delete_id_ticket" id="delete_id_ticket"
							value="${ticketId}" />
						<button type="submit" class="btn btn-danger">Confirm</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<!-- Footer -->
	<div class="footer">
		<div class="col-md-6">
			<p>© GFT Group</p>
		</div>
	</div>
</body>
</html>
