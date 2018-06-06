<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		
   </head>
   <body>
   	 <script>
	  	$(document).ready(function() {
	  		$('#queryBtn').click(function(event) {
		  		$('#userQueryForm').submit();
	  		});
	  		$('#resetBtn').click(function(event){
	  			$('input[type="text"]').val('');
	  		});
	  	});
   	 </script>
	 <c:import url="../adminMenu.jsp" />
	 <div class="page-header">
			<h4 id="pagetitle">New Registration Request Query</h4>
		</div>
	 <div class="form-container">
	 	<form:form id="userQueryForm" method="POST" 
	 		action="/olmsb-web/admin/users/newRegistrationQuery/result" modelAttribute="usersDto">
	 		
	 		
	 		
	 		
	 <span class="inputTable">
					<table  width="100%">
						<tr >
							<td width="25%">
								<label id="userIdLabel" class="text-info label-field pull-left">User Id</label>
								<form:input type="text" id="userId" class="form-control pull-right" path="userId"  style="width: 300px;"/>
							</td>
							<td width="25%">
								<label id="firstNameLabel" class="text-info label-field pull-left">First Name</label>
								<form:input type="text" id="firstName" class="form-control pull-right" path="firstName"  style="width: 300px;"/>
							</td>
							<td width="25%">
								<label id="lastNameLabel" class="text-info label-field pull-left">Last Name</label>
								<form:input type="text" id="lastName" class="form-control pull-right" path="lastName"  style="width: 300px;"/>
							</td>
							<td width="25%">
								<label id="emailLabel" class="text-info label-field pull-left">Email Id</label>
								<form:input type="text" id="email" class="form-control pull-right" path="email"  style="width: 300px;"/>
							</td>
						</tr>
						<!--<tr >
							<td width="25%">
								<label id="emailLabel" class="text-info label-field pull-left">Email Id</label>
								<form:input type="text" id="email" class="form-control pull-right" path="email"  style="width: 300px;"/>
							</td>
							<td></td>
							<td></td>
						</tr> -->
						<tr></tr>
					</table>
				</span>
		
		
		<div class="row">
			<div class="col-md-offset-9 pull-right">
			<br><br>
				<button type="button" id="resetBtn" class="btn btn-danger">Reset</button>
				<button type="button" id="queryBtn" class="btn btn-success">Query</button>
			</div>
		</div>
		</form:form>
	 </div>
	 <footer class="footercontainer navbar-fixed-bottom">
		<p>&copy; Nomura Research Institute, Ltd.</p>
	</footer>
   </body>
</html>