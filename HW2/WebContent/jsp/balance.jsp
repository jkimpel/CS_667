<BODY>
<H1>Balance Page</H1>
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