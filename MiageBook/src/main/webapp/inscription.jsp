<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1">
<title>S'inscrire</title>
<link rel="stylesheet" type="text/css" href="css/inscription.css" />
<script src="js/jquery-3.3.1.min.js"></script>
<script src="js/inscription.js"></script>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<script src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
  <form action="inscription" method="post">
	 <h3>Inscription</h3>
    <div class="form-group">
      <label for="pseudo">Pseudo:</label>
      <input type="text" class="form-control" id="pseudo" placeholder="Entrer votre pseudo" name="pseudo">
      <span id="msgbox" style="display:none"></span><br>
    </div>
    <div class="form-group">
      <label for="nom">Nom:</label>
      <input type="text" class="form-control" id="nom" placeholder="Entrer votre nom" name="nom">
    </div>
    <div class="form-group">
      <label for="prenom">Prenom:</label>
      <input type="text" class="form-control" id="prenom" placeholder="Entrer votre prenom" name="prenom">
    </div>
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" class="form-control" id="email" placeholder="Entrer votre email" name="email">
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