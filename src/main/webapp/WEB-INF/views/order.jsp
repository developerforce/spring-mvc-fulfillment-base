<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<title>Order</title>
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/screen.css" />" type="text/css" media="screen, projection">
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/print.css" />" type="text/css" media="print">
		<!--[if lt IE 8]>
			<link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection">
		<![endif]-->
		<link rel="stylesheet" href="<c:url value="/resources/popup.css" />" type="text/css" media="screen, projection">
		<script type="text/javascript" src="<c:url value="/resources/jquery-1.4.min.js" /> "></script>
		<script type="text/javascript" src="<c:url value="/resources/json.min.js" /> "></script>
	</head>
	<body>
		<div class="container">
			<h2>
				Order <c:out value="${order.orderId}"/>
			</h2>
			<div class="span-12 last">
				<h3>Invoice</h3>	
				<p>Number: <c:out value="${invoice.number}"/></p>
				<p>Description: <c:out value="${invoice.description}"/></p>
				<p>Status: <c:out value="${invoice.status}"/></p>
				<input id="delete" type="submit" value="Delete" />
			</div>
		</div>	
	</body>
	<script>
	$("#delete").click(function() {
		$.deleteJSON("${order.orderId}", function(data) {
			alert("Deleted order ${order.orderId}");
			location.href = "/order";
		}, function(data) {
			alert("Error deleting order ${order.orderId}");
		});
		return false;				
	});
	</script>
</html>