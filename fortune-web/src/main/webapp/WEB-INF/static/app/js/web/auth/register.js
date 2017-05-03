$(function () {
    var $form = $('#register-form');
    var $btn = $("#submit");

    $form.validate({
        rules: {
            username: {
                required: true,
                isUsername: true,
                remote: {
                    url: "/validate/user",
                    type: 'post',
                    data: {
                        'username': function () {
                            return $('#username').val()
                        }
                    }
                }
            },
            password: {
                required: true,
                isPassword: true
            },
            rePassword: {
                required: true,
                equalTo: "#password"
            },
            fullname: {
                required: true,
                isFullname: true
            },
            captcha: {
                required: true,
                isCaptcha: true
            },
            agree: {
                required: true
            }
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

