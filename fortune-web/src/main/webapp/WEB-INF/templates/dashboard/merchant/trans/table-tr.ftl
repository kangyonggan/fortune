<#assign ctx="${(rca.contextPath)!''}">

<tr id="trans-${trans.tranCo}">
    <td>${trans.tranCo}</td>
    <td>${trans.tranNm}</td>
    <td><#if trans.singQuota==-1>无限额<#else>${trans.singQuota}元</#if></td>
    <td><#if trans.dateQuota==-1>无限额<#else>${trans.dateQuota}元</#if></td>
    <td><#if trans.isPaused==1><span class="red">已暂停</span><#else><span class="green">正常</span></#if></td>
    <td><#include "delete.ftl"></td>
    <td><@c.relative_date datetime=trans.createdTime/></td>
    <td>
        <div class="btn-group">
            <a data-toggle="modal" class="btn btn-xs btn-inverse"
               href="${ctx}/dashboard/merchant/trans/${trans.tranCo}/edit"
               data-target="#myModal">编辑</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="javascript:" data-role="trans-remove" title="删除银行卡"
                       data-url="${ctx}/dashboard/merchant/trans/${trans.tranCo}/remove">
                        永久删除
                    </a>
                </li>
            </ul>
        </div>
    </td>
</tr>