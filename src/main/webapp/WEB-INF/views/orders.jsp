<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
	<head>
		<title>Orders</title>
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/screen.css" />" type="text/css" media="screen, projection">
		<link rel="stylesheet" href="<c:url value="/resources/blueprint/print.css" />" type="text/css" media="print">
		<!--[if lt IE 8]>
			<link rel="stylesheet" href="<c:url value="/resources/blueprint/ie.css" />" type="text/css" media="screen, projection">
		<![endif]-->
		<script type="text/javascript" src="<c:url value="/resources/jquery-1.4.min.js" /> "></script>
		<script type="text/javascript" src="<c:url value="/resources/json.min.js" /> "></script>
	</head>
	<body>
		<div class="container">
			<h2>
				Create Order
			</h2>
			<div class="span-12 last">
				<p>
					Enter a 15 or 18 character Invoice Statement ID to create an order.
				</p>
				<form:form modelAttribute="order" action="order" method="post">
				  	<fieldset>		
						<legend>Order Fields</legend>
						<p>
							<form:label	id="idLabel" for="id" path="id" cssErrorClass="error">Id</form:label><br/>
							<form:input path="id" /><form:errors path="id" />
						</p>
						<p>	
							<input id="create" type="submit" value="Create" />
						</p>
					</fieldset>
				</form:form>
			</div>
			<hr>
			<h2>
				Existing Orders
			</h2>
			<table>
				<tr><th>Invoice ID</th><th>Order Number</th></tr>
			    <c:forEach items="${orders}" var="order">
			    	<tr><td><a href="order/${order.orderId}"><c:out value="${order.id}"/></a></td><td><a href="order/${order.orderId}"><c:out value="${order.orderId}"/></a></td></tr>
			    </c:forEach>			
			</table>
		</div>
	</body>

	<script type="text/javascript">	
		$(document).ready(function() {
			$("#order").submit(function() {
				var order = $(this).serializeObject();
				$.postJSON("order", [order], function(data) {
					$.getJSON("order/" + data[0].order_number, function(order) {
						alert("Created order "+data[0].order_number+
								"\nID = "+order.id);
						window.location.reload(true);
					});			
				}, function(data) {
					var response = JSON.parse(data.response);
					alert("Error: "+response[0].id);
				});
				return false;				
			});
		});
	</script>
	
</html>
