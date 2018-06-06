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
		<script src="${bootboxJs}"></script>
		<script src="${toastrJs}"></script>
		<script src="${olmsbJs}"></script>
		
   </head>
   <body>
   	 <script>
	 $(document).ready(function() {
		   	$("#parentCheck").change(function () {
		   	    $("input:checkbox[name=selectedCategories]").prop('checked', $(this).prop("checked"));
		   	});
		   	$('#removeBtn').click(function(event){
	  			var selectedUsers = [];
	  			$("input:checkbox[name=selectedCategories]:checked").each(function(){
	  				selectedUsers.push($(this).val());
	  			});
		  		if(selectedUsers.length > 0) {
		  			bootbox.confirm("Are you sure you want to delete these categories", function(result){
		  				if(result){
		  					$.ajax({
		  						type : "POST",
		  						contentType : "application/json",
		  						url : "/olmsb-web/admin/bookcategory/remove",
		  						data : JSON.stringify(selectedUsers),
		  						dataType : 'json',
		  						timeout : 10000,
		  						success : function(response) {
		  							if(response.success) {		
		  								toastr.success('Category deleted successfully');
		  								setTimeout(function(){
		  					  				$(location).attr('href', '/olmsb-web/admin/bookcategory/query/page')
		  								}, 2000);
		  							}
		  							else{
		  								var errors = response.errors;
		  									for(var i=0; i < errors.length; i++) {
		  										toastr.error(errors[i]);
		  									}
		  							}
		  						},
		  						error : function(err) {
		  							toastr.error("ERROR");
		  							console.log("ERROR: ", err);
		  						}
		  					});
		  		  		}
		  			});
		  		} else {
		  			toastr.error('Please select atleast one category');
		  		}
	  		});
		 	});
   	 </script>
	 <c:import url="../adminMenu.jsp" />
	 <div class="page-header">
			<h4 id="pagetitle">Book Category Query Result</h4>
		</div>
	 <div class="form-container">
			<div class="col-md-offset-9 pull-right">
				<button type="button" id="removeBtn" class="btn btn-danger">Remove</button>
				<br><br>
			</div>
		  <table class="table table-striped table-bordered table-hover table-condensed">
		    <thead class="thead-inverse">
		      <tr>
		        <th><input type="checkbox" id=parentCheck></th>
		        <th>Book Category Name</th>
		        <th>Description</th>
		      </tr>
		    </thead>
		    <tbody>
		    	<c:forEach items="${data}" var="category">
			  	  <tr>
			  	  	<td><input id="selectedCategories" name="selectedCategories" type="checkbox" value="${category.bookCategoryPk}"></td>
			  	  	<td> <c:out value="${category.bookCategoryName}"/></td>
			        <td> <c:out value="${category.description}"/></td>			        

			      </tr>
		    	</c:forEach>
		    </tbody>
		  </table>
		</div>
		<!-- Footer -->
	<footer class="footercontainer navbar-fixed-bottom">
		<p>&copy; Nomura Research Institute, Ltd.</p>
	</footer>
   </body>
</html>