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
        <form action="#" method="post" class="register-form" enctype="multipart/form-data">
            <label for="register-username">Username:</label>
            <input type="text" id="register-username" name="register-username" required>

            <label for="register-password">Password:</label>
            <input type="password" id="register-password" name="register-password" required>

            <label for="user-pic">Pic:</label>
            <input type="file" id="user-pic" name="user-pic">


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
