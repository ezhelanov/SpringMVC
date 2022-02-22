<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Game - ${game.getName()}</title>
    <jsp:include page="../../../include/icon.jsp"/>
    <style><%@include file="gameDetailsPage.css"%></style>
</head>
<body>

<div class="box">
    <p><u>Game name:</u> ${game.getName()}</p>
    <p><u>Game year:</u> ${game.getYear()}</p>
    <p><u>Game type:</u> ${game.getType()}</p>
</div>

<jsp:include page="../../../include/goHome.jsp"/>

</body>
</html>
