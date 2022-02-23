<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" isErrorPage="true" %>
<%@ taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="egortags" uri="/WEB-INF/tld/egortags.tld"%>

<html>
<head>
  <title>Games</title>
  <jsp:include page="../../../include/icon.jsp"/>
  <jsp:include page="../../../include/bootstrap.jsp"/>
  <style><%@include file="gamesPage.css"%></style>
</head>
<body>

<h3>Games</h3>
<c:if test="${errorObjs != null}">
  <c:forEach items="${errorObjs}" var="errorObj">
    <div class="alert alert-danger egor-content-box">
      No such game with id <strong>${errorObj.getId()}</strong> in database !
    </div>
  </c:forEach>
</c:if>
<ul>
  <c:forEach items="${ids}" var="id" >
   <li>
     <a href="/${id}">${id.toUpperCase()}</a>
     <a href="/${id}/edit" title="Редактировать" class="hidden-underline">
       <img class="little-icon" src="https://img.icons8.com/ios/50/000000/edit-property.png" alt="[edit]"/>
     </a>
     <a href="/${id}/replace" title="Заменить" class="hidden-underline">
       <img class="little-icon" src="https://img.icons8.com/ios-filled/50/000000/replace.png" alt="[replace]"/>
     </a>
     <a href="/${id}/delete" title="Удалить" class="hidden-underline">
       <img class="little-icon" src="https://img.icons8.com/fluent/48/000000/delete-sign.png" alt="[delete]"/>
     </a>
   </li>
  </c:forEach>
</ul>
<a href="/new">Добавить игру</a>
</body>
</html>
