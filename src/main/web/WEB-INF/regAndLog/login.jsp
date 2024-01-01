<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        <%@include file="styles.css"%>
    </style>
    <title>Login</title>
</head>
<body>
<div class="container">
    <div class="form-container">
        <form action="${pageContext.request.contextPath}/login" method="post" class="login-form">
            <h2>Login</h2>
            <label for="login">Login:</label>
            <input type="text" id="login" name="login" required>
            <label for="login-password">Password:</label>
            <input type="password" id="login-password" name="login-password" required>
            <button type="submit">Login</button>
        </form>
    </div>
</div>
</body>
</html>
