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
	  	  $('#pagetitle').text("Book Category User Confirmation");
	  	  $("#okBtn").hide();
	  	  $('#confirmBtn').click(function(event) {
				var data = {
					"bookCategoryName" : $('#categoryName').text(),
					"description" : $('#categoryDescription').text()
				};
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : "/olmsb-web/admin/bookcategory/entry/confirm",
					data : JSON.stringify(data),
					dataType : 'json',
					timeout : 10000,
					success : function(response) {
						if(response.success) {
							toastr.success('Book category added successfully');
							$('#backBtn').remove();
							$('#confirmBtn').remove();
							$('#okBtn').show();
							$('#pagetitle').text("Book Category System Confirmation");
							$('#okBtn').click(function(event){
								toastr.success('Redirecting to Book Category home page');
								setTimeout(function(){
									$(location).attr('href', '/olmsb-web/admin/bookcategory/entry/page')}, 1000);
							})
						}
					},
					error : function(err) {
						toastr.error('Error while adding book category');
						console.log("ERROR: ", err);
					}
				});
	  	  });
	  	});

   	 </script>
	 <c:import url="../adminMenu.jsp" />
	 <div class="page-header">
			<h4 id="pagetitle"></h4>
	</div>
	<div class="form-container">
		<span class="inputTable">
			<table width="100%">
				<tr >
					<td width="25%">
						<label id="categoryLabel" class="text-info label-field">Category Name :</label>
						<span class="text-left" id="categoryName">${data.bookCategoryName}</span>
					</td>
					<td width="25%">
						<label id="descriptionLabel" class="text-info label-field">Description :</label>
						<span class="text-left" id="categoryDescription">${data.description}</span>
					</td>
					<td></td>
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