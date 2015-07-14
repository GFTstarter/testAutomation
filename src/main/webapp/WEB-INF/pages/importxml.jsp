<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:insertDefinition name="importxmlTemplate">
    <tiles:putAttribute name="body">
    <div class="container">
		<div class="row" id="adjust">
			<div class="col-md-6 col-md-offset-3">
				<form:form id="form" method="POST" action="uploadFile"
					enctype="multipart/form-data"
					onsubmit="javascript:return validate(this);">
					<div class="form-group">
						<input type="hidden" name="id_release" value="${id_release}" />
						<input type="hidden" name="project" value="${project}" />
						<input type="hidden" name="tag" value="${tag}" /> <label>Import XML</label> 
						<input type="file" name="file">
						 
						<p class="help-block">Observations:</p>
						<ul>
						  <li class="help-block">Only XML files are allowed;</li>
						  <li class="help-block">Some generated JIRA's XMLs may contain the '-' character on the beginning of the first lines of the document that might cause problems when importing.
						  </li>
						</ul> 
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
	</div>
  </tiles:putAttribute>
</tiles:insertDefinition>