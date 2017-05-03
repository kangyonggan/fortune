$(function () {
    var $form = $('#login-form');
    var $btn = $("#login");

    $("#reset").click(function () {
        $form.find("input").val("");
        return false;
    });

    $form.validate({
        rules: {
            username: {
                required: true,
                rangelength: [5, 30]
            },
            password: {
                required: true,
                isPassword: true
            },
            captcha: {
                required: true,
                isCaptcha: true
            }
        },
        messages: {
            username: "请输入5至20位的用户名、手机号或者邮箱"
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

