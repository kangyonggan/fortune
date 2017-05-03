<#assign ctx="${(rca.contextPath)!''}">
<#assign template = RequestParameters.template!'' />
<#assign title = RequestParameters.title!'' />

<div class="page-header">
    <h1>
        内容列表
        <small class="pull-right">
            <a href="#content/content/create" class="btn btn-sm btn-inverse">添加</a>
        </small>
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get" novalidate>
    <div class="form-group">
        <select name="template" class="form-control">
            <option value="">全部模板</option>
        <#list templates as t>
            <option value="${t.code}" <#if template=='${t.code}'>selected</#if>>${t.value}</option>
        </#list>
        </select>
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="title" value="${title}" placeholder="标题"/>
    </div>

    <button class="btn btn-sm btn-inverse" data-toggle="search-submit">
        搜索
        <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
    </button>
</form>

<div class="space-10"></div>

<table id="content-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>模板</th>
        <th width="50%">标题</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?size gt 0>
        <#list page.list as content>
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
<@c.pagination url="#content/content" param="template=${template}&title=${title}"/>

<script src="${ctx}/static/app/js/dashboard/content/content/list.js"></script>
