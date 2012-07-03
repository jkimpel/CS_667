<!doctype html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<c:forEach var="product" varStatus="status" items="${products}">
		<c:if test="${status.first}">
   			<table border="1">
				<tr style="background-color:#A7A7A7">
					<th>Product</th>
					<th>Price</th>
				</tr>
 		</c:if>
 		<c:choose>
 			<c:when test="${status.count % 2 == 0}">
 				<tr style="background-color:#E6E6E6">
 			</c:when>
 			<c:otherwise>
 				<tr style="background-color:#CFCFCF">
 			</c:otherwise>
 		</c:choose>
  		<td>${product.name} </td>
  		<td><fmt:formatNumber type="currency" value="${product.price}"/></td>
  		</tr>
  		<c:if test="${status.last}">
  			</table>
  		</c:if>
	</c:forEach>
	<FORM action="CustomerOrderServlet">
     	<%@ include file="/jsp/Actions.jsp" %>
		<INPUT TYPE = "SUBMIT" VALUE="Submit">
	</FORM>
</BODY>
</html>