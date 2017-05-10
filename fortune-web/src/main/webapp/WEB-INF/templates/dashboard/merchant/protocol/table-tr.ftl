<#assign ctx="${(rca.contextPath)!''}">

<tr id="protocol-${protocol.id}">
    <td>${protocol.protocolNo}</td>
    <td>${protocol.acctNo}</td>
    <td>${protocol.acctNm}</td>
    <td>${protocol.mobile}</td>
    <td><#include "id-tp.ftl">(${protocol.idTp})</td>
    <td>${protocol.idNo}</td>
    <td>${(protocol.isUnsign==1)?string('已解约', '正常')}</td>
    <td>${protocol.expiredTime?datetime}</td>
    <td><@c.relative_date datetime=protocol.createdTime/></td>
</tr>