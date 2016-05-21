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
    <div class="panel panel-default">
        <div class="panel-heading"><h4>Вход в систему:</h4></div>
        <div class="panel-body">
            <c:url var="loginUrl" value="/auth" />
            <form action="${loginUrl}" method="post" class="form-horizontal">
                <c:if test="${param.error != null}">
                <div class="alert alert-danger">
                    <p>Неправильный логин или пароль</p>
                </div>
                </c:if>
                <c:if test="${param.logout != null}">
                <div class="alert alert-success">
                    <p>Авторизация прошла успешно</p>
                </div>
                </c:if>
                    <div class="input-group input-sm">
                    <label class="input-group-addon" for="username"><span class="glyphicon glyphicon-user"></span></label>
                    <input type="text" class="form-control" id="username" name="ssoId" placeholder="Введите логин" required>
                </div>
                <div class="input-group input-sm">
                    <label class="input-group-addon" for="password"><span class="glyphicon glyphicon-lock"></span></label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Введите пароль" required>
                </div>
                <input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" />

                <div class="col-md-2 col-md-offset-5">
                    <input type="submit"
                           class="btn btn-block btn-primary btn-default" value="Вход">
                </div>
        </div>
    </div>
</div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>