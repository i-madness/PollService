var respondent = null;
var answers = [];
var rightAnswers = []
var rightCount = 0;

/**
 * Получает правильные ответы на тест с сервера, сверяет их с пользовательскими и проводит нужные изменения на странице
 */
var getTestAnswers = function (poll_id) {
    $.get("/poll/" + poll_id + "/getAnswers", function (rightAns) {
        $('.page-header').show();
        rightAnswers = rightAns;
        $('.question-panel').each(function () { // для каждого вопроса отображаем, правильный ли ответ
            var isCorrect = false;
            $(this).find('.answer').each(function () {
                $(this).addClass('disabled');
                if ($(this).prop('checked')) {
                    if ($.inArray($(this).data('id'), rightAnswers) != -1) {
                        rightCount++;
                        $(this).parent().parent().append('<span class="input-group-addon label-success correct-indicator"><span class="glyphicon glyphicon-ok"></span></span>');
                        isCorrect = true;
                    }
                    else
                        $(this).parent().parent().append('<span class="input-group-addon label-danger correct-indicator"><span class="glyphicon glyphicon-remove"></span></span>');
                }
            })
            if (isCorrect) {
                $(this).addClass('panel-success');
                return;
            }
            else
                $(this).addClass('panel-danger')
        })
        $('#complete-btn').remove();
        $('small').html(respondent.name + ',');
        $('.progress-bar').html(rightCount + " правильных ответов из " + answers.length);
        var percent = 100 * rightCount / rightAns.length;
        if (percent > 0)
            $('.progress-bar').css('width', percent + '%').attr('aria-valuenow', percent);
        else {
            $('.progress-bar').removeClass('progress-bar-success');
            $('.progress-bar').addClass('progress-bar-danger')
        }
        $('html,body').animate({scrollTop: 0}, 'slow')
    })
}

/**
 * Получает статистику по опросу с сервера и проводит соответствующие изменения на странице
 * @param pollId id опроса
 */
var getPollResults = function (pollId, votedId) {
    $.get("/poll/"+pollId+"/getVotes", function (votes) {
        $('.question-panel').hide();
        $('#complete-btn').hide();
        $('#poll-results').fadeIn(200);
        $('#poll-results-title').html('<h4>Результаты опроса:</h4>');
        var votesSummary = 0;
        $(votes).each(function () {
           votesSummary += this;
        });
        for (var i = 0; i < votes.length; i++) {
            var percent = 100 * votes[i] / votesSummary;
            $('#poll-results-body').append(
                '<div class="row" data-id="'+curPoll.questions[0].options[i].id+'">' +
                    '<div class="col-md-5">'+curPoll.questions[0].options[i].content+'</div>'+
                    '<div class="col-md-7">' +
                        '<div class="progress"><div class="progress-bar" role="progressbar" aria-valuenow="'+votes[i]+'" aria-valuemin="0" aria-valuemax="100" style="width: '+percent+'%;">'+votes[i]+'</div></div>' +
                    '</div>' +
                '</div>'
            );
        }
        $('#poll-results-body').find('[data-id = '+votedId+']').find('.col-md-5').first().wrapInner("<b></b>");
    });
}

/**
 * Активация модали и всплывающих подсказок
 */

$(window).load(function () {
    /*$('#respondent-modal').modal({
        keyboard: false,
        backdrop: "static"
    });*/
    $('#respondent-name').tooltip();
    $('#respondent-email').tooltip();
});

/**
 * Валидация введённых пользователем данных и формирование объекта с данными
 */
$('body').on('click', '#accept-data', function () {
    var respondentName = $('#respondent-name').val();
    var email = $('#respondent-email').val();
    if (!/^[а-яА-ЯёЁa-zA-Z0-9]+$/.test(respondentName)) {
        $('#respondent-name').tooltip('toggle');
        return false;
    }
    respondent = {id: 0, name: respondentName, email: email, polls: null, answers: null};
    $('#respondent-modal').modal('hide');
});

/**
 * завершение прохождения опроса: отправка данных на сервер, получение правильных ответов и отображение результатов
 * @param pollId id опроса/теста
 * @param isTest true, если test, иначе - false
 */
var exchangeData = function (pollId, isTest) {
    $('.answer').each(function () {
        if ($(this).prop('checked'))
            answers.push($(this).data('id'))
    });
    var holder = {respondent: respondent, options: answers};
    $.ajax({
        type: 'POST',
        url: "/poll/" + pollId + "/save",
        contentType: 'application/json',
        data: JSON.stringify(holder),
        success: isTest ? getTestAnswers(pollId) : getPollResults(pollId, answers[0])
    });
};

/**
 * Событие клика по варианту ответа теперь активирует соответствующий radio button / checkbox
 */
$('body').on('click', '.opt-content', function () {
    $(this).parent().find('.answer').prop('checked', true)
});