$(function () {
    updateState("content/content");
    var $table = $('#content-table');

    $table.on('click', 'a[data-role=content-delete]', function () {
        var $trigger = $(this);
        var url = $trigger.data('url');
        var title = $trigger.attr("title");

        $.messager.confirm("提示", "确定" + title + "吗?", function () {
            $.get(url).success(function () {
                window.location.reload();
            }).error(function () {
                Message.error("网络错误，请稍后重试");
            })
        });
    });
});