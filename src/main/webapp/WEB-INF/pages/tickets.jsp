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
					<input type="hidden" name="id_release" id="idRelease" value="${id_release}" /> 
					<input type="hidden" name="project" value="${project}" /> 
					<input type="hidden" name="tag" id="ticketTag" value="${tag}" /> 
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
				<c:if test="${param.emsg != null}">
					<div class="alert alert-danger" role="alert">Can't be deleted. This ticket has test cases assigned to it.</div>
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
						<!-- updateTicket -->
						<form:form id="editTicket" method="POST" commandName="ticket" class="form-horizontal" action="${pageContext.request.contextPath}/editTicketAjax.json"
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
						<!-- deleteTicket -->
						<form:form id="deleteTicket" method="POST" commandName="ticket" action="${pageContext.request.contextPath}/deleteTicketAjax.json"
							role="form">
							<div class="modal-footer">
								<input type="hidden" name="delete_id_ticket" id="delete_id_ticket" value="${ticketId}" />
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
						<!-- addTicket -->
						<form:form id="createTicket" cssClass="form-horizontal" action="${pageContext.request.contextPath}/createTicket.json" commandName="ticket">
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
										<form:input path="developer" type="email" cssClass="form-control" />
										<form:errors path="developer" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="inputTester" class="col-sm-2 control-label">Tester</label>
									<div class="col-sm-10">
										<form:input path="tester" type="email" cssClass="form-control" />
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
							<input type="hidden" name="id_release" value="${id_release}" /> 
							<input type="hidden" name="project" value="${project}" /> 
							<input type="hidden" name="tag" value="${tag}" /> 
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