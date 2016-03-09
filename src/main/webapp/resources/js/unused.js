/**
 * poll.jsp
 */
var dataStorageTime = new Date(); // время хранения Cookie
dataStorageTime.setDate(dataStorageTime.getDate()+30); // 30 дней

if (document.cookie.indexOf("resp=; email=; id=")==-1) {
    var cookies = document.cookie.split("; ")
    respondent = {
        id: cookies[2],
        name: cookies[0],
        email: cookies[1],
        polls: null,
        answers: null
    }
    $('#clear').show();
} else {
    $(document).ready(function () {
        $('#respondent-modal').modal({
            keyboard: false,
            backdrop: "static"
        });
    });
}

$('body').on('click','#accept-data',function(){
    var respondentName = $('#respondent-name').val();
    var email = $('#respondent-email').val();

    document.cookie = "resp="+respondentName+"; expires="+dataStorageTime.toUTCString();
    document.cookie = "email="+email+"; expires="+dataStorageTime.toUTCString();
    respondent = { id: 0, name: respondentName, email: email, /*ipaddress: null,*/ polls: null, answers: null };
    $('#respondent-modal').modal('hide');
});

// очистка cookie
$('body').on('click','#clear',function() {
    document.cookie = "resp=; expires=-1";
    document.cookie = "email=; expires=-1";
    document.cookie = "id=; expires=-1";
    location.reload();
});

// завершение прохождения опроса: отправка данных на сервер, получение правильных ответов и отображение результатов
$('body').on('click','#complete-btn',function(){
    if(respondent == null)
        location.reload();
    else {
        $('.answer').each(function(){
            if($(this).prop('checked'))
                answers.push($(this).data('id'))
        });
        var holder = { respondent: respondent, options: answers };
        $.ajax({
            type: 'POST',
            url: "/poll/${id}/save",
            contentType: 'application/json',
            data: JSON.stringify(holder),
            success: function() {
                $.get("/poll/${id}/getAnswers",function(rightAns) {
                    // если успешно получили данные об ответах - проводим нужные изменения на странице
                    $('.page-header').show();
                    rightAnswers = rightAns;
                    $('.answer').each(function(){ // для каждого вопроса отображаем, правильный ли ответ
            /*  !  */   var id = document.cookie.split("; ")[2]; // сохраняем полученную от сервера Cookie
            /*  !  */   document.cookie = "id="+id+"; expires="+dataStorageTime.toUTCString();
                        $(this).addClass('disabled');
                        if($(this).prop('checked') && $.inArray($(this).data('id'),rightAnswers)) {
                            $(this).parent().parent().parent().parent().addClass('panel-success');
                            rightCount++;
                        }
                        else $(this).parent().parent().parent().parent().addClass('panel-danger');
                    })
                    $('.progress-bar').html(rightCount+"/"+answers.length);
                    $('.progress-bar').prop('aria-valuenow',rightCount/answers.length);
                    $('html,body').animate({scrollTop: 0},'slow')
                });
            }
        });
    }
});