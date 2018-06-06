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
		var validateForm = function () {
			 var categoryName = $('#bookCategoryName').val();
			 if(!categoryName) {
				 toastr.error('Book category name cannot be empty'); 
				 return false;
			 } else {
				 return  true;
			 }
		};
		
	  	$(document).ready(function() {
	  	  $('#submitBtn').click(function(event) {
	  	      if(validateForm()) {
				var data = {
					"bookCategoryName" : $('#bookCategoryName').val(),
					"description" : $('#description').val()
				};
				
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : "/olmsb-web/admin/bookcategory/entry/validate",
					data : JSON.stringify(data),
					dataType : 'json',
					timeout : 10000,
					success : function(response) {
						if(response.success) {
							$('#bookCategoryForm').submit();
						} else {
							var err = ''; 
							response.errors.forEach(function(item, index) {
								err += item + '<br>'
							});
							toastr.error(err);
						}
					},
					error : function(err) {
						toastr.error('Error validating book category');
						console.log("ERROR: ", err);
					}
				});
	  	      }
	  	  });
	  	  
	  	$('#resetBtn').click(function(event){
  			$('input[type="text"]').val('');
  		});
	  	});

   	 </script>
	 <c:import url="../adminMenu.jsp" />
	 <div class="page-header">
			<h4 id="pagetitle">Book Category Entry</h4>
	</div>
	 <div class="form-container">
	 	<form:form id="bookCategoryForm" method="POST" 
	 		action="/olmsb-web/admin/bookcategory/entry/submit" modelAttribute="bookCategoryDto">	
		<span class="inputTable">
			<table width="100%">
				<tr >
					<td width="25%">
						<label id="categoryLabel" class="text-info label-field pull-left">Category Name</label>
						<label class="text-danger pull-left">*</label>
						<form:input type="text" id="bookCategoryName" class="form-control pull-right" path="bookCategoryName"/>
					</td>
					<td width="25%">
						<label id="descriptionLabel" class="text-info label-field pull-left">Description</label>
						<form:input type="text" id="description" class="form-control pull-right" path="description"/>
					</td>
					<td></td>
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