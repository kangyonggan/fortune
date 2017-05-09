<#assign ctx="${(rca.contextPath)!''}">
<#assign startDate = RequestParameters.startDate!'' />
<#assign endDate = RequestParameters.endDate!'' />
<#assign tranSt = RequestParameters.tranSt!'' />

<div class="page-header">
    <h1>
        交易列表
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get">
    <div class="form-group">
        <input type="text" class="form-control" name="startDate" value="${startDate}" placeholder="开始日期"
               autocomplete="off"/>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="endDate" value="${endDate}" placeholder="结束日期"
               autocomplete="off"/>
    </div>
    <div class="form-group">
        <select name="tranSt" class="form-control">
            <option value="">全部交易</option>
        <#list allTrans as t>
            <option value="${t.code}" <#if t.code=tranSt>selected</#if>>${t.value}</option>
        </#list>
        </select>
    </div>

    <button class="btn btn-sm btn-inverse" data-toggle="search-submit">
        搜索
        <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
    </button>
</form>

<div class="space-10"></div>

<table id="trans-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>商户流水号</th>
        <th>发财付流水号</th>
        <th>协议号</th>
        <th>交易类型</th>
        <th>请求日期</th>
        <th>请求时间</th>
        <th>交易金额</th>
        <th>交易状态</th>
        <th>交易时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?size gt 0>
        <#list page.list as command>
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
<@c.pagination url="#merchant/command" param="startDate=${startDate}&endDate=${endDate}&tranSt=${tranSt}"/>

<script src="${ctx}/static/app/js/dashboard/merchant/command/list.js"></script>
