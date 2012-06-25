<!doctype html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<!-- 
		Joe Kimpel CS667
		6.24.2012
 -->
<head>
	<title>Order Page</title>
	<meta charset="utf-8"/>
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