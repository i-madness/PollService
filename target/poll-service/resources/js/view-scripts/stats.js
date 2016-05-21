
    var statData;

    $('body').on('click', '.poll-link', function () {
        $('#poll-name').html($(this).children('a').html());
        var id = $(this).data('id');
        $.get('/manage/stats/' + $(this).data('id'), function (data) {
            $('#poll-stats').empty();
            $('#poll-panel').fadeIn(200);
            $('#poll-stats').append('<thead><td><b>Вопрос</b></td><td><b>Правильно ответили</b></td></thead><tbody></tbody>')
            $('#download-stats').show();
            statData = data;
            $('#poll-info').html("Всего опрос прошли: " + statData[0][1] + " человек");
            for (var i = 1; i < statData.length; i++)
                $('#poll-stats').children('tbody').append('<tr data-id="'+ id +'"><td>' + statData[i][0] + '</td><td>' + statData[i][1] + '</td></tr>')
            $('#download-stats').prop('href', '/manage/stats/' + id + '/download');
        });
    });

    $('body').on('click', 'tr', function () {
        $.get('/manage/stats/questionresp/', function (data) {
            $('#qrespondents').empty();
            for (var i = 0; i < data.length; i++) {
                var icolbl = data[i].id < 70 ? '<span class="label label-success"><span class="glyphicon glyphicon-ok"></span></span>'
                                             : '<span class="label label-danger"><span class="glyphicon glyphicon-remove"></span></span>'
                $('#qrespondents').append('<tr><td>' + data[i].id + '</td>' +
                '<td>' + data[i].name + '</td>' +
                '<td>' + data[i].ipAddress + '</td>' +
                '<td align="center">' + icolbl + '</td></tr>');
            }
            $('#respondent-modal').modal('show');
        });
    });

