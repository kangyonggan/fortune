<#assign ctx="${(rca.contextPath)!''}">

<tr id="dictionary-${dictionary.code}">
    <td><#include "type.ftl"></td>
    <td>${dictionary.code}</td>
    <td>${dictionary.value}</td>
    <td>${dictionary.sort}</td>
    <td><#include "delete.ftl"></td>
    <td><@c.relative_date datetime=dictionary.createdTime/></td>
    <td>
        <div class="btn-group">
            <a data-toggle="modal" class="btn btn-xs btn-inverse"
               href="${ctx}/dashboard/system/dictionary/${dictionary.id}/edit"
               data-target="#myModal">编辑</a>
        </div>
    </td>
</tr>