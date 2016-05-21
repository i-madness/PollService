
var newPassValue;
var newPassRepValue;

$('body').on('click', '#change-passwd', function () {
   $('#chg-passwd').fadeIn(100);
});

$('body').on('change','#new-passwd', function () {
    newPassValue = $(this).val();
});

$('body').on('change','#new-passwd-repeat', function () {
    newPassRepValue = $(this).val();
});

$('body').on('click', '#chg-passwd-confirm', function () {
    if (newPassValue == "" || newPassRepValue == "")
        alert('nope');
    if (newPassValue == newPassRepValue) {
        $('#chg-passwd').fadeOut(200);
        $('#password').val(newPassValue);
    } else {
        alert('nope');
    }
});