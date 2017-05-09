<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="${trans.tranCo???string('编辑交易', '添加新交易')}" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/merchant/trans/${trans.tranCo???string('update', 'save')}">
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>交易名称<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <#if trans.tranCo??>
                    <@s.formInput "trans.tranCo" 'class="form-control readonly" readonly'/>
                <#else>
                    <select name="tranCo" class="form-control">
                        <#list allTrans as t>
                            <option value="${t.code}">${t.value}</option>
                        </#list>
                    </select>
                </#if>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>单笔限额</label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "trans.singQuota" 'class="form-control" placeholder="默认为-1，代表无限额"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>日累计限额</label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput path="trans.dateQuota" attributes='class="form-control" placeholder="默认为-1，代表无限额"' fieldType="number"/>
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

<button class="btn btn-sm btn-inverse" id="submit" data-loading-text="正在保存..." data-toggle="form-submit"
        data-target="#modal-form">
    <i class="ace-icon fa fa-check"></i>
    <@s.message "app.button.save"/>
</button>

<script src="${ctx}/static/app/js/dashboard/merchant/trans/form-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>