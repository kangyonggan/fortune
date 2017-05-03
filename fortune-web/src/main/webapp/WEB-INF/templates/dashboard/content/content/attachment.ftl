<div class="col-sm-7 attachment-parent">
    <label class="ace-file-input width-90 inline">
        <a class="ace-file-container selected" data-title="下载" href="${ctx}/${attachment.path}" target="_blank">
            <span class="ace-file-name" data-title="${attachment.name}">
                <i class=" ace-icon fa fa-file"></i>
            </span>
        </a>
        <a class="remove remove-old" href="javascript:" title="删除${attachment.name}" data-url="${ctx}/dashboard/content/content/${content.id}/attachment/${attachment.id}/delete">
            <i class=" ace-icon fa fa-times"></i>
        </a>
    </label>
</div>