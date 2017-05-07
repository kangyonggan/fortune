<#assign ctx="${(rca.contextPath)!''}">

<tr id="merchant-${merchant.id}">
    <td>${merchant.merchCo}</td>
    <td>${merchant.merchNm}</td>
    <td>${merchant.charset}</td>
    <td><#include "debug.ftl"></td>
    <td><#include "delete.ftl"></td>
    <td><@c.relative_date datetime=merchant.createdTime/></td>
    <td>
        <div class="btn-group">
            <a data-toggle="modal" class="btn btn-xs btn-inverse" href="${ctx}/dashboard/system/merchant/${merchant.merchCo}"
               data-target="#myModal">查看</a>

            <button data-toggle="dropdown" class="btn btn-xs btn-inverse dropdown-toggle">
                <span class="ace-icon fa fa-caret-down icon-only"></span>
            </button>

            <ul class="dropdown-menu dropdown-menu-right dropdown-inverse">
                <li>
                    <a href="${ctx}/dashboard/system/merchant/${merchant.merchCo}/edit" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">编辑</a>
                </li>
                <li>
                    <a href="${ctx}/dashboard/system/merchant/${merchant.merchCo}/password" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">修改密码</a>
                </li>
                <li>
                    <a href="${ctx}/dashboard/system/merchant/${merchant.merchCo}/roles" data-toggle="modal" data-target="#myModal"
                       data-backdrop="static">设置角色</a>
                </li>
            </ul>
        </div>
    </td>
</tr>