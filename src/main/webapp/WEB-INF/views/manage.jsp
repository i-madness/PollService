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
    <title>Poll Service: управление опросами / настройки</title>
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"></script>
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
            <a class="navbar-brand" href="/"><img height="35" src="/resources/img/sLogo_final.png"></a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="/">Опросы</a></li>
                <li class="active"><a href="/manage">Управление</a></li>
                <li><a href="/manage/stats">Статистика</a></li>
                <li><a href="/manage/settings">Настройки</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>

<div class="page-header col-md-8 col-md-offset-2">
    <h3>Управление опросами</h3>
</div>

<div class="container col-md-8 col-md-offset-2" style="margin-top: 20px; margin-bottom: 30px">
    <div><%--Container for buttons--%>
        <div class="btn-group">
            <button class="btn btn-default btn-lg dropdown-toggle" type="button" data-toggle="dropdown">
                Выберите опрос <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                <c:forEach var="poll" items="${polls}">
                    <li class="poll-link" data-id="${poll.id}"><a href="#"><c:out value="${poll.name}"/></a></li>
                </c:forEach>
            </ul>
        </div>
        <div class="btn-group">
            <button id="new-poll" class="btn btn-primary btn-lg" type="button" data-toggle="dropdown">
                <span class="glyphicon glyphicon-th-list"></span> Новый опрос
            </button>
            <button id="new-test" class="btn btn-primary btn-lg" type="button" data-toggle="dropdown">
                <span class="glyphicon glyphicon-list-alt"></span> Новый тест
            </button>
        </div>
    </div>

    <%--Container for poll panel--%>
    <div class="panel panel-default" id="poll-panel" style="display: none">
        <div class="panel-heading" style="overflow: auto">
            <h4 id="poll-name"></h4>
            <div class="btn-group floating-button">
                <button id="edit-poll" class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span></button>
                <button id="save-poll" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-save"></span> Сохранить изменения</button>
                <button id="add-quest" class="btn btn-info"><span class="glyphicon glyphicon-plus"></span> Добавить вопрос</button>
                <button id="delete-poll" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span></button>
            </div>
        </div>
        <div class="panel-body">
            <div id="poll-description"></div>
        </div>
    </div>

    <div class="col-md-offset-1 col-md-10" id="question-body"><%--Container for questions--%></div>
</div>

<footer class="footer">
    <div class="">
        <p class="text-muted" align="center">© sPoll 2016</p>
    </div>
</footer>

<script src="/resources/js/view-scripts/manage.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>