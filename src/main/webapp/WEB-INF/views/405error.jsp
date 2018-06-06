<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<spring:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="/resources/css/global.css" var="globalCss" />
<spring:url value="/resources/js/libs/jquery-1.12.4.min.js"
	var="jqueryJs" />
<spring:url value="/resources/js/libs/bootstrap.min.js"
	var="bootstrapJs" />
<spring:url value="/resources/images/405error.jpg" var="abc" />

<!-- Bootstrap -->
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${globalCss}" rel="stylesheet" />

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${jqueryJs}"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${bootstrapJs}"></script>
</head>
<body>
	<script>
	//$("body").css("background-image","url('${abc}')","auto");
	</script>
	<img src="${abc}"
		style="position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%);"></img>
<footer class="footercontainer navbar-fixed-bottom">
		<p>&copy; Nomura Research Institute, Ltd.</p>
	</footer>
</body>
</html>