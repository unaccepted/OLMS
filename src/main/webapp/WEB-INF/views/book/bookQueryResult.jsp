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
		<spring:url value="/resources/js/libs/bootbox.min.js" var="bootboxJs" />
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
		<style>
 a:hover {
  cursor:pointer;
 }
</style>
		
   </head>
   <body>
    <div style="display:none;" id="loader"></div>
   	 <script>
   	$(document).ready(function() {
   		var isbn='';
	   	$("#parentCheck").change(function () {
	   	    $("input:checkbox[name=selectedBook]").prop('checked', $(this).prop("checked"));
	   	});
	   
  		$('#removeBtn').click(function(event){
  			var selectedBooks = [];
			$("input:checkbox[name=selectedBook]:checked").each(function(){
				selectedBooks.push($(this).val());
			});
			if(selectedBooks.length!=0){
  			bootbox.confirm("Are you sure you want to delete these books?", function(result) {
  			  if(result) {
  				$.ajax({
  					type : "POST",
  					contentType : "application/json",
  					url : "/olmsb-web/admin/book/query/delete",
  					data : JSON.stringify(selectedBooks),
  					dataType : 'json',
  					timeout : 10000,
  					success : function(response) {
  						if(response.success) {		
								document.getElementById("bookInventoryModal").style.display = "none";
								toastr.success('Book Deleted successfully');
							document.getElementById("loader").style.display = "block";
                            setTimeout(showPage, 2000);
							function showPage() {
                              document.getElementById("loader").style.display = "none";
							  $(location).attr('href', '/olmsb-web/admin/book/query/page');
                            }
							}
  							else {
  								var errors = response.errors;
  								for(var i=0; i < errors.length; i++) {
  									toastr.error(errors[i]);
  								}
  						}
  					},
  					error : function(err) {
  						toastr.error('Error while deleting books');
  						console.log("ERROR: ", err);
  					}
  				});
  			  }
  			}); 
			}
			else{
				toastr.error("Please select atleast one book");
			}
  		});
  		$("#inventoryCheck").change(function () {
	   	    $("input:checkbox[name=selectedBookCopy]").prop('checked', $(this).prop("checked"));
	   	});
  		$('#deleteBtn').click(function(event){
  			var selectedBookCopies = [];
	  			$("input:checkbox[name=selectedBookCopy]:checked").each(function(){
	  				selectedBookCopies.push($(this).val());
	  			});
	  			if(selectedBookCopies!=0){
  			bootbox.confirm("Are you sure you want to delete these book copies?", function(result) {
  			  if(result) {
  	  			
  				$.ajax({
  					type : "POST",
  					contentType : "application/json",
  					url : "/olmsb-web/admin/book/inventory/query/delete",
  					data : JSON.stringify(selectedBookCopies),
  					dataType : 'json',
  					timeout : 10000,
  					success : function(response) {
  						if(response.success) {		
								document.getElementById("bookInventoryModal").style.display = "none";
								toastr.success('Book Copies Deleted successfully');
							document.getElementById("loader").style.display = "block";
                            setTimeout(showPage, 2000);
							function showPage() {
                              document.getElementById("loader").style.display = "none";
							  $(location).attr('href', '/olmsb-web/admin/book/query/page');
                            }
							}
  							else {
  								var errors = response.errors;
  								for(var i=0; i < errors.length; i++) {
  									toastr.error(errors[i]);
  								}
  						}
  						
  					},
  					error : function(err) {
  						toastr.error('Error while deleting book copies');
  						console.log("ERROR: ", err);
  					}
  				});
  			  }
  			}); 
	  			}
	  			else{
					toastr.error("Please select atleast one book copy");
				}
  		});
  		
  		$('#addBookCopy').click(function(event){
  			console.log(isbn);
  			bootbox.prompt("Enter the no of copies ", function(result) {
  				if (isNaN($.trim(result) )) {              
  					toastr.error('Please enter a valid number');
  				 } else if (($.trim(result)) <= 0 && result!=null) {      
  					toastr.error('Provided value should be more than zero');
  				 } else if(result && result!=null){
  				    	var addData={
  				    	 	noOfCopies : $.trim(result),
  				    	 	isbn : isbn
  				    	};
  				$.ajax({
  					type : "POST",
  					contentType : "application/json",
  					url : "/olmsb-web/admin/book/inventory/query/add",
  					data : JSON.stringify(addData),
  					dataType : 'json',
  					timeout : 10000,
  					success : function(response) {
  							if(response.success) {		
  								document.getElementById("bookInventoryModal").style.display = "none";
  								toastr.success('Book Copies Added successfully');
								document.getElementById("loader").style.display = "block";
                                setTimeout(showPage, 2000);
								function showPage() {
                                  document.getElementById("loader").style.display = "none";
								  $(location).attr('href', '/olmsb-web/admin/book/query/page');
                                }
  							}
  							else {
  								var errors = response.errors;
  								for(var i=0; i < errors.length; i++) {
  									toastr.error(errors[i]);
  								}
  						}
  					},
  					error : function(err) {
  						toastr.error('Error while deleting book copies');
  						console.log("ERROR: ", err);
  					}
  				});
  			  }
  			}); 
  		});
  		
		$('.isbnCat').click(function(event){
			console.log(event);
			isbn = $(this).text();
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/olmsb-web/admin/book/inventory/query",
				data : isbn,
				dataType : 'json',
				timeout : 10000,
				success : function(response) {
					if(response.success) {
						var markup = '';
						$(".bookcopies").remove();
						response.data.forEach(function(data,index) {
							markup +='<tr class="bookcopies">'+
							'<td><input id="selectedBookCopies" name="selectedBookCopy"  type="checkbox" value="'+data.bookInventoryPk+'"></td>'+
							'<td>'+data.bookId+'</td>';
							if(data.available === 'y') {
								markup += '<td><span class="label label-success">NOT ISSUED</span></td>';
							} else {
								markup += '<td><span class="label label-warning">ISSUED</span></td>'
							}
							markup +='</tr>';
						});
						$("#bookInventoryModal").modal('show');
						$("#inventorybody").append(markup);
					}
					else {
						var errors = response.errors;
						for(var i=0; i < errors.length; i++) {
							toastr.error(errors[i]);
						}
					}
				},
				error : function(err) {
					toastr.error('Error while loading book inventory');
				}
			});

		});
		
  	});	
	
   	 </script>
	 <c:import url="../adminMenu.jsp" />
	 <div class="page-header">
			<h4 id="pagetitle">Book Query Result</h4>
		</div>
	 <div class="form-container">
	 	
		
		<div class="row">
			<div class="col-md-offset-9 pull-right">
				<button type="button" id="removeBtn" class="btn btn-danger">Delete</button>
			</div>
		<br>
		
		  <table class="table table-striped table-bordered table-hover table-condensed">
		    <thead>
		      <tr>
		        <th><input type="checkbox" id=parentCheck></th>
		        <th>ISBN</th>
		        <th>Book Name</th>
		        <th>Author</th>
		        <th>Publisher</th>
		        <th>Edition</th>
				<th>No. of copies</th>
		      </tr>
		    </thead>
		    <tbody>
		    	<c:forEach items="${data}" var="category">
			  	  <tr>
			  	  	<td><input id="selectedBooks" name="selectedBook"  type="checkbox" value="${category.bookPk}"></td>
			  	  	<td><a class="isbnCat"><c:out value="${category.isbn}"/></a></td>
			  	  	<td> <c:out value="${category.bookName}"/></td>
			        <td> <c:out value="${category.author}"/></td>
			        
			        <td> <c:out value="${category.publisher}"/></td>
			        <td> <c:out value="${category.edition}"/></td>
					<td> <c:out value="${category.noOfBook}"/></td>
			  	  	
			    </tr>
		    	</c:forEach>
		    </tbody>
		  </table>
		</div>
		</div>
		<footer class="footercontainer navbar-fixed-bottom">
		<p>&copy; Nomura Research Institute, Ltd.</p>
	</footer>
	<!-- Modal HTML -->
	<div id="bookInventoryModal" class="modal fade">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title">Book Inventory</h4>
	            </div>
	            <div class="modal-body">
				  <table class="table table-striped table-bordered table-hover table-condensed">
					<thead>
					  <tr>
						<th><input type="checkbox" id=inventoryCheck></th>
						<th>Book ID</th>
						<th>Issue Status</th>
					  </tr>
					</thead>
					<tbody id='inventorybody'>
					</tbody>
				  </table>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-danger" id="deleteBtn" >Delete</button>
	                <button type="button" class="btn btn-success" id="addBookCopy">Add</button>
	            </div>
	        </div>
	    </div>
	</div>
	<footer class="footercontainer navbar-fixed-bottom">
			<p>&copy; Nomura Research Institute, Ltd.</p>
		</footer>
   </body>
</html>