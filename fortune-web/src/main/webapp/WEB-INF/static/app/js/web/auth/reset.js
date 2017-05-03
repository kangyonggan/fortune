$(function () {
    var $form = $('#reset-form');
    var $btn = $("#submit");

    $form.validate({
        rules: {
            email: {
                required: true,
                email: true,
                remote: {
                    url: "/validate/email",
                    type: 'post',
                    data: {
                        'email': function () {
                            return $('#email').val()
                        }
                    }
                }
            },
            captcha: {
                required: true,
                isCaptcha: true
            }
        },
        messages: {
            email: "邮箱不存在"
        },
        submitHandler: function () {
            $btn.button('loading');
            $form.ajaxSubmit({
                dataType: 'json',
                success: function (response) {
                    if (response.errCode == "success") {
                        window.location.href = ctx + response.errMsg;
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
            return false;
        },
        errorPlacement: function (error, element) {
            error.appendTo(element.parent());
        },
        errorElement: "div",
        errorClass: "error"
    });
});

