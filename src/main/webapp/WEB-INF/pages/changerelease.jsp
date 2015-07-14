<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="changereleaseTemplate">
    <tiles:putAttribute name="body">
		<div class="row" id="adjust">
			<div class="col-md-6 col-md-offset-3">
				<input type="hidden" name="id_release" value="${id_release}" /> <input
					type="hidden" name="project" value="${project}" /> <input
					type="hidden" name="tag" value="${tag}" />
				<form:form role="form" method="POST" action="changeRelease"	commandName="ReleaseTicket">
					<div class="form-group">
						<label>Choose the tickets: </label>
						<form:select path="ticketList" cssClass="form-control">
							<form:options items="${ticketList}" itemLabel="jira" itemValue="id_ticket" />
						</form:select>
					</div>
					<div class="form-group">
						<label>Choose the release to transfer to: </label>
						<form:select path="releaseList" cssClass="form-control"	multiple="false">
							<form:options items="${releaseList}" itemLabel="projecttag"	itemValue="id_release" />
						</form:select>
					</div>
					<br>
					<input type="hidden" name="id_release" value="${id_release}" />
					<input type="hidden" name="project" value="${project}" />
					<input type="hidden" name="tag" value="${tag}" />
					<a href="ticketsList?project=${project}&tag=${tag}&id_release=${id_release}"
						class="btn btn-primary">Back</a>
					<button type="submit" class="btn btn-primary" id="upload">Save changes</button>
				</form:form>
			</div>
		</div>
  </tiles:putAttribute>
</tiles:insertDefinition>