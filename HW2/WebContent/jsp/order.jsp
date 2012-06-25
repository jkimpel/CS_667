<!doctype html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Order</title>
</head>
<BODY>
<H1>Order Page</H1>
	<table border="1">
		<tr>
			<th>Product</th>
			<th>Price</th>
		</tr>
		<c:forEach var="product" items="${products}">
	  		<tr><td>${product.name} </td><td> ${product.price}</td></tr>
		</c:forEach>
	</table>
	<FORM action="CustomerOrderServlet">
     	<%@ include file="/jsp/Actions.jsp" %>
		<INPUT TYPE = "SUBMIT" VALUE="Submit">
	</FORM>
</BODY>
</html>