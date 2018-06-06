<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<spring:url value="/resources/js/libs/jquery-1.12.4.min.js"
	var="jqueryJs" />
<spring:url value="/resources/js/libs/bootstrap.min.js"
	var="bootstrapJs" />
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
				var c=0;
				var clienterrors=[];
		var validateForm = function () {
			clienterrors=[];
			c=0;
			 var isValid = true;
			 var bookName = $('#bookName').val();
			 var author = $('#author').val();
			 var isbn = $('#isbn').val();
			 var publisher = $('#publisher').val();
			 var category = $('#category').val();
			 var edition = $('#edition').val();
			 var noOfCopies = $('#noOfCopies').val();
			 
			 if(!bookName) {
				 clienterrors.push('Book  name cannot be empty');
				// toastr.error('Book  name cannot be empty'); 
				 isValid =  false;
				 c++;
			 } 
			
			if (!author) {
				clienterrors.push('Author cannot be empty');
				isValid =  false;
				c++;
			}
			if (!isbn) {
				clienterrors.push('ISBN cannot be empty');
				isValid = false;
				c++;
			}
			if (!publisher) {
				clienterrors.push('Publisher cannot be empty');
				isValid = false;
				c++;
			}
			if (!category) {
				clienterrors.push('Category cannot be empty');
				isValid = false;
				c++;
			}
			if (!edition) {
				clienterrors.push('Edition cannot be empty');
				isValid = false;
				c++;
			}
			if (!noOfCopies) {
				clienterrors.push('No Of Copies cannot be empty');
				isValid = false;
				c++;
			}
			return isValid;
		};
		$(document)
			.ready(
				function() {
						$('#submitBtn')
									.click(
											function(event) {
												if (validateForm()) {
													var data = {
														"bookName" : $(
																'#bookName')
																.val(),
														"author" : $(
																'#author')
																.val(),
														"isbn" : $(
																'#isbn')
																.val(),
														"publisher" : $(
																'#publisher')
																.val(),
														"category" : $(
																'#category')
																.val(),
														"edition" : $(
																'#edition')
																.val(),
														"noOfCopies" : $(
																'#noOfCopies')
																.val()
													};
	
													$
															.ajax({
																type : "POST",
																contentType : "application/json",
																url : "/olmsb-web/admin/book/entry/validate",
																data : JSON
																		.stringify(data),
																dataType : 'json',
																timeout : 10000,
																success : function(
																		response) {
																	if (response.success) {
																		$(
																				'#bookForm')
																				.submit();
																	} else {
																		var errors = response.errors;
																		var e = '';
																		for (var i = 0; i < errors.length; i++) {
																			e += errors[i]
																					+ '<br>';
																		}
																		toastr
																				.error(e);
																	}
	
																},
	
																error : function(
																		err) {
	
																	toastr
																			.error('Error validating book');
																	console
																			.log(
																					"ERROR: ",
																					err);
																}
															});
												}
												else{
													
													var e = '';
													for (var i = 0; i < clienterrors.length ; i++) {
													e +=  clienterrors[i]
														+ '<br>';
															}
																	toastr
																				.error(e);
												}
											});
	
							$('#resetBtn').click(function(event) {
								$('input[type="text"]').val('');
							});
	
						});
			</script>

	<c:import url="../adminMenu.jsp" />
	<div class="page-header">
		<h4 id="pagetitle">Book Entry</h4>
	</div>
	<div class="form-container">
		<form:form id="bookForm" method="POST"
			action="/olmsb-web/admin/book/entry/submit" modelAttribute="bookDto">


			<span class="inputTable">
				<table width="100%">
					<tr>
						<td><label id="bookLabel"
							class="text-info label-field pull-left">Book Name</label> <label
							class="text-danger pull-left">*</label> <form:input type="text"
								id="bookName" class="form-control pull-right" path="bookName" />
						</td>
						<td><label id="authorLabel"
							class="text-info label-field pull-left">Author</label> <label
							class="text-danger pull-left">*</label> <form:input type="text"
								id="author" class="form-control pull-right" path="author" /></td>
						<td><label id="isbnLabel"
							class="text-info label-field pull-left">ISBN</label> <label
							class="text-danger pull-left">*</label> <form:input type="text"
								id="isbn" class="form-control pull-right" path="isbn" /></td>
						<td><label id="publisherLabel"
							class="text-info label-field pull-left">Publisher</label> <label
							class="text-danger pull-left">*</label> <form:input type="text"
								id="publisher" class="form-control pull-right" path="publisher" />
						</td>
					</tr>
					<tr>
						<td><label id="authorLabel"
							class="text-info label-field pull-left">Category Name</label> <label
							class="text-danger pull-left">*</label> <form:select
								class="form-control pull-right" path="category" name="category">
								<form:option value="" label="" />
								<c:forEach items="${categoryList}" var="category">

									<option value="${category.bookCategoryName}">${category.bookCategoryName}</option>
								</c:forEach>
							</form:select></td>
						<td><label id="editionLabel"
							class="text-info label-field pull-left">Edition</label> <label
							class="text-danger pull-left">*</label> <form:input type="text"
								id="edition" class="form-control pull-right" path="edition" /></td>
						<td><label id="noofcopiesLabel"
							class="text-info label-field pull-left">No. of Copies</label> <label
							class="text-danger pull-left">*</label> <form:input type="text"
								id="noOfCopies" class="form-control pull-right"
								path="noOfCopies" /></td>
						<td></td>
					</tr>
					<tr></tr>
				</table>
			</span>



			<div class="row">
				<div class="col-md-offset-15 pull-right"
					style="padding-top: 30px; padding-right: 15px;">
					<button type="button" id="resetBtn" class="btn btn-danger">Reset</button>
					<button type="button" id="submitBtn" class="btn btn btn-success">Submit</button>
				</div>

			</div>






		</form:form>
	</div>
	<footer class="footercontainer navbar-fixed-bottom">
		<p>&copy; Nomura Research Institute, Ltd.</p>
	</footer>
</body>
</html>