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
		<spring:url value="/resources/js/libs/bootbox.min.js" var="bootboxJs" />
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
		<script src="${bootboxJs}"></script>
		<script src="${olmsbJs}"></script>
		
   </head>
   <body>
    <div style="display:none;" id="loader"></div>
   	<script>
	  	$(document).ready(function() {
		   	$("#parentCheck").change(function () {
		   	    $("input:checkbox[name=selecteduser]").prop('checked', $(this).prop("checked"));
		   	});
	  		$('#acceptBtn').click(function(event){
	  			var selectedUsers = [];
	  			$("input:checkbox[name=selecteduser]:checked").each(function(){
	  				selectedUsers.push($(this).val());
	  			});
	  			if(selectedUsers.length) {
		  			bootbox.confirm("Are you sure you want to add these as members?", function(result){
		  				if(result){
							$.ajax({
								type : "POST",
								contentType : "application/json",
								url : "/olmsb-web/admin/users/newRegistrationQuery/add",
								data : JSON.stringify(selectedUsers),
								dataType : 'json',
								timeout : 10000,
								success : function(response) {
									
									toastr.success('Member Added successfully');
								document.getElementById("loader").style.display = "block";
	                            setTimeout(showPage, 2000);
								function showPage() {
	                              document.getElementById("loader").style.display = "none";
								  $(location).attr('href', '/olmsb-web/admin/users/newRegistrationQuery/page');
	                            }
								
									
								},
								error : function(err) {
									toastr.error('Error while Adding users');
									console.log("ERROR: ", err);
								}
							});
		  					
		  				}
		  			});
	  			} else {
	  				toastr.error('Please select atleast one member');
	  			}

	  		});
	  		$('#rejectBtn').click(function(event){
	  			
	  			var selectedUsers = [];
	  			$("input:checkbox[name=selecteduser]:checked").each(function(){
	  				selectedUsers.push($(this).val());
	  			});
	  			if(selectedUsers.length){
	  				bootbox.confirm("Are you sure you want to reject these User Requests?", function(result){
	  		  			if(result){
	  		  				$.ajax({
	  							type : "POST",
	  							contentType : "application/json",
	  							url : "/olmsb-web/admin/users/newRegistrationQuery/reject",
	  							data : JSON.stringify(selectedUsers),
								dataType : 'json',
								timeout : 10000,
	  							success : function(response) {
	  								if(response.success) {		
	  									

										toastr.success('Member Request rejected successfully');
									document.getElementById("loader").style.display = "block";
		                            setTimeout(showPage, 2000);
									function showPage() {
		                              document.getElementById("loader").style.display = "none";
									  $(location).attr('href', '/olmsb-web/admin/users/newRegistrationQuery/page');
		                            }
										
	  									
	  							  	}
	  							},
	  							error : function(err) {
	  								toastr.error('Error while Rejecting users request ');
	  								console.log("ERROR: ", err);
	  							}
	  						});
	  		  			}	
	  		  			
	  			  	});	
	  			}else{
	  				toastr.error('Please select atleast one member');
	  			}
  			});	  		
	  	});
   	 </script>
	 <c:import url="../adminMenu.jsp" />
	 <div class="page-header">
			<h4 id="pagetitle">New User Query Result</h4>
		</div>
	 <div class="form-container">
		<div class="row">
			<div class="col-md-offset-9 pull-right">
				
				<button type="button" id="rejectBtn" class="btn btn-danger">Reject</button>
				<button type="button" id="acceptBtn" class="btn btn-success">Accept</button>
				<br><br>
			</div>
		<br>
		  <table class="table table-striped table-bordered table-hover table-condensed">
		    <thead>
		      <tr>
		        <th><input type="checkbox" id=parentCheck></th>
		        <th>User ID</th>
		        <th>First name</th>
		        <th>Last Name</th>
		        <th>Email</th>
		      </tr>
		    </thead>
		    <tbody>
		    	<c:forEach items="${data}" var="user">
			  	  <tr>
			  	  <td><input id="selectedUsers" name="selecteduser" type="checkbox" value="${user.userPk}"></td>
			  	  	<td> <c:out value="${user.userId}"/></td>
			        <td> <c:out value="${user.firstName}"/></td>
			        <td> <c:out value="${user.lastName}"/></td>
			        <td> <c:out value="${user.email}"/></td>
			      </tr>
		    	</c:forEach>
		    </tbody>
		  </table>
		</div>
		</div>
		
		<footer class="footercontainer navbar-fixed-bottom">
		<p>&copy; Nomura Research Institute, Ltd.</p>
	</footer>
   </body>
  
</html>