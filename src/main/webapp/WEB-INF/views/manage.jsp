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
            <a class="navbar-brand" href="#"><img height="35" src="/resources/img/sLogo_final.png"></a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="/">Опросы</a></li>
                <li><a href="/results">Результаты</a></li>
                <li class="active"><a href="/manage">Управление</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
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
        <div class="btn-group">
            <button class="btn btn-primary btn-lg dropdown-toggle" type="button" data-toggle="dropdown">
                <span class="glyphicon glyphicon-plus-sign"></span> Новый опрос
            </button>
        </div>
    </div>

    <%--Container for poll panel--%>
    <div class="panel panel-default" id="poll-panel" style="display: none">
        <div class="panel-heading" style="overflow: auto">
            <h3 id="poll-name"></h3>
            <div class="btn-group floating-button">
                <button id="edit-poll" class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span></button>
                <button id="save-poll" class="btn btn-primary"><span class="glyphicon glyphicon-floppy-save"></span> Сохранить изменения</button>
                <button id="add-quest" class="btn btn-info"><span class="glyphicon glyphicon-plus"></span> Добавить вопрос</button>
                <button id="delete-poll" class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span></button>
            </div>
        </div>
        <div class="panel-body" id="poll-description"></div>
    </div>

    <div class="col-md-offset-1 col-md-10" id="question-body"><%--Container for questions--%>

    </div>
</div>
<script>
    /**
     * Основные идентификаторы DOM-элементов:
     * #poll-link - кликабельный элемент списка опросов
     * #poll-panel - панель опроса
     * #poll-name - имя опроса
     * #poll-description - описание опроса
     * #delete-poll - кнопка удаления опроса
     * #edit-poll - кнопка редактирования опроса
     * #save-poll - кнопка созранения опроса
     * #add-quest - кнопка для добавления вопроса
     * #question-body - контейнер для всех панелей вопросов
     * .question-panel - панель вопроса
     * .opt-panel - панель ответа
     * .add-opt - кнопка для добавления ответа
     * .delete-quest - кнопка для удаления вопроса
     * .delete-opt - кнопка для удаления ответа
     */
    var currentPoll;
    var questions;
    var wrapQuestion = function(Question) {
        var options = Question.options;
        var wrappedQuestion = '<div data-id="'+Question.id+
                '" class="panel panel-default question-panel"><div class="panel-heading"><span class="floating-title">'+
                Question.name+'</span><div class="floating-button btn-group">' +
                '<button class="btn btn-info add-opt"><span class="glyphicon glyphicon-plus"></span></button>'+
                '<button class="delete-quest btn btn-danger"><span class="glyphicon glyphicon-trash"></span></button>' +
                '</div></div><div class="panel-body">';
        for (var i=0; i < options.length; i++) {
            wrappedQuestion += '<div data-id="'+options[i].id+'" class="input-group opt-panel"><span class="input-group-addon"><input class="is-right" type="radio"></span><input value="'+options[i].content+
                    '" type="text" class="form-control"><span class="delete-opt input-group-addon btn-danger"><span class="glyphicon glyphicon-remove"></span></span></div> ';
        }
        wrappedQuestion +='</div></div>';
        return wrappedQuestion;
    }
    var parsePollForm = function() {
        var questionPanels = $('.question-panel');
        var questions = [];
        for (var i = 0; i < questionPanels.length; i++) {
            var optionPanels = $(questionPanels[i]).children('.opt-panel');
            var options = [];
            for (var j = 0; j < optionPanels.length; j++) {
                options.push({
                    id: optionPanels[j].data('id'),
                    content: $(optionPanels[j]).children('.form-control').val(),
                    isRight: $(optionPanels[j]).children('.is-right').prop('checked')
                })
            }
            questions.push({
                id: $(questionPanels[i]).data('id'),
                name: $(questionPanels[i]).children('.floating-title').html(),
                options: options,
                poll: currentPoll
            });
        }
        return {
            id: currentPoll.id,
            name: $('#poll-name').html(),
            description: $('#poll-description').html(),
            questions: questions
        };
    }
    $('body').on('click','.poll-link',function(){
        var id = $(this).data('id');
        $.get("/manage/getpoll/"+id, function(Poll) {
            currentPoll = Poll;
            questions = currentPoll.questions;
            $('#poll-panel').show();
            $('#poll-name').html(currentPoll.name);
            $('#poll-description').html(currentPoll.description);
            $('#question-body').empty();
            $('#save-poll').hide();
            $('#add-quest').hide();
        })
    });
    $('body').on('click','#edit-poll', function(){
        $('#question-body').empty();
        $('#save-poll').show();
        $('#add-quest').show();
        if (questions !== undefined && questions != null)
            for (var i = 0; i < questions.length; i++) {
                $('#question-body').append(wrapQuestion(questions[i]));
            }

        $('#question-body').show();
    });
    $('body').on('click','#save-poll', function(){
        $('#save-poll').hide();
        $('#add-quest').hide();
    });

</script>
<script src="/resources/js/bootstrap.min.js"></script>
</body>
</html>