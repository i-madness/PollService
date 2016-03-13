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