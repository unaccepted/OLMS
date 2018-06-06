<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="/resources/css/toastr.min.css" var="toastrCss" />
<spring:url value="/resources/css/global.css" var="globalCss" />
<spring:url value="/resources/js/libs/jquery-1.12.4.min.js" var="jqueryJs" />
<spring:url value="/resources/js/libs/bootstrap.min.js" var="bootstrapJs" />
<spring:url value="/resources/js/libs/toastr.min.js" var="toastrJs" />
<spring:url value="/resources/js/olmsb.js" var="olmsbJs" />
<spring:url value="/resources/js/memberRegistration.js" var="memberRegistrationJs" />


<!-- Bootstrap -->
<link href="${bootstrapCss}" rel="stylesheet" />
<!-- Plugin css  -->
<link href="${toastrCss}" rel="stylesheet" />
<link href="${globalCss}" rel="stylesheet" />

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${jqueryJs}"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${bootstrapJs}"></script>
<script src="${toastrJs}"></script>
<script src="${olmsbJs}"></script>
<script src="${memberRegistrationJs}"></script>

</head>
<body>
	<c:import url="guestMenu.jsp" />
	<!-- Header -->
	<div class="page-header">
		<h4 id="pagetitle">Member Registration Entry</h4>
	</div>
	<div class="form-container">
		<form:form id="memberRegistrationForm" method="POST"
			action="/olmsb-web/registration/submit"
			modelAttribute="memberRegistrationDto">
			<span class="inputTable">
				<table  width="100%">
					<tr >
						<td>
							<label id="userIdLabel" class="text-info label-field pull-left">User ID</label>
							<label class="text-danger pull-left">*</label>
							<form:input type="text" id="userId" class="form-control pull-right" path="userId" />
						</td>
						<td>
							<label id="passwordLabel" class="text-info label-field pull-left">Password</label>
							<label class="text-danger pull-left">*</label>
							<form:input type="password" id="password" class="form-control pull-right" path="password" />
						</td>
						<td>
							<label id="confirmPasswordLabel" class="text-info label-field pull-left">Confirm Password</label>
							<label class="text-danger pull-left">*</label>
							<form:input type="password" id="confirmPassword" class="form-control pull-right" path="confirmPassword" />
						</td>
						<td>
							<label id="firstNameLabel" class="text-info label-field pull-left">First Name</label>
							<label class="text-danger pull-left">*</label>
							<form:input type="text" id="firstName" class="form-control pull-right" path="firstName" />
						</td>
					</tr>
					<tr >
						<td>
							<label id="lastNameLabel" class="text-info label-field pull-left">Last Name</label>
							<form:input type="text" id="lastName" class="form-control pull-right" path="lastName" />
						</td>
						<td>
							<label id="contactNoLabel" class="text-info label-field pull-left">Contact No</label>
							<form:input type="text" id="contactNo" class="form-control pull-right" path="contactNo" />
						</td>
						<td>
							<label id="emailLabel" class="text-info label-field pull-left">Email</label>
							<form:input type="email" id="email" class="form-control pull-right" path="email" />
						</td>
						<td></td>
					</tr>
					<tr></tr>
				</table>
			</span>
			
			<div class="row">
				<div class="col-md-offset-15 pull-right" style="padding-top: 30px;padding-right: 15px;">
					<button type="button" id="resetBtn" class="btn btn-danger">Reset</button>
					<button type="button" id="submitBtn" class="btn btn btn-success">Submit</button>
				</div>
			</div>
		</form:form>
	</div>
	<!-- Footer -->
	<footer class="footercontainer navbar-fixed-bottom">
		<p>&copy; Nomura Research Institute, Ltd.</p>
	</footer>
</body>
</html>