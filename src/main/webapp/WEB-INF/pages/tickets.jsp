<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="ticketTemplate">
    <tiles:putAttribute name="body">
		<!-- Main container -->
		<div class="container">
			<div class="row">
				<div class="col-md-3">
					<ol class="breadcrumb">
						<li><a href="back">/ Releases</a></li>
						<li class="active">${project}</li>
					</ol>
				</div>
		
				<!-- Hidden parameters and buttons -->
				<div class="col-md-9" id="buttonsRight">
					<input type="hidden" name="id_release" value="${id_release}" /> 
					<input type="hidden" name="project" value="${project}" /> 
					<input type="hidden" name="tag" value="${tag}" /> 
					<a
						href="changeRelease?id_release=${id_release}&project=${project}&tag=${tag}"
						class="btn btn-primary">Transfer tickets to another release <span
						class="glyphicon glyphicon-transfer"></span>
					</a> 
					<a href="#" class="add btn btn-primary">Add	new Ticket</a> 
					<c:if test="${parameter.importJIRAxmlButton == 1}">
						<a
							href="uploadFile?id_release=${id_release}&project=${project}&tag=${tag}"
							class="btn btn-primary">Import JIRA XML <span
							class="glyphicon glyphicon-import"></span>
						</a>
					</c:if> 
					<a
						href="getZip?id_release=${id_release}&tag=${tag}&project=${project}"
						class="btn btn-primary">Export all <span
						class="glyphicon glyphicon-compressed"></span>
					</a>

				</div>
				<div class="footer"></div>
		
				<!-- Messages -->
				<c:if test="${param.smsg != null}">
					<div class="alert alert-success" role="alert">Tickets have been	transfered successfully.</div>
				</c:if>
				
				<!-- Validation messages -->
				<div id="validationAlert" class="alert alert-danger" role="alert">${param.msg != null}</div>
				<div id="opFailTicketResponse" class="alert alert-danger" role="alert"></div>
		
				<!-- Ticket tables -->
				<table id="table_id" class="table table-hover table-striped">
					<thead>
						<tr>
							<th>Ticket</th>
							<th>Description</th>
							<th>Environment</th>
							<th>Developer</th>
							<th>Tester</th>
							<th>Status</th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="ticket" items="${ticketList}">
							<tr>
								<td class="jira"><c:choose>
										<c:when test="${ticket.testcase_status == 'Failed'}">
											<a href="http://dbatlas.db.com/jira02/browse/${ticket.jira}"
												target="_blank" class="failedLink">${ticket.jira}</a>
										</c:when>
										<c:otherwise>
											<a href="http://dbatlas.db.com/jira02/browse/${ticket.jira}"
												target="_blank">${ticket.jira}</a>
										</c:otherwise>
									</c:choose></td>
								<td class="description"><p align="justify">${ticket.description}</p></td>
								<td class="environment">${ticket.environment}</td>
								<td class="developer">${ticket.developer}</td>
								<td class="tester">${ticket.tester}</td>
								<td class="status">${ticket.status}</td>
		
								<td><a title="Click to edit the row data" href="#"
									class="edit" data-toggle="modal" data-id="${ticket.id_ticket}">
										<span class="glyphicon glyphicon-pencil"></span>
								</a></td>
		
								<td><a title="Click to details"
									href="testCases?id_ticket=${ticket.id_ticket}&jira=${ticket.jira}&tag=${tag}&description=${ticket.description}&developer=${ticket.developer}&tester=${ticket.tester}&environment=${ticket.environment}&run_time=${ticket.run_time}&status=${ticket.status}">
										<span class="glyphicon glyphicon-list"></span>
								</a></td>
								
								<td><a title="Click start tests" href=""
									   onclick="window.open('startTests?id_ticket=${ticket.id_ticket}', 'newwindow', 'width=450, height=650'); return false;"
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
								<!-- CONFIGURED BUT NOT WORKING -->
								<!--<td>
								
								 	<a title="Send Email"
												href="sendMail?developer=${ticket.developer}&jira=${ticket.jira}"><span
												class="glyphicon glyphicon-envelope"></span></a>		
								</td> -->
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
						<form:form method="POST" commandName="ticket" class="form-horizontal" action="updateTicket"
							role="form">
							<div class="modal-body">
								<div class="form-group">
									<label for="ticketJira" class="col-sm-2 control-label">Ticket</label>
									<div class="col-sm-10">
										<form:input path="jira" cssClass="form-control" id="ticketJira"/>
										<form:errors path="jira" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="ticketDescription" class="col-sm-2 control-label">Description</label>
									<div class="col-sm-10">
										<form:input path="description" cssClass="form-control" id="ticketDescription"/>
										<form:errors path="description" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="ticketEnvironment" class="col-sm-2 control-label">Environment</label>
									<div class="col-sm-10">
										<form:input path="environment" cssClass="form-control" id="ticketEnvironment"/>
										<form:errors path="environment" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="ticketDeveloper" class="col-sm-2 control-label">Developer</label>
									<div class="col-sm-10">
										<form:input path="developer" type="email" name="ticketDeveloper"
											id="ticketDeveloper" value="" cssClass="form-control" />
										<form:errors path="developer" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="ticketTester" class="col-sm-2 control-label">Tester</label>
									<div class="col-sm-10">
										<form:input path="tester" type="email" name="ticketTester" 
										id="ticketTester" value="" cssClass="form-control" />
										<form:errors path="tester" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">Status</label>
									<div class="col-sm-10">
										<form:select path="status" id="ticketStatus"
											cssClass="form-control">
											<form:option value="Open" label="Open" />
											<form:option value="In UAT" label="In UAT" />
											<form:option value="Ready for Development" label="Ready for Development" />
											<form:option value="In Testing" label="In Testing" />
											<form:option value="Ready for Testing" label="Ready for Testing" />
											<form:option value="On Hold" label="On Hold" />
											<form:option value="Failed" label="Failed" />
											<form:option value="Passed" label="Passed" />
										</form:select>
									</div>
								</div>
								<form:input type="" cssClass="form-control" path="id_release" name="id_release" id="id_release" value="${id_release}" /> 
								<input type="" name="project" id="project" value="${project}" /> 
								<input type="" name="tag" id="tag" value="${tag}" /> 
								<form:input type="" cssClass="form-control" path="id_ticket" name="id_ticket" id="id_ticket" value="${id_ticket}" />
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
			
			<!-- Modal to add new ticket -->
			<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Add new Ticket</h4>
						</div>
						<!-- AJAX - TO BE TESTED-> action="${pageContext.request.contextPath}/createTicket.json"  -->
						<form:form id="createTicket" cssClass="form-horizontal"
						action="addTicket" commandName="ticket">
							<div class="modal-body">
								<div class="form-group">
									<label for="inputTicket" class="col-sm-2 control-label">Ticket</label>
									<div class="col-sm-10">
										<form:input path="jira" cssClass="form-control"/>
										<form:errors path="jira" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="inputDescription" class="col-sm-2 control-label">Description</label>
									<div class="col-sm-10">
										<form:input path="description" cssClass="form-control"/>
										<form:errors path="description" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="inputEnvironment" class="col-sm-2 control-label">Environment</label>
									<div class="col-sm-10">
										<form:input path="environment" cssClass="form-control"/>
										<form:errors path="environment" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="inputDeveloper" class="col-sm-2 control-label">Developer</label>
									<div class="col-sm-10">
										<form:input path="developer" type="text" cssClass="form-control" />
										<form:errors path="developer" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="inputTester" class="col-sm-2 control-label">Tester</label>
									<div class="col-sm-10">
										<form:input path="tester" type="text" cssClass="form-control" />
										<form:errors path="tester" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">Status</label>
									<div class="col-sm-10">
										<form:select path="status" id="ticketStatus"
											cssClass="form-control">
											<form:option value="Open" label="Open" />
											<form:option value="In UAT" label="In UAT" />
											<form:option value="Ready for Development" label="Ready for Development" />
											<form:option value="In Testing" label="In Testing" />
											<form:option value="Ready for Testing" label="Ready for Testing" />
											<form:option value="On Hold" label="On Hold" />
											<form:option value="Failed" label="Failed" />
											<form:option value="Passed" label="Passed" />
										</form:select>
									</div>
								</div>
							</div>
							<input type="" name="id_release" value="${id_release}" /> 
							<input type="" name="project" value="${project}" /> 
							<input type="" name="tag" value="${tag}" /> 
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
								<button type="submit" class="btn btn-primary">Save</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
			
	</tiles:putAttribute>
</tiles:insertDefinition>