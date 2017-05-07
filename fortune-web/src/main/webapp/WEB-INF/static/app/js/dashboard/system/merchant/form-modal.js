$(function () {

    var $form = $('#modal-form');
    var $btn = $("#submit");

    $form.validate({
        rules: {
            merchCo: {
                required: true,
                rangelength: [5, 15],
                remote: {
                    url: ctx + "/validate/merchant",
                    type: 'post',
                    data: {
                        'merchCo': function () {
                            return $('#merchCo').val()
                        },
                        'oldMerchCo': function () {
                            return $('#old-merchCo').val();
                        }
                    }
                }
            },
            merchNm: {
                required: true,
                maxlength: 64
            },
            password: {
                required: true,
                isPassword: true
            },
            rePassword: {
                required: true,
                equalTo: "#password"
            },
            charset: {
                required: false,
                maxlength: 8
            },
            isDebug: {
                required: false,
                range: [0, 1]
            },
            ftpHost: {
                required: false,
                maxlength: 20
            },
            ftpUser: {
                required: false,
                maxlength: 64
            },
            ftpPwd: {
                required: false,
                maxlength: 128
            },
            ftpDir: {
                required: false,
                maxlength: 128
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