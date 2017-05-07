<#assign ctx="${(rca.contextPath)!''}">
<#assign merchCo = RequestParameters.merchCo!'' />

<div class="page-header">
    <h1>
        缓存列表
        <small class="pull-right">
            <a href="${ctx}/dashboard/system/cache/clearall?merchCo=${merchCo}" id="clearall" class="btn btn-sm btn-danger">清空列表</a>
        </small>
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get" novalidate>
    <div class="form-group">
        <input type="text" name="merchCo" value="${merchCo}" class="form-control" placeholder="商户号"/>
    </div>

    <button class="btn btn-sm btn-inverse" data-toggle="search-submit">
        搜索
        <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
    </button>
</form>

<div class="space-10"></div>

<table id="cache-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>商户号</th>
        <th>键</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if keys?size gt 0>
        <#list keys as key>
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

<script src="${ctx}/static/app/js/dashboard/system/cache/list.js"></script>
