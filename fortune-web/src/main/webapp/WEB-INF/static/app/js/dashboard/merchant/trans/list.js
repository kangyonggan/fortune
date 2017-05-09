$(function () {
    updateState("merchant/trans");

    var $table = $('#trans-table');

    $table.on('click', 'a[data-role=trans-remove]', function () {
        var $trigger = $(this);
        var url = $trigger.data('url');
        var title = $trigger.attr("title");

        $.messager.confirm("提示", "确定" + title + "吗?", function () {
            $.get(url).success(function () {
                $trigger.parents("tr").remove();
                if ($table.find("tr").length == 1) {
                    $table.find("tbody").append('<tr><td colspan="20"><div class="empty">暂无查询记录</div></td></tr>');
                }
            }).error(function () {
                Message.error("网络错误，请稍后重试");
            })
        });
    });
});