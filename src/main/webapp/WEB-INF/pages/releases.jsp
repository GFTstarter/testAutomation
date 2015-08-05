<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="releaseTemplate">
    <tiles:putAttribute name="body">
		<!-- Main container -->
		<div class="container">
			<div class="row">
				<div class="col-md-6">
					<ol class="breadcrumb">
						<li class="active">/ Releases</li>
					</ol>
				</div>
				<div class="col-md-6">
					<a href="#" class="add btn btn-primary" id="newRelease">Add new release</a>
				</div>
				
				<div class="footer"></div>
		
				<!-- Validation messages -->
				<c:if test="${param.msg != null}">
					<div class="alert alert-danger" role="alert">All fields must not be empty.</div>
				</c:if>
		
				<div align="center" class="alert alert-success col-md-12" role="alert" id="addReleaseResponse"></div>
				<div align="center" class="alert alert-danger col-md-12" role="alert" id="opFailReleaseResponse"></div>
				<!-- Releases table -->
				<table id="releases" class="table table-hover table-striped">
					 <thead>
						<tr>
							<th>Project</th>
							<th>Tag</th>
							<th>Name</th>
							<th>Target Date</th>
							<th>&nbsp;</th>
							<th>&nbsp;</th>
							<th>&nbsp;</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
					<!-- <tbody>
						<c:forEach var="release" items="${releaseList}">
							<tr>
								<td class="project">${release.project}</td>
								<td class="tag">${release.tag}</td>
								<td class="name">${release.name}</td>
								<td class="target_date">${release.target_date}</td>
		
								<td><a title="Click to edit the row data" href="#"
									class="edit" data-toggle="modal" data-id="${release.id_release}">
										<span class="glyphicon glyphicon-pencil"></span>
								</a></td>
		
								<td><a title="Click to options"
									href="refreshTicket?project=${release.project}&tag=${release.tag}&id_release=${release.id_release}">
									<span class="glyphicon glyphicon-option-horizontal"></span> </a>
								</td>
		
								<td><a title="Click to delete" href="#" class="delete"
									data-toggle="modal" data-id="${release.id_release}"> <span
										class="glyphicon glyphicon-remove"></span>
								</a></td>
							</tr>
						</c:forEach>
					</tbody> -->
				</table>
				<!-- <a href="#" class="btn btn-primary" id="refresh">Refresh</a>  -->
			</div>
		</div>
		
			<!-- Modal to add new release -->
			<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Add new Release</h4>
						</div>
						<!-- AJAX-> action="${pageContext.request.contextPath}/createRelease.json"  -->
						<form:form id="createRelease" cssClass="form-horizontal"
						action="${pageContext.request.contextPath}/createRelease.json" commandName="release">
							<div class="modal-body">
								<div class="form-group">
									<label for="inputProject" class="col-sm-2 control-label">Project</label>
									<div class="col-sm-10">
										<form:input path="project" cssClass="form-control"
											placeholder="Project" />
										<form:errors path="project" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="inputTag" class="col-sm-2 control-label">Tag</label>
									<div class="col-sm-10">
										<form:input path="tag" cssClass="form-control"
											placeholder="xx.xx.x" />
										<form:errors path="tag" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="inputName" class="col-sm-2 control-label">Name</label>
									<div class="col-sm-10">
										<form:input path="name" cssClass="form-control"
											placeholder="Release name" />
										<form:errors path="name" cssClass="error" />
									</div>
								</div>
								<div class="form-group">
									<label for="inputTargetDate" class="col-sm-2 control-label">Target Date</label>
									<div class="col-sm-10">
										<form:input path="target_date" type="text" id="datepicker"
											cssClass="form-control" placeholder="dd/mm/yyyy" />
										<form:errors path="target_date" cssClass="error" />
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
								<button type="submit" class="btn btn-primary">Save</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		
			<!-- Modal to edit the release -->
			<div class="modal fade" id="editModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Edit release</h4>
						</div>
						<form:form id="editRelease" method="POST" action="${pageContext.request.contextPath}/editReleaseAjax.json" commandName="release"
							cssClass="form-horizontal">
							<div class="modal-body">
								<div class="form-group">
									<label for="inputProject" class="col-sm-2 control-label">Project</label>
									<div class="col-sm-10">
										<form:input path="project" cssClass="form-control"
											id="edit_project" name="edit_project" value="" />
									</div>
								</div>
								<div class="form-group">
									<label for="inputTag" class="col-sm-2 control-label">Tag</label>
									<div class="col-sm-10">
										<form:input path="tag" cssClass="form-control" id="edit_tag" name="edit_tag" value="" />
									</div>
								</div>
								<div class="form-group">
									<label for="inputName" class="col-sm-2 control-label">Name</label>
									<div class="col-sm-10">
										<form:input path="name" cssClass="form-control" id="edit_name" name="edit_name" value="" />
									</div>
								</div>
								<div class="form-group">
									<label for="inputTargetDate" class="col-sm-2 control-label">Target Date</label>
									<div class="col-sm-10">
										<form:input path="target_date" type="text" id="edit_target_date"
											name="edit_target_date" value="" cssClass="form-control" />
									</div>
								</div>
								<form:input type="hidden" cssClass="form-control" path="id_release" name="edit_id_release" id="edit_id_release" value="${id_release}" />
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
								<button type="submit" class="btn btn-primary">Save</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		
			<!-- Modal to delete the release -->
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
						
						<form:form id="deleteRelease" method="POST" action="${pageContext.request.contextPath}/deleteReleaseAjax.json" commandName="release" cssClass="form-horizontal">
							<div class="modal-footer">
								<form:input type="hidden" name="delete_id_release" id="delete_id_release" value="" path="id_release"/>
								<button type="submit" class="btn btn-danger">Confirm</button>
								<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
	  </tiles:putAttribute>
</tiles:insertDefinition>


