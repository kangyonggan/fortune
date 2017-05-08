<#assign ctx="${(rca.contextPath)!''}">

<div class="page-header">
    <h1>
        账户列表
        <small class="pull-right">
            <a href="${ctx}/dashboard/merchant/acct/create" class="btn btn-sm btn-inverse" data-toggle="modal" data-target="#myModal"
               data-backdrop="static">添加</a>
        </small>
    </h1>
</div>

<div class="space-10"></div>

<table id="merchAcct-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>卡号</th>
        <th>户名</th>
        <th>证件类型</th>
        <th>证件号</th>
        <th>手机号</th>
        <th>余额</th>
        <th>主卡</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if merchAccts?size gt 0>
        <#list merchAccts as merchAcct>
            <#include "table-tr.ftl"/>
        </#list>
    <#else>
    <tr>
        <td colspan="20">
            <div class="empty">暂无查询记录</div>
        </td>
    </tr>
    </#if>
    </tbody>
</table>

<script src="${ctx}/static/app/js/dashboard/merchant/acct/list.js"></script>
