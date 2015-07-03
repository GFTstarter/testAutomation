<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE HTML>
<tiles:insertDefinition name="testCaseTemplate">
    <tiles:putAttribute name="body">
		<!-- Main container -->
		<div class="container">
			<div class="row">
				<div>
					<ol class="breadcrumb">
						<li><a href="back">/ Releases</a></li>
						<li><a href="refreshTicket?project=${project}&tag=${tag}&jira=${jira}&id_release=${id_release}">${project}</a></li>
						<li class="active">${jira}</li>
					</ol>
					<h2></h2>
				</div>
		
				<input type="hidden" name="id_ticket" value="${id_ticket}" /> 
				<input type="hidden" name="description" value="${description}" /> 
				<input type="hidden" name="tag" value="${tag}" />
				<input type="hidden" name="environment" value="${environment}" /> 
				<input type="hidden" name="developer" value="${developer}" /> 
				<input type="hidden" name="tester" value="${tester}" />
				<input type="hidden" name="task_id" value="${task_id}" />
		
				<!-- Validation messages -->
				<c:if test="${param.msg != null}">
					<div class="alert alert-danger" role="alert">The test case description must not be empty.</div>
				</c:if>
		
				<!-- Panel -->
				<div id="body">
					<div class="panel panel-default">
						<div class="panel-heading">
							<table class="tcHeader">
								<tr>
									<th>Release:</th>
									<td><c:out value="${tag}" /></td>
									<td></td>	
									<th rowspan="5" class="lastLineCenter">${description}</th>
									<td class="lastLine"></td>
								</tr>
								<tr>
									<th>Ticket:</th>
									<td>${jira}</td>
									<td></td>
								</tr>
								<tr>
									<th>Environment:</th>
									<td>${environment}</td>
									<td></td>
								</tr>
								<tr>
									<th>Developer:</th>
									<td>${developer}</td>
									<td></td>
								</tr>
								<tr>
									<th>Test Case created by:</th>
									<td>${tester}</td>
									<td></td>
								</tr>
								<tr>
									<th class="lastLine">Time to run all tests:</th>
									<td class="lastLine time">${run_time}</td>
									<td class="lastLine"><a title="Click to edit the field" 
										href="#" class="editHeader" data-toggle="modal" data-id="${id_ticket}">
											<span class="glyphicon glyphicon-pencil"></span>
									</a></td>				
								</tr>
							</table>
						</div>
						<div class="panel-body" >
							<!-- Test cases table --> 
							<table id="testCases" class="table table-hover table-striped">
								<thead>
									<tr>
										<th>Task ID</th>
										<th>Status</th>
										<th>Tested By</th>
										<th>Tested On</th>
										<th>Pre-Requisite</th>
										<th>Description</th>
										<th>Expected Results</th>
										<th>Comments</th>
										<th>Testcase ID</th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
										<th></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="testcase" items="${testCasesList}">
										<tr>
											<td class="testCaseTaskId"></td>
											<td class="testCaseStatus">${testcase.status}</td>
											<td class="testCaseTestedBy">${testcase.tested_by}</td>
											<td class="testCaseTestedOn">${testcase.tested_on}</td>
											<td class="testCasePreRequisite">${testcase.pre_requisite}</td>
											<td class="testCaseDescription" style="width: 100px;">${testcase.testcase_description}</td>
											<td class="testCaseResults">${testcase.results}</td>
											<td class="testCaseComments">${testcase.comments}</td>
											<td class="testCaseId">${testcase.testcase_id}</td>
											<!-- Button order -->
											<td><a title="Click to move up the row data" href="#"
												class="moveup" data-toggle="modal"
												data-id="${testcase.testcase_id}"> 
												<span class="glyphicon glyphicon-chevron-up"></span>
											</a>
											<a title="Click to move down the row data" href="#"
												class="movedown" data-toggle="modal"
												data-id="${testcase.testcase_id}"> 
												<span class="glyphicon glyphicon-chevron-down"></span>
											</a></td>
																												
											<!-- Button edit -->
											<td><a title="Click to edit the row data" href="#"
												class="edit" data-toggle="modal"
												data-id="${testcase.testcase_id}"> 
												<span class="glyphicon glyphicon-pencil"></span>
											</a></td>
		
											<!-- Button Reset Test -->
											<td><a title="Click to Reset Tests field" href="#" class="reset"
												data-toggle="modal" data-id="${testcase.testcase_id}">
												 <span class="glyphicon glyphicon-refresh"></span>
											</a></td>
											
											<!-- Button Play Test -->  
											<td><a title="Click to Play Test" href=""
													onclick="window.open('startTestsSelected?id_testcase=${testcase.testcase_id}&id_ticket=${testcase.id_ticket}&status=${testcase.status}&pre_requisite=${testcase.pre_requisite}&testcase_description=${testcase.testcase_description}&results=${testcase.results}&comments=${testcase.comments}&id_task=${testcase.task_id}', 'newwindow', 'width=450, height=650'); return false;"
													class="play" data-id="${testcase.testcase_id}"> 
												<span class="glyphicon glyphicon-play"></span>
											</a></td>
											
											<!-- Button Delete -->
											<td><a title="Click to delete" href="#" class="delete"
												data-toggle="modal" data-id="${testcase.testcase_id}"> 
												<span class="glyphicon glyphicon-remove"></span>
											</a></td>
										</tr>								
									</c:forEach> 
								</tbody>
							</table>
							
						</div>
					</div>
					<a class="btn btn-primary" id="saveSort">Save</a>
					<div class="footer"></div>
					
					<!-- Add new testCase table -->
					<h4>Add new Test Case</h4>
					<form:form method="POST" commandName="testCase" action="testCases">
						<table id="addTestCases" class="table table-bordered">
							<thead>
								<tr>
								<!-- Column TaskID on table of a new testCases removed, TaskID is being incremented and reordered when a delete occurs -->
									<th style="display:none;">Task ID</th>
									<th>Status</th>
									<th>Tested By</th>
									<th>Tested On</th>
									<th>Pre-Requisite</th>
									<th>Description</th>
									<th>Expected Results</th>
									<th>Comments</th>
								</tr>				
							</thead>
							<tbody>
								<tr> 
									<td style="display:none;"><form:input readOnly="true" path="task_id" cssClass="form-control"
											placeholder="Task ID" size="10" value="${task_id}" /></td> 
									<td><form:select path="status" cssClass="form-control">
											<form:option value="On hold" label="On hold" />
											<form:option value="In development" label="In development" />
											<form:option value="Ready for test" label="Ready for test" />
											<form:option value="Testing In SIT" label="Testing In SIT" />
											<form:option value="Ready for UAT" label="Ready for UAT" />
											<form:option value="Testing in UAT" label="Testing in UAT" />
											<form:option value="Closed" label="Closed" />		
											<form:option value="Failed" label="Failed" />
											<form:option value="Passed" label="Passed" />	
										</form:select></td>
									<td><form:input path="tested_by" cssClass="form-control"
											placeholder="Tested By" value="${user_tested_by}" /></td>
									<td><form:input path="tested_on" type="text"
											cssClass="form-control" id="datepicker" /></td>
									<td><form:textarea path="pre_requisite" rows="6" cols="25"
											cssClass="form-control" value="${error_pre_requisite}" /></td>
									<td><form:textarea path="testcase_description" rows="6"
											cols="30" cssClass="form-control" /></td>
									<td><form:textarea path="results" rows="6" cols="25"
											cssClass="form-control" value="${error_results}" /></td>
									<td><form:textarea path="comments" rows="6" cols="30"
											cssClass="form-control" value="${error_comments}" /></td>
								</tr>
							</tbody>
						</table>
		
						<a href="refreshTicket?project=${project}&tag=${tag}&jira=${jira}&id_release=${id_release}"
						   class="btn btn-primary">Back</a>
		
						<button type="submit" class="btn btn-primary newTestCase" id="buttonfloat">Add new Test Case</button>
						<input type="hidden" name="id_ticket" value="${id_ticket}" />
						<input type="hidden" name="td_description" value="${description}" />
						<input type="hidden" name="td_tag" value="${tag}" />
						<input type="hidden" name="td_environment" value="${environment}" />
						<input type="hidden" name="td_developer" value="${developer}" />
						<input type="hidden" name="td_tester" value="${tester}" />
						<input type="hidden" name="td_run_time" value="${run_time}" />
						<input type="hidden" name="td_jira" value="${jira}" />
					</form:form>
				</div>
		
				<!-- Modal to edit test case -->
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
							<!--  updateTestCases  -->
							<form:form id="editTestCase" method="POST" commandName="testCase" action="${pageContext.request.contextPath}/editTestCaseAjax.json" role="form">
								<div class="modal-body">
									<div class="form-group">
										<label for="testCaseStatus">Status</label>
										<form:select path="status" cssClass="form-control" id="testCaseStatus">
											<form:option value="On hold" label="On hold" />
											<form:option value="In development" label="In development" />
											<form:option value="Ready for test" label="Ready for test" />
											<form:option value="Testing In SIT" label="Testing In SIT" />
											<form:option value="Ready for UAT" label="Ready for UAT" />
											<form:option value="Testing in UAT" label="Testing in UAT" />
											<form:option value="Closed" label="Closed" />
											<form:option value="Failed" label="Failed" />
											<form:option value="Passed" label="Passed" />					
										</form:select>
									</div>
									<div class="form-group">
										<label for="testCaseTestedBy">Tested By</label>
										<form:input path="tested_by" type="text" name="testCaseTestedBy"
											id="testCaseTestedBy" value="" cssClass="form-control" />
									</div>
									<div class="form-group">
										<label for="testCaseTestedOn">Tested On</label>
										<form:input path="tested_on" type="text" id="testCaseTestedOn"
											cssClass="form-control" value="" name="testCaseTestedOn" />
									</div>
									<div class="form-group">
										<label for="testCasePreRequisite">Pre-Requisite</label>
										<form:textarea path="pre_requisite" id="testCasePreRequisite"
											rows="4" cols="35" name="testCasePreRequisite" value=""
											cssClass="form-control" />
									</div>
									<div class="form-group">
										<label for="testCaseDescription">Description</label>
										<form:textarea path="testcase_description"
											id="testCaseDescription" rows="4" cols="35"
											name="testCaseDescription" value="" cssClass="form-control" />
									</div>
									<div class="form-group">
										<label for="testCaseResults">Expected Results</label>
										<form:textarea path="results" id="testCaseResults" rows="4"
											cols="35" name="testCaseResults" value=""
											cssClass="form-control" />
									</div>
		
									<div class="form-group">
										<label for="testCaseComments">Comments</label>
										<form:textarea path="comments" id="testCaseComments" rows="6"
											cols="35" name="testCaseComments" value=""
											cssClass="form-control" />
									</div>
		
									<form:input path="testcase_id" type="" name="testcase_id" id="testcase_id" value="" />
									<form:input path="task_id" type="" name="task_id" id="taskId" value="" />
									<input type="" name="id_ticket" id="ticket_id" value="${id_ticket}" />
								 	<input type="" name="tc_description" value="${description}" /> 
									<input type="" name="tc_tag" value="${tag}" />
									<input type="" name="tc_environment" value="${environment}" />
									<input type="" name="tc_developer" value="${developer}" />
									<input type="" name="tc_tester" value="${tester}" />
									<input type="" name="tc_run_time" value="${run_time}" />
									<input type="" name="tc_jira" value="${jira}" />
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									<button type="submit" class="btn btn-primary">Save changes</button>
								</div>
							</form:form>
						</div>
					</div>
				</div>
		
				<!-- Modal to edit "Time to run all tests and the whole ticket" -->
				<div class="modal fade" id="modalTime" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Edit the time to run all tests</h4>
							</div>
							<form:form method="POST" commandName="ticket" action="updateTime" role="form" cssClass="form-horizontal">
								<div class="modal-body">
									<div class="form-group">
									<label for="testCaseJira" class="col-sm-2 control-label">Ticket</label>
									<div class="col-sm-10">
										<form:input path="jira" cssClass="form-control" id="testCaseJira" value="${jira}"/>
										<form:errors path="jira" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="testCaseDescription" class="col-sm-2 control-label">Description</label>
									<div class="col-sm-10">
										<form:input path="description" cssClass="form-control" id="testCaseDescription" value="${description}"/>
										<form:errors path="description" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="testCaseEnvironment" class="col-sm-2 control-label">Environment</label>
									<div class="col-sm-10">
										<form:input path="environment" cssClass="form-control" id="testCaseEnvironment" value="${environment}"/>
										<form:errors path="environment" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="testCaseDeveloper" class="col-sm-2 control-label">Developer</label>
									<div class="col-sm-10">
										<form:input path="developer" type="email" name="testCaseDeveloper" value="${developer}"
											id="ticketDeveloper" cssClass="form-control" />
										<form:errors path="developer" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="testCaseTester" class="col-sm-2 control-label">Tester</label>
									<div class="col-sm-10">
										<form:input path="tester" type="email" name="testCaseTester" value="${tester}"
										id="ticketTester" cssClass="form-control" />
										<form:errors path="tester" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="run_time" class="col-sm-2 control-label">Time to run all tests</label>
									<div class="col-sm-10">
										<form:input path="run_time" type="text" name="run_time" value="${run_time}" 
											id="run_time" cssClass="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label lbStatus">Status</label>
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
									<input type="" name="tb_status" id="status" value="${status}" />
									<input type="" name="id_ticket" value="${id_ticket}" />
									<input type="" name="tb_description" value="${description}" />
									<input type="" name="tb_tag" value="${tag}" />
									<input type="" name="tb_environment" value="${environment}" />
									<input type="" name="tb_developer" value="${developer}" />
									<input type="" name="tb_tester" value="${tester}" />
									<input type="" name="tb_jira" value="${jira}" />
								</div>
		
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									<button type="submit" class="btn btn-primary">Save changes</button>
								</div>
							</form:form>
						</div>
					</div>		
				</div>
		
				<!-- Modal Reset Tests "clean fields: Status, Tested By, Tested On, Comments " -->
				<div class="modal fade" id="resetModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Information!</h4>
							</div>
		
							<div class="modal-body">You want to reset the test results?</div>
		
							<form:form method="POST" commandName="testCase" action="resetTestCase" role="form">
								<input type="hidden" name="id_ticket" value="${id_ticket}" />
								<input type="hidden" name="tf_description" value="${description}" />
								<input type="hidden" name="tf_tag" value="${tag}" />
								<input type="hidden" name="tf_environment" value="${environment}" />
								<input type="hidden" name="tf_developer" value="${developer}" />
								<input type="hidden" name="tf_tester" value="${tester}" />
								<input type="hidden" name="tf_run_time" value="${run_time}" />
								<input type="hidden" name="tf_jira" value="${jira}" />
		
								<div class="modal-footer">
									<input type="hidden" name="reset_testcase_id" id="reset_testcase_id" value="${id_ticket}" />
									<button type="submit" class="btn btn-primary">Confirm</button>
									<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
								</div>
							</form:form>
						</div>
					</div>
				</div>
		
				<!-- Modal Play Tests "Set fields: Status, Tested By, Tested On" -->
				<div class="modal fade" id="playModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">Information!</h4>
							</div>
		
							<div class="modal-body">You want to set the test results?</div>
		
							<form:form method="POST" commandName="testCase" action="playTestCase" role="form">
								<input type="hidden" name="id_ticket" value="${id_ticket}" />
								<input type="hidden" name="tg_description" value="${description}" />
								<input type="hidden" name="tg_tag" value="${tag}" />
								<input type="hidden" name="tg_environment" value="${environment}" />
								<input type="hidden" name="tg_developer" value="${developer}" />
								<input type="hidden" name="tg_tester" value="${tester}" />
								<input type="hidden" name="tg_run_time" value="${run_time}" />
								<input type="hidden" name="tg_jira" value="${jira}" />
								<input type="hidden" name="tg_testedBy" value="${tested_by}" />
								<input type="hidden" name="tg_testedOn" value="${tested_on}" />
								<input type="hidden" name="tg_user" value="${user_tested_by}" />												
														
								<div class="modal-footer">
									<input type="hidden" name="play_testcase_id" id="play_testcase_id" value="${id_ticket}" />
									<button type="submit" class="btn btn-primary">Confirm</button>
									<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
								</div>
							</form:form>
						</div>
					</div>
				</div>
		
				<!-- Modal to delete test case -->
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
		
							<form:form method="POST" commandName="testCase" action="deleteTestCase" role="form">
								<input type="hidden" name="id_ticket" value="${id_ticket}" />
								<input type="hidden" name="te_description" value="${description}" />
								<input type="hidden" name="te_tag" value="${tag}" />
								<input type="hidden" name="te_environment" value="${environment}" />
								<input type="hidden" name="te_developer" value="${developer}" />
								<input type="hidden" name="te_tester" value="${tester}" />
								<input type="hidden" name="te_run_time" value="${run_time}" />
								<input type="hidden" name="te_jira" value="${jira}" />
														
								<div class="modal-footer">
									<input type="hidden" name="delete_testcase_id" id="delete_testcase_id" value="${id_ticket}" />
									<button type="submit" class="btn btn-danger">Confirm</button>
									<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	  </tiles:putAttribute>
</tiles:insertDefinition>
