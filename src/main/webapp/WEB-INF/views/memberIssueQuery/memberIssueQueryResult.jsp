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
		
 	    $('#parentCheck').click(function(event){
			$('input[type="checkbox"]').val('checked');
		});
   	 
   	 </script>
	 <c:import url="../memberMenu.jsp" />
	 <div class="page-header">
			<h4 id="pagetitle">Member Issue Query Result</h4>
		</div>
	 <div class="form-container">		
		<div class="row">
		
		  <table class="table table-striped table-bordered table-hover table-condensed">
		    <thead>
		      <tr>
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
			  	  	<td> <c:out value="${issue.bookName}"/></td>
			        <td> <c:out value="${issue.isbn}"/></td>
			        <td> <c:out value="${issue.category}"/></td>
			        <td> <c:out value="${issue.requestDate}"/></td>
			        <td> 
			        <c:choose>
					    <c:when test="${issue.issueStatus == 'ISSUE_REQUESTED'}">
					       Issue Request
					    </c:when>
					     <c:when test="${issue.issueStatus == 'RETURN_REQUESTED'}">
					       Return Request
					    </c:when>
					    <c:when test="${issue.issueStatus == 'ISSUED'}">
					       Issued
					    </c:when>
					     <c:when test="${issue.issueStatus == 'RETURNED'}">
					       Returned
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