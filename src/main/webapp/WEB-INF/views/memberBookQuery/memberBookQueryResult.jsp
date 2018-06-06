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
		<script>
  	  	function testme(){
  	  	$(".chk").prop("disabled", true);
  	  	}
  	   </script>
   </head>
   <body onload="testme()">
   	 <script>
   	$(document).ready(function() {
	   	$("#parentCheck").change(function () {
	   	    $("input:checkbox[name=selectedbook]").prop('checked', $(this).prop("checked"));
	   	});
  		$('#issueBtn').click(function(event){
  			var selectedBooks = [];
  			$("input:checkbox[name=selectedbook]:checked").each(function(){
  				selectedBooks.push($(this).val());
  			});
			if(selectedBooks.length!=0){
				bootbox.confirm("Are you sure want to request for issuing this book", function(result){
					if(result) {
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/olmsb-web/member/books/query/issue",
				data : JSON.stringify(selectedBooks),
				dataType : 'json',
				timeout : 10000,
				success : function(response) {
					if(response.success) {		
						toastr.success('Book requested for issue successfully');
						setTimeout(function(){
			  				$(location).attr('href', '/olmsb-web/member/books/query/page')
						}, 2000);
					}
					else{
						var err = ''; 
						response.errors.forEach(function(item, index) {
							err += item + '<br>'
						});
						toastr.error(err);
					}
				},
				error : function(err) {
					toastr.error("ERROR");
					console.log("ERROR: ", err);
				}
			});
  			}
	}); 
   	}
			else{toastr.error("Please select atleast one book");}
	});
   	});	

   	 </script>
	 <c:import url="../memberMenu.jsp" />
	 <div class="page-header">
			<h4 id="pagetitle">Book Query Result</h4>
		</div>
	 <div class="form-container">
		<div class="col-md-offset-9 pull-right">
				<button type="button" id="issueBtn" class="btn btn-info">Issue</button>
		</div>
					<br>
		
			
		  <table class="table table-striped table-bordered table-hover table-condensed">
		    <thead>
		      <tr>
		        <th><input type="checkbox" id=parentCheck></th>
		        <th>Book Name</th>
		        <th>Category</th>
		        <th>Author</th>
		        <th>Publisher</th>
		        <th>ISBN</th>
		        <th>Copies Available for Issue</th>
		      </tr>
		    </thead>
		    <tbody>
		      	<c:forEach items="${data}" var="memberBook">
			  	  <tr>
			        <c:if test="${memberBook.availableForIssue < 1}">
			  	  	<td></td>
			  	   </c:if>
			  	   <c:if test="${memberBook.availableForIssue >= 1}">
			  	  	<td><input id="selectedBooks" name="selectedbook" type="checkbox"  value="${memberBook.bookPk}"></td>
			  	   </c:if>
			  	  	
			  	  	<td> <c:out value="${memberBook.bookName}"/></td>
			  	  	<td> <c:out value="${memberBook.bookCategoryName}"/></td>
			        <td> <c:out value="${memberBook.author}"/></td>
			        <td> <c:out value="${memberBook.publisher}"/></td>
			        <td> <c:out value="${memberBook.isbn}"/></td>
			        <td> <c:out value="${memberBook.availableForIssue}"/></td>
			      </tr>
		    	</c:forEach>
		    </tbody>
		  </table>
		</div>
		<footer class="footercontainer navbar-fixed-bottom">
		<p>&copy; Nomura Research Institute, Ltd.</p>
	</footer>
   </body>
</html>