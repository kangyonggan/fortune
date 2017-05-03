$(function () {

    var $form = $('#modal-form');
    var $btn = $("#submit");

    $form.validate({
        rules: {
            username: {
                required: true,
                isUsername: true,
                remote: {
                    url: ctx + "/validate/user",
                    type: 'post',
                    data: {
                        'username': function () {
                            return $('#username').val()
                        },
                        'oldUsername': function () {
                            return $('#old-username').val();
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
            mobile: {
                required: false,
                isMobile: true,
                remote: {
                    url: "/validate/mobile",
                    type: 'post',
                    data: {
                        'mobile': function () {
                            return $('#mobile').val()
                        },
                        'oldMobile': function () {
                            return $('#old-mobile').val()
                        }
                    }
                }
            },
            email: {
                required: false,
                email: true,
                remote: {
                    url: "/validate/email2",
                    type: 'post',
                    data: {
                        'email': function () {
                            return $('#email').val()
                        },
                        'oldEmail': function () {
                            return $('#old-email').val()
                        }
                    }
                },
                maxlength: 64
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