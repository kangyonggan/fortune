$(function () {
    updateState("merchant/protocol");

    $('.date-picker').datepicker({
        autoclose: true,
        todayBtn: true,
        todayHighlight: true
    }).next().on(ace.click_event, function () {
        $(this).prev().focus();
    });
});