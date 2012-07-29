<!doctype html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<!-- 
		Joe Kimpel CS667
		6.24.2012
 -->
<head>
	<title>Kitchen Requests</title>
	<meta charset="utf-8"/>
</head>
<body>
	<h1>14th Floor Kitchen Requests:</h1>
	
	<table border="1">
		<tr>
			<th>Name</th>
			<th>Description</th>
			<th>+</th>
			<th>-</th>
			<th>To Replace:</th>
		</tr>
		<c:forEach var="req" items="${requests}">
	  		<tr>
	  			<td>${req.value.name} </td>
	  			<td>${req.value.description}</td>
	  			<td>${req.value.plusses}
	  				<c:if test="${!req.value.frozen}">
		  				<form name="plusForm" action="RequestServlet" method="get">
		  					<input name="op" type="hidden" value="plus"/>
		  					<input name="name" type="hidden" value="${req.value.name}"/>
		  					<input value="+" type="submit"/>
		  				</form>
	  				</c:if>
	  			</td>
	  			<td>${req.value.minusses}
	  				<c:if test="${!req.value.frozen}">
		  				<form name="minusForm" action="RequestServlet" method="get">
		  					<input name="op" type="hidden" value="minus"/>
		  					<input name="name" type="hidden" value="${req.value.name}"/>
		  					<input value="-" type="submit"/>
		  				</form>
	  				</c:if>
	  			</td>
	  			<td>
	  				<table border="1">
	  					<tr>
	  						<th>Replacing:</th>
	  						<th>Description:</th>
	  						<th>+</th>
	  						<th>-</th>
	  					</tr>
	  				<c:forEach var="rem" items="${removals}">
	  					<c:if test="${req.value.name == rem.value.replacedBy}">
	  						<tr>
	  							<td>${rem.value.name}</td>
	  							<td>${rem.value.description}</td>
	  							<td>${rem.value.plusses}
	  								<form name="remPlusForm" action="RequestServlet" method="get">
	  									<input name="op" type="hidden" value="remplus"/>
	  									<input name="name" type="hidden" value="${rem.value.name}"/>
	  									<input name="replacedBy" type="hidden" value="${rem.value.replacedBy}"/>
	  									<input value="+" type="submit"/>
	  								</form>
	  							</td>
	  							<td>${rem.value.minusses}
	  								<form name="remMinusForm" action="RequestServlet" method="get">
	  									<input name="op" type="hidden" value="remminus"/>
	  									<input name="name" type="hidden" value="${rem.value.name}"/>
	  									<input name="replacedBy" type="hidden" value="${rem.value.replacedBy}"/>
	  									<input value="-" type="submit"/>
	  								</form>
	  							</td>
	  						</tr>
	  					</c:if>
	  				</c:forEach>
	  				</table>
	  			</td>
	  		</tr>
		</c:forEach>
	</table>
		
	<h2>Request a new item:</h2>
	<form name="requestForm" action="RequestServlet" method="get">
		<div>
			Name: <input name="name" type="text"/>
		</div>
		<div>
			Description: <input name="description" type="text"/>
		</div>
		<div>
			<input name="op" type="hidden" value="new"/>
			<input value="Submit" type="submit"/>
			<input value="Clear" type="reset"/>
		</div>
	</form>
	<h2>Suggest a removal:</h2>
	<form name="removalForm" action="RequestServlet" method="get">
		<div>
			Replace What: <input name="name" type="text"/>
		</div>
		<div>
			With What: 
			<select name="replacedBy">
				<c:forEach var="req" items="${requests}">	
					<c:if test="${!req.value.frozen}">
						<option value="${req.value.name}">${req.value.name}</option>
					</c:if>
				</c:forEach>
			</select>
		</div>
		<div>
			Description: <input name="description" type="text"/>
		</div>
		<div>
			<input name="addition" type="hidden" value="false"/>
			<input name="op" type="hidden" value="remove"/>
			<input value="Submit" type="submit"/>
			<input value="Clear" type="reset"/>
		</div>
	</form>
	<div><a href="jsp/RequestsAdmin.jsp">Go to Admin Page</a></div>
</body>
</html>