<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="Romanov V.M.">
    <title>Poll Service</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container col-md-offset-2">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><img height="35" src="/resources/img/sLogo_final.png"></a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Опросы</a></li>
                <li><a href="/manage">Управление</a></li>
                <li><a href="/manage/stats">Статистика</a></li>
                <li><a href="/manage/settings">Настройки</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>

<div class="container col-md-8 col-lg-offset-2" style="margin-top: 20px;">
    <div class="btn-group" style="margin-bottom: 10px">
        <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
            <span class="glyphicon glyphicon-th-list"></span>
            <span id="current-group-selection"> Все опросы и тесты</span>
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu">
            <li class="pollgroup-selection"><a href="#">Все опросы и тесты</a></li>
            <li class="pollgroup-selection"><a href="#">Опросы</a></li>
            <li class="pollgroup-selection"><a href="#">Тесты</a></li>
        </ul>
    </div>
    <c:forEach var="poll" items="${pollList}">
        <div class="panel panel-default">
            <div class="panel-heading"><h4><a href="/poll/r/${poll.id}"><c:out value="${poll.name}"/></a></h4></div>
            <div class="panel-body"><c:out value="${poll.description}"/></div>
        </div>
    </c:forEach>
</div>

<div class="footer">
    <p align="center" class="text-muted">© sPoll 2016. Developed by <a href="https://github.com/i-madness">Valery Romanov</a>. Powered by <a href="http://spring.io/">Spring Framework</a></p>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>