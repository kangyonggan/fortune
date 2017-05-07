<#assign modal_title="${dictionary.id???string('编辑字典', '添加新字典')}" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/system/dictionary/${dictionary.id???string('update', 'save')}">
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>类型<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <select name="type" class="form-control">
                    <#list types as e>
                        <option value="${e.getType()}"
                                <#if dictionary.id?? && dictionary.type=='${e.getType()}'>selected</#if>>${e.getName()}</option>
                    </#list>
                </select>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>字典代码<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "dictionary.code" 'class="form-control" placeholder="1至32位以字母开头的小写字母和数字的组合"'/>
                <input type="hidden" id="old-code" value="${dictionary.code!''}"/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>名称<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "dictionary.value" 'class="form-control" placeholder="1至64个字符"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label for="sort">排序</label>
            </div>

            <div class="col-md-7 controls">
                <@s.formInput "dictionary.sort" 'class="form-control" placeholder="0排在最上面"'/>
            </div>
        </div>
    </div>
</form>
</@override>

<@override name="modal-footer">
<button class="btn btn-sm" data-dismiss="modal">
    <i class="ace-icon fa fa-times"></i>
    <@s.message "app.button.cancel"/>
</button>

<button class="btn btn-sm btn-inverse" id="submit"
        data-loading-text="正在保存..." data-toggle="form-submit" data-target="#modal-form">
    <i class="ace-icon fa fa-check"></i>
    <@s.message "app.button.save"/>
</button>

<script src="${ctx}/static/app/js/dashboard/system/dictionary/form-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>