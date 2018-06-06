<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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
	<script>
	  	$(document).ready(function() {
	  	  $('#pagetitle').text("Member Registration User Confirmation");
	  	  $("#okBtn").hide();
	  	  $('#confirmBtn').click(function(event) {
				var data = {
					"userId" : $('#userId').text(),
					"password" : $('#password').text(),
					"confirmPassword" : $('#confirmPassword').text(),
					"firstName" : $('#firstName').text(),
					"lastName" : $('#lastName').text(),
					"contactNo" : $('#contactNo').text(),
					"email" : $('#email').text()
				};
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : "/olmsb-web/registration/confirm",
					data : JSON.stringify(data),
					dataType : 'json',
					timeout : 10000,
					success : function(response) {
						if(response.success) {
							toastr.success('Member details added successfully');
							$('#backBtn').remove();
							$('#confirmBtn').remove();
							$('#okBtn').show();
							$('#pagetitle').text("Member Registration System Confirmation");
							$('#okBtn').click(function(event){
								toastr.success('Redirecting to home page');
								setTimeout(function(){
									$(location).attr('href', '/olmsb-web/')}, 1000);
							})
						} else {
							var errors = response.errors;
							for(var i=0; i < errors.length; i++) {
								toastr.error(errors[i]);
							}
							}
						;
					},
					error : function(err) {
						toastr.error('Error while adding New Member');
						console.log("ERROR: ", err);
					}
				});
	  	  });
	  	});

   	 </script>
	<c:import url="guestMenu.jsp" />
	<!-- Header -->
	<div class="page-header">
		<h4 id="pagetitle">Member Registration Entry</h4>
	</div>
	<div class="form-container">
		<span class="inputTable">
			<table  width="100%">
				<tr >
					<td>
						<label id="userIdLabel" class="text-info label-field">User ID :</label>
						<span class="text-left " id="userId">${data.userId}</span>
					</td>
					<td class="hidden-block">
						<label id="passwordLabel" class="text-info label-field">Password :</label>
						<span class="text-left" id="password" style="visibility: hidden">${data.password}</span>
					</td>
					<td class="hidden-block">
						<label id="confirmPasswordLabel" class="text-info label-field">Confirm Password :</label>
						<span class="text-left" id="confirmPassword" style="visibility: hidden">${data.confirmPassword}</span>
					</td>
					<td>
						<label id="firstNameLabel" class="text-info label-field">First Name :</label>
						<span class="text-left" id="firstName">${data.firstName}</span>
					</td>
					<td>
						<label id="lastNameLabel" class="text-info label-field">Last Name :</label>
						<span class="text-left" id="lastName">${data.lastName}</span>
					</td>
				</tr>
				<tr >

					<td>
						<label id="contactNoLabel" class="text-info label-field">Contact No :</label>
						<span class="text-left" id="contactNo">${data.contactNo}</span>
					</td>
					<td>
						<label id="emailLabel" class="text-info label-field">Email :</label>
						<span class="text-left" id="email">${data.email}</span>
					</td>
					<td></td>
				</tr>
				<tr></tr>
			</table>
		</span>
		
		<div class="row">
			<div class="col-md-offset-15 pull-right" style="padding-top: 30px;padding-right: 15px;">
				<a href="javascript:history.back()"><button type="button" id="backBtn" class="btn btn btn-danger">Back</button></a>
				<button type="button" id="confirmBtn" class="btn btn-success">Confirm</button>
				<button type="button" id="okBtn" class="btn btn-success">Ok</button>
			</div>
		</div>
	</div>
	<!-- Footer -->
	<footer class="footercontainer navbar-fixed-bottom">
		<p>&copy; Nomura Research Institute, Ltd.</p>
	</footer>
</body>
</html>		