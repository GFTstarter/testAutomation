<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<tiles:insertDefinition name="loginTemplate">
    <tiles:putAttribute name="body">
    <div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<!--  -->
				<c:if test="${param.emsg != null}">
					<div class="alert alert-danger" role="alert">All the fields must not be empty.</div>
				</c:if>
				<c:if test="${param.pcmsg != null}">
					<div class="alert alert-danger" role="alert">Password and password confirmation doesn't match</div>
				</c:if>
				<c:if test="${param.smsg == true}">
					<div class="alert alert-success" role="alert">Login created successfully.</div>
				</c:if>
				<c:if test="${param.smsg == false}">
					<div class="alert alert-danger" role="alert">Username already exists</div>
				</c:if>
				<c:if test="${param.lmsg != null}">
					<div class="alert alert-info" role="alert">You have successfully logged out.</div>
				</c:if>
				<c:if test="${param.mailmsg != null}">
					<div class="alert alert-info" role="alert">An email has been sent with your login information.</div>
				</c:if>
				<form id="loginForm"
					action="<c:url value='j_spring_security_check'/>" method="POST">
					<div class="form-group">
						<label for="InputUsername">Username</label> <input type="text"
							class="form-control" name="j_username"
							placeholder="username@db.com">
					</div>
					<div class="form-group">
						<label for="InputPassword">Password</label> <input type="password"
							class="form-control" name="j_password" placeholder="Password">
					</div>
					<button type="submit" class="btn btn-primary">Login</button>
					<!-- 				<a href="#" class="lostpass btn btn-default"
						data-toggle="modal">Lost password</a> -->
					<a href="#" class="add btn btn-default" id="buttonfloat"
						data-toggle="modal">First access</a>
				</form>
			</div>
		</div>
	</div>
		<!-- Modal to add a new user -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">New user</h4>
					</div>
					<form:form method="POST" commandName="login" action="addUser" role="form">
						<div class="modal-body">
							<div class="form-group">
								<label for="username">Email</label>
								<form:input path="username" type="email" value="" cssClass="form-control" />
							</div>
							<div class="form-group">
								<label for="password">Password</label>
								<form:input path="password" type="password" value="" cssClass="form-control" />
							</div>
							<div class="form-group">
								<label for="password">Password confirmation</label>
								<form:password path="passwordConfirm" value="" cssClass="form-control" />
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-primary">Save changes</button>
						</div>
					</form:form>
				</div>
	
			</div>
		</div>
	
		<%-- <!-- Modal to recover password -->
		<div class="modal fade" id="lostPassword" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Recover password</h4>
					</div>
					<form:form method="POST" commandName="login" action="recoverPassword"
						role="form">
						<div class="modal-body">
							<div class="form-group">
								<label for="username">Enter your email</label>
								<form:input path="username" type="email" value="" cssClass="form-control" />
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-primary">Save
								changes</button>
						</div>
					</form:form>
				</div>
	
			</div>
		</div> --%>
	  </tiles:putAttribute>
</tiles:insertDefinition>
