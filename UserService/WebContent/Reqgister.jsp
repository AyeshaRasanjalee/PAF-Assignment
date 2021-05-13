<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.User"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.4.0.min.js"></script>
<script src="Components/rej.js"></script>
</head>
<body>
<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Register theUser</h1>
				<form id="formItem" name="formItem">
					 <br> User name:
					<input id="userName" name=userName type="text"
						class="form-control form-control-sm"> <br>
						 password: <input id="password" name="password" type="text"
						class="form-control form-control-sm" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"> <br> 
							Email : <input  id="email" name="email" type="email" pattern=".+@globex.com"
						class="form-control form-control-sm"> <br>
							Type : <input id="type" name="type" type="text"
						class="form-control form-control-sm"> <br>
						 <input id="btnSave" name="btnSave" type="button" value="Save" 
 class="btn btn-primary">
 <input type="hidden" id="hidItemIDSave" 
 name="hidItemIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divItemsGrid">
					 <%
						User userObj = new User();
						out.print(userObj.readUsers());
					%> 
				</div>
			</div>
		</div>
	</div>
</body>
</html>