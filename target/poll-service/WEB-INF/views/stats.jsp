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
        <table id="poll-stats" class="table table-striped">
        </table>
    </div>



</div>

<script>
    var statData;
    $('body').on('click','.poll-link',function(){
        $('#poll-name').html($(this).children('a').html());
        var id = $(this).data('id');
        $.get('/manage/stats/'+$(this).data('id'), function(data){
            $('#poll-stats').empty();
            $('#poll-panel').show();
            $('#poll-stats').append('<thead><td>Вопрос</td><td>Правильно ответили</td></thead><tbody></tbody>')
            $('#download-stats').show();
            statData = data;
            $('#poll-info').html("Всего опрос прошли: "+statData[0][1]+" человек");
            for(var i=1; i < statData.length; i++)
                $('#poll-stats').children('tbody').append('<tr><td>'+statData[i][0]+'</td><td>'+statData[i][1]+'</td></tr>')
            $('#download-stats').prop('href','/manage/stats/'+id+'/download');
        });
    });
</script>

<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>