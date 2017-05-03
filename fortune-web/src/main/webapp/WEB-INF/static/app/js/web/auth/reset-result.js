$(function () {
    setTimeout(function () {
        var hash = $("#jump-btn").attr('href');
        if (hash) {
            window.location.href = ctx + hash;
        }
    }, 5000);
});

