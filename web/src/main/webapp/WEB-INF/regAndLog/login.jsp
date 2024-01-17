<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <input type="text" id="login" name="login" value="${param.login}" required>
            <label for="login-password">Password:</label>
            <input type="password" id="login-password" name="login-password" required>
            <button type="submit">Login</button>
        </form>
        <c:if test="${param.error != null}">
            <div style="color: red; text-align: center; font-size: 16px; font-weight: bold; margin: 40px">
                <span>Incorrect login or password!</span>
            </div>
        </c:if>
        <p>Нет аккаунта? <a href="/registration">Регистрация</a></p>
    </div>
</div>
</body>
</html>
