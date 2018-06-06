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
   <div style="display:none;" id="loader"></div>
   	 <script>
	  	$(document).ready(function() {
	  	  $('#pagetitle').text("Book Entry User Confirmation");
	  	  $("#okBtn").hide();
	  	  $('#confirmBtn').click(function(event) {
				var data = {
						"bookName" : $('#bookName').text(),
						"author" : $('#author').text(),
						"isbn" : $('#isbn').text(),
						"publisher" : $('#publisher').text(),
						"category" : $('#category').text(),
						"edition" : $('#edition').text(),
						"noOfCopies" : $('#noOfCopies').text()
				};
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : "/olmsb-web/admin/book/entry/confirm",
					data : JSON.stringify(data),
					dataType : 'json',
					timeout : 10000,
					success : function(response) {
						if(response.success) {
							toastr.success('Book  added successfully');
							$('#resetBtn').remove();
							$('#confirmBtn').remove();
							$('#okBtn').show();
							$('#pagetitle').text("Book Entry System Confirmation");
							$('#okBtn').click(function(event){
								toastr.success('Redirecting to home page');
								document.getElementById("loader").style.display = "block";
								setTimeout(showPage, 2000);
								function showPage() {
								document.getElementById("loader").style.display = "none";
								$(location).attr('href', '/olmsb-web/admin/book/entry/page');
								}
							
							})
						}
					},
					error : function(err) {
						toastr.error('Error while adding book');
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
			<table  width="100%">
				<tr >
					<td>
					<label id="bookLabel" class="text-info label-field">Book Name :</label>
					<span class="text-left" id="bookName"  >${data.bookName}</span>
						
					</td>
					<td>
						<label id="authorLabel" class="text-info label-field ">Author :</label>
					<span class="text-left" id="author"  >${data.author}</span>
					</td>
					<td>
						<label id="isbnLabel" class="text-info label-field ">ISBN :</label>
					<span class="text-left" id="isbn"  >${data.isbn}</span>
					</td>
					<td>
						<label id="publisherLabel" class="text-info label-field">Publisher :</label>
					<span class="text-left" id="publisher"  >${data.publisher}</span>
					</td>
				</tr>
				<tr >
					<td>
						<label id="categoryLabel" class="text-info label-field ">Category :</label>
					<span class="text-left" id="category"  >${data.category}</span>
					</td>
					<td>
						<label id="editionLabel" class="text-info label-field ">Edition</label>
					<span class="text-left" id="edition"  >${data.edition}</span>
					</td>
					<td>
						<label id="noofcopiesLabel" class="text-info label-field ">No. of Copies :</label>
					<span class="text-left" id="noOfCopies"  >${data.noOfCopies}</span>
					</td>
					<td></td>
				</tr>
				<tr></tr>
			</table>
		</span>
		<br>
		<div class="row">
			<div class="col-md-offset-15 pull-right" style="padding-top: 30px;padding-right: 15px;">
				
				<button type="button" id="confirmBtn" class="btn btn-success">Confirm</button>
				<button type="button" id="okBtn" class="btn btn-success">Ok</button>
			</div>
		</div>
	</div>
	 
	 <footer class="footercontainer navbar-fixed-bottom">
		<p>&copy; Nomura Research Institute, Ltd.</p>
	</footer>
   </body>
</html>