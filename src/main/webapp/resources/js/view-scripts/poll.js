var respondent = null;
var answers = [];
var rightAnswers = []
var rightCount = 0;

// получает правильные ответы с сервера, сверяет их с пользовательскими и проводит нужные изменения на странице
var getAnswers = function(poll_id) {
    $.get("/poll/"+poll_id+"/getAnswers",function(rightAns){
        $('.page-header').show();
        rightAnswers = rightAns;
        $('.question-panel').each(function(){ // для каждого вопроса отображаем, правильный ли ответ
            var isCorrect = false;
            $(this).find('.answer').each(function(){
                $(this).addClass('disabled');
                if($(this).prop('checked')) {
                    if($.inArray($(this).data('id'),rightAnswers)!=-1) {
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
        $('#complete-btn').addClass('disabled');
        $('small').html(respondent.name+',');
        $('.progress-bar').html(rightCount+" правильных ответов из "+answers.length);
        var percent = 100*rightCount/rightAns.length;
        if(percent > 0)
            $('.progress-bar').css('width', percent+'%').attr('aria-valuenow', percent);
        else {
            $('.progress-bar').removeClass('progress-bar-success');
            $('.progress-bar').addClass('progress-bar-danger')
        }
        $('html,body').animate({scrollTop: 0},'slow')
    })
}

// активация модали и всплывающих подсказок
$(window).load(function () {
    $('#respondent-modal').modal({
        keyboard: false,
        backdrop: "static"
    });
    $('#respondent-name').tooltip();
    $('#respondent-email').tooltip();
});

// валидация введённых пользователем данных и формирование объекта с данными
$('body').on('click','#accept-data',function(){
    var respondentName = $('#respondent-name').val();
    var email = $('#respondent-email').val();
    if (!/^[а-яА-ЯёЁa-zA-Z0-9]+$/.test(respondentName)) {
        $('#respondent-name').tooltip('toggle');
        return false;
    }
    if (!/^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/.test(email)) {
        $('#respondent-email').tooltip('toggle');
        return false;
    }
    respondent = { id: 0, name: respondentName, email: email, polls: null, answers: null };
    $('#respondent-modal').modal('hide');
});

// завершение прохождения опроса: отправка данных на сервер, получение правильных ответов и отображение результатов
var exchangeData = function(poll_id){
    //if(respondent == null)
        //location.reload();
    //else {
        $('.answer').each(function(){
            if($(this).prop('checked'))
                answers.push($(this).data('id'))
        });
        var holder = { respondent: respondent, options: answers };
        $.ajax({
            type: 'POST',
            url: "/poll/"+poll_id+"/save",
            contentType: 'application/json',
            data: JSON.stringify(holder),
            success: getAnswers(poll_id)
        });
    //}
};

$('body').on('click','.opt-content',function(){
    $(this).parent().find('.answer').prop('checked',true)
});