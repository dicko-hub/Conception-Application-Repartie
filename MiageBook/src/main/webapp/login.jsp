<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/inscription.css" />
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/inscription.js"></script>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<script src="bootstrap/js/bootstrap.min.js"></script>
</head>
</head>
<body>
<div class="container">
  <form action="login" method="post">
	<c:if test="${not empty requestScope.message }">
	<p style="color: red">${requestScope.message}</p>
	</c:if>
	 <h2>Bienvenu veuillez vous identifier</h2>
    <div class="form-group">
      <label for="pseudo">Pseudo:</label>
      <input type="text" class="form-control" id="pseudo" placeholder="Entrer votre pseudo" name="pseudo">
    </div>
    <div class="form-group">
      <label for="password">Password:</label>
      <input type="password" class="form-control" id="password" placeholder="Entrer votre mot de passe" name="password">
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
  </form>
</div>
</body>
</html>