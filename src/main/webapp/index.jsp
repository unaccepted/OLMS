<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
   <head>   	
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
		<spring:url value="/resources/js/libs/jquery-1.12.4.min.js" var="jqueryJs" />
		<spring:url value="/resources/js/libs/bootstrap.min.js" var="bootstrapJs" />
		<spring:url value="/resources/css/index.css" var="indexCss" />
		
		<!-- Bootstrap -->
		<link href="${bootstrapCss}" rel="stylesheet" />
		<link href="${indexCss}" rel="stylesheet" />
		
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="${jqueryJs}"></script>
		
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="${bootstrapJs}"></script>
   </head>
   <body>
		<!-- Header -->
		<div class="headercontainer">
			<div class="jumbotron">
				<h1 class="title-header">OLMS</h1>
				<p>Online Library Management System</p>
			</div>
		</div>
		<!-- Body -->
		<table class="customTable" align="center">
			<tr>
				<td align="center">
				<h3>Registration</h3>
				<p>Not a member yet?</p>
				<p><a class="btn btn-primary" href="/olmsb-web/registration/page" role="button">Sign up</a></p>
			</td>
			<td align="center">
				<h3>Registered User</h3>
				<p>Already a member?</p>
				<p><a class="btn btn-primary" href="/olmsb-web/dashboard" role="button">Sign in</a></p>
			</td>
		  </tr>
		</table>
		<!-- Footer -->
		<footer class="footercontainer navbar-fixed-bottom">
			<p>&copy; Nomura Research Institute, Ltd.</p>
		</footer>
	</body>
</html>