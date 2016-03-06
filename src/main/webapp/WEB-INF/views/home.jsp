<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Poll Service</title>
</head>
<body>
    <h1>
        <c:forEach var="resp" items="${opt}">
            <c:out value="${resp.name}"></c:out><br>
        </c:forEach>
    </h1>
</body>
</html>