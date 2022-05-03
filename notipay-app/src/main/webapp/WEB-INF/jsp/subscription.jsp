<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>${error}</h1>
	<form action="/add-subscription/${userId}" method="post"> 
		Subscription Name: <input type="text" id="subscriptionName" name="subscriptionName"> <br>
		Amount: <input type="number" id="amount" name="amount"> <br>
		Date: <input type="date" id="renewalDate" name="renewalDate"> <br>
		<button type="submit"> Add Subscription </button>
	</form>
	
</body>
</html>