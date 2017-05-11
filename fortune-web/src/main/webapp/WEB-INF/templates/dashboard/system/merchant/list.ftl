<#assign ctx="${(rca.contextPath)!''}">
<#assign merchCo = RequestParameters.merchCo!'' />
<#assign merchNm = RequestParameters.merchNm!'' />

<div class="page-header">
    <h1>
        商户列表
        <small class="pull-right">
            <a href="${ctx}/dashboard/system/merchant/create" class="btn btn-sm btn-inverse" data-toggle="modal" data-target="#myModal"
               data-backdrop="static">添加</a>
        </small>
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get">
    <div class="form-group">
        <input type="text" class="form-control" name="merchCo" value="${merchCo}" placeholder="商户号"
               autocomplete="off"/>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="merchNm" value="${merchNm}" placeholder="商户名称"
               autocomplete="off"/>
    </div>

    <button class="btn btn-sm btn-inverse" data-toggle="search-submit">
        搜索
        <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
    </button>
</form>

<div class="space-10"></div>

<table id="merchant-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>商户名</th>
        <th>商户名称</th>
        <th>字符集</th>
        <th>清算模式</th>
        <th>调试模式</th>
        <th>逻辑删除</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?size gt 0>
        <#list page.list as merchant>
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
<@c.pagination url="#system/merchant" param="merchCo=${merchCo}&merchNm=${merchNm}"/>

<script src="${ctx}/static/app/js/dashboard/system/merchant/list.js"></script>
