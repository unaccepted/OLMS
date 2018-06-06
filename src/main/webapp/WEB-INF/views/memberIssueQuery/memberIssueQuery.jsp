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
		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
		
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="${jqueryJs}"></script>
		
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="${bootstrapJs}"></script>
		<script src="${toastrJs}"></script>
		<script src="${olmsbJs}"></script>
		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>
		
		
		
   </head>
   <body>
   	 <script>
   	 
	   	$( function() {
		    $( "#datepicker1" ).datepicker();
		  } );
		
		$( function() {
		    $( "#datepicker2" ).datepicker();
		  } );
		
			var validateForm = function () {
			 return  true;
			 };
		
	  	$(document).ready(function() {
	  	  $('#submitBtn').click(function(event) {
	  	      if(validateForm()) {
				var issueDto =     {"bookName":$('#bookName').val(),
									"bookCategory":$('#bookCategory').val(),
			   				        "isbnValue":$('#isbnValue').val(),
			   				        "fromDateStr":$('#datepicker1').val(),
			   				        "toDateStr":$('#datepicker2').val(),
			   				        "status":$('#issueStatus').val()};
									
				
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : "/olmsb-web/member/issue/query/validate",
					data : JSON.stringify(issueDto),
					dataType : 'json',
					timeout : 10000,
					success : function(response) {
						if(response.success) {
							$('#issueForm').submit();
						} else {
							var errors = response.errors;
							for(var i=0; i < errors.length; i++) {
								toastr.error(errors[i]);
							}
						}
					},
						error : function(err) {
						toastr.error('Error Validating Issue Details');
						console.log(toastr);
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
	 <c:import url="../memberMenu.jsp" />
	 <div class="page-header">
			<h4 id="pagetitle">Member Issue Query</h4>
		</div>
	 <div class="form-container">
	 		 	<form:form id="issueForm" method="POST" action="/olmsb-web/member/issue/query/submit" modelAttribute="issueDto">
				
				<span class="inputTable">
					<table  width="100%">
						<tr >
							<td width="25%">
								<label id="bookNameLabel" class="text-info label-field pull-left">Book Name </label> 
								<form:input type="text" id="bookName" class="form-control pull-right" path="bookName"/>
							</td>
							<td width="25%">
								<label id="statusLabel" class="text-info label-field pull-left">Category</label>
								<form:select id="bookCategory" class="form-control pull-right" path="bookCategory">
									<form:option value="" label="" />
											<c:forEach items="${categoryList}" var="category">
												<option value="${category.bookCategoryName}">${category.bookCategoryName}</option>
											</c:forEach>
									</form:select>
							</td>
							<td width="25%">
								<label id="ISBNLabel" class="text-info label-field pull-left">ISBN</label>
								<form:input type="text" id="isbnValue" class="form-control pull-right" path="isbnValue"/>
							</td>
							<td width="25%">
								<label id="fromDateLabel" class="text-info label-field pull-left">From Date</label> 
								<form:input type="text" id="datepicker1" class="form-control pull-right" path="fromDateStr"/>
							</td>
						</tr>
						<tr >
							<td width="25%">
								<label id="statusLabel" class="text-info label-field pull-left">To Date</label>
								<form:input type="text" id="datepicker2" class="form-control pull-right" path="toDateStr"/>
							</td>
							<td width="25%">
								<label id="statusLabel" class="text-info label-field pull-left">Issue Status</label>
								<form:select id="issueStatus" class="form-control pull-right" path="status">
									 <option value="ISSUE_REQUESTED">Issue Request</option>
									 <option value="RETURN_REQUESTED">Return Request</option>
									 <option value="ISSUED">Issued</option>
									 <option value="RETURNED">Returned</option>
								</form:select>
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
						<button type="button" id="submitBtn" class="btn btn-success">Query</button>
					</div>
				</div>
		</form:form>
	 </div>
	 <footer class="footercontainer navbar-fixed-bottom">
		<p>&copy; Nomura Research Institute, Ltd.</p>
	</footer>
   </body>
</html>