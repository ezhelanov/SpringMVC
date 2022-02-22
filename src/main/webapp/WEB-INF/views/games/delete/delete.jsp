<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <title>Delete ${formAction.toUpperCase()}</title>
  <jsp:include page="../../../include/icon.jsp"/>
  <jsp:include page="../../../include/bootstrap.jsp"/>
  <style><%@include file="delete.css"%></style>
</head>
<body>
<form:form action="/${formAction}" cssClass="form">
  <input type="hidden" name="_method" value="${formMethod}">
  <p>Удалить игру?</p>
  <button type="submit">Да</button>
  <div class="spinner-border text-success" id="spinner" role="status">
    <span class="sr-only">Loading...</span>
  </div>
  <button type="button"><a href="/?wasRedirectedFromDelete=true">Нет</a></button>
</form:form>
<script><%@include file="delete.js"%></script>
</body>
</html>
