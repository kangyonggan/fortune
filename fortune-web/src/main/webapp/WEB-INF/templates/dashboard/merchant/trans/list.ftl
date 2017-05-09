<#assign ctx="${(rca.contextPath)!''}">

<div class="page-header">
    <h1>
        交易类型列表
        <small class="pull-right">
            <a href="${ctx}/dashboard/merchant/trans/create" class="btn btn-sm btn-inverse" data-toggle="modal" data-target="#myModal"
               data-backdrop="static">添加</a>
        </small>
    </h1>
</div>

<div class="space-10"></div>

<table id="trans-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>交易码</th>
        <th>交易名称</th>
        <th>单笔限额</th>
        <th>日累计限额</th>
        <th>交易暂停</th>
        <th>逻辑删除</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if transs?size gt 0>
        <#list transs as trans>
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

<script src="${ctx}/static/app/js/dashboard/merchant/trans/list.js"></script>
