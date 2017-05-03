$(function () {
    updateState("content/content");

    /**
     * 初始化markdown编辑器
     */
    $("#content-md").markdown({resize: 'vertical'});

    /**
     * 附件
     */
    $('input[type=file]').ace_file_input()
        .closest('.ace-file-input')
        .addClass('width-90 inline')
        .wrap('<div class="form-group file-input-container"><div class="col-sm-7"></div></div>');

    $('#id-add-attachment')
        .on('click', function () {
            var file = $('<input type="file" name="attachment[]" />').appendTo('#form-attachments');
            file.ace_file_input();

            file.closest('.ace-file-input')
                .addClass('width-90 inline')
                .wrap('<div class="form-group file-input-container"><div class="col-sm-7"></div></div>')
                .parent().append('<div class="action-buttons pull-right col-xs-1">\
            <a href="#" data-action="delete" class="middle">\
                <i class="ace-icon fa fa-trash-o red bigger-130 middle"></i>\
            </a>\
        </div>')
                .find('a[data-action=delete]').on('click', function (e) {
                e.preventDefault();
                $(this).closest('.file-input-container').hide(300, function () {
                    $(this).remove()
                });
            });
        });

    $(".remove-old").click(function () {
        var $trigger = $(this);
        var url = $trigger.data('url');
        var title = $trigger.attr("title");

        $.messager.confirm("提示", "确定" + title + "吗?", function () {
            $.get(url).success(function () {
                $trigger.parents(".attachment-parent").remove();

                if ($(".old-attachments-list").children().length == 0) {
                    $(".old-attachments").remove();
                }

                Message.success("删除成功！")
            }).error(function () {
                Message.error("网络错误，请稍后重试");
            })
        });
    });

    var $form = $('#content-form');
    var $btn = $("#submit");

    $form.validate({
        rules: {
            title: {
                required: true,
                maxlength: 128
            },
            body: {
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
                        window.location.href = window.location.origin + window.location.pathname + "#content/content";
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