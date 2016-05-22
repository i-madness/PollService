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
    <title>Poll Service: управление опросами / просмотр статистики</title>
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
                <li><a href="/manage">Управление</a></li>
                <li class="active"><a href="#">Статистика</a></li>
                <li><a href="/manage/settings">Настройки</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>

<div class="page-header col-md-8 col-md-offset-2">
    <h3>Статистика по опросам</h3>
</div>

<div class="container col-md-8 col-md-offset-2" style="margin-top: 20px;">
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
    </div>

    <%--Container for poll panel--%>
    <div class="panel panel-default" id="poll-panel" style="display: none">
        <div class="panel-heading" style="overflow: auto">
            <h4 id="poll-name"></h4>
            <div class="btn-group floating-button">
                <a id="download-stats" target="_blank" class="btn btn-primary"><span class="glyphicon glyphicon-download-alt"></span> Загрузить статистику (.csv)</a>
            </div>
        </div>
        <div id="poll-info" class="panel-body"></div>
        <table id="poll-stats" class="table table-striped table-hover">
        </table>
    </div>


    <%-- Modal --%>
    <div class="modal fade" id="respondent-modal" tabindex="-1" role="dialog" aria-labelledby="modal-name">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="modal-name">Статистика вопроса "What city is the capital of Great Britain?"</h4>
                </div>
                <div class="modal-body panel-body">
                    <table class="table table-striped table-hover">
                        <thead>
                            <td><b>id</b></td>
                            <td><b>Имя</b></td>
                            <td><b>IP-адрес</b></td>
                            <td align="center"><b>Ответил правильно</b></td>
                        </thead>
                        <tbody id="qrespondents">
                        </tbody>
                    </table>
                </div>
                <div align="center"><ul class="pagination">
                    <li><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
                    <li><a href="#">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
                </ul></div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                </div>
            </div>
        </div>
    </div>
</div>

<footer class="footer">
    <div class="container">
        <p align="center" class="text-muted">© sPoll 2016. Developed by <a href="https://github.com/i-madness">Valery Romanov</a>. Powered by <a href="http://spring.io/">Spring Framework</a></p>
    </div>
</footer>

<script src="/resources/js/view-scripts/stats.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>