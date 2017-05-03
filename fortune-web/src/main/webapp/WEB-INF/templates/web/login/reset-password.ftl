<#assign ctx="${(rca.contextPath)!''}">

<@override name="content">
<div class="position-relative">
    <#if message??>
        <h3>${message}</h3>
        <div class="space-28"></div>
        <div class="center">
            <a href="#reset" class="forgot-password-link">
                <i class="ace-icon fa fa-arrow-left"></i>
                重新找回密码
            </a>
        </div>
    <#else>
        <div id="forgot-box" class="forgot-box widget-box fa-border">
            <div class="widget-body">
                <div class="widget-main clearfix">
                    <h4 class="header lighter bigger">
                        <i class="ace-icon fa fa-key"></i>
                        重置密码<small class="red">${user.email}</small>
                    </h4>

                    <div class="space-14"></div>

                    <form id="reset-password-form" action="${ctx}/reset/password" method="post">
                        <input type="hidden" name="code" value="${token.code}"/>
                        <input type="hidden" name="userId" value="${user.id}"/>
                        <fieldset>
                            <label class="block clearfix">
                                <span class="block input-icon input-icon-right">
                                <input type="password" id="password" name="password" class="form-control"
                                       placeholder="密码:6至20位的字母数字组合"/>
                                    <i class="ace-icon fa fa-key"></i>
                                </span>
                            </label>

                            <div class="space-10"></div>

                            <label class="block clearfix">
                                <span class="block input-icon input-icon-right">
                                <input type="password" id="rePassword" name="rePassword" class="form-control"
                                       placeholder="确认密码:6至20位的字母数字组合"/>
                                    <i class="ace-icon fa fa-key"></i>
                                </span>
                            </label>

                            <div class="space-10"></div>

                            <div class="clearfix">
                                <button id="submit" class="pull-right btn btn-sm btn-inverse"
                                        data-loading-text="正在提交...">
                                    <i class="ace-icon fa fa-check"></i>
                                    <@spring.message "app.button.save"/>
                                </button>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </#if>
</div>
</@override>

<@override name="script">
<script src="${ctx}/static/app/js/web/auth/reset-password.js"></script>
</@override>

<@extends name="../auth-layout.ftl"/>