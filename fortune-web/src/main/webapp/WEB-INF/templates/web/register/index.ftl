<#assign ctx="${(rca.contextPath)!''}">

<@override name="content">
<div id="signup-box" class="signup-box widget-box no-border visible">
    <div class="widget-body">
        <div class="widget-main">
            <h4 class="header dark lighter bigger">
                <i class="ace-icon fa fa-users dark"></i>
                新用户注册
            </h4>

            <div class="space-6"></div>

            <form id="register-form" action="${ctx}/register" method="post">
                <fieldset>
                    <label class="block clearfix">
                        <span class="block input-icon input-icon-right">
                        <input type="text" id="username" name="username" class="form-control"
                               placeholder="用户名:5至20位以字母开头的小写字母和数字的组合"/>
                            <i class="ace-icon fa fa-user"></i>
                        </span>
                    </label>

                    <div class="space-10"></div>

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

                    <label class="block clearfix">
                        <span class="block input-icon input-icon-right">
                            <input type="text" name="fullname" class="form-control"
                                   placeholder="姓名:2至4个汉字"/>
                            <i class="ace-icon fa fa-leaf"></i>
                        </span>
                    </label>

                    <div class="space-10"></div>

                    <label class="block clearfix">
                        <span class="block input-icon input-icon-right">
                            <input type="text" name="captcha" class="col-xs-6" placeholder="请输入4位数字的验证码"/>
                            <img onclick="this.src='${ctx}/captcha?'+Math.random();" src="${ctx}/captcha">
                        </span>
                    </label>

                    <div class="space-16"></div>

                    <label class="block">
                        <input type="checkbox" name="agree" class="ace">
                        <span class="lbl">
                            我已阅读并同意
                            <a href="${ctx}/#content/1" target="_blank">《注册协议》</a>
                        </span>
                    </label>

                    <div class="space-10"></div>

                    <div class="clearfix">
                        <button type="reset" class="width-30 pull-left btn btn-sm">
                            <i class="ace-icon fa fa-refresh"></i>
                            重置
                        </button>
                        <button id="submit" type="submit" class="width-65 pull-right btn btn-sm btn-inverse"
                                data-loading-text="注册中...">
                            注册并登陆
                            <i class="ace-icon fa fa-arrow-right icon-on-right"></i>
                        </button>
                    </div>
                </fieldset>
            </form>
        </div>

        <div class="toolbar center">
            <a href="#login" class="back-to-login-link">
                <i class="ace-icon fa fa-arrow-left"></i>
                已有账号,去登录
            </a>
        </div>
    </div>
</div>
</@override>

<@override name="script">
<script src="${ctx}/static/app/js/web/auth/register.js"></script>
</@override>

<@extends name="../auth-layout.ftl"/>