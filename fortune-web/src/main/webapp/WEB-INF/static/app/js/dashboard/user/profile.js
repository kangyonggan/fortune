$(function () {
    updateState("user/profile");

    var $form = $('#form');
    var $btn = $("#submit");

    $("#remove").click(function () {
        $("#avatar-div").empty().html('<input type="file" name="avatar"/>');

        initAvatar();
        return false;
    });

    initAvatar();
    
    function initAvatar() {
        var file_input = $form.find('input[type=file]');

        file_input.ace_file_input({
            style: 'well',
            btn_choose: '点击这里添加头像',
            btn_change: null,
            no_icon: 'ace-icon fa fa-picture-o',
            droppable: false,
            allowExt: ["jpeg", "jpg", "png", "gif"],
            allowMime: ["image/jpeg", "image/jpg", "image/png", "image/gif"],
            maxSize: 2097152,//bytes
            thumbnail: 'fit'
        });

        file_input.on('file.error.ace', function (event, info) {
            if (info.error_count['size']) Message.warning('超出最大上传限制。');
            if (info.error_count['ext'] || info.error_count['mime']) Message.warning('不合法的文件类型。');
            event.preventDefault();
        });
    }

    $form.validate({
        rules: {
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
            idCard: {
                required: false,
                isIdCard: true
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
            phone: {
                required: false,
                maxlength: 32
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
            },
            qq: {
                required: false,
                maxlength: 16
            },
            weixin: {
                required: false,
                maxlength: 64
            },
            address: {
                required: false,
                maxlength: 128
            },
            webSite: {
                required: false,
                url: true,
                maxlength: 64
            },
            remarks: {
                required: false,
                maxlength: 512
            }
        },
        submitHandler: function (form, event) {
            event.preventDefault();
            $btn.button('loading');
            $(form).ajaxSubmit({
                dataType: 'json',
                success: function (response) {
                    if (response.errCode == 'success') {
                        Message.success("修改成功");
                        var user = response.user;
                        // 更新navbar和当前页
                        if (user.smallAvatar != '') {
                            $(".nav-user-photo").attr("src", ctx + "/" + user.smallAvatar);
                        }
                        $("#navFullname").html(user.fullname);
                    } else {
                        Message.error(response.errMsg);
                    }
                    $btn.button('reset');
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

