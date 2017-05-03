<#assign ctx="${(rca.contextPath)!''}">

<@override name="content">
<div class="position-relative">
    <div id="forgot-box" class="forgot-box widget-box fa-border">
        <div class="widget-body">
            <div class="widget-main clearfix">
                <h4 class="header lighter bigger skin-color">
                    <i class="ace-icon fa fa-key skin-color"></i>
                    找回密码
                </h4>

                <div class="space-14"></div>

                <form id="reset-form" method="post" action="${ctx}/reset">
                    <div class="form-group clearfix">
                        <label class="col-xs-12 control-label no-padding-right">输入邮箱找回密码</label>
                        <div class="col-xs-12">
                            <span class="block input-icon input-icon-right">
                                <input type="text" id="email" name="email" class="form-control" placeholder="请输入邮箱"/>
                                <i class="ace-icon fa fa-envelope"></i>
                                <label class="error hide" for="email"></label>
                            </span>
                        </div>
                    </div>

                    <div class="space-14"></div>

                    <div class="form-group clearfix">
                        <label class="col-xs-12 control-label no-padding-right">验证码</label>
                        <div class="col-xs-7">
                        <span class="block input-icon input-icon-right">
                            <input value="" type="text" id="captcha" name="captcha" class="form-control"
                                   placeholder="请输入4位数字的验证码" autocomplete="off">
                            <i class="ace-icon fa fa-times-circle hide"></i>
                            <label class="error hide" for="captcha"></label>
                        </span>
                        </div>
                        <div class="col-xs-5">
                            <img id="captcha-img" onclick="this.src='${ctx}/captcha?' + Math.random();"
                                 src="${ctx}/captcha"/>
                        </div>
                    </div>

                    <div class="space-14"></div>

                    <div class="col-xs-4 col-xs-offset-8">
                        <button id="submit" class="btn btn-sm width-100 btn-inverse" data-loading-text="正在提交...">发送</button>
                    </div>
                </form>
            </div>

            <div class="toolbar center">
                <a href="#login" data-target="#login-box" class="back-to-login-link">
                    去登录
                    <i class="ace-icon fa fa-arrow-right"></i>
                </a>
            </div>
        </div>
    </div>
</div>
</@override>

<@override name="script">
<script src="${ctx}/static/app/js/web/auth/reset.js"></script>
</@override>

<@extends name="../auth-layout.ftl"/>