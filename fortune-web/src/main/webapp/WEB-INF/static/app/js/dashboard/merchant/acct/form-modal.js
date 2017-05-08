$(function () {

    var $form = $('#modal-form');
    var $btn = $("#submit");

    $form.validate({
        rules: {
            merchAcctNo: {
                required: true,
                rangelength: [10, 20],
                remote: {
                    url: ctx + "/validate/acct",
                    type: 'post',
                    data: {
                        'merchAcctNo': function () {
                            return $('#merchAcctNo').val()
                        },
                        'oldMerchAcctNo': function () {
                            return $('#old-merchAcctNo').val();
                        }
                    }
                }
            },
            merchAcctNm: {
                required: true,
                maxlength: 20
            },
            merchIdNo: {
                required: true,
                maxlength: 40
            },
            merchMobile: {
                required: true,
                isMobile: true
            }
        },
        submitHandler: function (form, event) {
            event.preventDefault();
            $btn.button('loading');
            $(form).ajaxSubmit({
                dataType: 'json',
                success: function (response) {
                    if (response.errCode == 'success') {
                        window.location.reload();
                    } else {
                        Message.error(response.errMsg);
                        $btn.button('reset');
                    }
                },
                error: function () {
                    Message.error("服务器内部错误，请稍后再试。");
                    $btn.button('reset');
                }
            });
        },
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        errorElement: "div",
        errorClass: "error"
    });
});