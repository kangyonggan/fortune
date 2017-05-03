<#assign ctx="${(rca.contextPath)!''}">

<@override name="content">
<h1 class="center green">
    ${message!''}
</h1>

<div class="space-20"></div>

<h4 class="text-success center">
    正在跳转到登录页...
    <a id="jump-btn" class="center" href="#login">
        点此直接进入
    </a>
</h4>
</@override>

<@override name="script">
<script src="${ctx}/static/app/js/web/auth/reset-result.js"></script>
</@override>

<@extends name="../auth-layout.ftl"/>