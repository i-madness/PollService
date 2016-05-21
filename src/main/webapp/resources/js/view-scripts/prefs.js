
var newPassValue;
var oldPassValue;

$('body').on('click', '#change-passwd', function () {
   $('#chg-passwd').fadeIn(100);
});

$('#new-password').on('change', function () {
    newPassValue = $(this).val();
});

$('#old-password').on('change', function () {
    oldPassValue = $(this).val();
});

$('body').on('click', '#chg-passwd-confirm', function () {
    if (oldPassValue == "" || newPassValue == "")
        $('#wrong-pass-alert').fadeIn(200);
    if (oldPassValue == password) {
        $('#chg-passwd').fadeOut(200);
        $('#old-password').val("");
        $('#new-password').val("");
        $('#password').val(newPassValue);
        $('#wrong-pass-alert').fadeOut(200);
        password = newPassValue;
    } else {
        $('#wrong-pass-alert').fadeIn(200);
    }
});

$('body').on('click', '#chg-passwd-cancel', function () {
    $('#chg-passwd').fadeOut(100);
    $('#old-password').val("");
    $('#new-password').val("");
    $('#wrong-pass-alert').fadeOut(200);
});

$('body').on('click','#apply-settings-btn', function () {
   var newSettings = JSON.stringify({
      login : $('#username').val(),
      password : password
   });
    $.ajax({
        type: 'POST',
        url: '/manage/settings/newSettings',
        contentType: 'application/json; charset=utf-8',
        data : JSON.stringify(newSettings),
        success : function () {
            $('#alert-msg').removeClass('alert-danger').addClass('alert-success').html('Настройки были успешно сохранены!').fadeIn(200);
            setTimeout(function () {
                $('#alert-msg').fadeOut(200);
            }, 6000)
        },
        fail : function () {
            $('#alert-msg').removeClass('alert-success').addClass('alert-danger').html('<b>Ошибка сервера:</b> настройки не были сохранены').fadeIn(200);
            setTimeout(function () {
                $('#alert-msg').fadeOut(200);
            }, 6000)
        }
    })

});