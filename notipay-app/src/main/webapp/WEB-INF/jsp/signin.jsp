<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign In to Notipay</title>
<script type="text/javascript">
	let error = "${error}";
	let success = "${success}";
	if (error != "") {
		alert(error);
		error = "";
	} 
	if (success != "") {
		alert(success);
	} 
</script>
</head>
<body>
	<form action="/auth" onsubmit="return validate()" method="post">
		Email: <input type="email" id="email" name="email"> <br>
		Password: <input type="password" id="password" name="password"> <br>
		<button type="submit">Sign In</button>
	</form>

</body>
<script src="js/mainScript.js"></script>
</html>