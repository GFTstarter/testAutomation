<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="startTestsTemplate">
    <tiles:putAttribute name="body">
			<!-- Content -->
			<div id="startTest">
				<div class="container">
					<c:if test="${param.nmsg == 1}">
						<div class="alert alert-danger" role="alert">No register found.</div>
					</c:if>
					<table id="addTestCases"
						class="table table-bordered table-hover table-striped">
						<thead>
							<tr>
								<th>Step</th>
								<th>Status</th>
								<th>Play</th>
							</tr>
						</thead>
						<tbody>
						<!-- If the current task id matches the taks_id in the at the moment of the loop, it will receive a different colour for the row -->
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
									href="startTestsSelected?id_testcase=${testcase.testcase_id}&id_ticket=${testcase.id_ticket}&status=${testcase.status}&pre_requisite=${testcase.pre_requisite}&testcase_description=${testcase.testcase_description}&results=${testcase.results}&comments=${testcase.comments}&id_task=${testcase.task_id}">
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
							<form:textarea path="comments" rows="4" cols="30" placeholder="${comments}"
											cssClass="form-control" value="${comments}" />
						</div>
		
						<input type="hidden" path="testcase_id" name="id_testcase" value="${id_testcase}" />
						<input type="hidden" path="status" name="status" value="${status}" />
						<input type="hidden" path="id_ticket" name="id_ticket" value="${id_ticket}" />
						<!--<a href="refreshTicket?project=${project}&tag=${tag}&jira=${jira}&id_release=${id_release}"
						   class="btn btn-primary">Back</a> -->
						
						<c:choose>
							<c:when test="${param.nmsg == 1}">
								<span disabled="true" class="btn btn-primary">Passed</span>
								<span disabled="true" class="btn btn-danger" id="buttonfloat">Failed</span>
							</c:when>
							<c:otherwise>
								<button type="submit" class="btn btn-primary" 
								name="action" value="passed">Passed</button>
								<button type="submit" class="btn btn-danger" id="buttonfloat"
										name="action" value="failed">Failed</button> 
							</c:otherwise>
						</c:choose>
					</div>
				</form:form>
			</div>
	  </tiles:putAttribute>
</tiles:insertDefinition>