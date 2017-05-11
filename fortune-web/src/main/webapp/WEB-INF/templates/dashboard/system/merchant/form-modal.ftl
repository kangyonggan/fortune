<#assign ctx="${(rca.contextPath)!''}">
<#assign modal_title="${merchant.merchCo???string('编辑商户', '添加新商户')}" />

<@override name="modal-body">
<form class="form-horizontal" role="form" id="modal-form" method="post"
      action="${ctx}/dashboard/system/merchant/${merchant.merchCo???string('update', 'save')}">
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>商户号<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "merchant.merchCo" 'class="form-control" placeholder="5至15位以字母开头的小写字母和数字的组合"'/>
                <input type="hidden" id="old-merchCo" value="${merchant.merchCo!''}"/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>商户名称<span class="red">*</span></label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "merchant.merchNm" 'class="form-control" placeholder="商户名称：最多64个字符"'/>
            </div>
        </div>
    </div>
    <#if !merchant.merchCo??>
        <div class="row">
            <div class="row form-group">
                <div class="col-md-3 control-label">
                    <label>密码<span class="red">*</span></label>
                </div>
                <div class="col-md-7 controls">
                    <input type="password" id="password" name="password" class="form-control" placeholder="密码：6至20位的字母数字组合"/>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="row form-group">
                <div class="col-md-3 control-label">
                    <label>确认密码<span class="red">*</span></label>
                </div>
                <div class="col-md-7 controls">
                    <input type="password" name="rePassword" class="form-control" placeholder="确认密码：6至20位的字母数字组合"/>
                </div>
            </div>
        </div>
    </#if>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>字符集</label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "merchant.charset" 'class="form-control" placeholder="默认为UTF-8编码"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>对账模式</label>
            </div>
            <div class="col-md-7 controls">
                <label class="inline">
                    <input name="ftpType" value="" type="radio" class="ace"
                           <#if !merchant.merchCo?? || merchant.ftpType==''>checked</#if>>
                    <span class="lbl middle"> 不对账</span>
                </label>

                &nbsp; &nbsp; &nbsp;
                <label class="inline">
                    <input name="ftpType" value="common" type="radio" class="ace"
                           <#if merchant.merchCo?? && merchant.ftpType=='common'>checked</#if>>
                    <span class="lbl middle"> 普通对账</span>
                </label>

                &nbsp; &nbsp; &nbsp;
                <label class="inline">
                    <input name="ftpType" value="fund" type="radio" class="ace"
                           <#if merchant.merchCo?? && merchant.ftpType=='fund'>checked</#if>>
                    <span class="lbl middle"> 基金公司</span>
                </label>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>调试模式</label>
            </div>
            <div class="col-md-7 controls">
                <label class="inline">
                    <input name="isDebug" value="0" type="radio" class="ace"
                           <#if !merchant.merchCo?? || merchant.isDebug==0>checked</#if>>
                    <span class="lbl middle"> 否</span>
                </label>

                &nbsp; &nbsp; &nbsp;
                <label class="inline">
                    <input name="isDebug" value="1" type="radio" class="ace"
                           <#if merchant.merchCo?? && merchant.isDebug==1>checked</#if>>
                    <span class="lbl middle"> 是</span>
                </label>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>FTP主机名</label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "merchant.ftpHost" 'class="form-control" placeholder="请输入FTP主机名"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>FTP用户名</label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "merchant.ftpUser" 'class="form-control" placeholder="请输入FTP用户名"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>FTP密码</label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "merchant.ftpPwd" 'class="form-control" placeholder="请输入FTP密码"'/>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="row form-group">
            <div class="col-md-3 control-label">
                <label>FTP目录</label>
            </div>
            <div class="col-md-7 controls">
                <@s.formInput "merchant.ftpDir" 'class="form-control" placeholder="请输入FTP目录"'/>
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

<button class="btn btn-sm btn-inverse" id="submit" data-loading-text="正在保存..." data-toggle="form-submit" data-target="#modal-form">
    <i class="ace-icon fa fa-check"></i>
    <@s.message "app.button.save"/>
</button>

<script src="${ctx}/static/app/js/dashboard/system/merchant/form-modal.js"></script>
</@override>

<@extends name="../../../modal-layout.ftl"/>