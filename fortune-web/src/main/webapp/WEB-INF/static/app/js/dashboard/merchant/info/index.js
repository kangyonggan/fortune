$(function () {
    updateState("merchant/info");

    var $file_input = $('#publicKey').ace_file_input({
        no_file:'请选择商户公钥...',
        btn_choose:'选择',
        btn_change:'重选',
        droppable:false,
        onchange:null,
        allowExt: ["pem"],
        maxSize: 10241000,//bytes
        thumbnail: 'fit'
    });

    $file_input.on('file.error.ace', function (event, info) {
        if (info.error_count['size']) Message.warning('超出最大上传限制。');
        if (info.error_count['ext'] || info.error_count['mime']) Message.warning('不合法的文件类型。');
        event.preventDefault();
    });

    var $form = $('#form');
    var $btn = $("#submit");

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
            merchCo: {
                required: true
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
                        var merchant = response.merchant;
                        $("#navFullname").html(merchant.merchNm);
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

