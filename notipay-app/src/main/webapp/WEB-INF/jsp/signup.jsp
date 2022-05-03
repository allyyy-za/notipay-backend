<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign Up to Notipay</title>
<script type="text/javascript">
	let error = "${error}";
	let success = "${success}";
	if(error != "") {
		alert(error);
	} 
	if(success != "") {
		alert(success);
	}
</script>
<script src="js/mainScript.js"> </script>
</head>
<body>
	<form action="/register" onSubmit="return checkPassword()" method="post">
		Full Name: <input type="text" id="fullName" name="fullName"><br>
		Email: <input type="email" id="email" name="email"> <br>
		Password <input type="password" id="password" name="password"> <br>
		Confirm Password <input type="password" id="confirmPassword" name="confirmPassword"> <br>
		<button type="submit"> Sign Up </button>
	</form>

</body>
</html>