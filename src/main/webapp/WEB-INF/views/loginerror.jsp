<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
		<spring:url value="/resources/css/toastr.min.css" var="toastrCss" />
		<spring:url value="/resources/css/login.css" var="loginCss" />
		<spring:url value="/resources/js/libs/jquery-1.12.4.min.js" var="jqueryJs" />
		<spring:url value="/resources/js/libs/bootstrap.min.js" var="bootstrapJs" />
		<spring:url value="/resources/js/libs/toastr.min.js" var="toastrJs" />
		<!-- Bootstrap -->
		<link href="${bootstrapCss}" rel="stylesheet" />
		<link href="${loginCss}" rel="stylesheet" />
		<link href="${toastrCss}" rel="stylesheet" />

		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="${jqueryJs}"></script>
		
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="${bootstrapJs}"></script>
		<script src="${toastrJs}"></script>
</head>
<body style="background-color:#ddd;">
<script>

$('#body').ready(function(){
    toastr.error("Authentication Failed");
});

</script>

	<!-- Header -->
	<div class="headercontainer">
		<div class="jumbotron">
			<h1 >OLMS</h1>
			<p>Online Library Management System</p>
		</div>
	</div>
	<!-- Body -->
	<div class="container">
		<div class="custom-login-container">
			<div id="output"></div>
			<div class="avatar"></div>
			<div class="form-box">
				<form action="j_security_check" method="POST">
					<input name="j_username" type="text" placeholder="username">
					<input name="j_password" type="password" placeholder="password">
					<button class="login-btn btn btn-primary" type="submit">Login</button>
				</form>
			</div>
		</div>
	</div>
	 <!-- Footer -->
	<footer class="footercontainer navbar-fixed-bottom">
		<p>&copy; Nomura Research Institute, Ltd.</p>
	</footer>
</body>
</html>
