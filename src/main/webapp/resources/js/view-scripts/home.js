var wrapPollCategory = function (category) {
    $('.panel').remove();
    $(category).each(function () {
        $('<div class="panel panel-default">')
            .append('<div class="panel-heading"><h4><a href="/poll/r/'+this.id+'">'+this.name+'</a></h4></div>')
            .append('<div class="panel-body">'+this.description+'</div>')
            .appendTo($('#panel-container'));
    });
}

$('body').on('click', '.pollgroup-selection', function () {
    var selectedVal = $(this).html()
    $('#current-group-selection').html(selectedVal);
    switch ($(this).data('id')) {
        case 1 : wrapPollCategory(allEntry);
            break;
        case 2 : wrapPollCategory(allPolls);
            break;
        case 3 : wrapPollCategory(allTests);
            break;
    }
});