<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String eSignRequest = (String) session.getAttribute("eSignRequest");
	
 	
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<head>
<title>Redirecting to portal</title>
<style type='text/css'>
td {
	font-family: Verdana, Arial, sans-serif;
	font-size: 8pt;
	color: #08185A;
}

td.red {
	font-family: Verdana, Arial, sans-serif;
	font-size: 10pt;
	color: red;
}
</style>

</head>
</head>
<body onload='document.forms[0].submit()'>


	<form action='https://esignservices.karnataka.gov.in/frmesign.aspx'
		method='post'>
		<input type="hidden" name='msg' value='<%=eSignRequest%>'>
	</form>

</body>


</html>