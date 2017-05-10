<#assign ctx="${(rca.contextPath)!''}">
<#assign startDate = RequestParameters.startDate!'' />
<#assign endDate = RequestParameters.endDate!'' />
<#assign protocolNo = RequestParameters.protocolNo!'' />
<#assign acctNo = RequestParameters.acctNo!'' />

<link rel="stylesheet" href="${ctx}/static/ace/dist/css/datepicker.min.css"/>

<div class="page-header">
    <h1>
        协议列表
    </h1>
</div>

<div class="space-10"></div>

<form class="form-inline" method="get">
    <div class="form-group">
        <div class="input-group">
            <input class="form-control date-picker" value="${startDate}" id="startDate" name="startDate" type="text"
                   data-date-format="yyyy-mm-dd" placeholder="开始日期"/>
            <span class="input-group-addon">
                <i class="fa fa-calendar bigger-110"></i>
            </span>
        </div>
    </div>
    <div class="form-group">
        <div class="input-group">
            <input class="form-control date-picker" value="${endDate}" id="endDate" name="endDate" type="text"
                   data-date-format="yyyy-mm-dd" placeholder="结束日期"/>
            <span class="input-group-addon">
                <i class="fa fa-calendar bigger-110"></i>
            </span>
        </div>
    </div>
    <div class="form-group">
        <input type="text" name="protocolNo" value="${protocolNo}" class="form-control" autocomplete="off"
               placeholder="按协议号过滤"/>
    </div>

    <div class="form-group">
        <input type="text" name="acctNo" value="${acctNo}" class="form-control" autocomplete="off" placeholder="按卡号过滤"/>
    </div>

    <button class="btn btn-sm btn-inverse" data-toggle="search-submit">
        搜索
        <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
    </button>
</form>

<div class="space-10"></div>

<table id="protocol-table" class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th>协议号</th>
        <th>卡号</th>
        <th>户名</th>
        <th>手机号</th>
        <th>证件类型</th>
        <th>证件号</th>
        <th>是否解约</th>
        <th>协议有效期</th>
        <th>创建时间</th>
    </tr>
    </thead>
    <tbody>
    <#if page.list?size gt 0>
        <#list page.list as protocol>
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
<@c.pagination url="#merchant/protocol" param="startDate=${startDate}&endDate=${endDate}&protocolNo=${protocolNo}&acctNo=${acctNo}"/>

<script src="${ctx}/static/ace/dist/js/date-time/bootstrap-datepicker.min.js"></script>
<script src="${ctx}/static/app/js/dashboard/merchant/protocol/list.js"></script>
