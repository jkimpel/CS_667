<!doctype html>
<html lang="en">
<!-- 
		Joe Kimpel CS667
		6.24.2012
 -->
<head>
	<title>Customer Info</title>
	<meta charset="utf-8"/>
</head>
<BODY>
	<H1>Customer Info Page</H1>
	<UL>
	  <LI><B>Customer ID:</B> ${cust.customerID}
	  <LI><B>Last Name:</B> ${cust.lastName}
	  <LI><B>First Name:</B> ${cust.firstName}
	  <LI><B>Email Address:</B> ${cust.emailAddress}
	</UL>
	<FORM action="CustomerOrderServlet">
	     <%@ include file="/jsp/Actions.jsp" %>
	<INPUT TYPE = "SUBMIT" VALUE="Submit">
	</FORM>
</BODY>
</html>