<!doctype html>
<html lang="en">
<!-- 
		Joe Kimpel CS667
		6.24.2012
 -->
<head>
	<title>Customer Payment</title>
	<meta charset="utf-8"/>
</head>
<BODY>
	<H1>Payment Page</H1>
	<jsp:useBean id="cust" type="kimpel.beans.CustomerInfo" scope="session" />
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