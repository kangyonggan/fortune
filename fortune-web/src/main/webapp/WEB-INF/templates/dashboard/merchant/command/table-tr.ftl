<#assign ctx="${(rca.contextPath)!''}">

<tr id="command-${command.id}">
    <td>${command.merchSerialNo}</td>
    <td>${command.fpaySerialNo}</td>
    <td>${command.protocolNo}</td>
    <td><#include "tran-co.ftl">(${command.tranCo})</td>
    <td>${command.reqDate}</td>
    <td>${command.reqTime}</td>
    <td>${command.amount}元</td>
    <td><#include "tran-st.ftl">(${command.tranSt})</td>
    <td><@c.relative_date datetime=command.createdTime/></td>
    <td>
        <div class="btn-group">
            <a data-toggle="modal" class="btn btn-xs btn-inverse" href="${ctx}/dashboard/merchant/command/${command.id}"
               data-target="#myModal">详情</a>
        </div>
    </td>
</tr>