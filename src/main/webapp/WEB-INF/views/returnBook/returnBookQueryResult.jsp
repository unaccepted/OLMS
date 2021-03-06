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
		
   </head>
   <body>
       <div style="display:none;" id="loader"></div>
   	 <script>
   	 
   	$(document).ready(function() {
   		$("#parentCheck").change(function () {
	   	    $("input:checkbox[name=selectedissue]").prop('checked', $(this).prop("checked"));
	   	});
   		$('#acceptBtn').click(function(event){
			var selectedIssues = [];
			$("input:checkbox[name=selectedissue]:checked").each(function(){
				selectedIssues.push($(this).val());
			});
			if(selectedIssues.length!=0){
				bootbox.confirm("Are you sure you want to return these books?", function(result){
					if(result) {
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/olmsb-web/member/books/return/accept",
			data : JSON.stringify(selectedIssues),
			dataType : 'json',
			timeout : 10000,
			success : function(response) {
				if(response.success) {
					toastr.success('Book return requested');
					document.getElementById("loader").style.display = "block";
                    setTimeout(showPage, 2000);
					function showPage() {
                      document.getElementById("loader").style.display = "none";
					  $(location).attr('href', '/olmsb-web/member/books/return/page');
					}
				}
				else{
					var errors = response.errors;
					for(var i=0; i < errors.length; i++) {
						toastr.error(errors[i]);
					}
					
				}	
			},
			error : function(err) {
				toastr.error('Error while returning books');
				console.log("ERROR: ", err);
			}
		});
		}
	}); 
			}
			else{toastr.error("Please select atleast one issue");}
   	});
   	});
   	 </script>
	 <c:import url="../memberMenu.jsp" />
	 <div class="page-header">
			<h4 id="pagetitle">Return Book Query Result</h4>
		</div>
	 <div class="form-container">		
		<div class="row">
		<div class="col-md-offset-9 pull-right">
				<button type="button" id="acceptBtn" class="btn btn-info">Return</button>
			</div>
			<br>
		
		  <table class="table table-striped table-bordered table-hover table-condensed">
		    <thead>
		      <tr>
		      	<th><input type="checkbox" id=parentCheck></th>
		        <th>Book Name</th>
		        <th>ISBN</th>
		        <th>Category</th>
		        <th>Request Date</th>
		        <th>Issue Status</th>
		      </tr>
		    </thead>
		    <tbody>
		    	<c:forEach items="${data}" var="issue">
			  	  <tr>
			  	  	<td><input id="selectedIssues" type="checkbox" name=selectedissue value="${issue.issuePk}"></td>
			  	  	<td> <c:out value="${issue.bookName}"/></td>
			        <td> <c:out value="${issue.isbn}"/></td>
			        <td> <c:out value="${issue.category}"/></td>
			        <td> <c:out value="${issue.requestDate}"/></td>
			        <td> 
			        <c:choose>
					    <c:when test="${issue.issueStatus == 'ISSUED'}">
					       Issued
					    </c:when>
					</c:choose>
			        </td>
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