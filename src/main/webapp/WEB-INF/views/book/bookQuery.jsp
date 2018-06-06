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
		  	     
					var data = {
						
							
							"isbn" : $('#isbn').val(),
						};
					
					$.ajax({
						type : "POST",
						contentType : "application/json",
						url : "/olmsb-web/admin/book/query/validate",
						data : JSON.stringify(data),
						dataType : 'json',
						timeout : 10000,
						success : function(response) {
							if(response.success) {
								$('#bookQueryForm').submit();
							} else {
								var errors = response.errors;
								var  e = '';
								for(var i=0; i < errors.length; i++) {
									e += errors[i] + '<br>';
								}
								toastr.error(e);
							}
							
						},
						
						error : function(err) {
							
							toastr.error('Error validating book');
							console.log("ERROR: ", err);
						}
					});
		  	     
		  	  });
		  	  
		  	  
		  	$('#resetBtn').click(function(event){
	  			$('input[type="text"]').val('');
	  		});
		  	  
		  	});
	  	
	 	 

	  	
   	 </script>
   <c:import url="../adminMenu.jsp" />
   <div class="page-header">
			<h4 id="pagetitle">Book Query</h4>
		</div>
	<div class="form-container">
	 	
	 	<form:form id="bookQueryForm" method="POST" 
	 		action="/olmsb-web/admin/book/query/result" modelAttribute="bookDto">
	 		<span class="inputTable">
				<table  width="100%">
					<tr >
						<td>
							<label id="bookLabel"  class="text-info label-field pull-left">Book Name</label>
					        <form:input type="text"  id="bookName" class="form-control pull-right" path="bookName"/>
						</td>
						<td>
							<label id="authorLabel" class="text-info label-field pull-left">Author</label>
					         <form:input type="text" id="author" class="form-control pull-right" path="author"/>
						</td>
						<td>
							<label id="isbnLabel" class="text-info label-field pull-left">ISBN</label>
					        <form:input type="text" id="isbn" class="form-control pull-right" path="isbn"/>
						</td>
						<td>
							<label id="publisherLabel" class="text-info label-field pull-left">Publisher</label>
					        <form:input type="text" id="publisher" class="form-control pull-right" path="publisher"/>
						</td>
					</tr>
					<tr >
						<td>
							<label id="authorLabel" class="text-info label-field pull-left">Category Name</label>
					         <form:select class="form-control pull-right" path="category" name="category">
					 <form:option value="" label="" />
    					<c:forEach items="${categoryList}" var="category">
    					
        					<option value="${category.bookCategoryName}">${category.bookCategoryName}</option>
   				 		</c:forEach>
					</form:select>
						</td>
						<td>
							<label id="editionLabel" class="text-info label-field pull-left">Edition</label>
					<form:input type="text" id="edition" class="form-control pull-right" path="edition"/>
						</td>
						<td></td>
						<td></td>
					</tr>
					<tr></tr>
				</table>
			</span>
           <div class="row">
           		<br>
				<div class="col-md-offset-15 pull-right" style="padding-top: 30px;padding-right: 15px;">
					<button type="button" id="resetBtn" class="btn btn-danger">Reset</button>
					<button type="button" id="queryBtn" class="btn btn btn-success">Query</button>
				</div>
				
			</div>
		</form:form>
	 </div>
	 <footer class="footercontainer navbar-fixed-bottom">
		<p>&copy; Nomura Research Institute, Ltd.</p>
	</footer>
   </body>
</html>