<%--
  Created by IntelliJ IDEA.
  User: SSC tuatara
  Date: 30.12.2023
  Time: 0:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        <%@include file="styles.css"%>
    </style>
    <title>Register</title>
</head>
<body>
<div class="container">
    <div class="form-container">
        <h2 class="page-title">Register</h2>
        <form action="#" method="post" class="register-form">
            <label for="register-username">Username:</label>
            <input type="text" id="register-username" name="register-username" required>

            <label for="register-email">Email:</label>
            <input type="email" id="register-email" name="register-email" required>

            <label for="register-password">Password:</label>
            <input type="password" id="register-password" name="register-password" required>

            <button type="submit" class="button-pulse">Register</button>
        </form>

        <!-- Окно успешной регистрации -->
        <div class="success-message" id="success-message">
            Congratulations! You have successfully registered.
        </div>

        <div class="separator"></div>

        <!-- Ссылка на вход -->
        <div class="login-link">
            Already have an account? <a href="login.html">Login here</a>.
        </div>
    </div>
</div>
</body>
</html>
