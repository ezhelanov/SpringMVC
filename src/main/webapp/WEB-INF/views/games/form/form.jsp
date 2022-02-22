<%@ page import="egor.core.entities.GameType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <title>New game</title>
  <jsp:include page="../../../include/icon.jsp"/>
  <style><%@include file="form.css"%></style>
</head>
<body>

<form:form action="/${formAction}" modelAttribute="game" cssClass="form">

  <!-- Для передачи неявного параметра -->
  <input type="hidden" name="_method" value="${formMethod}">
  <!-- _______________________________ -->

  <div class="box">
    <span>Название игры</span>
    <div>
      <form:input path="name"/>
      <c:if test="${nameError != null}">
        <small class="error-message">${nameError.getDefaultMessage()}</small>
      </c:if>
    </div>
  </div>

  <div class="box">
    <span>Год</span>
    <div>
      <form:input type="number" path="year"/>
      <c:if test="${yearError != null}">
        <small class="error-message">${yearError.getDefaultMessage()}</small>
      </c:if>
    </div>
  </div>

  <div class="box">
    <span>Тип игры</span>
    <form:select path="type">
      <c:forEach items="<%=GameType.values()%>" var="type">
        <form:option value="${type}">${type}</form:option>
      </c:forEach>
    </form:select>
  </div>

  <div class="box-center">
    <button>
      <c:choose>
        <c:when test="${formMethod.toString().equals(\"patch\")}">
          Сохранить изменения
        </c:when>
        <c:when test="${formMethod.toString().equals(\"put\")}">
          Заменить игру
        </c:when>
        <c:otherwise>
          Добавить игру
        </c:otherwise>
      </c:choose>
    </button>
  </div>

</form:form>

<jsp:include page="../../../include/goHome.jsp"/>

<script><%@include file="form.js"%></script>

</body>
</html>
