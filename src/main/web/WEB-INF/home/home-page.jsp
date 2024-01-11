<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <c:if test="${sessionScope.theme == 'dark'}">
        <style>
            <%@include file="style-dark.css"%>
        </style>
    </c:if>
    <c:if test="${sessionScope.theme == 'light'}">
        <style>
            <%@include file="style-light.css"%>
        </style>
    </c:if>

    <title>BAZAR Chat</title>
</head>
<body>
<div class="header-tile tile">
    <h1>BazarChat</h1>
    <div id="header-buttons" class="header-buttons">
        <form action="/theme" method="get">
            <button class="theme-switch-button" name="theme" value="swap"><c:if
                    test="${sessionScope.theme == 'dark'}">&#127774;</c:if> <c:if
                    test="${sessionScope.theme == 'light'}">&#127769;</c:if></button>
        </form>
        <form action="/login" method="get">
            <button class="login-button">Login</button>
        </form>
        <form action="/logout" method="post">
            <button class="login-button">Logout</button>
        </form>
    </div>
</div>
<div class="container">
    <div id="chat" class="chat tile">
        <div id="message-box" class="message-box">

        </div>
        <!-- Форма для отправки сообщения -->
        <form id="messageForm" action="/chat" method="post">
            <input type="text" id="messageInput" name="message" placeholder="Type your message...">
<%--            <button  class="emoji-button">--%>
<%--                <img src="smile.png" alt="Smiley Emoji" class="emoji-icon">--%>
<%--            </button>--%>
            <button type="submit">BAZAR</button>
        </form>
    </div>
    <div class="online-users-tile tile">
        <h2>Online Users</h2>
        <ul class="online-users-list">
            <c:forEach items="${requestScope.onlineUsers}" var="onlineUser">
                <li>${onlineUser.login}</li>
            </c:forEach>
        </ul>
    </div>

    <div class="clock tile">
        <h2>Current Time</h2>
        <p id="clockDisplay">Loading...</p>
    </div>
    <div class="profile-tile tile">
        <h2>знает что</h2>
        <button class="profile-button">Ну чет делает</button>
    </div>
    <div class="profile-info-tile tile">
        <h2>Ты</h2>
        <div class="profile-info">
            <p><strong>Name:</strong> John Doe</p>
            <p><strong>Email:</strong> john@example.com</p>
            <p><strong>Location:</strong> City, Country</p>
        </div>
        <button class="edit-profile-button">Edit Profile</button>
    </div>
</div>
    <script>
        <%@include file="script.js"%>
    </script>
</body>
</html>

