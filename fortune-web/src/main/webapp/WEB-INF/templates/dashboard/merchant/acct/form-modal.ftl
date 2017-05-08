<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="${merchAcct.merchAcctNo???string('编辑银行卡', '添加新银行卡')}" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/merchant/acct/${merchAcct.merchAcctNo???string('update', 'save')}">
    <#if merchAcct.merchAcctNo??>
        <input type="hidden" name="id" value="${merchAcct.id}"/>
    </#if>

    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>卡号<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "merchAcct.merchAcctNo" 'class="form-control" placeholder="银行卡号：最多20位"'/>
                <input type="hidden" id="old-merchAcctNo" value="${merchAcct.merchAcctNo!''}"/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>户名<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "merchAcct.merchAcctNm" 'class="form-control" placeholder="账户姓名：最多20位"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>证件类型<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <select name="merchIdTp" class="form-control">
                    <#list idTps as idTp>
                        <option value="${idTp.code}">${idTp.value}</option>
                    </#list>
                </select>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>证件号</label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "merchAcct.merchIdNo" 'class="form-control" placeholder="证件号码：最多40位"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>手机号</label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "merchAcct.merchMobile" 'class="form-control" placeholder="手机号：11位"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>是否主卡</label>
            </div>
            <div class="col-md-7 controls">
                <label class="inline">
                    <input name="isMaster" value="0" type="radio" class="ace"
                           <#if !merchAcct.merchAcctNo?? || merchAcct.isMaster==0>checked</#if>>
                    <span class="lbl middle"> 否</span>
                </label>

                &nbsp; &nbsp; &nbsp;
                <label class="inline">
                    <input name="isMaster" value="1" type="radio" class="ace"
                           <#if merchAcct.merchAcctNo?? && merchAcct.isMaster==1>checked</#if>>
                    <span class="lbl middle"> 是</span>
                </label>
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

<script src="${ctx}/static/app/js/dashboard/merchant/acct/form-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>