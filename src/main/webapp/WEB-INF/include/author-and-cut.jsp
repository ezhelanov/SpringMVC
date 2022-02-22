<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="egortags" uri="/WEB-INF/tld/egortags.tld"%>

<header>
  <h6 style="background-color: aquamarine">${author}</h6>
</header>

<egortags:cut-body>
  <p style="background-color: greenyellow"><%=new Date(98, Calendar.SEPTEMBER, 7)%></p>
</egortags:cut-body>
