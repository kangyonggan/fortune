<#assign ctx="${(rca.contextPath)!''}">

<link rel="stylesheet" href="${ctx}/static/ace/dist/css/dropzone.min.css"/>
<link rel="stylesheet" href="${ctx}/static/libs/bootstrap/css/bootstrap-markdown.min.css"/>

<div class="space-10"></div>
<form id="content-form" method="post" enctype="multipart/form-data" class="form-horizontal"
      action="${ctx}/dashboard/content/content/${content.id???string('update', 'save')}">

<#if content.id??>
    <input type="hidden" name="id" value="${content.id}"/>
</#if>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right">模板<span class="red">*</span></label>
        <div class="col-xs-12 col-sm-5">
            <select name="template" class="form-control">
            <#list templates as t>
                <option value="${t.code}"
                        <#if content.id?? && content.template==t.code>selected</#if>>${t.value}</option>
            </#list>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-3 control-label no-padding-right">标题<span class="red">*</span></label>
        <div class="col-xs-12 col-sm-5">
        <@spring.formInput "content.title" 'class="form-control" placeholder="简单描述一下内容,最多128字"'/>
        </div>
    </div>

    <div class="form-group" id="markdown-content">
        <label class="col-xs-2 control-label pull-left">内容<span class="red">*</span></label>
        <div class="col-xs-10 col-xs-offset-1">
                <textarea required name="body" class="width-100" id="content-md" rows="13"
                          placeholder="请输入内容">${content.body!''}</textarea>
        </div>
    </div>

    <div class="hr hr-18 dotted"></div>

<#if content.id?? && attachments?size gt 0>
    <div class="form-group old-attachments">
        <label class="col-xs-10 col-xs-offset-1 pull-left">原附件</label>

        <div class="col-xs-10 col-xs-offset-1 old-attachments-list">
            <#list attachments as attachment>
                <#include "attachment.ftl"/>
            </#list>
        </div>
    </div>
</#if>

    <div class="form-group">
        <label class="col-xs-10 col-xs-offset-1 pull-left"><#if content.id?? && attachments?size gt 0>新</#if>附件</label>

        <div class="col-xs-10 col-xs-offset-1">
            <div id="form-attachments">
                <input type="file" name="attachment[]"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="center">
            <button id="id-add-attachment" type="button" class="btn btn-sm btn-danger">
                <i class="ace-icon fa fa-paperclip bigger-140"></i>
                添加更多
            </button>
        </div>
    </div>

    <div class="clearfix form-actions">
        <div class="col-xs-offset-3">
            <button id="submit" class="btn btn-inverse" data-loading-text="正在提交...">
                <i class="ace-icon fa fa-check"></i>
            <@s.message "app.button.save"/>
            </button>
        </div>
    </div>
</form>

<script type="text/javascript" src="${ctx}/static/libs/bootstrap/js/marked.min.js"></script>
<script type="text/javascript" src="${ctx}/static/libs/bootstrap/js/bootstrap-markdown.min.js"></script>
<script src="${ctx}/static/app/js/dashboard/content/content/form.js"></script>
